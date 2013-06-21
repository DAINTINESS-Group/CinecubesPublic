import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import AudioMgr.AudioEngine;
import AudioMgr.MaryTTSAudioEngine;
import CubeMgr.CubeMgr;
import CubeMgr.CubeBase.BasicStoredCube;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Measure;
import CubeMgr.StarSchema.SqlQuery;
import HighlightMgr.HighlightTable;
import ParserMgr.ParserManager;
import StoryMgr.Act;
import StoryMgr.PptxSlideshow;
import StoryMgr.StoryMgr;
import StoryMgr.Tabular;
import StoryMgr.PptxSlide;
import TaskMgr.SubTask;
import TaskMgr.Task;
import TaskMgr.TaskBrothers;
import TaskMgr.TaskDrillIn;
import TaskMgr.TaskMgr;
import TextMgr.TextExtraction;
import TextMgr.TextExtractionPPTX;
import WrapUpMgr.PptxWrapUpMgr;
import WrapUpMgr.WrapUpMgr;


public class MainEngine {
    
	public CubeMgr CubeManager;
	public AudioEngine AudioMgr;
	public StoryMgr StorMgr;
	public TaskMgr TskMgr;
	public TextExtraction TxtMgr;
	public WrapUpMgr WrapUp;
	private ParserManager PrsMng;
	public String dbname;
	ArrayList<Integer> numSlideToRemove=new ArrayList<Integer>();
	ArrayList<PptxSlide> slideToEnd=new ArrayList<PptxSlide>();
	
    MainEngine(){
    	StorMgr=new StoryMgr(); 
    	TskMgr=new TaskMgr();
    }
    
    public String predefinedSqlQueries(String Query){
    	if(Query.length()==0){
    		/*Query="SELECT A.occupation,A.work_class,AVG(hours_per_week) \n" +
        	   "FROM ADULT A, OCCUPATION O, WORK_CLASS W \n" +
        	   "WHERE A.occupation = O.level0 AND O.level1 = 'Blue-collar' \n" +
        	   " AND W.level0 = A.work_class AND W.level2 = 'With-Pay' \n" +
        	   " GROUP BY A.occupation,A.work_class";x
    		Query="SELECT A.education,A.work_class,AVG(hours_per_week) \n" +
    	        	   "FROM ADULT A, EDUCATION E, WORK_CLASS W \n" +
    	        	   "WHERE A.education = E.level0 AND E.level2 = 'Assoc' \n" +
    	        	   " AND W.level0 = A.work_class AND W.level2 = 'With-Pay' \n" +
    	        	   " GROUP BY A.education,A.work_class";*/
    		/*Query="SELECT A.education,A.occupation,AVG(hours_per_week) \n" +
 	        	   "FROM ADULT A, EDUCATION E, OCCUPATION O \n" +
 	        	   "WHERE A.education = E.level0 AND E.level2 = 'Assoc' \n" +
 	        	   " AND A.occupation = O.level0 AND O.level1 = 'Blue-collar' \n" +
 	        	   " GROUP BY A.education,A.occupation";*/
    		Query="SELECT A.education,A.MARITAL_STATUS,AVG(hours_per_week) \n" +
  	        	   "FROM ADULT A, EDUCATION E, MARITAL_STATUS M \n" +
  	        	   "WHERE A.education = E.level0 AND E.level2 = 'University' \n" +
  	        	   " AND A.MARITAL_STATUS = M.level0 AND M.level1 = 'Partner-absent' \n" +
  	        	   " GROUP BY A.education,A.MARITAL_STATUS";
    	}
    	return Query;
    }
    
    public CubeQuery DefaultCubeQuery(){
    	CubeQuery cubequery=new CubeQuery("New Request");
    	cubequery.AggregateFunction="AVG";
    	cubequery.Msr=new ArrayList<Measure>();
    	
    	Measure msrToAdd=new Measure();
    	msrToAdd.id=1;
    	msrToAdd.name="Hrs";
    	msrToAdd.Attr=CubeManager.CBase.DB.getFieldOfSqlTable("adult", "hours_per_week");
    	cubequery.Msr.add(msrToAdd);
    	cubequery.addGammaExpression("marital_dim", "lvl0");
    	cubequery.addGammaExpression("education_dim", "lvl1");
    	
    	cubequery.addSigmaExpression("marital_dim.lvl1", "=", "'Partner-absent'");
    	cubequery.addSigmaExpression("education_dim.lvl2", "=", "'University'");
    	for(BasicStoredCube bsc: CubeManager.CBase.BasicCubes){
    		if(bsc.name.equals("adult_cube")){
    			cubequery.referCube=bsc;
    		}
    	}
    	return cubequery;
    }
    
