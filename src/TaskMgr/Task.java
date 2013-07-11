package TaskMgr;

import java.util.ArrayList;

import StoryMgr.Act;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.CubeBase.CubeQuery;

/**
 * @author  Asterix
 */
public abstract class Task {
    
    protected ArrayList<SubTask> subTasks;
    public ArrayList<CubeQuery> cubeQuery;
    
    public Task(){
    	subTasks=new ArrayList<SubTask>();
    	cubeQuery=new ArrayList<CubeQuery>();
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
    
    public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
    
    public abstract void generateSubTasks(CubeBase cubeBase);
	
	public abstract void constructActEpidoses(Act currentAct);
}
