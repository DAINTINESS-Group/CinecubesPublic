package StoryMgr;

import java.util.ArrayList;
import TaskMgr.Task;


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
