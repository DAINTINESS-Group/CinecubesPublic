/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

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
    
    public void computeKeyFindings(Database DB){
    	SubTask stsk=getLastSubTask();
    	ArrayList<ArrayList<String>> lst=stsk.computeFinding(DB);
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
       
}
