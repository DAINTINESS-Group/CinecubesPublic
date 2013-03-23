/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.StarSchema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import TaskMgr.ExtractionMethod;
import TaskMgr.Result;

/**
 *
 * @author Asterix
 */
public class SqlQuery extends ExtractionMethod {
    
    public String SelectClauseMeasure;  	/* AggregateFuncName~field */  
    public ArrayList<String> FromClause; 	/* TABLE~customName */
    public ArrayList<String> WhereClause;	/* sqlfld1~op~sqlfld2 */
    public ArrayList<String> GroupByClause;
    
    
    public SqlQuery(){
    	init();
    }
    
    private void init(){
    	SelectClauseMeasure="";
    	FromClause=new ArrayList<>();
    	WhereClause=new ArrayList<>();
    	GroupByClause=new ArrayList<>();
    }
    
    public SqlQuery(String Aggregatefunc,ArrayList<String> Tables,ArrayList<String> Conditions,ArrayList<String> GroupAttr){
    	init();
    	SelectClauseMeasure=Aggregatefunc;
        FromClause.addAll(Tables);
        WhereClause.addAll(Conditions);
        GroupByClause.addAll(GroupAttr);
        
    }
    
    public String getSelectClause(){
    	String ret_value="";
    	String[] aggregate=SelectClauseMeasure.split("~");
    	
    	ret_value+=getGroupClause()+",";
    	ret_value+= aggregate[0]+"("+aggregate[1]+") ";
    	return ret_value;
    }
       
    public String getFromClause(){
    	int i=0;
    	String ret_value="";
    	
    	for(String x : FromClause ) {    
    		if(i>0) ret_value+=",";
    		ret_value+=mergeSplittingString(x,"~");
    		i++;
    	}
    	return ret_value;
    }
    
    public String getWhereClause(){
    	int i=0;
    	String ret_value="";
    	
    	for(String x : WhereClause ) {    
    		if(i>0) ret_value+=" AND ";
    		ret_value+=mergeSplittingString(x,"~");
    		i++;
    	}
    	return ret_value;
    }
    
    public String getGroupClause(){
    	String ret_value="";
    	int i=0;
    	
    	for(String x : GroupByClause ){
    		if(i>0) ret_value+=",";
    		ret_value+=x;
    		i++;
    	}
    	
    	return ret_value;
    }
    
    public void printQuery(){
    	System.out.println(returnQuery());
    	printBorderLine();
    }
    
    
    void printBorderLine(){
    	System.out.println("=====================================");
    }
    /*
     *  
     */
    public String returnQuery(){
    	String ret_value="SELECT "+getSelectClause();
    	ret_value+="\nFROM "+ getFromClause();
    	ret_value+="\nWHERE "+getWhereClause();
    	ret_value+="\nGROUP BY "+getGroupClause();
    	return ret_value ;
    }
    
    private String mergeSplittingString( String toSplit, String SplitValue){
    	String ret_value="";
    	String[] tmp=toSplit.split(SplitValue);
    	for(int i=0;i<tmp.length;i++){
    		ret_value+=" "+tmp[i];
    	}
    	return ret_value;
    }
    
    public void setResult(ResultSet result){
    	Res=new Result();
    	Res.createResultArray(result);
    }

	@Override
	public Result getResult() {
		return this.Res;
	}

	@Override
	public void findBrothers(Database DB) {
		printBorderLine();
		System.out.println("Generated Queries");
		printBorderLine();
		ArrayList<String> finds=new ArrayList<>();
		for(String x : WhereClause){
			String [] tmp=x.split("~");
			if(tmp[2].contains("'") || tryParseInt(tmp[2])){
				String[] tmp2=tmp[0].split("\\."); //0->table  ,1--> field name
				String table=tmp2[0];
				for(String y : FromClause){
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
		generateQueries(finds);
		//printArrayList(finds);
	}
	
	private void generateQueries(ArrayList<String> finds) {
		ArrayList<SqlQuery> lstsql=new ArrayList<>();
		ArrayList<String> tmp=new ArrayList<>();
		for(String x:finds){
			tmp.addAll(WhereClause);
			int j=0;
			for(String y:WhereClause){
				//System.out.println(y+"||||"+x.split("@")[1]+"=>"+(y==x.split("@")[1]));
				if(y.equals(x.split("@")[1])){
					String[] tmp_tbl=y.split("~");
					if(tryParseInt(x.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+x.split("@")[0]);
					else tmp.set(j, tmp_tbl[0]+"~"+"='"+x.split("@")[0]+"'");
				}
				j++;
			}
			lstsql.add(new SqlQuery(this.SelectClauseMeasure, this.FromClause, tmp, this.GroupByClause));
			tmp.clear();
		}
		for(String x:finds){
			tmp.clear();
			tmp.addAll(WhereClause);
			for(String y:finds){
				if(x.equals(y)==false && x.split("@")[0].equals(y.split("@")[0])==false){
					int j=0;
					for(String w:WhereClause){
						//System.out.println(y+"||||"+x.split("@")[1]+"=>"+(y==x.split("@")[1]));
						if(w.equals(x.split("@")[1])){
							String[] tmp_tbl=w.split("~");
							if(tryParseInt(x.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+x.split("@")[0]);
							else tmp.set(j, tmp_tbl[0]+"~"+"='"+x.split("@")[0]+"'");
						}
						else if(w.equals(y.split("@")[1])){
							String[] tmp_tbl=w.split("~");
							if(tryParseInt(y.split("@")[0])) tmp.set(j, tmp_tbl[0]+"~"+"="+y.split("@")[0]);
							else tmp.set(j, tmp_tbl[0]+"~"+"='"+y.split("@")[0]+"'");
						}
						j++;
					}
				}
			}
			SqlQuery sql_tmp=new SqlQuery(this.SelectClauseMeasure, this.FromClause, tmp, this.GroupByClause);
			boolean addTo=true;
			for(SqlQuery s:lstsql) {
				if(s.WhereClause.equals(tmp)) addTo=false;
			}
			if(addTo) lstsql.add(sql_tmp);
		}
		printSqlQueryArrayList(lstsql);
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
	
	boolean tryParseInt(String value)  
	{  
	     try  
	     {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch(NumberFormatException nfe)  
	      {  
	          return false;  
	      }  
	}
    
    
}
