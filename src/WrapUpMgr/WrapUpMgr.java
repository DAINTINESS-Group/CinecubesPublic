/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WrapUpMgr;

import StoryMgr.FinalResult;
import StoryMgr.Story;

/**
 *
 * @author Asterix
 */
public abstract class WrapUpMgr {
    protected FinalResult finalResult;
    
    public WrapUpMgr(){
    	
    }
    
    abstract public FinalResult getFinalResult();
    abstract public void setFinalResult(FinalResult finalresult);

	abstract public void doWrapUp(Story story);
    
}
