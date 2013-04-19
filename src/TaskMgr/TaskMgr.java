package TaskMgr;

import java.util.ArrayList;

public class TaskMgr {
    private ArrayList<Task> Tasks;
        
    public TaskMgr(){
    }
    
    public void createTasks(){
    	Tasks=new ArrayList<>();  	
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
