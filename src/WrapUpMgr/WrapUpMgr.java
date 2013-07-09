package WrapUpMgr;

import StoryMgr.FinalResult;
import StoryMgr.Story;

public abstract class WrapUpMgr {
    /**
	 * @uml.property  name="finalResult"
	 * @uml.associationEnd  
	 */
    protected FinalResult finalResult;
    
    public WrapUpMgr(){
    	
    }
    
    /**
	 * @return
	 * @uml.property  name="finalResult"
	 */
    abstract public FinalResult getFinalResult();
    /**
	 * @param finalresult
	 * @uml.property  name="finalResult"
	 */
    abstract public void setFinalResult(FinalResult finalresult);

	abstract public void doWrapUp(Story story);
    
}
