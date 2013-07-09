package TaskMgr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Dimension;
import CubeMgr.CubeBase.Hierarchy;
import CubeMgr.StarSchema.SqlQuery;
import HighlightMgr.HighlightDominationColumn;
import HighlightMgr.HighlightMax;
import HighlightMgr.HighlightMin;
import HighlightMgr.HighlightTable;
import StoryMgr.Act;
import StoryMgr.PptxSlide;
import StoryMgr.Tabular;

public class TaskActII extends Task {

	
	ArrayList<SubTask> substodelete;
	ArrayList<CubeQuery> cubequerytodelete;
	ArrayList<SubTask> subschecked;
	
	public TaskActII() {
		super();
		substodelete=new ArrayList<SubTask>();
		subschecked=new ArrayList<SubTask>();
		cubequerytodelete=new ArrayList<CubeQuery>();
	}

	public void addNewSubTask(){
    	subTasks.add(new SubTask());
    };
	
	public int getNumSubTasks(){
    	return subTasks.size();
    }
    
    public SubTask getSubTask(int i){
    	return subTasks.get(i);
    }
    
    public SubTask getLastSubTask(){
    	return getSubTask(getNumSubTasks()-1);
    }
    
    public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
   
    public void generateSubTasks(CubeBase cubeBase){
    	//createHighlight();/* highlight for Original*/
    	this.generateSubTasks_per_row(cubeBase);
    	this.generateSubTasks_per_col(cubeBase);
    }
    
    @Override
	public void constructActEpidoses(Act currentAct,Act OriginalAct) {
		SubTask origSubtsk=OriginalAct.getTask().getSubTask(0);
		for(int j=0;j<currentAct.getTask().getNumSubTasks();j++){
    		
			SubTask subtsk=currentAct.getTask().getSubTask(j);
    		SqlQuery currentSqlQuery=((SqlQuery)subtsk.getExtractionMethod());
    		CubeQuery currentCubeQuery=currentAct.getTask().cubeQuery.get(j);
    		PptxSlide newSlide=new PptxSlide();
        	newSlide.CbQOfSlide.add(currentCubeQuery);
        	
	        Tabular tbl=new Tabular();
	        newSlide.setVisual(tbl);
	        
	        String[] extraPivot=new String[2];
	        extraPivot[0]="";
	        extraPivot[1]="";
	        
	        if((currentSqlQuery.Res.getResultArray()!=null)){
		    	/*====== Compute Pivot Table =======*/
	        	if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-4){
		        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
		        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getRowPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
		        }
		        if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-5){
		        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
		        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getColPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
		        }
		    	newSlide.timeCreationTabular=System.nanoTime();
			    tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(),
			    		 			  subtsk.getExtractionMethod().Res.getColPivot(),
			    		 			  subtsk.getExtractionMethod().Res.getResultArray(),
			    		 			  extraPivot);
			    newSlide.timeCreationTabular=System.nanoTime()-newSlide.timeCreationTabular;
			    
			    if(subtsk.getHighlight()==null) subtsk.setHighlight(new HighlightTable());
			    //HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
			    
			    /*====== Calculate Highlioghts =======*/
	        	/*newSlide.timeComputeHighlights=System.nanoTime();
	        	hltbl.createMinHightlight(subtsk.getExtractionMethod().Res.getResultArray());
		    	hltbl.createMaxHightlight(subtsk.getExtractionMethod().Res.getResultArray());
		    	hltbl.createMiddleHightlight(subtsk.getExtractionMethod().Res.getResultArray());
		    	tbl.boldColumn=hltbl.boldColumn;
	    		tbl.boldRow=hltbl.boldRow;
		    	newSlide.timeComputeHighlights=System.nanoTime()-newSlide.timeComputeHighlights;*/
		    	
		    	/*====== Compute Color Table =======*/
		    	/*newSlide.timeCreationColorTable=System.nanoTime();
		    	tbl.setColorTable(hltbl);
	        	newSlide.timeCreationColorTable=System.nanoTime()-newSlide.timeCreationColorTable;*/
			    