    public void getCubeQueriesFromFile(File file){
    	Scanner sc;
		try {
			sc = (new Scanner(file)).useDelimiter("@");
			while(sc.hasNext()){
				newRequestCubeQuery(createCubeQueryFromString(sc.next()));/*create Story For Query*/
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    }
    
    public CubeQuery createCubeQueryFromString(String cubeQstring){
    	String[] rows=cubeQstring.trim().split("\n");
    	String nameCQ = null, aggregateFunction = null, measureName = null;
    	String[][] sigma = null;
    	String[][] gamma = null;
    	for(int i=0;i<rows.length;i++){
    		String[] temp=rows[i].split(":");
    		if(temp[0].equals("Name")){
    			nameCQ=temp[1].trim();
    		}
    		else if(temp[0].equals("AggrFunc")){
    			aggregateFunction=temp[1].trim();
    		}
    		else if(temp[0].equals("Measure")){
    			measureName=temp[1];
    		}
    		else if(temp[0].equals("Gamma")){
    			String[] tmp_gamma=temp[1].split(",");
    			gamma=new String[tmp_gamma.length][2];
    			for(int j=0;j<tmp_gamma.length;j++){
    				String[] split_gamma=tmp_gamma[j].trim().split("\\.");
    				gamma[j][0]=split_gamma[0];
    				gamma[j][1]=split_gamma[1];
    			}
    		}
    		else if(temp[0].equals("Sigma")){
    			String[] tmp_sigma=temp[1].split(",");
    			sigma=new String[tmp_sigma.length][3];
    			for(int j=0;j<tmp_sigma.length;j++){
    				String[] split_sigma=tmp_sigma[j].trim().split("=");
    				sigma[j][0]=split_sigma[0];
    				sigma[j][1]="=";
    				sigma[j][2]=split_sigma[1];
    			}
    		}
    	}
    	CubeQuery cubequery=new CubeQuery(nameCQ);
    	cubequery.AggregateFunction=aggregateFunction;
    	/* Must Create Measure In Cube Parser->> I Have Done this */
    	/* Search for Measure */
    	Measure msrToAdd=new Measure();
    	msrToAdd.id=1;
    	msrToAdd.name=measureName;
    	msrToAdd.Attr=CubeManager.CBase.DB.getFieldOfSqlTable("adult", "hours_per_week");
    	cubequery.Msr.add(msrToAdd);
    	/*Need work to done up here */
    	
    	for(int i=0;i<gamma.length;i++){
	    	cubequery.addGammaExpression(gamma[i][0], gamma[i][1]);	    	
    	}
    	
    	for(int i=0;i<sigma.length;i++){
    		cubequery.addSigmaExpression(sigma[i][0],sigma[i][1], sigma[i][2]);
    	}
    	for(BasicStoredCube bsc: CubeManager.CBase.BasicCubes){
    		if(bsc.name.equals("adult_cube")){
    			cubequery.referCube=bsc;
    		}
    	}
    	
    	return cubequery;
    }
    
    public void newRequestCubeQuery(CubeQuery cubequery){
    	if(cubequery==null) cubequery=DefaultCubeQuery();
    	TxtMgr=new TextExtractionPPTX();
    	AudioMgr=new MaryTTSAudioEngine();
        AudioMgr.InitializeVoiceEngine();
    	StorMgr.createStory();
    	StorMgr.createTasks(TskMgr);
    	/*==============Act 0===================*/
    	StorMgr.getStory().createNewAct();
    	StorMgr.getStory().getLastAct().setId(0);
    	TaskBrothers taskAct0=new TaskBrothers();
    	TskMgr.createNewTask(taskAct0);
    	StorMgr.addNewTaskToStory(taskAct0);
    	TskMgr.getLastTask().cubeQuery.add(cubequery);
    	SetupSlideEpisodes(StorMgr.getStory().getLastAct());
    	
    	/*==============Act 1===================*/
    	StorMgr.getStory().createNewAct();
    	StorMgr.getStory().getLastAct().setId(1);
        
        TskMgr.createNewTask(new TaskBrothers());
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),"I"));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(TskMgr.getLastTask().cubeQuery.get(1));
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(newSqlQuery);
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        if(TskMgr.getLastTask().getLastSubTask().getExtractionMethod().Res.getResultArray()==null){
        	System.err.println("Your query does not have result. Try again!");
        	return;
        }
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
                
