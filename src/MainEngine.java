import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileOutputStream;

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
import StoryMgr.FinResult;
import StoryMgr.StoryMgr;
import StoryMgr.Tabular;
import StoryMgr.PptxSlide;
import TaskMgr.SubTask;
import TaskMgr.TaskActI;
import TaskMgr.TaskActII;
import TaskMgr.TaskIntro;
import TaskMgr.TaskMgr;
import TaskMgr.TaskOriginal;
import TaskMgr.TaskSummary;
import TextMgr.TextExtraction;
import TextMgr.TextExtractionPPTX;
import WrapUpMgr.PptxWrapUpMgr;
import WrapUpMgr.WordMgr;
import WrapUpMgr.WrapUpMgr;

public class MainEngine extends UnicastRemoteObject implements
IMainEngine {
    
	
	public CubeMgr CubeManager;
	public AudioEngine AudioMgr;
	public StoryMgr StorMgr;
	public TaskMgr TskMgr;
	public TextExtraction TxtMgr;
	public WrapUpMgr WrapUp;
	private ParserManager PrsMng;
	private Options OptMgr;
	ArrayList<Integer> numSlideToRemove=new ArrayList<Integer>();
	ArrayList<PptxSlide> slideToEnd=new ArrayList<PptxSlide>();
	PrintWriter cubeQueryWriter;
	String act_story_time;
	int slideToPrint=0;
	
	
	
	File queryFile; //File with one or more queries
	public static String dbname; //Database Name
    public static String username;//Database username
    public static String password ;//Database password
    public static String inputlookup;
    public static String msrname;
    
	MainEngine() throws RemoteException{
		super();
    	StorMgr=new StoryMgr(); 
    	TskMgr=new TaskMgr();
    	OptMgr = new Options();
    	
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
    
    public void AnswerCubeQueriesFromFile(File file) throws RemoteException{
    	Scanner sc;
    	TxtMgr=new TextExtractionPPTX();
		//long initial_audio_time=System.nanoTime();
    	AudioMgr=new MaryTTSAudioEngine();
        AudioMgr.InitializeVoiceEngine();
        //System.out.println("Init Time of Audio\t"+(System.nanoTime()-initial_audio_time));
		try {
			sc = (new Scanner(file)).useDelimiter("@");
			while(sc.hasNext()){
				act_story_time="";
				long startTime = System.nanoTime();
				CubeQuery currentCubQuery=createCubeQueryFromString(sc.next());
				/*System.out.println("Time Creation for Original Cube Query from String"+"\t"+(System.nanoTime()-startTime));
				
				try {
					cubeQueryWriter= new PrintWriter(currentCubQuery.name+"_cubeQuery.ini", "UTF-8");
					cubeQueryWriter.write("");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				};*/
				
				startTime = System.nanoTime();
				newRequestCubeQuery(currentCubQuery);/*create Story For Query*/				
				//System.out.println("Story Creation Time "+"\t"+(System.nanoTime()-startTime));				
				act_story_time+="Story Creation Time "+"\t"+(System.nanoTime()-startTime)+"\n";
				
				/*PrintWriter forconsole=new PrintWriter(currentCubQuery.name+"_ActsStoryTime.txt");
				forconsole.write(act_story_time);
				forconsole.close();
				
				printOutTheTime(StorMgr.getStory().getActs(),currentCubQuery.name);
				cubeQueryWriter.close();*/
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//cubeQueryWriter.close();
    }
    
  
	public CubeQuery createCubeQueryFromString(String cubeQstring)throws RemoteException{	
    	String[] rows=cubeQstring.trim().split("\n");
    	String Cbname = null,nameCQ = null, aggregateFunction = null, measureName = null;
    	String[][] sigma = null;
    	String[][] gamma = null;
    	for(int i=0;i<rows.length;i++){
    		String[] temp=rows[i].split(":");
    		if(temp[0].equals("CubeName")){
    			Cbname=temp[1].trim();
    		}
    		if(temp[0].equals("Name")){
    			nameCQ=temp[1].trim();
    		}
    		else if(temp[0].equals("AggrFunc")){
    			aggregateFunction=temp[1].trim();
    		}
    		else if(temp[0].equals("Measure")){
    			measureName=temp[1].trim();
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
    	msrToAdd.Attr=CubeManager.CBase.DB.getFieldOfSqlTable(Cbname,measureName);
    	//msrToAdd.Attr=CubeManager.CBase.DB.getFieldOfSqlTable("loan", "amount");
    	cubequery.Msr.add(msrToAdd);
    	msrname=measureName;
    	/*Need work to done up here */
    	
    	for(int i=0;i<gamma.length;i++){
	    	cubequery.addGammaExpression(gamma[i][0], gamma[i][1]);	    	
    	}
    	
    	for(int i=0;i<sigma.length;i++){
    		cubequery.addSigmaExpression(sigma[i][0],sigma[i][1], sigma[i][2]);
    	}
    	for(BasicStoredCube bsc: CubeManager.CBase.BasicCubes){
    		if(bsc.name.equals(Cbname+"_cube")){
    			cubequery.referCube=bsc;
    		}
    	}
    	return cubequery;
    }
    
    public void newRequestCubeQuery(CubeQuery cubequery){
    	long initial_time=System.nanoTime();
    	if(cubequery==null) cubequery=DefaultCubeQuery();
    	
    	StorMgr.createStory();
    	StorMgr.createTasks(TskMgr);
    	//System.out.println("Init Time\t"+(System.nanoTime()-initial_time));
    	act_story_time+="Init Time\t"+(System.nanoTime()-initial_time)+"\n";
    	
    	/*==============Act 0===================*/
    	initial_time=System.nanoTime();
    	StorMgr.getStory().createNewAct();
    	//System.out.println("Act 0 Creation Time\t"+(System.nanoTime()-initial_time));
    	StorMgr.getStory().getLastAct().creationTime=System.nanoTime();
    	StorMgr.getStory().getLastAct().setId(0);
    	TskMgr.createNewTask(new TaskIntro());
    	StorMgr.addNewTaskToStory(TskMgr.getLastTask());
    	TskMgr.getLastTask().cubeQuery.add(cubequery);
    	TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
    	TskMgr.getLastTask().constructActEpidoses(StorMgr.getStory().getLastAct());
    	//SetupSlideEpisodes(StorMgr.getStory().getLastAct(),null,cubequery);
    	constructTxtIntroAct(StorMgr.getStory().getLastAct());
    	StorMgr.getStory().getLastAct().creationTime=System.nanoTime()-StorMgr.getStory().getLastAct().creationTime;
    	//System.out.println("Intro Act\t"+StorMgr.getStory().getLastAct().creationTime);
    	act_story_time+="Intro Act\t"+StorMgr.getStory().getLastAct().creationTime+"\n";
    	/*==============Original Act===================*/
    	initial_time=System.nanoTime();
    	StorMgr.getStory().createNewAct();
    	//System.out.println("Act Orig Creation Time\t"+(System.nanoTime()-initial_time));
    	StorMgr.getStory().getLastAct().creationTime=System.nanoTime();
    	StorMgr.getStory().getLastAct().setId(20);
    	TskMgr.createNewTask(new TaskOriginal());
    	StorMgr.addNewTaskToStory(TskMgr.getLastTask());
    	
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        TskMgr.getLastTask().constructActEpidoses(StorMgr.getStory().getLastAct());
        SubTask OriginSbTsk=TskMgr.getLastTask().getLastSubTask();
    	//OriginSbTsk.setHighlight(new HighlightTable());
        
        
        if(TskMgr.getLastTask().getLastSubTask().getExtractionMethod().Res.getResultArray()==null){
        	System.err.println("Your query does not have result. Try again!");
        	System.exit(2);
        }
        //SetupSlideEpisodes(StorMgr.getStory().getLastAct(),OriginSbTsk,cubequery);
        constructTxtOriginalAct(StorMgr.getStory().getLastAct());
        StorMgr.getStory().getLastAct().creationTime=System.nanoTime()-StorMgr.getStory().getLastAct().creationTime;
    	//System.out.println("Original Act\t"+StorMgr.getStory().getLastAct().creationTime);
    	act_story_time+="Original Act\t"+StorMgr.getStory().getLastAct().creationTime+"\n";
    	/*==============Act 1===================*/
    	
    	initial_time=System.nanoTime();
    	StorMgr.getStory().createNewAct();
    	//System.out.println("Act 1 Creation Time\t"+(System.nanoTime()-initial_time));   	
    	StorMgr.getStory().getLastAct().creationTime=System.nanoTime();
    	StorMgr.getStory().getLastAct().setId(1);
        TskMgr.createNewTask(new TaskActI());
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        
        long timeSbts=System.nanoTime();
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),"I"));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        TskMgr.getLastTask().getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-timeSbts;
        
        TskMgr.getLastTask().getSubTasks().add(OriginSbTsk);
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        /*TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        //SqlQuery newSqlQuery=new SqlQuery();
        //newSqlQuery.produceExtractionMethod(TskMgr.getLastTask().cubeQuery.get(1));
        //TskMgr.getLastTask().getLastSubTask().setExtractionMethod(newSqlQuery);
        //TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);*/
       
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
       // StorMgr.getStory().getLastAct().getTask().cubeQuery.remove(1);
        //SetupSlideEpisodes(StorMgr.getStory().getLastAct(),OriginSbTsk,cubequery);
        StorMgr.getStory().getLastAct().getTask().constructActEpidoses(StorMgr.getStory().getLastAct());
        setupTextAct1(StorMgr.getStory().getLastAct(),cubequery);
        //StorMgr.getStory().getActs().get(StorMgr.getStory().getActs().size()-2).creationTime=System.nanoTime()-StorMgr.getStory().getActs().get(StorMgr.getStory().getActs().size()-2).creationTime;
       
        //System.out.println("Act 1\t"+StorMgr.getStory().getActs().get(StorMgr.getStory().getActs().size()-2).creationTime);
       // act_story_time+="Act 1\t"+StorMgr.getStory().getActs().get(StorMgr.getStory().getActs().size()-2).creationTime+"\n";
        StorMgr.getStory().getLastAct().creationTime=System.nanoTime()-StorMgr.getStory().getLastAct().creationTime;
        act_story_time+="Act 1\t"+StorMgr.getStory().getLastAct().creationTime+"\n";
        
        
        /*============== Act 2 ===================*/
    	
    	initial_time=System.nanoTime();
    	StorMgr.getStory().createNewAct();
    	//System.out.println("Act 2 Creation Time\t"+(System.nanoTime()-initial_time));
        StorMgr.getStory().getLastAct().creationTime=System.nanoTime();
        StorMgr.getStory().getLastAct().setId(2);
        TaskActII tskdrillin=new TaskActII();
        TskMgr.createNewTask(tskdrillin);
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),"II"));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        TskMgr.getLastTask().getSubTasks().add(OriginSbTsk);
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        /*TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().cubeQuery.add(cubequery);        
        SqlQuery newSqlQuery2=new SqlQuery();
        newSqlQuery2.produceExtractionMethod(TskMgr.getLastTask().cubeQuery.get(1));        
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(newSqlQuery);
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        TskMgr.getLastTask().cubeQuery.get(1).sqlQuery=newSqlQuery;
        */
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        if(TskMgr.getLastTask().getNumSubTasks()>2){
        	//SetupSlideEpisodes(StorMgr.getStory().getLastAct(),OriginSbTsk,cubequery);
        	TskMgr.getLastTask().constructActEpidoses(StorMgr.getStory().getLastAct());
        	this.setupTextAct2(StorMgr.getStory().getLastAct(),cubequery,OriginSbTsk);
        }
        StorMgr.getStory().getLastAct().creationTime=System.nanoTime()-StorMgr.getStory().getLastAct().creationTime;
    	//System.out.println("Act 2\t"+StorMgr.getStory().getLastAct().creationTime);
    	act_story_time+="Act 2\t"+StorMgr.getStory().getLastAct().creationTime+"\n";
        /*============== End Act ===================*/
    	initial_time=System.nanoTime();
    	StorMgr.getStory().createNewAct();
    	//System.out.println("Act End Creation Time\t"+(System.nanoTime()-initial_time));
        StorMgr.getStory().getLastAct().creationTime=System.nanoTime();
    	StorMgr.getStory().getLastAct().setId(-1);
    	TskMgr.createNewTask(new TaskSummary());
    	StorMgr.addNewTaskToStory(TskMgr.getLastTask());
    	TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
    	TskMgr.getLastTask().constructActEpidoses(StorMgr.getStory().getLastAct());
   		
    	constructTxtEndAct(StorMgr.getStory().getActs(),StorMgr.getStory().getLastAct());
   		
   		StorMgr.getStory().getLastAct().creationTime=System.nanoTime()-StorMgr.getStory().getLastAct().creationTime;
    	//System.out.println("End Act\t"+StorMgr.getStory().getLastAct().creationTime);
    	act_story_time+="End Act\t"+StorMgr.getStory().getLastAct().creationTime+"\n";
    	/*====== Auxiliary SLIDE to the END OF STORY =========*/
    	//StorMgr.getStory().getActs().set(3, StorMgr.getStory().getActs().set(4,StorMgr.getStory().getAct(3) ));
       // StorMgr.getStory().getActs().set(4, StorMgr.getStory().getActs().set(5,StorMgr.getStory().getAct(4) ));
    	
        /*======== Set The Filename For PPTX ==========================*/
      //Word Wrap Up
    	if(OptMgr.getWord()==true){
        StorMgr.getStory().setFinalResult(new FinResult());
        StorMgr.getStory().getFinalResult().setFilename("OutputFiles/"+cubequery.name+".docx");
        WrapUp = new WordMgr();
        WrapUp.setFinalResult(StorMgr.getStory().getFinalResult());
    	WrapUp.doWrapUp(StorMgr.getStory());
    	}
    	//PPTX Wrap up
    	StorMgr.getStory().setFinalResult(new FinResult());
        StorMgr.getStory().getFinalResult().setFilename("OutputFiles/"+cubequery.name+".pptx");
        WrapUp=new PptxWrapUpMgr();
        WrapUp.setFinalResult(StorMgr.getStory().getFinalResult());
        long strWraUpTime=System.nanoTime();
        WrapUp.doWrapUp(StorMgr.getStory());
       // System.out.print("WrapUp Time\t");
        //System.out.println(TimeUnit.SECONDS.convert(System.nanoTime()-strWraUpTime,TimeUnit.NANOSECONDS)+" sec");
        //System.out.println((System.nanoTime()-strWraUpTime));
        
        
       act_story_time+=((PptxWrapUpMgr)WrapUp).UnZipZipTime;
       act_story_time+="WrapUp Time\t"+(System.nanoTime()-strWraUpTime)+"\n";
    }

	
    
    public SqlQuery createCubeQueryStartOfActSlide(Act act,String num_act){
    	long strTime=System.nanoTime();
    	CubeQuery cubequery=new CubeQuery("Act "+String.valueOf(num_act));
    	cubequery.AggregateFunction="Act "+String.valueOf(num_act);
    	Measure msrToAdd=new Measure();
    	msrToAdd.id=1;
    	msrToAdd.name=msrname;
    	msrToAdd.Attr=null;
    	cubequery.Msr.add(msrToAdd);
    	cubequery.referCube=null;
    	act.getTask().getLastSubTask().timeProduceOfCubeQuery=System.nanoTime()-strTime;
    	
    	act.getTask().cubeQuery.add(cubequery);
    	SqlQuery newSqlQuery=new SqlQuery();
    	strTime=System.nanoTime();
        newSqlQuery.produceExtractionMethod(cubequery);
        act.getTask().getLastSubTask().timeProduceOfCubeQuery=System.nanoTime()-strTime; 
        cubequery.sqlQuery=newSqlQuery;
        return newSqlQuery;
    	
    }
    
    private void constructTxtIntroAct(Act currentAct) { 	
		PptxSlide tmpslide=(PptxSlide) currentAct.getEpisode(0);
		long strTime=System.nanoTime();
		tmpslide.setTitle("CineCube Report");
		
		tmpslide.timeCreationText=System.nanoTime();
		tmpslide.setSubTitle(((TextExtractionPPTX)TxtMgr).createTxtForIntroSlide(currentAct.getTask().cubeQuery.get(0).GammaExpressions,
																				currentAct.getTask().cubeQuery.get(0).SigmaExpressions, 
																				currentAct.getTask().cubeQuery.get(0).AggregateFunction, 
																				currentAct.getTask().cubeQuery.get(0).Msr.get(0).name));
		tmpslide.timeCreationText=System.nanoTime()-tmpslide.timeCreationText;
		tmpslide.setNotes(tmpslide.getSubTitle());
		/*====== Add Audio To Episode  =======*/
		if(OptMgr.getAudio()==true){
		tmpslide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
		tmpslide.timeCreationAudio=System.nanoTime();
		AudioMgr.CreateAudio(tmpslide.getNotes(), tmpslide.getAudio().getFileName());
		tmpslide.timeCreationAudio=System.nanoTime()-tmpslide.timeCreationAudio;
		tmpslide.timeCreation=System.nanoTime()-strTime;
		}
		//currentAct.addEpisode(tmpslide);
	}
    
    private void constructTxtOriginalAct(Act currentAct){
    	//SubTask subtsk=currentAct.getTask().getSubTask(0);		
		CubeQuery currentCubeQuery=currentAct.getTask().cubeQuery.get(0);
    	PptxSlide newSlide=(PptxSlide) currentAct.getEpisode(0);
    	Tabular tbl=(Tabular) newSlide.getVisual();
    	
	   // HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
	    
    	/*====== Create Txt For Original =======*/
    	newSlide.timeCreationText=System.nanoTime();
		newSlide.setTitle("Answer to the original question");
		newSlide.setNotes(((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct1( currentCubeQuery.GammaExpressions, 
																					currentCubeQuery.SigmaExpressions, 
																					currentCubeQuery.AggregateFunction,
																					currentCubeQuery.Msr.get(0).name,
																					newSlide.getHighlight()).replace("  ", " "));
		
		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForColumnsDominate(tbl.getPivotTable(),newSlide.getHighlight().get(2));
		add_to_notes+=((TextExtractionPPTX)this.TxtMgr).createTxtForRowsDominate(tbl.getPivotTable(),newSlide.getHighlight().get(3));
		newSlide.setNotes(newSlide.getNotes() + "\n"+add_to_notes);
		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
		
		if(add_to_notes.length()>0) currentAct.ActHighlights="Concerning the original query, some interesting findings include:\n\t";
		currentAct.ActHighlights+=add_to_notes.replace("\n", "\n\t");
		
		
		/*====== Add Audio To Episode  =======*/
		if(OptMgr.getAudio()==true){
		newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    	newSlide.timeCreationAudio=System.nanoTime();
    	AudioMgr.CreateAudio(newSlide.getNotes(), newSlide.getAudio().getFileName());
    	newSlide.timeCreationAudio=System.nanoTime()-newSlide.timeCreationAudio;
		}
    }
    
    private void constructTxtEndAct(ArrayList<Act> acts,Act currentAct) {
    	PptxSlide newSlide=(PptxSlide) currentAct.getEpisode(0);
    	newSlide.timeCreationText=System.nanoTime();
    	newSlide.setNotes("In this slide we summarize our findings.");
    	newSlide.setTitle("Summary");    	
    	for(Act actItem: acts){
    		if(actItem.ActHighlights.length()>0){
    			if(newSlide.getNotes().length()>0) newSlide.setNotes(newSlide.getNotes() + "@");
    			newSlide.setNotes(newSlide.getNotes() + actItem.ActHighlights);
    		}
    		newSlide.setNotes(newSlide.getNotes().replace("\n\n\n", "\n").replace("\n\n", "\n").replace("\n\t\n", "\n\t"));
    	}
    	newSlide.setNotes(newSlide.getNotes().replace("\n\n\n", "\n").replace("\n\n", "\n").replace("\t", "").replace("\r", ""));
    	newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
    	
    	/*====== Add Audio To Episode  =======*/
    	if(OptMgr.getAudio()==true){
    	newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    	newSlide.timeCreationAudio=System.nanoTime();
    	AudioMgr.CreateAudio(newSlide.getNotes(), newSlide.getAudio().getFileName());
    	newSlide.timeCreationAudio=System.nanoTime()-newSlide.timeCreationAudio;
    	}
	}
    
    void printCubeQueries(String CubeQ,String SqlQ){
    	cubeQueryWriter.append("---------------\n");
    	cubeQueryWriter.append(CubeQ+"\n@\n");
    	cubeQueryWriter.append(SqlQ+"\n");
    	cubeQueryWriter.append("---------------\n\n");
    }
    
    
    
    void setupTextAct1(Act act,CubeQuery origCubeQuery){
    	boolean ActHasWriteHiglights=false;
    	for(int j=0;j<act.getNumEpisodes();j++){
    		PptxSlide currentSlide=(PptxSlide) act.getEpisode(j);
    		if(j==0) {
    			currentSlide.timeCreationText=System.nanoTime();
    			currentSlide.setTitle(currentSlide.getTitle() + ": Putting results in context");
    			currentSlide.setSubTitle("In this series of slides we put the original result in context, by comparing the behavior of its defining values with the behavior of values that are similar to them.");
    			currentSlide.setNotes(currentSlide.getTitle()+"\n"+currentSlide.getSubTitle());
    			currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    		
    			/*====== Add Audio To Episode  =======*/
    			if(OptMgr.getAudio()==true){
    			currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    			currentSlide.timeCreationAudio=System.nanoTime();
    			AudioMgr.CreateAudio(currentSlide.getNotes(), currentSlide.getAudio().getFileName());
    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    			}
    		}
    		else{
    			SubTask subtsk=currentSlide.getSubTasks().get(0);
        		CubeQuery currentCubeQuery=currentSlide.CbQOfSlide.get(0);
    			SqlQuery currentSqlQuery=(SqlQuery) subtsk.getExtractionMethod();
    			Tabular tbl=(Tabular) currentSlide.getVisual();
    			HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
    			if(subtsk.getDifferenceFromOrigin(0)==-1){
	    			int gamma_index_change=subtsk.getDifferenceFromOrigin(1);
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.setTitle("Assessing the behavior of ");
		        	currentSlide.setTitle(currentSlide.getTitle() + currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].replace("_dim", ""));
		        	currentSlide.getVisual().getPivotTable()[0][0]=" Summary for "+currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].split("_")[0];		        	
		        	currentSlide.setNotes(((TextExtractionPPTX)this.TxtMgr).createTextForAct1(currentCubeQuery.GammaExpressions,
																	        			origCubeQuery.SigmaExpressions,
																	        			currentCubeQuery.SigmaExpressions,
																	        			currentSqlQuery.Res.getResultArray(),
																	        			currentSlide.getHighlight(),
																	        			subtsk.getDifferenceFromOrigin(1),
																	        			currentCubeQuery.AggregateFunction,
																	        			currentCubeQuery.Msr.get(0).name));
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	String add_to_notes="";
		        	if(gamma_index_change==0) {
		        		long strTimeTxt=System.nanoTime();
		        		add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn(tbl.getPivotTable(),currentSlide.getHighlight().get(2));
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        	}
		        	else {
		        		long strTimeTxt=System.nanoTime();
		        		add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingRow(tbl.getPivotTable(),currentSlide.getHighlight().get(3));
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        	}
		        	currentSlide.setNotes(currentSlide.getNotes() + "\n"+add_to_notes);
	        		
		        	if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
	        			ActHasWriteHiglights=true;
	        			act.ActHighlights+="@First, we tried to put the original result in context, by comparing its defining values with similar ones.\n\t";
	        		}
		        	String toReplaceString1="Compared to its sibling we observe that in";
		        	String toReplaceString2="Compared to its sibling we observe the following:";
		        	String newString="##When we compared ";
		        	
		        	if(tbl.boldColumn>-1) newString+=tbl.getPivotTable()[0][tbl.boldColumn];
		        	if(tbl.boldRow>-1) newString+=tbl.getPivotTable()[tbl.boldRow][0];
		        	
		        	newString+=" to its siblings, grouped by "+currentCubeQuery.GammaExpressions.get(0)[0].replace("_dim", "")+" and "+currentCubeQuery.GammaExpressions.get(1)[0].replace("_dim", "")+
		        			", we observed the following:\n~~";
		        	
		        	
	        		act.ActHighlights+=add_to_notes.replace("\n", "\n\t").replace(toReplaceString1, newString+"In").replace(toReplaceString2, newString);
	        		
	        		/*====== Add Audio To Episode  =======*/
	        		if(OptMgr.getAudio()==true){
	        		currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
	    			currentSlide.timeCreationAudio=System.nanoTime();
	    			AudioMgr.CreateAudio(currentSlide.getNotes(), currentSlide.getAudio().getFileName());
	    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
	        		}
    			}
    			else{
    				slideToEnd.add(currentSlide);
		        	numSlideToRemove.add(j);
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.setTitle("The ~ which changed @ : ");
		        	for(int i=0;i<subtsk.getDifferencesFromOrigin().size();i++){
			        	if(i>0) currentSlide.setTitle(currentSlide.getTitle() + " AND ");
			        	currentSlide.setTitle(currentSlide.getTitle() + act.getTask().cubeQuery.get(1).GammaExpressions.get(subtsk.getDifferenceFromOrigin(i))[0]);
			        }
		        	String text_cond="Conditions";
			        String text_are="are";
			        if(subtsk.getDifferencesFromOrigin().size()==1){
			        	text_cond="Condition";
			        	text_are="is";
			        }
		        	currentSlide.setTitle(currentSlide.getTitle().replace("~", text_cond).replace("@", text_are));
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    			}
    		}
    	}
    	
    	if(slideToEnd.size()>0){
    		/*======== Act 3 =============*/
    		StorMgr.getStory().createNewAct();
    		StorMgr.getStory().getLastAct().setId(3);
    		PptxSlide newSlide=new PptxSlide();
        	newSlide.setTitle("Auxiliary slides for Act I");
        	StorMgr.getStory().getLastAct().addEpisode(newSlide);
        	for(int k=0;k<slideToEnd.size();k++) {
        		act.getEpisodes().remove(slideToEnd.get(k));
        		StorMgr.getStory().getLastAct().addEpisode(slideToEnd.get(k));
        	}
        	slideToEnd.clear();
    	}
    }  
    
    void setupTextAct2(Act act,CubeQuery origCubeQuery,SubTask origSubtsk){
    	boolean ActHasWriteHiglights=false;
    	for(int j=0;j<act.getNumEpisodes();j++){
    		PptxSlide currentSlide=(PptxSlide) act.getEpisode(j);
    		SubTask subtsk=currentSlide.getSubTasks().get(0);
    		CubeQuery currentCubeQuery=currentSlide.CbQOfSlide.get(0);
			SqlQuery currentSqlQuery=(SqlQuery) subtsk.getExtractionMethod();
			Tabular tbl=(Tabular) currentSlide.getVisual();
			HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
    		if(j==0) {
    			currentSlide.timeCreationText=System.nanoTime();
    			currentSlide.setTitle(currentSlide.getTitle() + ": Explaining results");
    			currentSlide.setNotes(currentSlide.getTitle());
    			currentSlide.setSubTitle("In this series of slides we will present a detailed analysis of the values involved in the result of the original query. To this end, " +
        				"we drill-down the hierarchy of grouping levels of the result to one level of aggregation lower, whenever this is possible.");
    			currentSlide.setNotes(currentSlide.getTitle()+"\n"+currentSlide.getSubTitle());
    			currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    		}
    		else if(subtsk.getDifferencesFromOrigin().size()>1){
    			
    			long start_creation_domination=System.nanoTime();
    			hltbl.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
    			currentSlide.timeComputeHighlights+=System.nanoTime()-start_creation_domination;
    			
    			String add_to_notes="";
    			currentSlide.setNotes("");
    			if(subtsk.getDifferencesFromOrigin().get(0)==-4){
    				currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.setTitle("Drilling down the Rows of the Original Result");		        	
		        	currentSlide.setNotes(((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   0,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getRowPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2))));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v2(tbl.getPivotTable());
		        	add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,currentSlide.getHighlight(),false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.setNotes(currentSlide.getNotes() + add_to_notes);
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-5){
		        	/*this.transoper(tbl.getPivotTable());
		        	this.transoper(tbl.colortable);*/
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.setTitle("Drilling down the Columns of the Original Result");
		        	currentSlide.setNotes(((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   1,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getColPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2))));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v3(tbl.getPivotTable());
		        	
		        	add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,currentSlide.getHighlight(),false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.setNotes(currentSlide.getNotes() + add_to_notes);
		        }
    			if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
        			ActHasWriteHiglights=true;
        			act.ActHighlights+="Then we analyzed the results by drilling down one level in the hierarchy.\n\t\n\t";
        		}
    			try{
    				
    				act.ActHighlights+="##When we drilled down "+
    							currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2))[0].replace("_dim", ", ")+" we observed the following facts:\n"
    						+"~~"+add_to_notes.split(":")[1].replace("\n", "\n\t").replace("\t\t", "\t");
    				act.ActHighlights=act.ActHighlights.replace("  "," ");
    			}
    			catch(Exception ex){
    				//Do Nothing
    			}
    		}
    		else {
    			currentSlide.timeCreationText=System.nanoTime();
        		currentSlide.setTitle("Answer to the original question");
        		currentSlide.setNotes(((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct2(currentCubeQuery.GammaExpressions,
        																					   currentCubeQuery.SigmaExpressions,
        																					   currentSqlQuery.Res.getResultArray()).replace("  ", " "));
        		currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    		}
    		/*====== Add Audio To Episode  =======*/
    		if(OptMgr.getAudio()==true){
    		currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    		currentSlide.timeCreationAudio=System.nanoTime();
			AudioMgr.CreateAudio(currentSlide.getNotes(), currentSlide.getAudio().getFileName());
			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    		}
    	}
    }
   
   
        
	public void ParseFile(InputStream inputStream){
		if(inputStream!=null){
	    	PrsMng=new ParserManager();
			Scanner  sc=(new Scanner(inputStream)).useDelimiter(";");
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
		}
    }
   	
	
	
	public void ParseFile2(File file) throws FileNotFoundException{
		if(file!=null){
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
		}
    }
	
	
	void printBorderLines(){
		System.out.println();
		System.out.println("=====================================");
		System.out.println("=====================================");
	}
	
	void transoper(String[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array[j].length; j++) {
				String temp = array[i][j];
				array[i][j] = array[j][i];
				array[j][i] = temp;
            }
        }
	}
	
	void transoper(Color[][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array[j].length; j++) {
				Color temp = array[i][j];
				array[i][j] = array[j][i];
				array[j][i] = temp;
            }
        }
	}
	
	
    public void InitializeCubeMgr(String lookup)throws RemoteException {
    	CubeManager=new CubeMgr(lookup);
    }
    
    public void createDefaultFolders() throws RemoteException{
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
    
   public void constructDimension(String inputlookup,String cubeName)throws RemoteException{
    	/*/var/www/html/components/com_sqlform/assets/pptx/*/
        //this.ParseFile(getClass().getClassLoader().getResourceAsStream("resources/beta.txt"));/*Create Dimension,Cube*/
        try {
        		this.ParseFile2(new File("InputFiles/"+inputlookup+"/"+cubeName+".ini"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
  
    
   public static void main(String[] args) throws RemoteException { 
	            MainEngine MainEng=new MainEngine(); 
	           // MainEng.InitializeCubeMgr(); 
	            MainEng.createDefaultFolders(); 
	           // MainEng.CubeManager.CreateCubeBase(MainEng.InsertFromKeyboardDBInfos());         
	            //Me.ParseFile(Me.GetFileCmds()); 
	        //    MainEng.constructDimension(); 
	            //MainEng.NewRequestSqlQuery(""); 
	            MainEng.AnswerCubeQueriesFromFile(new File("InputFiles/test.ini"));/*Create Stories*/ 
	            //MainEng.getCubeQueriesFromFile(new File(args[0])); 
	            //MainEng.getCubeQueriesFromFile(new File("InputFiles/NativeAge.ini")); 
	    //      MainEng.newRequestCubeQuery(null); 
	             
	            System.out.println("=======Finish======"); 
	            //MTTS.server.Mary.shutdown(); 
	              System.exit(0); 
	        } 

    
    
    public void initialize_connection(String schema_name,String login,String passwd,String inputfile,String cubeName) throws RemoteException{
    	createDefaultFolders();
    	dbname = schema_name;
    	username = login;
    	password = passwd;
    	inputlookup = inputfile;
    	InitializeCubeMgr(inputlookup);
    	CubeManager.CreateCubeBase(dbname,username,password);
    	constructDimension(inputlookup,cubeName);
    }
    
    public void OptionsChoice(boolean Audio, boolean Word)throws RemoteException{
    	OptMgr.setAudio(Audio);
    	OptMgr.setWord(Word);
    }
    
}
