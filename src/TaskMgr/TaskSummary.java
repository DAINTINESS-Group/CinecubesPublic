package TaskMgr;

import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
import StoryMgr.Act;
import StoryMgr.PptxSlide;

public class TaskSummary extends Task {

	public TaskSummary() {
		super();
	}

	
	@Override
	public void addNewSubTask() {
		subTasks.add(new SubTask());
	}

	@Override
	public int getNumSubTasks() {
		return subTasks.size();
	}

	@Override
	public SubTask getSubTask(int i) {
		return subTasks.get(i);
	}

	@Override
	public SubTask getLastSubTask() {
		return getSubTask(getNumSubTasks()-1);
	}

	@Override
	public void generateSubTasks(CubeBase DB) {
		this.addNewSubTask();
	}

	@Override
	public ArrayList<SubTask> getSubTasks() {
		return this.subTasks;
	}

	@Override
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks=arrayList;
	}

	@Override
	public void constructActEpidoses(Act currentAct, Act OriginalAct) {
		PptxSlide tmpslide=new PptxSlide();
		currentAct.addEpisode(tmpslide);
	}

}
