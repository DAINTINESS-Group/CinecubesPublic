package StoryMgr;

import java.util.ArrayList;

import AudioMgr.Audio;
import HighlightMgr.Highlight;
import TaskMgr.SubTask;

public abstract class Episode {
    
    protected ArrayList<SubTask> subTask;
   /* protected KeyFinding keyFinding;*/
    protected Visual visual;   
    protected Audio audio;
  /* protected Text txt;*/
    public ArrayList<Highlight> highlight;
    
    public Episode(){    	
    	//keyFinding=new KeyFinding();
    	audio=new Audio();
    	/*txt=new Text();*/
    	subTask=new ArrayList<SubTask>();
    	highlight=new ArrayList<Highlight>();
    }

	/**
	 * @return the subTask
	 */
	abstract public ArrayList<SubTask> getSubTasks();
	/**
	 * @param subtask the subTask to set
	 */
	abstract public void setSubTasks(ArrayList<SubTask> subtask);
	abstract public void addSubTask(SubTask subtask);
	
	/**
	 * @param vis
	 * @uml.property  name="visual"
	 */
	abstract public void setVisual(Visual vis);


}
