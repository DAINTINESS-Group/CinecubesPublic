/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import TaskMgr.Task;
import TaskMgr.TaskMgr;

/**
 *
 * @author Asterix
 */
public class StoryMgr {
    private Story story;
    
    public StoryMgr(){
    	
    }
    
    public void createStory(){
    	story=new Story();
    }
        
    public void createStoryOriginalRequest(){
    	story.createOriginalAct();
    }
    
    public void createTasks(TaskMgr tskMgr){
    	tskMgr.createTasks();
    }
    
    public void addNewTaskToStory(Task task){
    	story.getLastAct().setTask(task);
    }
    
    public void setStory(Story stor){
    	story=stor;
    }
    
    public Story getStory(){
    	return story;
    }
    
    public void regroup(){};
    public void DoWrapUp(){}
    
	
}
