/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.StarSchema;

import java.sql.ResultSet;
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
 
    
}
