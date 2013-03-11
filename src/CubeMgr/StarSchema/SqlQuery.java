/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.StarSchema;

import java.util.ArrayList;

import TaskMgr.ExtractionMethod;

/**
 *
 * @author Asterix
 */
public class SqlQuery extends ExtractionMethod {
    
    public String SelectClauseMeasure;  /* AggregateFuncName~field */  
    public ArrayList<String> FromClause;
    public ArrayList<String> WhereClause;
    public ArrayList<String> GroupByClause;
    
    public String getSelectClause(){
        return "";
    }
    
    public SqlQuery(){
    	SelectClauseMeasure="";
    	FromClause=new ArrayList<>();
    	WhereClause=new ArrayList<>();
    	GroupByClause=new ArrayList<>();
    }
    
    public SqlQuery(String Aggregatefunc,ArrayList<String> Tables,ArrayList<String> Conditions,ArrayList<String> GroupAttr){
    	SelectClauseMeasure=Aggregatefunc;
        FromClause=Tables;
        WhereClause=Conditions;
        GroupByClause=GroupAttr;
        
    }
    
    public void printQuery(){
    	System.out.print("SELECT ");
    	for(String x : GroupByClause ) System.out.print(x+",");
    	System.out.print(SelectClauseMeasure);
    	System.out.println();
    	System.out.print ("FROM ");
    	for(String x : FromClause ) System.out.print(x+",");
    	System.out.println();
    	System.out.print ("WHERE ");
    	for(String x : WhereClause ) System.out.print(x+" AND ");
    	System.out.println();
    	System.out.print ("GROUP BY ");
    	for(String x : GroupByClause ) System.out.print(x+",");
    	
    }
    /*set add function*/
    
   /* public void addSelectAttribute(String attr){
        this.selectAttribute.add(attr);
    }
    
    public void setAggregateFunc(String func){
        this.aggregateFunc=func;
    }
    
    public void addGroupAttribute(String attr){
        this.groupAttribute.add(attr);
    }
    
    public void addTable(String table){
        this.fromTables.add(table);
    }
    
    public void addCondition(String con){
        this.condition.add(con);
    }
    
    /*get add function*/
    /*
    public String getSelectAttribute(int i){
        return this.selectAttribute.get(i);
    }
    
    public List<String> getAllSelectAttribute(){
        return this.selectAttribute;
    }
    
    public String getAggregateFunc(){
        return this.aggregateFunc;
    }
    
    public String getGroupAttribute(int i){
        return this.groupAttribute.get(i);
    }
    
    public List<String> getAllGroupAttribute(){
        return this.groupAttribute;
    }
    
    public String getTable(int i){
        return this.fromTables.get(i);
    }
    
    public List<String> getAllTable(){
        return this.fromTables;
    }
    
    public String getCondition(int i){
        return this.condition.get(i);
    }
    
    public List<String> getAllCondition(){
        return this.condition;
    }*/
    
}
