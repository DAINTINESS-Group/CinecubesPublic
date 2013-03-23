/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import java.util.ArrayList;
import TaskMgr.Task;


/**
 *
 * @author Asterix
 */
public class Act {
    private ArrayList<Episode> Episodes;
    private Task tsk;
    
    public Act(){
    	Episodes=new ArrayList<Episode>();
    }

	public ArrayList<Episode> getEpisodes() {
		return Episodes;
	}

	public void setEpisodes(ArrayList<Episode> episodes) {
		Episodes = episodes;
	}
	
	public void addEpisode(Episode episode){
		episode.setSubTask(getTask().getLastSubTask());
		Episodes.add(episode);
	}
	
	public Episode getEpisode(int i){
		return Episodes.get(i);
	}
	
	public int getNumEpisodes(){
		return Episodes.size();
	}
	
	public void setTask(Task task){
		tsk=task;
	}
	
	public Task getTask(){
		return tsk;
	}
    
}
