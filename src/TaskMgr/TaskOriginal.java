package TaskMgr;

import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.StarSchema.SqlQuery;
import HighlightMgr.HighlightDominationColumn;
import HighlightMgr.HighlightDominationRow;
import HighlightMgr.HighlightMax;
import HighlightMgr.HighlightMin;
import HighlightMgr.HighlightTable;
import StoryMgr.Act;
import StoryMgr.PptxSlide;
import StoryMgr.Tabular;

public class TaskOriginal extends Task {

	public TaskOriginal() {
		super();
	}

	
	@Override
	public void generateSubTasks(CubeBase cubeBase) {
		this.addNewSubTask();
		SqlQuery newSqlQuery=new SqlQuery();
        long time_produce_original=System.nanoTime();
        newSqlQuery.produceExtractionMethod(this.cubeQuery.get(0));
        time_produce_original=System.nanoTime()-time_produce_original;
        this.getLastSubTask().timeProduceOfExtractionMethod=time_produce_original;
        this.getLastSubTask().setExtractionMethod(newSqlQuery);
        this.getLastSubTask().execute(cubeBase.DB);
        this.cubeQuery.get(0).sqlQuery=newSqlQuery;
	}

	@Override
	public void constructActEpidoses(Act currentAct) {
		PptxSlide newSlide=new PptxSlide();
		SubTask subtsk=currentAct.getTask().getSubTask(0);
		CubeQuery currentCubeQuery=currentAct.getTask().cubeQuery.get(0);
    	Tabular tbl=new Tabular();
    	String[] extraPivot=new String[2];
    	
    	HighlightMin hlmin=new HighlightMin();
    	HighlightMax hlmax=new HighlightMax();
    	HighlightDominationRow hldomrow=new HighlightDominationRow();
    	HighlightDominationColumn hldomcol=new HighlightDominationColumn();
    	subtsk.setHighlight(new HighlightTable());
	  //  HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
	    
	    newSlide.addSubTask(subtsk);
	    newSlide.highlight.add(hlmin);
	    newSlide.highlight.add(hlmax);
	    newSlide.highlight.add(hldomcol);
	    newSlide.highlight.add(hldomrow);
    	newSlide.CbQOfSlide.add(currentCubeQuery);
    	currentAct.addEpisode(newSlide);
        newSlide.setVisual(tbl);
        
        
        extraPivot[0]="";
        extraPivot[1]="";
		
	    /*====== Compute Pivot Table =======*/
    	newSlide.timeCreationTabular=System.nanoTime();
	    tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(),
	    		 			  subtsk.getExtractionMethod().Res.getColPivot(),
	    		 			  subtsk.getExtractionMethod().Res.getResultArray(),
	    		 			  extraPivot);
	    newSlide.timeCreationTabular=System.nanoTime()-newSlide.timeCreationTabular;
	    
	    
	    /*====== Calculate Highlioghts =======*/
    	newSlide.timeComputeHighlights=System.nanoTime();
    	hlmin.execute(subtsk.getExtractionMethod().Res.getResultArray());
    	hlmax.execute(subtsk.getExtractionMethod().Res.getResultArray());
    	
    	hldomcol.semanticValue=hlmax.semanticValue;
    	hldomcol.helpValues2=hlmin.semanticValue;
    	hldomcol.execute(tbl.getPivotTable());
    	
    	hldomrow.semanticValue=hlmax.semanticValue;
    	hldomrow.helpValues2=hlmin.semanticValue;
    	hldomrow.execute(tbl.getPivotTable());
    	//hltbl.createMiddleHightlight(subtsk.getExtractionMethod().Res.getResultArray());
    	newSlide.timeComputeHighlights=System.nanoTime()-newSlide.timeComputeHighlights;
    	//newSlide.highlight=hltbl;
    	
    	/*====== Compute Color Table =======*/
    	newSlide.timeCreationColorTable=System.nanoTime();
    	tbl.setColorTable(newSlide.highlight);
    	newSlide.timeCreationColorTable=System.nanoTime()-newSlide.timeCreationColorTable;
    	
    	/*====== Calculate domination Highlioghts =======*/
    	long start_creation_domination=System.nanoTime();
		//hltbl.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
		newSlide.timeComputeHighlights+=System.nanoTime()-start_creation_domination;
		
	}

}
