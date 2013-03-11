/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import CubeMgr.StarSchema.SqlQuery;

/**
 *
 * @author Asterix
 */
public class StoryMgr {
    private Story ST;
    
    public StoryMgr(){
    	ST=new Story();
    }
    public void createStory(SqlQuery query){
    	ST.addOriginalRequest(query);
    	ST.createAct();
    };
    
    public void createTasks(){};
    public void regroup(){};
    public void DoWrapUp(){}
    
	
}
