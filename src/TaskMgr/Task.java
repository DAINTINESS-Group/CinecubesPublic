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
   
    public abstract void addNewSubTask();
    
    public abstract int getNumSubTasks();    
    
    public abstract SubTask getSubTask(int i);
    
    public abstract SubTask getLastSubTask();
    
    public abstract ArrayList<SubTask> getSubTasks() ;
    
	public abstract void setSubTasks(ArrayList<SubTask> arrayList);
    
    public abstract void generateSubTasks(CubeBase cubeBase);
	
	public abstract void constructActEpidoses(Act currentAct,Act OriginalAct);
}
