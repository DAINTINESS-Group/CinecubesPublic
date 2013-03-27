/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CubeMgr.StarSchema.Database;
import CubeMgr.StarSchema.SqlQuery;
/**
 *
 * @author Asterix
 */
public class Task {
    private ArrayList<SubTask> subTasks;
    
    
    public Task(){
    	setSubTasks(new ArrayList<SubTask>());
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
    	ArrayList<ArrayList<String>> lst=findBrothers(DB,(SqlQuery) stsk.getExtractionMethod());
    	SqlQuery Sbsql=(SqlQuery) stsk.getExtractionMethod();
    	for(int i=0;i<lst.size();i++){
    		SubTask sbtsk=new SubTask();
    		sbtsk.setExtractionMethod(new SqlQuery(Sbsql.SelectClauseMeasure,Sbsql.FromClause,lst.get(i),Sbsql.GroupByClause));
    		sbtsk.execute(DB);
    		subTasks.add(sbtsk);
    	}
    }
        
	public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
 
	
	public ArrayList<ArrayList<String>> findBrothers(Database DB, SqlQuery Original) {
		printBorderLine();
		System.out.println("Generated Queries");
		printBorderLine();
		ArrayList<String> finds=new ArrayList<>();
		for(String x : Original.WhereClause){
			String [] tmp=x.split("~");
			if(tmp[2].contains("'") || tryParseInt(tmp[2])){
				String[] tmp2=tmp[0].split("\\."); //0->table  ,1--> field name
				String table=tmp2[0];
				for(String y : Original.FromClause){
					String[] tmp3=y.split("~");
					
					if(tmp3.length==1 && table.equals(tmp3[0])) break;
					else if(table.equals(tmp3[1]) || table.equals(tmp3[0])) {
							table=tmp3[0];
							
					}
				}
				String tmp_query="SELECT DISTINCT "+tmp2[1]+ " FROM "+table+" WHERE "+tmp2[1]+"!="+tmp[2];
				ResultSet rs=DB.executeSql(tmp_query);
				
				try {
					while(rs.next()){
						finds.add(rs.getString(1)+"@"+x);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return generateQueries(finds,Original);
	}
	
	private ArrayList<ArrayList<String>> generateQueries(ArrayList<String> finds, SqlQuery Original) {
		ArrayList<SqlQuery> lstsql=new ArrayList<SqlQuery>();
		ArrayList<String> tmp=new ArrayList<>();
		for(String x:finds){
			tmp.addAll(Original.WhereClause);
			int j=0;
			for(String y:Original.WhereClause){
				//System.out.println(y+"||||"+x.split("@")[1]+"=>"+(y==x.split("@")[1]));
				if(y.equals(x.split("@")[1])){
					String[] tmp_tbl=y.split("~");
					if(tryParseInt(x.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+x.split("@")[0]);
					else tmp.set(j, tmp_tbl[0]+"~"+"='"+x.split("@")[0]+"'");
				}
				j++;
			}
			//lstsql.add(new SqlQuery(this.SelectClauseMeasure, this.FromClause, tmp, this.GroupByClause));
			tmp.clear();
		}
		for(String x:finds){
			tmp.clear();
			tmp.addAll(Original.WhereClause);
			for(String y:finds){
				if(x.equals(y)==false && x.split("@")[0].equals(y.split("@")[0])==false){
					int j=0;
					for(String w:Original.WhereClause){
						//System.out.println(y+"||||"+x.split("@")[1]+"=>"+(y==x.split("@")[1]));
						if(w.equals(x.split("@")[1])){
							String[] tmp_tbl=w.split("~");
							if(tryParseInt(x.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+x.split("@")[0]);
							else tmp.set(j, tmp_tbl[0]+"~"+"='"+x.split("@")[0]+"'");
							createNewBrotherQuery(lstsql, tmp,Original);
						}
						else if(w.equals(y.split("@")[1])){
							String[] tmp_tbl=w.split("~");
							if(tryParseInt(y.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+y.split("@")[0]);
							else tmp.set(j, tmp_tbl[0]+"~"+"='"+y.split("@")[0]+"'");
							createNewBrotherQuery(lstsql, tmp,Original);
						}
						j++;
					}
				}
			}
			
		}
		printSqlQueryArrayList(lstsql);
		
		ArrayList<ArrayList<String>> ret_value ;
		ret_value=new ArrayList<ArrayList<String>>();
		for(int i=0;i<lstsql.size();i++){
			ret_value.add(lstsql.get(i).WhereClause);
		}
		
		return ret_value;
	}
	
	void createNewBrotherQuery(ArrayList<SqlQuery> lstsql,ArrayList<String> tmp,SqlQuery Original){
		SqlQuery sql_tmp=new SqlQuery(Original.SelectClauseMeasure, Original.FromClause, tmp, Original.GroupByClause);
		boolean addTo=true;
		for(SqlQuery s:lstsql) {
			if(s.WhereClause.equals(tmp)) addTo=false;
		}
		if(addTo) lstsql.add(sql_tmp);
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
	
}
