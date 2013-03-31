package StoryMgr;
import AudioMgr.Audio;
import TaskMgr.SubTask;

/**
 * @author Asterix
 *
 */
public class pptxSlide extends Episode {
	
	public String Notes;
	public String Title;
	/**
	 * 
	 */
	public pptxSlide() {
		super();
	}
	
	public void setText(String txt){
		this.txt.setText(txt);
	}

	@Override
	public SubTask getSubTask() {
		return subTask;
	}

	@Override
	public void setSubTask(SubTask subtask) {
		subTask=subtask;
	}

	@Override
	public void createVisual(Visual vis) {
		this.visual=vis;		
	}
	
	public Visual getVisual(){
		return visual;
	}

	public void setAudioFile(String fileName) {
		this.audio.setFileName(fileName);
	}
	
	public Audio getAudio(){
		return audio;
		
	}
	
}
