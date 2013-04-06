/**
 * 
 */
package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.StarSchema.Database;
import CubeMgr.StarSchema.SqlQuery;

/**
 * @author Asterix
 *
 */
public class TaskBrothers extends Task {

	/**
	 * 
	 */
		
	public TaskBrothers() {
		super();
	}

	public void addNewSubTask(){
    	subTasks.add(new SubTask());
    };
	
	public int getNumSubTasks(){
    	return subTasks.size();
    }
    
    public SubTask getSubTask(int i){
    	return subTasks.get(i);
    }
    
    public SubTask getLastSubTask(){
    	return getSubTask(getNumSubTasks()-1);
    }
    
    public void generateSubTasks(CubeBase cubeBase){
    	SubTask stsk=getLastSubTask();
    	SqlQuery Sbsql=(SqlQuery) stsk.getExtractionMethod();
    	ArrayList<String[]> lst=findBrothers(cubeBase,Sbsql);
    	ArrayList<String> numOffieldsToSummarized=new ArrayList<String>();
    	for(int i=0;i<lst.size();i++){
    		if(numOffieldsToSummarized.contains(lst.get(i)[1])==false){
    			numOffieldsToSummarized.add(lst.get(i)[1]);
    			this.createSummarizedQuery(lst, Sbsql, cubeBase.DB, Integer.parseInt(lst.get(i)[1]));
    		}
    		createSubTask(Sbsql,cubeBase.DB,lst.get(i),null);
    		/*for(int j=i+1;j<lst.size();j++){ //for cousins
    			if(lst.get(i)[1].equals(lst.get(j)[1])==false){
    				createSubTask(Sbsql,cubeBase.DB,lst.get(i),lst.get(j));
    			}
       		}*/
    	}
    	printBorderLine();
    	printBorderLine();
    }
    
