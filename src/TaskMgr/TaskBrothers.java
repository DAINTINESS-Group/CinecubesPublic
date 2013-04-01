/**
 * 
 */
package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    
    public void generateSubTasks(Database DB){
    	SubTask stsk=getLastSubTask();
    	SqlQuery Sbsql=(SqlQuery) stsk.getExtractionMethod();
    	ArrayList<String[]> lst=findBrothers(DB,Sbsql);
    	
    	for(int i=0;i<lst.size();i++){
    		createSubTask(Sbsql,DB,lst.get(i),null);
    		for(int j=i;j<lst.size();j++){
    			if(j!=i  && lst.get(i)[1]!=lst.get(j)[1]){
    				createSubTask(Sbsql,DB,lst.get(i),lst.get(j));
    			}
       		}
    	}
    	printBorderLine();
    	printBorderLine();
    	Sbsql.printQuery();
    }
    
    private void createSubTask(SqlQuery Sbsql,Database DB,String [] condA,String [] condB){
    	SubTask sbtsk=new SubTask();
		ArrayList<String []> newWhere=new ArrayList<String []>();
		CopyWhereClause(Sbsql.WhereClause, newWhere);
		newWhere.get(Integer.parseInt(condA[1]))[2]=condA[0];
		if(condB!=null && Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])) newWhere.get(Integer.parseInt(condB[1]))[2]=condB[0];
		SqlQuery newsql=new SqlQuery(Sbsql.SelectClauseMeasure,Sbsql.FromClause,newWhere,Sbsql.GroupByClause);
		newsql.printQuery();
		sbtsk.setExtractionMethod(newsql);
		sbtsk.addDifferenceFromOrigin(Integer.parseInt(condA[1]));
		if(condB!=null && Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])) sbtsk.addDifferenceFromOrigin(Integer.parseInt(condB[1]));
		sbtsk.execute(DB);
		subTasks.add(sbtsk);
    }
    
	public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
 
	
	public ArrayList<String[]> findBrothers(Database DB, SqlQuery Original) {
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
				String tmp_query="SELECT DISTINCT "+tmp2[1]+ " FROM "+table+" WHERE "+tmp2[1]+"!="+condition[2];
				ResultSet rs=DB.executeSql(tmp_query);
				
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
	
	void CopyWhereClause(ArrayList<String[]> from,ArrayList<String[]> to){
		for(int i=0;i<from.size();i++){
			String[] old=from.get(i);
			String[] toadd=new String[old.length];
			for(int j=0;j<old.length;j++){
				toadd[j]=old[j];
			}
			to.add(toadd);
		}
	}
}