        SetupSlideEpisodes(StorMgr.getStory().getLastAct());
        
        /*============== Act 2 ===================*/
        StorMgr.getStory().createNewAct();
        StorMgr.getStory().getLastAct().setId(2);
        TaskDrillIn tskdrillin=new TaskDrillIn();
        TskMgr.createNewTask(tskdrillin);
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),"II"));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().cubeQuery.add(cubequery);        
        SqlQuery newSqlQuery2=new SqlQuery();
        newSqlQuery2.produceExtractionMethod(TskMgr.getLastTask().cubeQuery.get(1));        
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(newSqlQuery);
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        TskMgr.getLastTask().cubeQuery.get(1).sqlQuery=newSqlQuery;
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        
        SetupSlideEpisodes(StorMgr.getStory().getLastAct());
       
        StorMgr.getStory().setFinalResult(new PptxSlideshow());
        StorMgr.getStory().getFinalResult().setFilename("ppt/"+cubequery.name+".pptx");
        
        
        /*====== Auxiliary SLIDE to the END OF STORY =========*/
        StorMgr.getStory().getActs().set(2, StorMgr.getStory().getActs().set(3,StorMgr.getStory().getAct(2) ));
        
        /*============== Act END ===================*/
        StorMgr.getStory().createNewAct();
    	StorMgr.getStory().getLastAct().setId(-1);
    	SetupSlideEpisodesEndAct(StorMgr.getStory().getActs(),StorMgr.getStory().getLastAct());
        
        WrapUp=new PptxWrapUpMgr();
        WrapUp.setFinalResult(StorMgr.getStory().getFinalResult());
        WrapUp.doWrapUp(StorMgr.getStory());
    }

	/* 
     * Maybe the parameter of this function
     * must change to different type (Like Query).....
     * 
     */
    public void NewRequestSqlQuery(String Query){
    	
        PrsMng.parse(predefinedSqlQueries(Query));
        SqlQuery query=new SqlQuery(PrsMng.aggregatefunc,PrsMng.tablelst,PrsMng.conditionlst,PrsMng.groupperlst);
        
        StorMgr.createStory();
        /*StorMgr.createStoryOriginalRequest();*/
        StorMgr.createTasks(TskMgr);
        TaskBrothers tskBrths=new TaskBrothers();
        TskMgr.createNewTask(tskBrths);
        StorMgr.addNewTaskToStory(tskBrths);
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(query);
        
        
        
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        //TskMgr.getLastTask().getLastSubTask().computeFinding();
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        AudioMgr=new MaryTTSAudioEngine();
        AudioMgr.InitializeVoiceEngine();
        
        SetupSlideEpisodes(StorMgr.getStory().getLastAct());
        
        StorMgr.getStory().setFinalResult(new PptxSlideshow());
        /*StorMgr.getStory().getFinalResult().setFilename("ppt/q6.pptx");
        
        WrapUp=new PptxWrapUpMgr();
        WrapUp.setFinalResult(StorMgr.getStory().getFinalResult());
        WrapUp.doWrapUp(StorMgr.getStory());*/
        
    }
    
    public void SetTheInputCmd(){
    	
    }
    
    public SqlQuery createCubeQueryStartOfActSlide(Act act,String num_act){
    	CubeQuery cubequery=new CubeQuery("Act "+String.valueOf(num_act));
    	cubequery.AggregateFunction="Act "+String.valueOf(num_act);
    	Measure msrToAdd=new Measure();
    	msrToAdd.id=1;
    	msrToAdd.name="Hrs";
    	msrToAdd.Attr=null;
    	cubequery.Msr.add(msrToAdd);
    	cubequery.referCube=null;
    	act.getTask().cubeQuery.add(cubequery);
    	SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(cubequery);
    	        
        return newSqlQuery;
    	
    }
    
    
    private void SetupSlideEpisodesEndAct(ArrayList<Act> acts,Act currenctAct) {
    	PptxSlide newSlide=new PptxSlide();
    	newSlide.Notes="";
    	newSlide.Title="Summary";
    	    	
    	for(Act actItem: acts){
    		if(actItem.ActHighlights.length()>0){
    			if(newSlide.Notes.length()>0) newSlide.Notes+="\n";
    			newSlide.Notes+=actItem.ActHighlights;
    		}
    	}
    	newSlide.Notes=newSlide.Notes.replace("\n\n\n", "\n").replace("\n\n", "\n");
    	newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    	AudioMgr.CreateSound(newSlide.Notes, newSlide.getAudio().getFileName());
    	currenctAct.addEpisode(newSlide);
	}
    
    /* This Function Add Create Slide per ACT
     *  
     * Maybe this Function to add in each Task!!
     * 
     */
    public void SetupSlideEpisodes(Act act){
    	//SqlQuery original=(SqlQuery)act.getTask().getSubTask(0).getExtractionMethod();
    	int timesIN=0;
    	boolean swap_first=false;
    	
    	System.out.println("Sum of subtasks:"+act.getTask().getNumSubTasks());
    	SubTask origSubtsk=new SubTask();
    	if(act.getId()>0){
	    	for(int j=0;j<act.getTask().getNumSubTasks();j++){
	    		SubTask subtsk=act.getTask().getSubTask(j);
	    		if(j==1) {
	    			origSubtsk=act.getTask().getSubTask(j);
	    		}
	    		SqlQuery currentSqlQuery=((SqlQuery)subtsk.getExtractionMethod());
		        if((currentSqlQuery.Res.getResultArray()!=null)){
		        	timesIN++;
		        	PptxSlide newSlide=new PptxSlide();
			        
			        Tabular tbl=new Tabular();
			        String[] extraPivot=new String[2];
			        extraPivot[0]="";
			        extraPivot[1]="";
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && (subtsk.getDifferencesFromOrigin().get(0)==-2 || subtsk.getDifferencesFromOrigin().get(0)==-3)){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getRowPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }/*to delete if*/
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-4){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getRowPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }
			        if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-5){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getColPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }
			       
			        tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(), subtsk.getExtractionMethod().Res.getColPivot(), 
							subtsk.getExtractionMethod().Res.getResultArray(),extraPivot);
			        if(j>0 && act.getTask().highlights.size()>0) {
			        	tbl.setColorTable((HighlightTable) act.getTask().highlights.get(j-1));
			        }
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && (subtsk.getDifferencesFromOrigin().get(0)==-4 || subtsk.getDifferencesFromOrigin().get(0)==-5)&& subtsk.getDifferencesFromOrigin().get(1)>0){
			        	/* the code above to be function */
			        	PptxSlide tmpSlide=(PptxSlide)StorMgr.getStory().getLastAct().getEpisode(StorMgr.getStory().getLastAct().getNumEpisodes()-1);
			        	String [][] SlideTable=tmpSlide.getVisual().getPivotTable();
			        	Color [][] colorTable=((Tabular)tmpSlide.getVisual()).colortable;
			        	String [][] currentTable=tbl.getPivotTable();
			        	Color [][] currentColorTable=tbl.colortable;
			        	String [][] newTable;
			        	Color[][] newColorTable;
			        	int col_width=SlideTable[0].length;
			        	int rows_width=SlideTable.length+currentTable.length;
			        	if(SlideTable[0].length<currentTable[0].length){
			        		col_width=currentTable[0].length;		        	
			        	}
			        	
			        	newTable=new String[rows_width+1][col_width];
			        	newColorTable=new Color[rows_width+1][col_width];
			        	
			        	System.arraycopy(SlideTable, 0, newTable, 0, SlideTable.length);
			        	System.arraycopy(colorTable, 0, newColorTable, 0, colorTable.length);
			        	
			        	for(int cols_index=0;cols_index<col_width;cols_index++) {
			        		newTable[SlideTable.length][cols_index]="";
			        		newColorTable[colorTable.length][cols_index]=Color.black;
			        	}
			        	
			        	System.arraycopy(currentTable,0,newTable,SlideTable.length+1,currentTable.length);
			        	System.arraycopy(currentColorTable,0,newColorTable,colorTable.length+1,currentColorTable.length);
			        	tmpSlide.getVisual().setPivotTable(newTable);
			        	tmpSlide.addSubTask(subtsk);
			        	((Tabular)tmpSlide.getVisual()).colortable=newColorTable;
			        	
			        	
			        	for(int k=0;k<newTable.length;k++){
			        		for(int l=0;l<newTable[k].length;l++) {
			        			if(newTable[k][l]==null) {
			        				newTable[k][l]="";
			        				newColorTable[k][l]=Color.black;
			        			}
			        		}
			        	}
			        }
			        else{
				        newSlide.setVisual(tbl);
				        newSlide.addSubTask(subtsk);
				        newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
				        StorMgr.getStory().getLastAct().addEpisode(newSlide);
			        }
		        }
		        else if(currentSqlQuery.Res.TitleOfColumns!=null && currentSqlQuery.Res.TitleOfColumns.contains("Act")) {
		        	PptxSlide newSlide=new PptxSlide();
			        newSlide.addSubTask(subtsk);
		        	newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
		        	if(act.getId()==1) {
		        		swap_first=true;
		        		newSlide.Title+=": Putting results in context";
		        		newSlide.SubTitle="In this series of slides we will put the original result in context, by comparing the behavior of its defining values with the behavior of values that are similar to them.";
		        		newSlide.Notes=newSlide.Title+"\n"+newSlide.SubTitle;
		        	}
		        	else if(act.getId()==2){
		        		newSlide.Title+=": Explaining results";
		        		newSlide.Notes=newSlide.Title;
		        	}
		        	if(newSlide.Notes.length()>0){
			        	newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
			        	AudioMgr.CreateSound(newSlide.Notes, newSlide.getAudio().getFileName());
		        	}
		        	StorMgr.getStory().getLastAct().addEpisode(newSlide);	        	
		        }
	    	}
	    	SetupTextOfEpisodes(act,act.getId());
	    	if(swap_first && act.getEpisodes().size()>1){
	    		PptxSlide tmpslide=(PptxSlide) act.getEpisodes().get(0);
	    		act.getEpisodes().set(0, act.getEpisodes().get(1));
	    		act.getEpisodes().set(1, tmpslide);
	    	}
    	}
    	else if(act.getId()==0) {
    		PptxSlide tmpslide=new PptxSlide();
    		tmpslide.Title="CineCube Report";
    		tmpslide.SubTitle=((TextExtractionPPTX)TxtMgr).createTxtForIntroSlide(act.getTask().cubeQuery.get(0).GammaExpressions,
															    			  act.getTask().cubeQuery.get(0).SigmaExpressions, 
		 													    			  act.getTask().cubeQuery.get(0).AggregateFunction, 
		 													    			  act.getTask().cubeQuery.get(0).Msr.get(0).name);
    		tmpslide.Notes=tmpslide.Title+"\n"+tmpslide.SubTitle;
    		tmpslide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    		AudioMgr.CreateSound(tmpslide.Notes, tmpslide.getAudio().getFileName());
    		act.addEpisode(tmpslide);
		}
    	System.out.println("TimesIN:"+timesIN);
    }

    void SetupTextOfEpisodes(Act act, int num_act){
    	SubTask origSubtsk=new SubTask();   
    	CubeQuery origCubeQuery = null;
    	int i_cubequery=1;
    	
    	for(int j=1;j<act.getNumEpisodes();j++){
    		if(j==1) {
    			origSubtsk=act.getTask().getSubTask(j);
    			origCubeQuery=act.getTask().cubeQuery.get(j);
    		}
    		
    		PptxSlide currentSlide=(PptxSlide) act.getEpisode(j);
    		SubTask subtsk=currentSlide.getSubTask().get(0);
    		CubeQuery currentCubeQuery=act.getTask().cubeQuery.get(i_cubequery);
			SqlQuery currentSqlQuery=(SqlQuery) subtsk.getExtractionMethod();
			Tabular tbl=(Tabular) currentSlide.getVisual();
    		HighlightTable htable=(HighlightTable) act.getTask().highlights.get(i_cubequery-1);
    		
    		
    		if(currentSlide.getSubTask().size()>1 && tbl.colortable!=null){
    			htable.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
    			currentSlide.Notes="";
    			if(subtsk.getDifferencesFromOrigin().get(0)==-4){
		        	currentSlide.Title="Drill In Slide For Rows of Original";
		        	
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   (HighlightTable) act.getTask().highlights.get(i_cubequery-1),
							   0,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getRowPivot().size(),
							   ((SqlQuery)currentSlide.getSubTask().get(0).getExtractionMethod()).GroupByClause.get(0));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v2(tbl.getPivotTable());
		        	String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowsColumns(tbl.getPivotTable(),tbl.colortable,(HighlightTable) act.getTask().highlights.get(i_cubequery-1));
		        	currentSlide.Notes+=add_to_notes;
		        	act.ActHighlights+=add_to_notes.split(":")[1];
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-5){
		        	currentSlide.Title="Drill In Slide For Columns of Original";
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   (HighlightTable) act.getTask().highlights.get(i_cubequery-1),
							   1,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getColPivot().size(),
							   ((SqlQuery)currentSlide.getSubTask().get(0).getExtractionMethod()).GroupByClause.get(0));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v3(tbl.getPivotTable());
		        	String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowsColumns(tbl.getPivotTable(),tbl.colortable,(HighlightTable) act.getTask().highlights.get(i_cubequery-1));
		        	currentSlide.Notes+=add_to_notes;
		        	act.ActHighlights+=add_to_notes.split(":")[1];;
		        }
    			
    			AudioMgr.CreateSound(currentSlide.Notes, currentSlide.getAudio().getFileName());
    		}
    		else{
    			htable.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
    			currentSlide.Notes="";
    			
    			currentSlide.TitleColumn=new String(currentSqlQuery.Res.TitleOfColumns);
    			currentSlide.TitleRow=new String(currentSqlQuery.Res.TitleOfRows);
    			if(subtsk.getDifferencesFromOrigin().size()==0){
		        	currentSlide.Title="Original";
		        	if(num_act==1) currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct1(currentCubeQuery.GammaExpressions, currentCubeQuery.SigmaExpressions, currentSqlQuery.Res.getResultArray(),(HighlightTable) act.getTask().highlights.get(i_cubequery-1));
		        	else currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct2(currentCubeQuery.GammaExpressions, currentCubeQuery.SigmaExpressions, currentSqlQuery.Res.getResultArray(),(HighlightTable) act.getTask().highlights.get(i_cubequery-1));
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-1){
		        	int gamma_index_change=subtsk.getDifferenceFromOrigin(1);
		        	currentSlide.Title="Assessing the behavior of ";
		        	currentSlide.Title+=act.getTask().cubeQuery.get(1).GammaExpressions.get(gamma_index_change)[0].replace("_dim", "");
		        	currentSlide.getVisual().getPivotTable()[0][0]=" Summary for "+act.getTask().cubeQuery.get(1).GammaExpressions.get(gamma_index_change)[0].split("_")[0];
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct1(origCubeQuery.GammaExpressions,origCubeQuery.SigmaExpressions,currentCubeQuery.SigmaExpressions,currentSqlQuery.Res.getResultArray(),(HighlightTable) act.getTask().highlights.get(i_cubequery-1),subtsk.getDifferenceFromOrigin(1),currentCubeQuery.AggregateFunction,currentCubeQuery.Msr.get(0).name);
		        	
		        	if(gamma_index_change==0) {
		        		htable.ComparingToSiblingColumn_v1(tbl.getPivotTable());
		        		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v1(tbl.getPivotTable(),htable.boldColumn,htable);
		        		currentSlide.Notes+="\n"+add_to_notes;
		        		act.ActHighlights+=add_to_notes;
		        	}
		        	else {
		        		htable.ComparingToSiblingRow_v1(tbl.getPivotTable());
		        		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingRow_v1(tbl.getPivotTable(),htable);
		        		currentSlide.Notes+="\n"+add_to_notes;
		        		act.ActHighlights+=add_to_notes;
		        	}
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-2){
		        	currentSlide.Title="Drill In Slide For Row "+String.valueOf(i_cubequery-1)+" of Original";
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-3){
		        	currentSlide.Title="Drill In Slide For Row "+String.valueOf(i_cubequery-1)+" of Original";
		        }
		        else {
		        	slideToEnd.add(currentSlide);
		        	numSlideToRemove.add(j);
		        	currentSlide.Title="The ~ which changed @ : ";
		        	for(int i=0;i<subtsk.getDifferencesFromOrigin().size();i++){
			        	if(i>0) currentSlide.Title+=" AND ";
			        	currentSlide.Title+=act.getTask().cubeQuery.get(1).GammaExpressions.get(subtsk.getDifferenceFromOrigin(i))[0];
			        }
		        	String text_cond="Conditions";
			        String text_are="are";
			        if(subtsk.getDifferencesFromOrigin().size()==1){
			        	text_cond="Condition";
			        	text_are="is";
			        }
		        	currentSlide.Title=currentSlide.Title.replace("~", text_cond).replace("@", text_are);
		        }
    			currentSlide.Notes=currentSlide.Notes.replace("_dim.", " at ").replace("_dim", " ").replace("lvl", " level ").replace("  ", " ");
    			AudioMgr.CreateSound(currentSlide.Notes, currentSlide.getAudio().getFileName());
    			i_cubequery++;
    		}
    	}
    	
    	
    	if(slideToEnd.size()>0){
    		/*======== Act 3 =============*/
    		StorMgr.getStory().createNewAct();
    		StorMgr.getStory().getLastAct().setId(3);
    		PptxSlide newSlide=new PptxSlide();
        	newSlide.Title="Auxiliary slides for Act I";
        	StorMgr.getStory().getLastAct().addEpisode(newSlide);
        	for(int k=0;k<slideToEnd.size();k++) {
        		act.getEpisodes().remove(slideToEnd.get(k));
        		StorMgr.getStory().getLastAct().addEpisode(slideToEnd.get(k));
        	}
        	slideToEnd.clear();
    	}
    }
    
    public File GetFileCmds(){
    	File file=null;
    	JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            System.out.println("Opening: " + file.getAbsolutePath() + ".");
        } else {
        	System.out.println("Open command cancelled by user.");
        }
        return file;
    }
        
	public void ParseFile(File file){
		if(file!=null){
	    	try {
	    		PrsMng=new ParserManager();
				Scanner  sc=(new Scanner(file)).useDelimiter(";");
				while(sc.hasNext()){
					//ParseCreation(sc.next().replaceAll("(?m)^[ \t]*\r?\n", ""));
					PrsMng.parse(sc.next()+";");
					if(PrsMng.mode==2){
						this.CubeManager.InsertionDimensionLvl(PrsMng.name_creation,PrsMng.sqltable,PrsMng.originallvllst,PrsMng.customlvllst,PrsMng.dimensionlst);
						//System.out.println("DIMENSION");
					}
					else if(PrsMng.mode==1){
						//System.out.println("CUBE");
						this.CubeManager.InsertionCube(PrsMng.name_creation,PrsMng.sqltable,PrsMng.dimensionlst,PrsMng.originallvllst,PrsMng.measurelst,PrsMng.measurefields);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
   	
	public void ParseCreation(String Inpstr){
		if(Inpstr.length()>0){
			if(Inpstr.startsWith("CREATE CUBE")){
				//System.out.println("To Do things for Cube");
				this.ParseCrtCube(Inpstr.trim().split("\\s+"));
			}
			else if(Inpstr.startsWith("CREATE DIMENSION")){
				this.ParseCrtLevels(Inpstr.trim().split("\\s+"));
				//System.out.println("To Do things for Dimension");
			}
			else {
				System.out.println("You have Errors"+Inpstr);
			}
		}
	}
	
	/* This Function Parse this String :
	 * 	CREATE CUBE "custom name" RELATED SQL_TABLE "name of table"
	 *  REFERENCES DIMENSION "name_dimension",......,"name_dimension";
	 */
	public void ParseCrtCube(String[] crt_cb){
		String CubeName;
		String SqlTable;
		String[] name_of_dimension;
		boolean error=true;
		//for(int i=0;i<crt_cb.length;i++) System.out.println(crt_cb[i]);
		//System.out.println("Length: "+crt_cb.length);
		if(crt_cb.length==9){
			if(crt_cb[0].toUpperCase().equals("CREATE")){
				if(crt_cb[1].toUpperCase().equals("CUBE")){
					CubeName=crt_cb[2];
					System.out.println("CubeName: "+CubeName);
					if(crt_cb[3].toUpperCase().equals("RELATED")){
						if(crt_cb[4].toUpperCase().equals("SQL_TABLE")){
							SqlTable=crt_cb[5];
							System.out.println("SqlTblName: "+SqlTable);
							if(crt_cb[6].toUpperCase().equals("REFERENCES")){
								if(crt_cb[7].toUpperCase().equals("DIMENSION")){
									name_of_dimension=crt_cb[8].split(",");
									System.out.print("Dimensions:");
									for(int i=0;i<name_of_dimension.length;i++) System.out.print(name_of_dimension[i]+",");
									this.printBorderLines();
									error=false;
								}
							}
						}
					}
				}
			}
		}
		
		if(error){
			this.printParseError(crt_cb);
		}
	}
	
	void printParseError(String[] token){
		System.out.println("Parse Error In Statment:");
		for(int i=0;i<token.length;i++) System.out.println(i+": "+token[i]);
	}
	
	void printBorderLines(){
		System.out.println();
		System.out.println("=====================================");
		System.out.println("=====================================");
	}
	
	/* This Function Parse this String :
	 * 	CREATE DIMENSION "custom name" 
	 *  RELATED SQL_TABLE "name of table"
	 *  LIST OF LEVEL { "field_table" as "custom_name",
	 *  				.
	 *  				.
	 *  				.
	 *  				,"field_table" as "custom_name" }
	 *  HIERACHY "custom_name">"custom_name" .... >"custom_name";
	 */
	public void ParseCrtLevels(String[] crt_lvl){
		String DimensionName = null;
		String DimensionTbl = null;
		ArrayList<String> Fld_Name=new ArrayList<>();
		ArrayList<String> CustomFld_Name=new ArrayList<>();
		
		String[] Hierachy = null;
		int i=9;
		boolean error=true;
		//for(int i=0;i<crt_cb.length;i++) System.out.println(crt_cb[i]);
		if(crt_lvl[0].toUpperCase().equals("CREATE")){
			if(crt_lvl[1].toUpperCase().equals("DIMENSION")){
				DimensionName=crt_lvl[2];
				System.out.println("Dimension Name: "+DimensionName);
				if(crt_lvl[3].toUpperCase().equals("TO")){
					if(crt_lvl[4].toUpperCase().equals("DIMENSION_TABLE")){
						DimensionTbl=crt_lvl[5];
						System.out.println("Dimension Table: "+DimensionTbl);
						if(crt_lvl[6].toUpperCase().equals("LIST")){
							if(crt_lvl[7].toUpperCase().equals("OF")){
								if(crt_lvl[8].toUpperCase().equals("LEVEL")){
									if(crt_lvl[9].equals("{")){
										boolean mode=true;
										while(crt_lvl[i].equals("}")==false){
											if(crt_lvl[i].toUpperCase().equals("AS")) mode=false;
											else if(crt_lvl[i].equals(",")) mode=true;
											i++;
											if(mode) Fld_Name.add(crt_lvl[i]);
											else CustomFld_Name.add(crt_lvl[i]);
											i++;
										}
										System.out.println("Levels:");
										for(int j=0;j<Fld_Name.size();j++){
											System.out.println(Fld_Name.get(j)+" AS "+CustomFld_Name.get(j));
										}
										i++;
										if(crt_lvl[i].toUpperCase().equals("HIERARCHY")){
											error=false;
											Hierachy=crt_lvl[i+1].split(">");
											System.out.print("Hierachy:");
											for(int j=0;j<Hierachy.length;j++) System.out.print(Hierachy[j]+">");
											this.printBorderLines();
											i++;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(error || crt_lvl.length==i || Fld_Name.size()!=CustomFld_Name.size()){
			this.printParseError(crt_lvl);
		}
		//else this.CubeManager.InsertionDimensionLvl(DimensionName,DimensionTbl,Fld_Name,CustomFld_Name,Hierachy);
	}
	
    public void InitializeCubeMgr() {
    	CubeManager=new CubeMgr();
    }
    
    void createDefaultFolders(){
    	File audio=new File("audio");
    	if(!audio.exists()){
    		audio.mkdir();
    	}
    	audio.deleteOnExit();
    	File ppt=new File("ppt");
    	if(!ppt.exists()){
    		ppt.mkdir();
    	}
    }
    
    public static void main(String[] args) {
        MainEngine MainEng=new MainEngine();
        MainEng.InitializeCubeMgr();
        MainEng.createDefaultFolders();
        MainEng.CubeManager.CreateCubeBase(MainEng.InsertFromKeyboardDBInfos());        
        //Me.ParseFile(Me.GetFileCmds());
        
        MainEng.ParseFile(new File("InputFiles/BETA/beta.txt"));/*Create Dimension,Cube*/
        //MainEng.NewRequestSqlQuery("");
        MainEng.getCubeQueriesFromFile(new File("InputFiles/cubeQueries2013_05_31.ini"));/*Create Stories*/
//      MainEng.newRequestCubeQuery(null);
        
        System.out.println("=======Finish======");
        //marytts.server.Mary.shutdown();
        System.exit(0);
    }
    
    public String InsertFromKeyboardDBInfos(){
    	dbname="adult_no_dublic";
    	return dbname;
    }
}
