package StoryMgr;
import AudioMgr.Audio;
import TaskMgr.SubTask;

public class PptxSlide extends Episode {
	
	public String Notes;
	public String Title;
	public String TitleColumn;
	public String TitleRow;
	
	public PptxSlide() {
		super();
	}
	
	public void setText(String txt){
		this.txt.setText(txt);
	}
	
	public void createHighlight(){
		this.highlight=new PptxHighlight();
		((PptxHighlight)highlight).findMax(this.visual.PivotTable);
		((PptxHighlight)highlight).findMin(this.visual.PivotTable);
		((PptxHighlight)highlight).findMedian(this.visual.PivotTable);
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
