/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.util.ArrayList;

import CubeMgr.StarSchema.Database;
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
    		getLastSubTask().computeFinding(DB);
    }
        
	public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
       
}