    private void createSubTask(SqlQuery Sbsql,Database DB,String [] condA,String [] condB){
    	SubTask sbtsk=new SubTask();
		ArrayList<String []> newWhere=new ArrayList<String []>();
		copyListofArrayString(Sbsql.WhereClause, newWhere);
		newWhere.get(Integer.parseInt(condA[1]))[2]=condA[0];
		
		if(condB!=null){
			if(Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])){
				newWhere.get(Integer.parseInt(condB[1]))[2]=condB[0];
			}
		}
		
		SqlQuery newsql=new SqlQuery(Sbsql.SelectClauseMeasure,Sbsql.FromClause,newWhere,Sbsql.GroupByClause);
		newsql.printQuery();
		sbtsk.setExtractionMethod(newsql);
		sbtsk.addDifferenceFromOrigin(Integer.parseInt(condA[1]));
		
		if(condB!=null){
			if(Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])){
				sbtsk.addDifferenceFromOrigin(Integer.parseInt(condB[1]));
			}
		}
		
		sbtsk.execute(DB);
		subTasks.add(sbtsk);
    }
    
	public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
 
	
	public ArrayList<String[]> findBrothers(CubeBase cubebase, SqlQuery Original) {
		printBorderLine();
		System.out.println("Generated Queries");
		printBorderLine();
		ArrayList<String[]> finds=new ArrayList<>();
		for( int i=0;i<Original.WhereClause.size();i++){
			String[] condition=Original.WhereClause.get(i);
			if(condition[2].contains("'") || tryParseInt(condition[2])){
				String[] tmp2=condition[0].split("\\."); //0->table  ,1--> field name
				String table=tmp2[0];
				for(String[] fromTable : Original.FromClause){
					if(fromTable.length==1 && table.equals(fromTable[0])) break;
					else if(table.equals(fromTable[1]) || table.equals(fromTable[0])) {
							table=fromTable[0];
							
					}
				}
				if(cubebase.returnIfTableIsDimensionTbl(table)) {/*1->Fact Table, 2->DimensionTable*/
					String tmp_query="SELECT DISTINCT "+tmp2[1]+ " FROM "+table+" WHERE "+tmp2[1]+"!="+condition[2];
					ResultSet rs=cubebase.DB.executeSql(tmp_query);
										
					try {
						rs.beforeFirst();
						while(rs.next()){
							String newValue="'"+rs.getString(1)+"'";
							if(tryParseInt(rs.getString(1))) newValue=rs.getString(1);
							String[] toAdd={newValue,Integer.toString(i)};
							finds.add(toAdd);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return finds;
	}
	
	void printSqlQueryArrayList(ArrayList<SqlQuery> toprint){
		for(SqlQuery x : toprint) x.printQuery();
	}
	
	void printStringArrayList(ArrayList<String> toprint){
		printBorderLine();
		for(String x: toprint) {
			System.out.println(x);
		}
		printBorderLine();
	}
	
	boolean tryParseInt(String value){
		boolean ret_value=true;
	    try{
	    	Integer.parseInt(value);
	    }catch(NumberFormatException nfe){  
	          ret_value=false;
	    }  
	     	return ret_value;  
	}
	
	void printBorderLine(){
    	System.out.println("=====================================");
    }
	
	void copyListofArrayString(ArrayList<String[]> from,ArrayList<String[]> to){
		for(int i=0;i<from.size();i++){
			String[] old=from.get(i);
			String[] toadd=new String[old.length];
			for(int j=0;j<old.length;j++){
				toadd[j]=old[j];
			}
			to.add(toadd);
		}
	}
	
	void createSummarizedQuery(ArrayList<String[]> lst,SqlQuery Original,Database DB,int tmp){
		//ArrayList<String> numOffieldsToSummarized=getUniqueValueInListOfArrayString(lst,1);
		System.out.println("Summarized Queries");
		printBorderLine();
		String condConnected=getConnectedCondition(Original.WhereClause,tmp);
			
		ArrayList<String []> newWhere=new ArrayList<String []>();
		ArrayList<String[]> newGrouper=new ArrayList<String[]>();
		copyListofArrayString(Original.GroupByClause, newGrouper);
		copyListofArrayString(Original.WhereClause, newWhere);
		
		for(int j=0;j<newGrouper.size();j++){
			/*System.out.println(newGrouper.get(j)[0]);
			System.out.println("CondConn:"+condConnected);
			//System.out.println(newGrouper.get(j)[0].replace(condConnected, Original.WhereClause.get(tmp)[0]));*/
			if(newGrouper.get(j)[0].equals(condConnected)){
				newGrouper.get(j)[0]=Original.WhereClause.get(tmp)[0];
			}
		}
		
		newWhere.remove(tmp);
		
		SqlQuery newsql=new SqlQuery(Original.SelectClauseMeasure,Original.FromClause,newWhere,newGrouper);
		SubTask sbtsk=new SubTask();
		sbtsk.setExtractionMethod(newsql);
		sbtsk.addDifferenceFromOrigin(-1);
		sbtsk.addDifferenceFromOrigin(tmp);
		sbtsk.execute(DB);
		subTasks.add(sbtsk);
		newsql.printQuery();
	}
	
	ArrayList<String> getUniqueValueInListOfArrayString(ArrayList<String[]> lst,int column){
		ArrayList<String> ret_value=new ArrayList<>();
		for(int i=0;i<lst.size();i++){
			if(ret_value.contains(lst.get(i)[column])==false){
				ret_value.add(new String(lst.get(i)[column]));
			}
		}
		return ret_value;
	}
	
	String getConnectedCondition(ArrayList<String[]> WhereClause,int condTocheckID){
		String[] condTocheck=WhereClause.get(condTocheckID);
		for(int i=0;i<WhereClause.size();i++){
			if(i!=condTocheckID){
				if(WhereClause.get(i)[0].split("\\.")[0].equals(condTocheck[0].split("\\.")[0])){
					return WhereClause.get(i)[2];
				}
				if(WhereClause.get(i)[2].split("\\.")[0].equals(condTocheck[0].split("\\.")[0])){
					return WhereClause.get(i)[0];
				}
			}
		}
		return null;
	}
}
