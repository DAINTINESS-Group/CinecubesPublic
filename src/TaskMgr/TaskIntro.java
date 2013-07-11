package TaskMgr;

import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
import StoryMgr.Act;
import StoryMgr.PptxSlide;

public class TaskIntro extends Task {

	public TaskIntro() {
		super();
	}

	@Override
	public void generateSubTasks(CubeBase DB) {
		this.addNewSubTask();
	}
	
	@Override
	public void constructActEpidoses(Act currentAct) {
		PptxSlide tmpslide=new PptxSlide();
		currentAct.addEpisode(tmpslide);
	}

}
