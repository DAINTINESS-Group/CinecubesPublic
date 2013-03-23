/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import java.util.ArrayList;

import AudioMgr.AudioEngine;
import TaskMgr.FactCompilation;
import TextMgr.TextExtraction;

/**
 *
 * @author Asterix
 */
public class Story {
    
    private TextExtraction Texts;
    private AudioEngine Sounds;
    private FinalResult FinRes;
    private ArrayList<Act> Acts;
    private FactCompilation FactComp;
    public Act OriginalAct;
       
    Story(){
    	setActs(new ArrayList<Act>());
    };
    
    public void createNewAct(){
    	addAct(new Act());
    }

    private void addAct(Act act) {
		Acts.add(act);
	}


	public void createOriginalAct(){
		OriginalAct=new Act();
		addAct(OriginalAct);
    }
	
	public Act getAct(int i){
		return Acts.get(i);
	}
	
	private int getNumOfActs(){
		return Acts.size();
	}
	
	public Act getLastAct(){		
		return getAct(getNumOfActs()-1);
		
	}
	
	public TextExtraction getTexts() {
		return Texts;
	}

	public void setTexts(TextExtraction texts) {
		Texts = texts;
	}

	public AudioEngine getSounds() {
		return Sounds;
	}

	public void setSounds(AudioEngine sounds) {
		Sounds = sounds;
	}

	public FinalResult getFinalResult() {
		return FinRes;
	}

	public void setFinalResult(FinalResult finRes) {
		FinRes = finRes;
	}


	public ArrayList<Act> getActs() {
		return Acts;
	}

	public void setActs(ArrayList<Act> arrayList) {
		Acts = arrayList;
	}
}