			    if(subtsk.getDifferencesFromOrigin().size()>0 && (subtsk.getDifferencesFromOrigin().get(0)==-4 || subtsk.getDifferencesFromOrigin().get(0)==-5) && subtsk.getDifferencesFromOrigin().get(1)>0){
			    	/*====== Combine Subtask and Pivot Table =======*/
		        	PptxSlide tmpSlide=(PptxSlide)currentAct.getEpisode(currentAct.getNumEpisodes()-1);
		        	long strTimecombine=System.nanoTime();
		        	String [][] SlideTable=tmpSlide.getVisual().getPivotTable();
		        	String [][] currentTable=tbl.getPivotTable();
		        	String [][] newTable = null;
		        	/*Color [][] colorTable=((Tabular)tmpSlide.getVisual()).colortable;
		        	  Color [][] currentColorTable=tbl.colortable;
		        	  Color[][] newColorTable =null;*/
		        	
		        	int rows_width=SlideTable.length+currentTable.length;
		        	TreeSet<String> cols=new  TreeSet<String>();
		        	
		        	for(int i=2;i<SlideTable[0].length;i++) cols.add(SlideTable[0][i]);
		        	for(int i=2;i<currentTable[0].length;i++) cols.add(currentTable[0][i]);
		        				        	
		        	newTable=new String[rows_width][cols.size()+2];
		        	/*newColorTable=new Color[rows_width][cols.size()+2];*/
		        	customCopyArray(SlideTable,currentTable,/*colorTable,currentColorTable,*/newTable/*,newColorTable*/);
		        	
		        	tmpSlide.getVisual().setPivotTable(newTable);
		        	//((Tabular)tmpSlide.getVisual()).colortable=newColorTable;
		        	
		        	tmpSlide.timeCombineSlide+=System.nanoTime()-strTimecombine;
		        	
		        	//tmpSlide.timeCreationColorTable+=newSlide.timeCreationColorTable;
		        	tmpSlide.timeCreationTabular+=newSlide.timeCreationTabular;
		        	tmpSlide.timeComputeHighlights+=newSlide.timeComputeHighlights;
		        	
		        	tmpSlide.addSubTask(subtsk);
		        	tmpSlide.CbQOfSlide.add(currentCubeQuery);
		        	
		        }
			    else{
		        	newSlide.addSubTask(subtsk);
			        currentAct.addEpisode(newSlide);
		        }
	        }
	        else if(currentSqlQuery.Res.TitleOfColumns!=null && currentSqlQuery.Res.TitleOfColumns.contains("Act")){
	        	newSlide.timeCreationText=0;
        		newSlide.timeCreationText=System.nanoTime();
        		newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
        		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
        		
        		newSlide.addSubTask(subtsk);
		        currentAct.addEpisode(newSlide);
		    }
		}
		
		for(int i=1;i<currentAct.getNumEpisodes();i++){
			PptxSlide currentSlide=(PptxSlide) currentAct.getEpisode(i);
			Tabular tbl=(Tabular) currentSlide.getVisual();
			currentSlide.highlight.clear();
			if(currentSlide.getSubTasks().get(0).getDifferencesFromOrigin().size()>1){
				//if(currentSlide.getSubTasks().get(0).getDifferencesFromOrigin().get(0)==-4){
					HighlightMin hlmin=new HighlightMin();
			    	HighlightMax hlmax=new HighlightMax();
			    	HighlightDominationColumn hldomcol=new HighlightDominationColumn();
			    	currentSlide.highlight.add(hlmin);
			    	currentSlide.highlight.add(hlmax);
			    	currentSlide.highlight.add(hldomcol);
			    	String [][] allResult = null;
			    	int data_length=0;
			    	for(int k=0;k<currentSlide.getSubTasks().size();k++) {
			    		data_length+=currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray().length-2;
			    	}
			    	allResult=new String[data_length+2][3];
			    	int pos_to_copy=0;
			    	for(int k=0;k<currentSlide.getSubTasks().size();k++) {
			    		if(k==0){
			    			System.arraycopy(currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray(), 0, 			    	
			    						allResult, pos_to_copy,
			    						currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray().length );
			    			pos_to_copy+=currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray().length;
			    		}
			    		else{
			    			//System.out.println();
			    			System.arraycopy(currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray(), 2, 			    	
		    						allResult, pos_to_copy,
		    						currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray().length-2 );
			    			pos_to_copy+=currentSlide.getSubTasks().get(k).getExtractionMethod().Res.getResultArray().length-2;
			    		}
			    		
			    	}
			    	currentSlide.timeComputeHighlights=System.nanoTime();
			    	hlmin.execute(allResult);
			    	hlmax.execute(allResult);
			    	
			    	hldomcol.semanticValue=hlmax.semanticValue;
			    	hldomcol.helpValues2=hlmin.semanticValue;
			    	hldomcol.execute(tbl.getPivotTable());
			    				    	
			    	currentSlide.timeComputeHighlights=System.nanoTime()-currentSlide.timeComputeHighlights;
			}
			else {
				HighlightMin hlmin=new HighlightMin();
		    	HighlightMax hlmax=new HighlightMax();
		    	currentSlide.highlight.add(hlmin);
		    	currentSlide.highlight.add(hlmax);
		    	currentSlide.timeComputeHighlights=System.nanoTime();
		    	hlmin.execute(currentSlide.getSubTasks().get(0).getExtractionMethod().Res.getResultArray());
		    	hlmax.execute(currentSlide.getSubTasks().get(0).getExtractionMethod().Res.getResultArray());
		    	currentSlide.timeComputeHighlights=System.nanoTime()-currentSlide.timeComputeHighlights;
		    	
			}
			currentSlide.timeCreationColorTable=System.nanoTime();
			tbl.setColorTable(currentSlide.highlight);
			currentSlide.timeCreationColorTable=System.nanoTime()-currentSlide.timeCreationColorTable;
		}
	}
    
    private void generateSubTasks_per_row(CubeBase cubeBase){
    	SqlQuery newSqlQuery=this.cubeQuery.get(1).sqlQuery;
    	HashSet<String> col_per_row=new HashSet<String>();/*new HashSet[newSqlQuery.Res.getColPivot().size()];*/
    	
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
    		String[][] table=newSqlQuery.Res.getResultArray();
    		String Value=newSqlQuery.Res.getRowPivot().toArray()[i].toString();
    		//col_per_row[i]=new HashSet<String>();
    		col_per_row.clear();
    		for(int j=2;j<table.length;j++){
    			if(table[j][1].equals(Value)) col_per_row.add(table[j][0]);
    		}
    		/*for(int k=0;k<col_per_row[i].size();k++){*/
	    		String[] todrillinValues=new String[2];
	    		todrillinValues[1]=Value;
	    		todrillinValues[0]=col_per_row.toArray()[0].toString();
	    		
	    		if(doDrillInRowVersion(cubeBase,todrillinValues,col_per_row)) {
	    			this.getLastSubTask().addDifferenceFromOrigin(i);
	    			this.getLastSubTask().addDifferenceFromOrigin(1);
	    		}
	    	/*}
    		//this.recreateResultArray(1,0);
    		//this.createHighlight();*/
    	}
    	//System.out.print("Recreation Result For Rows result:");
    	//System.out.println(all_time);
    	
    }
    
    private void generateSubTasks_per_col(CubeBase cubeBase){
    	SqlQuery newSqlQuery=this.cubeQuery.get(1).sqlQuery;
    	/*HashSet<String>[] row_per_col=new HashSet[newSqlQuery.Res.getColPivot().size()];*/
    	HashSet<String> row_per_col=new HashSet<String>();
    	//long all_time=0;
    	for(int i=0;i<newSqlQuery.Res.getColPivot().size();i++){
    		String[][] table=newSqlQuery.Res.getResultArray();
    		String Value=newSqlQuery.Res.getColPivot().toArray()[i].toString();
    		row_per_col.clear();
    		for(int j=2;j<table.length;j++){
    			if(table[j][0].equals(Value)) row_per_col.add(table[j][1]);
    		}
    		/*for(int k=0;k<row_per_col[i].size();k++){*/
	    		String[] todrillinValues=new String[2];
	    		todrillinValues[1]=Value;
	    		todrillinValues[0]=row_per_col.toArray()[0].toString();
	    		
	    		if(doDrillInColVersion(cubeBase,todrillinValues,row_per_col)) {
	    			this.getLastSubTask().addDifferenceFromOrigin(i);
	    			this.getLastSubTask().addDifferenceFromOrigin(1);
	    		}
	    	/*}
    		long recreate_time=System.nanoTime();
    		this.recreateResultArray(1,0);
    		all_time+=System.nanoTime()-recreate_time;*/
    		//this.createHighlight();
    	}
    	//System.out.print("Recreation Result For Columns result:");
    	//System.out.println(all_time);
    }
    
    /* Cubequery Version to Generate Subtasks
     * 
     */
  /*  public void generateSubTasks_RowminmaxVersion(CubeBase cubeBase){
    	
    	SqlQuery newSqlQuery=this.cubeQuery.get(1).sqlQuery;
    	    	
    	int[] minRowPerValue=new int[newSqlQuery.Res.getRowPivot().size()];
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
    		minRowPerValue[i]=this.getMinRow(newSqlQuery.Res.getResultArray(), newSqlQuery.Res.getRowPivot().toArray()[i].toString(), 1);
    	}
    	
    	int[] maxRowPerValue=new int[newSqlQuery.Res.getRowPivot().size()];
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
    		maxRowPerValue[i]=this.getMaxRow(newSqlQuery.Res.getResultArray(), newSqlQuery.Res.getRowPivot().toArray()[i].toString(), 1);
    	}
    	
    	    	
    	/* First Approach* /
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
	        String[] valueOfMin=new String[2];
	        valueOfMin[0]=newSqlQuery.Res.getResultArray()[minRowPerValue[i]][0]; /*Gamma La value* /
	        valueOfMin[1]=newSqlQuery.Res.getResultArray()[minRowPerValue[i]][1]; /*Gamma Lb balue* /
	        String[] valueOfMax=new String[2];
	        valueOfMax[0]=newSqlQuery.Res.getResultArray()[maxRowPerValue[i]][0]; /*Gamma La value* /
	        valueOfMax[1]=newSqlQuery.Res.getResultArray()[maxRowPerValue[i]][1]; /*Gamma Lb balue* /
	        
	        
	       /* doDrillIn(cubeBase,valueOfMin,-2);/*MIN* /
	       /* doDrillIn(cubeBase,valueOfMax,-3);/*MAX* /
	       // System.out.println("Row Min Drill In : "+String.valueOf(i+1));
	        doDrillInRowVersion(cubeBase,valueOfMin,-2);
	        this.getLastSubTask().addDifferenceFromOrigin(i);
	        
	        //System.out.println("Row Max Drill In : "+String.valueOf(i+1));
	        doDrillInRowVersion(cubeBase,valueOfMax,-3);
	        this.getLastSubTask().addDifferenceFromOrigin(i);
	        
	        recreateResultArrayForMinMaxOfArray_v1();
    	}
    	//for(SubTask dlt:substodelete) this.subTasks.remove(dlt);
        
    }*/
    
  /*  void doDrillInRowVersion(CubeBase cubeBase,String[] GammaLaLb,int mode){
    	
    	String[] gamma_tmp=this.cubeQuery.get(1).GammaExpressions.get(1);
    	
    	int index_sigma_todelete=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,gamma_tmp[0]);
    	int index_sigma_todelete_column=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,this.cubeQuery.get(1).GammaExpressions.get(0)[0]);
		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
		
		CubeQuery newQuery=new CubeQuery("");
		copyListofArrayString(this.cubeQuery.get(1).GammaExpressions, newQuery.GammaExpressions);
		copyListofArrayString(this.cubeQuery.get(1).SigmaExpressions, newQuery.SigmaExpressions);
		newQuery.AggregateFunction=this.cubeQuery.get(1).AggregateFunction;
		newQuery.referCube=this.cubeQuery.get(1).referCube;
		newQuery.setMsr(this.cubeQuery.get(1).getMsr());		
		
		if(index_sigma_todelete>-1){
			newQuery.SigmaExpressions.get(index_sigma_todelete)[0]=newQuery.GammaExpressions.get(1)[0]+"."+newQuery.GammaExpressions.get(1)[1];
			newQuery.SigmaExpressions.get(index_sigma_todelete)[2]="'"+GammaLaLb[1]+"'";
		}
		if(index_sigma_todelete_column>-1){
			newQuery.SigmaExpressions.get(index_sigma_todelete_column)[0]=newQuery.GammaExpressions.get(0)[0]+"."+newQuery.GammaExpressions.get(0)[1];
			newQuery.SigmaExpressions.get(index_sigma_todelete_column)[2]="'"+GammaLaLb[0]+"'";
		}
		
		
		newQuery.GammaExpressions.get(1)[1]=child_level_of_gamma;
		newQuery.GammaExpressions.remove(0);
		String [] toadd=new String[2];
		toadd[0]="";
		if(mode==-2) toadd[1]="''";
		else toadd[1]="'max'";
		
		newQuery.GammaExpressions.add(toadd);
		
		addSubTask(newQuery,mode);
		
		this.getLastSubTask().execute(cubeBase.DB);
    }
    */
    
    boolean doDrillInRowVersion(CubeBase cubeBase,String[] valuesToChange,HashSet<String> cols){ /*valuesToChange[0]->Row Value,valuesToChange[1]->Column Value  */
    	long strTime=System.nanoTime();
    	String[] gamma_tmp=this.cubeQuery.get(1).GammaExpressions.get(1); /*column dimension */
    	
    	int index_sigma_change_bygamma=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,gamma_tmp[0]);
    	//int index_sigma_todrillIn=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,this.cubeQuery.get(1).GammaExpressions.get(0)[0]);
		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
		
		CubeQuery newQuery=new CubeQuery("");
		copyListofArrayString(this.cubeQuery.get(1).GammaExpressions, newQuery.GammaExpressions);
		copyListofArrayString(this.cubeQuery.get(1).SigmaExpressions, newQuery.SigmaExpressions);
		newQuery.AggregateFunction=this.cubeQuery.get(1).AggregateFunction;
		newQuery.referCube=this.cubeQuery.get(1).referCube;
		newQuery.setMsr(this.cubeQuery.get(1).getMsr());		
		
		if(index_sigma_change_bygamma>-1){
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[0]=newQuery.GammaExpressions.get(1)[0]+"."+newQuery.GammaExpressions.get(1)[1];
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[2]="'"+valuesToChange[1]+"'";
		}
		/*if(index_sigma_todrillIn>-1){
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[0]=newQuery.GammaExpressions.get(0)[0]+"."+newQuery.GammaExpressions.get(0)[1];
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[1]=" IN ";
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]="(";
			for(int x=0;x<cols.size();x++){
				if(x>0 && x<cols.size()) newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+=",";
				newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+="'"+cols.toArray()[x].toString()+"'";
			}
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+=")";
		}*/
		
		if(child_level_of_gamma!=null){
			newQuery.GammaExpressions.get(1)[1]=child_level_of_gamma;
			//newQuery.GammaExpressions.set(0, newQuery.GammaExpressions.set(1,newQuery.GammaExpressions.get(0)));
			
			//newQuery.GammaExpressions.remove(0);
			//String [] toadd=new String[2];
			//toadd[0]="";
			//toadd[1]="'"+valuesToChange[0]+"'";
			
			//newQuery.GammaExpressions.add(toadd);
			long endTime=System.nanoTime();
			
			addSubTask(newQuery,-4);
			this.getLastSubTask().execute(cubeBase.DB);
			//subschecked.add(this.getLastSubTask());
			this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
			this.getLastSubTask().timeProduceOfCubeQuery=endTime-strTime;
		}
		else return false;
		return true;
    }
    
    boolean doDrillInColVersion(CubeBase cubeBase,String[] valuesToChange, HashSet<String> row_per_col){/*valuesToChange[0]->Row Value,valuesToChange[1]->Column Value  */
    	long strTime=System.nanoTime();
    	String[] gamma_tmp=this.cubeQuery.get(1).GammaExpressions.get(0); /*Row Dimension*/
    	
    	int index_sigma_change_bygamma=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,gamma_tmp[0]);/*getRow Sigma*/
    	//int index_sigma_todrillIn=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,this.cubeQuery.get(1).GammaExpressions.get(1)[0]);/*get Col Sigma*/
		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
		
		CubeQuery newQuery=new CubeQuery("");
		copyListofArrayString(this.cubeQuery.get(1).GammaExpressions, newQuery.GammaExpressions);
		copyListofArrayString(this.cubeQuery.get(1).SigmaExpressions, newQuery.SigmaExpressions);
		newQuery.AggregateFunction=this.cubeQuery.get(1).AggregateFunction;
		newQuery.referCube=this.cubeQuery.get(1).referCube;
		newQuery.setMsr(this.cubeQuery.get(1).getMsr());		
		
		if(index_sigma_change_bygamma>-1){
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[0]=newQuery.GammaExpressions.get(0)[0]+"."+newQuery.GammaExpressions.get(0)[1];
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[2]="'"+valuesToChange[1]+"'";
		}
		/*if(index_sigma_todrillIn>-1){
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[0]=newQuery.GammaExpressions.get(1)[0]+"."+newQuery.GammaExpressions.get(1)[1];
			//newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]="'"+valuesToChange[0]+"'";
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[1]=" IN ";
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]="(";
			for(int x=0;x<row_per_col.size();x++){
				if(x>0 && x<row_per_col.size()) newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+=",";
				newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+="'"+row_per_col.toArray()[x].toString()+"'";
			}
			newQuery.SigmaExpressions.get(index_sigma_todrillIn)[2]+=")";
		}*/
		
		if(child_level_of_gamma!=null){
			newQuery.GammaExpressions.get(0)[1]=child_level_of_gamma;
			newQuery.GammaExpressions.set(0, newQuery.GammaExpressions.set(1,newQuery.GammaExpressions.get(0)));
			//newQuery.GammaExpressions.remove(1);
			//String [] toadd=new String[2];
			//toadd[0]="";
			//toadd[1]="'"+valuesToChange[0]+"'";
			
			//newQuery.GammaExpressions.add(toadd);
			long endTime=System.nanoTime();

			addSubTask(newQuery,-5);
			
			this.getLastSubTask().execute(cubeBase.DB);
			
			//this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
			this.getLastSubTask().timeProduceOfCubeQuery=endTime-strTime;
		}
		else return false;
		return true;
    }
    
   /* @SuppressWarnings("unused")
	private void doDrillIn(CubeBase cubeBase,String[] GammaLaLb,int mode){
    	for(int i=0;i<this.cubeQuery.get(1).GammaExpressions.size();i++){
    		String[] gamma_tmp=this.cubeQuery.get(1).GammaExpressions.get(i); /*get Gamma Li* /
    		int index_sigma_todelete=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,gamma_tmp[0]);
    		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
    		if(child_level_of_gamma!=null) {
    			this.createSubTask(this.cubeQuery.get(1), GammaLaLb[i], index_sigma_todelete,child_level_of_gamma,i,mode);
    			this.getLastSubTask().execute(cubeBase.DB);
    			
    		}
    	}
    }*/
    
    private int getIndexOfSigmaToDelete(ArrayList<String[]> sigmaExpressions,String gamma_dim) {
		int ret_value=-1;
		int i=0;
		for(String[] sigma : sigmaExpressions ){
			if(sigma[0].split("\\.")[0].equals(gamma_dim)) ret_value=i;
			i++;
		}
		return ret_value;
	}

	private String getChildOfGamma(CubeBase cubeBase, String[] gamma_tmp) {
		String ret_value=null;
		for(int i=0;i<cubeBase.dimensions.size();i++){
			Dimension tmp=cubeBase.dimensions.get(i);
			if(tmp.name.equals(gamma_tmp[0])){
				for(Hierarchy hier: tmp.getHier()){
					for(int j=0;j<hier.lvls.size();j++){
						if(hier.lvls.get(j).name.equals(gamma_tmp[1])){ 
							if(j>0) ret_value=hier.lvls.get(j-1).name;
						}
					}
				}
			}
		}
		return ret_value;
	}

	/*private void createSubTask(CubeQuery startQuery, String value, int index_sigma_todelete, String child_level_of_gamma, int index_gamma, int mode){
    	CubeQuery newQuery=new CubeQuery("");
		copyListofArrayString(startQuery.GammaExpressions, newQuery.GammaExpressions);
		copyListofArrayString(startQuery.SigmaExpressions, newQuery.SigmaExpressions);
		newQuery.AggregateFunction=startQuery.AggregateFunction;
		newQuery.referCube=startQuery.referCube;
		newQuery.setMsr(startQuery.getMsr());		
		
		if(index_sigma_todelete>-1){
			newQuery.SigmaExpressions.get(index_sigma_todelete)[0]=startQuery.GammaExpressions.get(index_gamma)[0]+"."+startQuery.GammaExpressions.get(index_gamma)[1];
			newQuery.SigmaExpressions.get(index_sigma_todelete)[2]="'"+value+"'";
		}
		newQuery.GammaExpressions.get(index_gamma)[1]=child_level_of_gamma;
		//newQuery.GammaExpressions.remove((index_gamma+1)%2);
		
    }*/
    
    private void addSubTask(CubeQuery cubequery,int difference){
    	this.addNewSubTask();
		this.cubeQuery.add(cubequery);
        SqlQuery newSqlQuery=new SqlQuery();
        long strTime=System.nanoTime();
        newSqlQuery.produceExtractionMethod(cubequery);
        this.getLastSubTask().timeProduceOfExtractionMethod=System.nanoTime()-strTime;
        cubequery.sqlQuery=newSqlQuery;
        this.getLastSubTask().setExtractionMethod(newSqlQuery);
        this.getLastSubTask().addDifferenceFromOrigin(difference);
    }
    	
	/*void printStringArrayList(ArrayList<String> toprint){
		printBorderLine();
		for(String x: toprint) {
			System.out.println(x);
		}
		printBorderLine();
	}*/
	
		
	/*void printBorderLine(){
    	System.out.println("=====================================");
    }*/
	
	void copyListofArrayString(ArrayList<String[]> from,ArrayList<String[]> to){
		for(int i=0;i<from.size();i++){
			String[] old=from.get(i);
			String[] toadd=new String[old.length];
			for(int j=0;j<old.length;j++){
				toadd[j]=old[j];
			}
			to.add(toadd);
		}
	}
	
	/*ArrayList<String> getUniqueValueInListOfArrayString(ArrayList<String[]> lst,int column){
		ArrayList<String> ret_value=new ArrayList<String>();
		for(int i=0;i<lst.size();i++){
			if(ret_value.contains(lst.get(i)[column])==false){
				ret_value.add(new String(lst.get(i)[column]));
			}
		}
		return ret_value;
	}*/
	
	/*String getConnectedCondition(ArrayList<String[]> WhereClause,int condTocheckID){
		String[] condTocheck=WhereClause.get(condTocheckID);
		for(int i=0;i<WhereClause.size();i++){
			if(i!=condTocheckID){
				if(WhereClause.get(i)[0].split("\\.")[0].equals(condTocheck[0].split("\\.")[0])){
					return WhereClause.get(i)[2];
				}
				if(WhereClause.get(i)[2].split("\\.")[0].equals(condTocheck[0].split("\\.")[0])){
					return WhereClause.get(i)[0];
				}
			}
		}
		return null;
	}*/
	
	/*this maybe help for highlight*/
	/*int getMinRow(String[][] table,String Value,int column){ /*For result Array Not Pivot Table* /
		Float min=Float.parseFloat(table[table.length-1][2]);
		int ret_value=2;
		for(int i=2;i<table.length;i++){
			if(table[i][column].equals(Value)){
				if(min>Float.parseFloat(table[i][2])) {
					min=Float.parseFloat(table[i][2]);
					ret_value=i;
				}
			}
		}
		return ret_value;
	}
	
	int getMaxRow(String[][] table,String Value,int column){ /*For result Array Not Pivot Table* /
		Float max=Float.parseFloat(table[2][2]);
		int ret_value=2;
		for(int i=2;i<table.length;i++){
			if(table[i][column].equals(Value)){
				if(max<Float.parseFloat(table[i][2])) {
					max=Float.parseFloat(table[i][2]);
					ret_value=i;
				}
			}
		}
		return ret_value;
	}*/
	
	/*Create highlight*/
	/*void createHighlight(){
		SubTask sbtk=this.getLastSubTask();
    	//String[][] current=sbtk.getExtractionMethod().Res.getResultArray();
    	HighlightTable hltbl=new HighlightTable();
    	//if(current!=null){
	    	//this.highlights.add(hltbl);
	    	sbtk.setHighlight(hltbl);
	    	//hltbl.createMinHightlight(current);
	    	//hltbl.createMaxHightlight(current);
	    	//hltbl.createMiddleHightlight(current); 
	    	/*if(sbtk.getDifferencesFromOrigin().size()>0 && sbtk.getDifferenceFromOrigin(0)==-1){
	    		int tmp_it=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,this.cubeQuery.get(1).GammaExpressions.get(sbtk.getDifferenceFromOrigin(1))[0]);
	    		if(tmp_it>-1 && sbtk.getDifferenceFromOrigin(1)==0) hltbl.setBoldColumn(sbtk.getExtractionMethod().Res.getColPivot(),this.cubeQuery.get(1).SigmaExpressions.get(tmp_it)[2]);
	    		else hltbl.setBoldRow(sbtk.getExtractionMethod().Res.getRowPivot(),this.cubeQuery.get(1).SigmaExpressions.get(tmp_it)[2]);
	    	}* /
    	//}
	}*/
	/*
	private void recreateResultArrayForMinMaxOfArray_v1(){
		
        for(int i=2;i<this.subTasks.size();i++){
        	for(int j=i+1;j<this.subTasks.size();j++){
        		if(this.cubeQuery.get(i).GammaExpressions.get(0)[0].equals(this.cubeQuery.get(j).GammaExpressions.get(0)[0])){
        			if(this.subTasks.get(i).getDifferenceFromOrigin(0)!=this.subTasks.get(j).getDifferenceFromOrigin(0) 
        					&& !substodelete.contains(this.subTasks.get(j)) && !subschecked.contains(this.subTasks.get(i))){
        				
        				String[][] tmp_i=this.subTasks.get(i).getExtractionMethod().Res.getResultArray();
        				String[][] tmp_j=this.subTasks.get(j).getExtractionMethod().Res.getResultArray();
        				
        				if(tmp_i!=null && tmp_j!=null){ 
	        				String[][] new_array=new String[tmp_i.length+tmp_j.length-2][tmp_i[0].length]; 
	        				int new_array_iterator=0;
	        				this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().clear();
	        				this.subTasks.get(i).getExtractionMethod().Res.getColPivot().clear();
	        				try{
		        				for(int l=0;l<tmp_i.length;l++) {
		        					
		        					new_array[new_array_iterator][0]=new String(tmp_i[l][0]);	        					
		        					new_array[new_array_iterator][1]=new String(tmp_i[l][1]);
		        					new_array[new_array_iterator][2]=new String(tmp_i[l][2]);
		        					new_array[new_array_iterator][3]=new String(tmp_i[l][3]);
		        					
		        					if(!tmp_i[l][2].equals("measure")) {
		        						this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().add(new String(tmp_i[l][0]));
		        						this.subTasks.get(i).getExtractionMethod().Res.getColPivot().add(new String(tmp_i[l][1]));
		        					}
		        					
		        					new_array_iterator++;
		        				}
		        				
		        				for(int m=0;m<tmp_j.length;m++) {
		        					if(!tmp_j[m][2].equals("measure")){
			        					
			        					new_array[new_array_iterator][0]=new String(tmp_j[m][0]);
			        					this.subTasks.get(i).getExtractionMethod().Res.getColPivot().add(new String(tmp_j[m][1]));
			        					
			        					new_array[new_array_iterator][1]=new String(tmp_j[m][1]);
			        					this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().add(new String(tmp_j[m][0]));
			        					
			        					new_array[new_array_iterator][2]=new String(tmp_j[m][2]);
			        					new_array[new_array_iterator][3]=new String(tmp_j[m][3]);
			        					
			        					new_array_iterator++;
		        					}
		        				}
	        				}
	        				catch(Exception ex){
	        					ex.printStackTrace();
	        				}
	        				this.subTasks.get(i).getExtractionMethod().Res.setResultArray(new_array);
	        				substodelete.add(this.subTasks.get(j));
	        				cubequerytodelete.add(this.cubeQuery.get(j));
	        				subschecked.add(this.subTasks.get(i));
	        			}
        			}
        		}
        	}
        }
        for(SubTask dlt:substodelete) this.subTasks.remove(dlt);
        for(CubeQuery cbq:cubequerytodelete) this.cubeQuery.remove(cbq);
	}*/
		
	/*private void recreateResultArray(int col,int row){
		/*ArrayList<Cu>* /
        for(int i=2;i<this.getNumSubTasks();i++){
        	for(int j=i+1;j<this.getNumSubTasks();j++){
        		if(this.cubeQuery.get(i).GammaExpressions.get(0)[0].equals(this.cubeQuery.get(j).GammaExpressions.get(0)[0])){
        			if(this.getSubTask(i).getDifferenceFromOrigin(0)==this.getSubTask(j).getDifferenceFromOrigin(0) 
        					&& !substodelete.contains(this.getSubTask(j)) && !subschecked.contains(this.getSubTask(i))){
        				String[][] tmp_i=this.getSubTask(i).getExtractionMethod().Res.getResultArray();
        				String[][] tmp_j=this.getSubTask(j).getExtractionMethod().Res.getResultArray();
        				
        				String[][] new_array=new String[tmp_i.length+tmp_j.length-2][tmp_i[0].length]; 
        				int new_array_iterator=0;
        				this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().clear();
        				this.subTasks.get(i).getExtractionMethod().Res.getColPivot().clear();
        				try{
	        				for(int l=0;l<tmp_i.length;l++) {
	        					
	        					new_array[new_array_iterator][0]=new String(tmp_i[l][0]);	        					
	        					new_array[new_array_iterator][1]=new String(tmp_i[l][1]);
	        					new_array[new_array_iterator][2]=new String(tmp_i[l][2]);
	        					new_array[new_array_iterator][3]=new String(tmp_i[l][3]);
	        					
	        					if(!tmp_i[l][2].equals("measure")) {
	        						this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().add(new String(tmp_i[l][row]));
	        						this.subTasks.get(i).getExtractionMethod().Res.getColPivot().add(new String(tmp_i[l][col]));
	        					}
	        					
	        					new_array_iterator++;
	        				}
	        				/*System.arraycopy(tmp_i,0,new_array,0,tmp_i.length);
	        				System.arraycopy(tmp_j,2,new_array,tmp_i.length,tmp_j.length-2);  DEN DOULEVANE SWSTA* /
	        				for(int m=0;m<tmp_j.length;m++) {
	        					if(!tmp_j[m][2].equals("measure")){
		        					
		        					new_array[new_array_iterator][0]=new String(tmp_j[m][0]);
		        					this.subTasks.get(i).getExtractionMethod().Res.getColPivot().add(new String(tmp_j[m][col]));
		        					
		        					new_array[new_array_iterator][1]=new String(tmp_j[m][1]);
		        					this.subTasks.get(i).getExtractionMethod().Res.getRowPivot().add(new String(tmp_j[m][row]));
		        					
		        					new_array[new_array_iterator][2]=new String(tmp_j[m][2]);
		        					new_array[new_array_iterator][3]=new String(tmp_j[m][3]);
		        					
		        					new_array_iterator++;
	        					}
	        				}
        				}
        				catch(Exception ex){
        					ex.printStackTrace();
        				}
        				this.subTasks.get(i).getExtractionMethod().Res.setResultArray(new_array);
        				this.subTasks.get(i).timeCreationOfSbTsk+=this.subTasks.get(j).timeCreationOfSbTsk;
        				this.subTasks.get(i).timeExecutionQuery+=this.subTasks.get(j).timeExecutionQuery;
        				this.subTasks.get(i).timeProduceOfCubeQuery+=this.subTasks.get(j).timeProduceOfCubeQuery;
        				this.subTasks.get(i).timeProduceOfExtractionMethod+=this.subTasks.get(j).timeProduceOfExtractionMethod;
        				substodelete.add(this.getSubTask(j));
        				cubequerytodelete.add(this.cubeQuery.get(j));
        			}
        		}
        	}
        	subschecked.add(this.subTasks.get(i));
        }
        for(SubTask dlt:substodelete) this.subTasks.remove(dlt);
        for(CubeQuery cbq:cubequerytodelete) this.cubeQuery.remove(cbq);
        
	}*/
	
	void customCopyArray(String[][] SlideTable,String[][] currentTable,/*Color[][] colorTable,Color[][] currentColorTable,*/String [][] newTable/*,Color[][] newColorTable*/){
    	
    	int col_width=SlideTable[0].length;
    	TreeSet<String> cols=new  TreeSet<String>();
    	
    	for(int i=2;i<SlideTable[0].length;i++) cols.add(SlideTable[0][i]);
    	for(int i=2;i<currentTable[0].length;i++) cols.add(currentTable[0][i]);
    	
    	if(SlideTable[0].length<currentTable[0].length){
    		col_width=currentTable[0].length;
    	}
    	    	
    	for(int i=0;i<SlideTable.length;i++){
    		if(i==0){
    			newTable[i][0]=SlideTable[i][0];
    			newTable[i][1]=SlideTable[i][1];
    			/*newColorTable[i][0]=colorTable[i][0];
    			newColorTable[i][1]=colorTable[i][1];*/
    			for(int j=0;j<cols.size();j++) {
    				newTable[i][j+2]=cols.toArray()[j].toString();
        			//newColorTable[i][j+2]=colorTable[i][0];
    			}
    		}
    		else {
    			newTable[i][0]=SlideTable[i][0];
    			newTable[i][1]=SlideTable[i][1];
    			/*newColorTable[i][0]=colorTable[i][0];
    			newColorTable[i][1]=colorTable[i][1];*/
    			for(int j=2;j<newTable[i].length;j++) {
    				newTable[i][j]="-";
    				//newColorTable[i][j]=colorTable[0][0];
    				for(int k=0;k<SlideTable[i].length;k++){
    					if(newTable[0][j].equals(SlideTable[0][k])) {
    						newTable[i][j]=SlideTable[i][k];
    		    			//newColorTable[i][j]=colorTable[i][k];
    					}
    				}
    			}
    		}
    	}
    	
    	for(int cols_index=0;cols_index<col_width;cols_index++) {
    		newTable[SlideTable.length][cols_index]="";
    		//newColorTable[colorTable.length][cols_index]=Color.black;
    	}
    	
    	for(int i=0;i<currentTable.length;i++){
    		if(i==0){
    			newTable[SlideTable.length+i][0]=currentTable[i][0];
    			newTable[SlideTable.length+i][1]=currentTable[i][1];
    			/*newColorTable[SlideTable.length+i][0]=colorTable[i][0];
    			newColorTable[SlideTable.length+i][1]=colorTable[i][1];*/
    			for(int j=0;j<cols.size();j++) {
    				newTable[i][j+2]=cols.toArray()[j].toString();
        			//newColorTable[i][j+2]=colorTable[i][0];
    			}
    		}
    		else {
    			newTable[SlideTable.length+i][0]=currentTable[i][0];
    			newTable[SlideTable.length+i][1]=currentTable[i][1];
    			/*newColorTable[SlideTable.length+i][0]=currentColorTable[i][0];
    			newColorTable[SlideTable.length+i][1]=currentColorTable[i][1];*/
    			for(int j=2;j<newTable[i].length;j++) {
    				newTable[SlideTable.length+i][j]="-";
    				//newColorTable[SlideTable.length+i][j]=currentColorTable[0][0];
    				for(int k=0;k<currentTable[i].length;k++){
    					if(newTable[0][j].equals(currentTable[0][k])) {
    						newTable[SlideTable.length+i][j]=currentTable[i][k];
    		    			//newColorTable[SlideTable.length+i][j]=currentColorTable[i][k];
    					}
    				}
    			}
    		}
    	}
    	
    	for(int k=0;k<newTable.length;k++){
    		for(int l=0;l<newTable[k].length;l++) {
    			if(newTable[k][l]==null) {
    				newTable[k][l]="";
    				//newColorTable[k][l]=Color.black;
    			}
    		}
    	}
    }
}
