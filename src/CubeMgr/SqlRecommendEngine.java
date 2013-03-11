/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr;

import CubeMgr.StarSchema.SqlQuery;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Asterix
 */
public abstract class SqlRecommendEngine {
    
    private String dburl=null;
    private String dbClass=null;
    private SqlQuery firstQuery=null;
    private String[][] firstResult=null;
    private List<SqlQuery> RecommentQueries=null;
    private List<String[][]> RecommentResults=null; //maybe must change that
            
    SqlRecommendEngine(){
           RecommentQueries=new ArrayList<>();
           RecommentResults=new ArrayList<>();
           firstQuery=new SqlQuery();
    };
    
    SqlRecommendEngine(SqlQuery query){
        firstQuery=query;
        RecommentQueries=new ArrayList<>();
        RecommentResults=new ArrayList<>();
    }
    
    abstract void CreateConnectionWithDB();
    abstract void GenerateRecommendQueries();
    abstract void GenerateResultbyQuery();
    abstract void GenerateResultsForAll();
    
    public void setFirstQuery(SqlQuery query){
        firstQuery=query;
    }
    
    public void setDBOptions(String DBurl,String DBClass){
        dburl=DBurl;
        dbClass=DBClass;
    }
    
    public SqlQuery getFirstQuery(){
            return firstQuery;
    }
    
    public String[][] getFirstResult(){
        return firstResult;
    }
    
    public List<SqlQuery> getAllRecommendQueries(){
        return RecommentQueries;
    }
    
    public SqlQuery getRecommendQuery(int i){
        return RecommentQueries.get(i);
    }
    
    public List<String[][]> getAllRecommendResult(){
        return RecommentResults;
    }
    
    public String[][] getRecommendQueryResult(int i){
        return RecommentResults.get(i);
    }
}
