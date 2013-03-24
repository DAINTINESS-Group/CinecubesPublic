/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.sql.ResultSet;
import java.util.ArrayList;

import CubeMgr.StarSchema.Database;

/**
 *
 * @author Asterix
 */
public abstract class ExtractionMethod {
    public Result Res;
    
    public ExtractionMethod(){
    	Res=new Result();
    }

    abstract public void setResult(ResultSet resultSet);
    abstract public Result getResult();
    abstract public String returnQuery();
    abstract public ArrayList<ArrayList<String>> findBrothers(Database db) ;
    
}
