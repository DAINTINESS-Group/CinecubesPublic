/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import AudioMgr.Audio;
import TaskMgr.KeyFinding;
import TaskMgr.SubTask;
import TextMgr.Text;

/**
 *
 * @author Asterix
 */
public abstract class Episode {
    protected SubTask subTask;
    protected KeyFinding keyFinding;
    protected Visual visual;
    protected Audio audio;
    protected Text txt;
    
    public Episode(){
    	setSubTask(new SubTask());
    	keyFinding=new KeyFinding();
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
