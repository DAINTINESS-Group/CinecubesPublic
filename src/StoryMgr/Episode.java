package StoryMgr;

import AudioMgr.Audio;
import TaskMgr.SubTask;
import TextMgr.Text;

public abstract class Episode {
    protected SubTask subTask;
   /* protected KeyFinding keyFinding;*/
    protected Visual visual;
    protected Audio audio;
    protected Text txt;
    
    public Episode(){    	
    	//keyFinding=new KeyFinding();
    	audio=new Audio();
    	txt=new Text();
    }

	/**
	 * @return the subTask
	 */
	abstract public SubTask getSubTask();

	/**
	 * @param subtask the subTask to set
	 */
	abstract public void setSubTask(SubTask subtask);
	
	abstract public void createVisual(Visual vis);


}
