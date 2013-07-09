package StoryMgr;

import java.util.ArrayList;
import TaskMgr.Task;


public class Act {
	/**
	 * @uml.property  name="id"
	 */
	private int id;
    /**
	 * @uml.property  name="episodes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="StoryMgr.Episode"
	 */
    private ArrayList<Episode> Episodes;
    /**
	 * @uml.property  name="tsk"
	 * @uml.associationEnd  
	 */
    private Task tsk;
    /**
	 * @uml.property  name="creationTime"
	 */
    public long creationTime;

    /**
	 * @uml.property  name="actHighlights"
	 */
    public  String ActHighlights; 
    
    public Act(){
    	Episodes=new ArrayList<Episode>();
    	ActHighlights="";
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

	/**
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id  the id to set
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/* for Debug Reason*/
	public String toString(){
		return "Act id:"+String.valueOf(id)+"\n# Episodes:"+String.valueOf(this.getNumEpisodes());
		
	}
    
}
