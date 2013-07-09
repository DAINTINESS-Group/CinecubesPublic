package TaskMgr;

import java.util.ArrayList;

public class TaskMgr {
    /**
	 * @uml.property  name="tasks"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="TaskMgr.Task"
	 */
    private ArrayList<Task> Tasks;
        
    public TaskMgr(){
    }
    
    public void createTasks(){
    	Tasks=new ArrayList<Task>();  	
    }

    public void createNewTask(Task toadd){
    	Tasks.add(toadd);
    }
    
    public Task getTask(int i){
    	return Tasks.get(i);
    }
    
    private int getNumOfTasks(){
    	return Tasks.size();
    }
    
    public Task getLastTask(){
    	return getTask(getNumOfTasks()-1);
    }
    
	public ArrayList<Task> getTasks() {
		return Tasks;
	}

	public void setTasks(ArrayList<Task> tsks) {
		Tasks = tsks;
	};
	
	
}
