package StoryMgr;
import java.util.ArrayList;

import AudioMgr.Audio;
import CubeMgr.CubeBase.CubeQuery;
import TaskMgr.SubTask;

public class PptxSlide extends Episode {
	
	
	private String Notes;
	private String Title;
	private String SubTitle;
	private String TitleColumn;
	private String TitleRow;
	public ArrayList<CubeQuery> CbQOfSlide;
	public long timeCreation;
	public long timeCreationAudio;
	public long timeCreationText;
	public long timeCreationTabular;
	public long timeCreationColorTable;
	public long timeCreationPutInPPTX;
	public long timeCombineSlide;
	public long timeComputeHighlights;
	
	public PptxSlide() {
		super();
		setSubTitle("");
		setNotes("");
		timeCreation=0;
		timeCreationAudio=0;
		timeCreationPutInPPTX=0;
		timeCreationTabular=0;
		timeCreationColorTable=0;
		timeCreationText=0;
		timeCreationPutInPPTX=0;
		timeCombineSlide=0;
		timeComputeHighlights=0;
		CbQOfSlide=new ArrayList<CubeQuery>();
	}
	
	/*public void setText(String txt){
		this.txt.setText(txt);
	}*/
	
	@Override
	public ArrayList<SubTask> getSubTasks() {
		return subTask;
	}

	@Override
	public void setSubTasks(ArrayList<SubTask> subtask) {
		subTask=subtask;
	}

	@Override
	public void addSubTask(SubTask subtask) {
		subTask.add(subtask);
	}
	
	@Override
	public void setVisual(Visual vis) {
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

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSubTitle() {
		return SubTitle;
	}

	public void setSubTitle(String subTitle) {
		SubTitle = subTitle;
	}

	public String getTitleColumn() {
		return TitleColumn;
	}

	public void setTitleColumn(String titleColumn) {
		TitleColumn = titleColumn;
	}

	public String getTitleRow() {
		return TitleRow;
	}

	public void setTitleRow(String titleRow) {
		TitleRow = titleRow;
	}
	
}
