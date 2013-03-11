/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import AudioMgr.AudioEngine;
import CubeMgr.StarSchema.SqlQuery;
import TaskMgr.FactCompilation;
import TextMgr.TextExtraction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asterix
 */
public class Story {
    
    private List<SqlQuery> Queries;
    private TextExtraction Texts;
    private AudioEngine Sounds;
    private OriginalRequest OrigReq;
    private FinalResult FinRes;
    private List<Act> ACT;
    private FactCompilation FactComp;
    
    
    Story(){
    	Queries=new ArrayList<>();
    	ACT=new ArrayList<>();
    };
    
    
    void createAct(){}


	public List<SqlQuery> getQueries() {
		return Queries;
	}


	public void setQueries(List<SqlQuery> queries) {
		Queries = queries;
	};
	
	public void addQuery(SqlQuery query){
		Queries.add(query);
	}
    /* It needs more additions
     * .
     * .
     * .
     * .
     * .
     * .
     * .
     * .
     */


	public void addOriginalRequest(SqlQuery query) {
		addQuery(query);	
	}
}
