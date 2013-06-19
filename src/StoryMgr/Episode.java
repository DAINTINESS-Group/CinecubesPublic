package StoryMgr;

import java.util.ArrayList;

import AudioMgr.Audio;
import HighlightMgr.Highlight;
import TaskMgr.SubTask;
import TextMgr.Text;

public abstract class Episode {
    protected ArrayList<SubTask> subTask;
   /* protected KeyFinding keyFinding;*/
    protected Visual visual;
    protected Audio audio;
    protected Text txt;
    public Highlight highlight;
    
    public Episode(){    	
    	//keyFinding=new KeyFinding();
    	audio=new Audio();
    	txt=new Text();
    	subTask=new ArrayList<SubTask>();
    }

	/**
	 * @return the subTask
	 */
	abstract public ArrayList<SubTask> getSubTask();
	/**
	 * @param subtask the subTask to set
	 */
	abstract public void setSubTask(ArrayList<SubTask> subtask);
	abstract public void addSubTask(SubTask subtask);
	
	abstract public void setVisual(Visual vis);


}
