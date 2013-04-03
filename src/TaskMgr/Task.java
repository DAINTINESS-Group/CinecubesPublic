/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
/**
 *
 * @author Asterix
 */
public abstract class Task {
    protected ArrayList<SubTask> subTasks;
    
    
    public Task(){
    	subTasks=new ArrayList<SubTask>();
    }
   
    public abstract void addNewSubTask();;
    
    public abstract int getNumSubTasks();    
    
    public abstract SubTask getSubTask(int i);
    
    public abstract SubTask getLastSubTask();
    
    public abstract void generateSubTasks(CubeBase DB);
        
    public abstract ArrayList<SubTask> getSubTasks() ;
    
	public abstract void setSubTasks(ArrayList<SubTask> arrayList) ;

	
}
