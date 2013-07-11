import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
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
import TaskMgr.TaskActI;
import TaskMgr.TaskActII;
import TaskMgr.TaskIntro;
import TaskMgr.TaskMgr;
import TaskMgr.TaskOriginal;
import TaskMgr.TaskSummary;
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
	PrintWriter cubeQueryWriter;
	String act_story_time;
	int slideToPrint=0;
   

	MainEngine(){
    	StorMgr=new StoryMgr(); 
    	TskMgr=new TaskMgr();
    	
    }
    
  /*  public String predefinedSqlQueries(String Query){
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
 	        	   " GROUP BY A.education,A.occupation";* /
    		Query="SELECT A.education,A.MARITAL_STATUS,AVG(hours_per_week) \n" +
  	        	   "FROM ADULT A, EDUCATION E, MARITAL_STATUS M \n" +
  	        	   "WHERE A.education = E.level0 AND E.level2 = 'University' \n" +
  	        	   " AND A.MARITAL_STATUS = M.level0 AND M.level1 = 'Partner-absent' \n" +
  	        	   " GROUP BY A.education,A.MARITAL_STATUS";
    	}
    	return Query;
    }*/
    
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
    
   /* private void printOutTheTime(ArrayList<Act> acts,String filename) {
    	PrintWriter writer;
		try {
			writer = new PrintWriter(filename+"_Experiment.txt", "UTF-8");
			writer.print("slideid;");
			writer.print("TitleOfSlide;");
			writer.print("timeProduceOfCubeQuery;");
			writer.print("timeProduceSQL;");
			writer.print("timeExecutionSQLQuery;");
			//writer.print("timeCreationOfSbTsk;");			
			writer.print("timeCreationTabular;");
			writer.print("timeCreationHighlight;");
			writer.print("timeCreationColorTable;");
			writer.print("timeCombineSlide;");
			writer.print("timeCreationText;");
			writer.print("timeCreationAudio;");
			writer.print("timeCreationPutInPPTX\n");
			
			int slideid=1;
			
			for(Act act : acts){
				if(act.getId()==0){
					cubeQueryWriter.append("#============== Intro Act =====================\n\n");
				}
				else if(act.getId()==20){
					cubeQueryWriter.append("#============== Original Act ===================\n\n");
				}
				else if(act.getId()==1){
					cubeQueryWriter.append("#============== Act 1 ==========================\n\n");
				}
				else if(act.getId()==2){
					cubeQueryWriter.append("#============== Act 2 ==========================\n\n");
				}
				else if(act.getId()==3){
					cubeQueryWriter.append("#============== Act Auxiliary ==================\n\n");
				}
				else if(act.getId()==-1){
					cubeQueryWriter.append("#============== End Act =========================\n\n");
				}
				for(int i=0;i<act.getNumEpisodes();i++){
					PptxSlide slide=(PptxSlide)act.getEpisode(i);
					
					//int timeCreationOfSbTsk=0;
					int timeExecutionQuery=0;
					int timeProduceOfCubeQuery=0;
					int timeProduceOfExtractionMethod=0;
					//if((act.getId()==2 && i!=1) || (act.getId()!=2)) {
					for(SubTask sbtsk:slide.getSubTasks()){
						//timeCreationOfSbTsk+=sbtsk.timeCreationOfSbTsk;
						timeExecutionQuery+=sbtsk.timeExecutionQuery;
						timeProduceOfCubeQuery+=sbtsk.timeProduceOfCubeQuery;
						timeProduceOfExtractionMethod+=sbtsk.timeProduceOfExtractionMethod;
					}
					cubeQueryWriter.append("#-------Slide"+slideid+"------------\n\n");
					for(CubeQuery cq:slide.CbQOfSlide){
						printCubeQueries(cq.toString(), cq.sqlQuery.toString());
					}
					//}
					writer.print(slideid+";");
					writer.print(slide.Title+";");
					writer.print(convertNanoSec(timeProduceOfCubeQuery)+";");
					writer.print(convertNanoSec(timeProduceOfExtractionMethod)+";");
					writer.print(convertNanoSec(timeExecutionQuery)+";");
					//writer.print(convertNanoSec(timeCreationOfSbTsk)+";");
					
				//	writer.print(convertNanoSec(slide.timeCreation)+";");
					writer.print(convertNanoSec(slide.timeCreationTabular)+";");
					writer.print(convertNanoSec(slide.timeComputeHighlights)+";");
					writer.print(convertNanoSec(slide.timeCreationColorTable)+";");
					writer.print(convertNanoSec(slide.timeCombineSlide)+";");
					writer.print(convertNanoSec(slide.timeCreationText)+";");
					writer.print(convertNanoSec(slide.timeCreationAudio)+";");
					writer.print(convertNanoSec(slide.timeCreationPutInPPTX)+"\n");					
					slideid++;
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
    
    String convertNanoSec(long nanoSeconds){
    	//return String.valueOf(TimeUnit.MILLISECONDS.convert(nanoSeconds,TimeUnit.NANOSECONDS));
    	return String.valueOf(nanoSeconds);
    }
    */
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
        StorMgr.getStory().setFinalResult(new PptxSlideshow());
        StorMgr.getStory().getFinalResult().setFilename("ppt/"+cubequery.name+".pptx");
        
        /*======== Stat The Wrap UP ==========================*/
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

	/* 
     * Maybe the parameter of this function
     * must change to different type (Like Query).....
     * 
     */
   /* public void NewRequestSqlQuery(String Query){
    	
        PrsMng.parse(predefinedSqlQueries(Query));
        SqlQuery query=new SqlQuery(PrsMng.aggregatefunc,PrsMng.tablelst,PrsMng.conditionlst,PrsMng.groupperlst);
        
        StorMgr.createStory();
        /*StorMgr.createStoryOriginalRequest();* /
        StorMgr.createTasks(TskMgr);
        TaskActI tskBrths=new TaskActI();
        TskMgr.createNewTask(tskBrths);
        StorMgr.addNewTaskToStory(tskBrths);
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(query);
        
        
        
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        //TskMgr.getLastTask().getLastSubTask().computeFinding();
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        AudioMgr=new MaryTTSAudioEngine();
        AudioMgr.InitializeVoiceEngine();
        
        //SetupSlideEpisodes(StorMgr.getStory().getLastAct());
        
        StorMgr.getStory().setFinalResult(new PptxSlideshow());
        /*StorMgr.getStory().getFinalResult().setFilename("ppt/q6.pptx");
        
        WrapUp=new PptxWrapUpMgr();
        WrapUp.setFinalResult(StorMgr.getStory().getFinalResult());
        WrapUp.doWrapUp(StorMgr.getStory());* /
        
    }*/
    
    public SqlQuery createCubeQueryStartOfActSlide(Act act,String num_act){
    	long strTime=System.nanoTime();
    	CubeQuery cubequery=new CubeQuery("Act "+String.valueOf(num_act));
    	cubequery.AggregateFunction="Act "+String.valueOf(num_act);
    	Measure msrToAdd=new Measure();
    	msrToAdd.id=1;
    	msrToAdd.name="Hrs";
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
		tmpslide.Title="CineCube Report";
		
		tmpslide.timeCreationText=System.nanoTime();
		tmpslide.SubTitle=((TextExtractionPPTX)TxtMgr).createTxtForIntroSlide(currentAct.getTask().cubeQuery.get(0).GammaExpressions,
																				currentAct.getTask().cubeQuery.get(0).SigmaExpressions, 
																				currentAct.getTask().cubeQuery.get(0).AggregateFunction, 
																				currentAct.getTask().cubeQuery.get(0).Msr.get(0).name);
		tmpslide.timeCreationText=System.nanoTime()-tmpslide.timeCreationText;
		
		tmpslide.Notes=tmpslide.SubTitle;
		tmpslide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
		
		tmpslide.timeCreationAudio=System.nanoTime();
		AudioMgr.CreateAudio(tmpslide.Notes, tmpslide.getAudio().getFileName());
		tmpslide.timeCreationAudio=System.nanoTime()-tmpslide.timeCreationAudio;
		
		tmpslide.timeCreation=System.nanoTime()-strTime;
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
		newSlide.Title="Answer to the original question";
		newSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct1( currentCubeQuery.GammaExpressions, 
																					currentCubeQuery.SigmaExpressions, 
																					currentCubeQuery.AggregateFunction,
																					currentCubeQuery.Msr.get(0).name,
																					newSlide.highlight).replace("  ", " ");
		
		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForColumnsDominate(tbl.getPivotTable(),newSlide.highlight.get(2));
		add_to_notes+=((TextExtractionPPTX)this.TxtMgr).createTxtForRowsDominate(tbl.getPivotTable(),newSlide.highlight.get(3));
		newSlide.Notes+="\n"+add_to_notes;
		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
		
		if(add_to_notes.length()>0) currentAct.ActHighlights="Concerning the original query, some interesting findings include:\n\t";
		currentAct.ActHighlights+=add_to_notes.replace("\n", "\n\t");
		
		/*====== Add Audio To Episode  =======*/
		newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    	newSlide.timeCreationAudio=System.nanoTime();
    	AudioMgr.CreateAudio(newSlide.Notes, newSlide.getAudio().getFileName());
    	newSlide.timeCreationAudio=System.nanoTime()-newSlide.timeCreationAudio;
    	
    }
    
    private void constructTxtEndAct(ArrayList<Act> acts,Act currentAct) {
    	PptxSlide newSlide=(PptxSlide) currentAct.getEpisode(0);
    	newSlide.timeCreationText=System.nanoTime();
    	newSlide.Notes="In this slide we summarize our findings.";
    	newSlide.Title="Summary";    	
    	for(Act actItem: acts){
    		if(actItem.ActHighlights.length()>0){
    			if(newSlide.Notes.length()>0) newSlide.Notes+="@";
    			newSlide.Notes+=actItem.ActHighlights;
    		}
    		newSlide.Notes=newSlide.Notes.replace("\n\n\n", "\n").replace("\n\n", "\n").replace("\n\t\n", "\n\t");
    	}
    	newSlide.Notes=newSlide.Notes.replace("\n\n\n", "\n").replace("\n\n", "\n").replace("\t", "").replace("\r", "");
    	newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
    	
    	/*====== Add Audio To Episode  =======*/
    	newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    	newSlide.timeCreationAudio=System.nanoTime();
    	AudioMgr.CreateAudio(newSlide.Notes, newSlide.getAudio().getFileName());
    	newSlide.timeCreationAudio=System.nanoTime()-newSlide.timeCreationAudio;
    	
	}
    
    void printCubeQueries(String CubeQ,String SqlQ){
    	cubeQueryWriter.append("---------------\n");
    	cubeQueryWriter.append(CubeQ+"\n@\n");
    	cubeQueryWriter.append(SqlQ+"\n");
    	cubeQueryWriter.append("---------------\n\n");
    }
    
    /* NOT USE IT THIS FUNCTION. I HAD SPLIT IN TASKS 2013/06/27
     * 
     * This Function Add Create Slide per ACT
     */
   /* public void SetupSlideEpisodes(Act act,SubTask origSubtsk,CubeQuery origCubeQuer){
    	//SqlQuery original=(SqlQuery)act.getTask().getSubTask(0).getExtractionMethod();
    	//int timesIN=0;
    	//boolean swap_first=false;
    	
    	//System.out.println("Sum of subtasks:"+act.getTask().getNumSubTasks());
    	//SubTask origSubtsk=new SubTask();
    	if(act.getId()>0){
    		slideToPrint=1;
	    	for(int j=0;j<act.getTask().getNumSubTasks();j++){
	    		SubTask subtsk=act.getTask().getSubTask(j);
	    		/*if(j==1) {
	    			origSubtsk=act.getTask().getSubTask(j);
	    		}* /
	    		SqlQuery currentSqlQuery=((SqlQuery)subtsk.getExtractionMethod());
	    		CubeQuery currentCubeQuery=act.getTask().cubeQuery.get(j);
		        if((currentSqlQuery.Res.getResultArray()!=null)){
		        	//timesIN++;
		        	PptxSlide newSlide=new PptxSlide();
		        	newSlide.CbQOfSlide.add(currentCubeQuery);
			        Tabular tbl=new Tabular();
			        String[] extraPivot=new String[2];
			        extraPivot[0]="";
			        extraPivot[1]="";
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && (subtsk.getDifferencesFromOrigin().get(0)==-2 || subtsk.getDifferencesFromOrigin().get(0)==-3)){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getRowPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }/*to delete if* /
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-4){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getRowPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }
			        if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferencesFromOrigin().get(0)==-5){
			        	extraPivot[0]=String.valueOf(subtsk.getDifferencesFromOrigin().get(0));
			        	extraPivot[1]=origSubtsk.getExtractionMethod().Res.getColPivot().toArray()[subtsk.getDifferencesFromOrigin().get(1)].toString();
			        }
			        
			        newSlide.timeCreationTabular=System.nanoTime();
			        tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(), subtsk.getExtractionMethod().Res.getColPivot(), 
							subtsk.getExtractionMethod().Res.getResultArray(),extraPivot);
			        newSlide.timeCreationTabular=System.nanoTime()-newSlide.timeCreationTabular;
			        
			        if(subtsk.getHighlight()==null) subtsk.setHighlight(new HighlightTable());
			        //if(j>0 && subtsk.getHighlight()!=null) {
			        	newSlide.timeCreationColorTable=System.nanoTime();
			        	/*HighlightTable hltbl=(HighlightTable) act.getTask().highlights.get(j-1);* /
			        	HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
			        	newSlide.timeComputeHighlights=System.nanoTime();
			        	hltbl.createMinHightlight(subtsk.getExtractionMethod().Res.getResultArray());
				    	hltbl.createMaxHightlight(subtsk.getExtractionMethod().Res.getResultArray());
				    	hltbl.createMiddleHightlight(subtsk.getExtractionMethod().Res.getResultArray());
				    	newSlide.timeComputeHighlights=System.nanoTime()-newSlide.timeComputeHighlights;
				    	newSlide.highlight=hltbl;
			        	tbl.setColorTable(hltbl);
			        	newSlide.timeCreationColorTable=System.nanoTime()-newSlide.timeCreationColorTable;
			        //}
			        
			        if(subtsk.getDifferencesFromOrigin().size()>0 && (subtsk.getDifferencesFromOrigin().get(0)==-4 || subtsk.getDifferencesFromOrigin().get(0)==-5) && subtsk.getDifferencesFromOrigin().get(1)>0){
			        	/* the code above to be function * /
			        	PptxSlide tmpSlide=(PptxSlide)StorMgr.getStory().getLastAct().getEpisode(StorMgr.getStory().getLastAct().getNumEpisodes()-1);
			        	long strTimecombine=System.nanoTime();
			        	String [][] SlideTable=tmpSlide.getVisual().getPivotTable();
			        	//Color [][] colorTable=((Tabular)tmpSlide.getVisual()).colortable;
			        	String [][] currentTable=tbl.getPivotTable();
			        	//Color [][] currentColorTable=tbl.colortable;
			        	String [][] newTable = null;
			        	Color[][] newColorTable =null;
			        	//int col_width=SlideTable[0].length;
			        	int rows_width=SlideTable.length+currentTable.length;
			        	/*if(SlideTable[0].length<currentTable[0].length){
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
			        	//tmpSlide.addSubTask(subtsk);
			        	((Tabular)tmpSlide.getVisual()).colortable=newColorTable;
			        	
			        	
			        	for(int k=0;k<newTable.length;k++){
			        		for(int l=0;l<newTable[k].length;l++) {
			        			if(newTable[k][l]==null) {
			        				newTable[k][l]="";
			        				newColorTable[k][l]=Color.black;
			        			}
			        		}
			        	}* /
			        	TreeSet<String> cols=new  TreeSet<String>();
			        	
			        	for(int i=2;i<SlideTable[0].length;i++) cols.add(SlideTable[0][i]);
			        	for(int i=2;i<currentTable[0].length;i++) cols.add(currentTable[0][i]);
			        				        	
			        	newTable=new String[rows_width][cols.size()+2];
			        	newColorTable=new Color[rows_width][cols.size()+2];
			        	//customCopyArray(SlideTable,currentTable,colorTable,currentColorTable,newTable,newColorTable);
			        	
			        	tmpSlide.getVisual().setPivotTable(newTable);
			        	((Tabular)tmpSlide.getVisual()).colortable=newColorTable;
			        	
			        	tmpSlide.timeCombineSlide+=System.nanoTime()-strTimecombine;
			        	
			        	printCubeQueries(currentCubeQuery.toString(),currentSqlQuery.toString());
			        	
			        	
			        	tmpSlide.timeCreationColorTable+=newSlide.timeCreationColorTable;
			        	tmpSlide.timeCreationTabular+=newSlide.timeCreationTabular;
			        	tmpSlide.timeComputeHighlights+=newSlide.timeComputeHighlights;
			        	tmpSlide.addSubTask(subtsk);
			        	
			        }
			        else{
			        	newSlide.addSubTask(subtsk);
			        	boolean printQuery=true;
			        	if(act.getId()==1 && subtsk.getDifferencesFromOrigin().size()==1) printQuery=false; 
			        	if(printQuery){	
			        		cubeQueryWriter.append("Slide "+slideToPrint+"\n\n");
			        		printCubeQueries(currentCubeQuery.toString(),currentSqlQuery.toString());
			        		slideToPrint++;
			        	}
			        	//}
				        newSlide.setVisual(tbl);
				        newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
				        StorMgr.getStory().getLastAct().addEpisode(newSlide);
			        }
		        }
		        else if(currentSqlQuery.Res.TitleOfColumns!=null && currentSqlQuery.Res.TitleOfColumns.contains("Act")) {
		        	PptxSlide newSlide=new PptxSlide();
			        newSlide.addSubTask(subtsk);
			        cubeQueryWriter.append("Slide "+slideToPrint+"\n\n");
	        		printCubeQueries(currentCubeQuery.toString(),currentSqlQuery.toString());
	        		slideToPrint++;
		        	//newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
		        	if(act.getId()==1) {
		        		//swap_first=true;
		        		newSlide.timeCreationText=System.nanoTime();
		        		newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
		        		newSlide.Title+=": Putting results in context";
		        		newSlide.SubTitle="In this series of slides we put the original result in context, by comparing the behavior of its defining values with the behavior of values that are similar to them.";
		        		newSlide.Notes=newSlide.Title+"\n"+newSlide.SubTitle;
		        		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
		        	}
		        	else if(act.getId()==2){
		        		newSlide.timeCreationText=0;
		        		newSlide.timeCreationText=System.nanoTime();
		        		newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
		        		newSlide.Title+=": Explaining results";
		        		newSlide.Notes=newSlide.Title;
		        		newSlide.SubTitle="In this series of slides we will present a detailed analysis of the values involved in the result of the original query. To this end, " +
		        				"we drill-down the hierarchy of grouping levels of the result to one level of aggregation lower, whenever is possible.";
		        		newSlide.Notes=newSlide.Title+"\n"+newSlide.SubTitle;
		        		
		        		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
		        	}
		        	if(newSlide.Notes.length()>0){
			        	newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
			        	
			        	newSlide.timeCreationAudio=System.nanoTime();
			        	
			        	AudioMgr.CreateSound(newSlide.Notes, newSlide.getAudio().getFileName());
			        	
			        	newSlide.timeCreationAudio=System.nanoTime()-newSlide.timeCreationAudio;
		        	}
		        	StorMgr.getStory().getLastAct().addEpisode(newSlide);	        	
		        }
	    	}
	    	//SetupTextOfEpisodes(act,act.getId(),origSubtsk,origCubeQuer);
	    	/*if(swap_first && act.getEpisodes().size()>1){
	    		PptxSlide tmpslide=(PptxSlide) act.getEpisodes().get(0);
	    		act.getEpisodes().set(0, act.getEpisodes().get(1));
	    		act.getEpisodes().set(1, tmpslide);
	    	}* /
    	}
    	else if(act.getId()==0) {
    		long strTime=System.nanoTime();
    		PptxSlide tmpslide=new PptxSlide();
    		tmpslide.Title="CineCube Report";
    		
    		tmpslide.timeCreationText=System.nanoTime();
    		tmpslide.SubTitle=((TextExtractionPPTX)TxtMgr).createTxtForIntroSlide(act.getTask().cubeQuery.get(0).GammaExpressions,
															    			  act.getTask().cubeQuery.get(0).SigmaExpressions, 
		 													    			  act.getTask().cubeQuery.get(0).AggregateFunction, 
		 													    			  act.getTask().cubeQuery.get(0).Msr.get(0).name);
    		tmpslide.timeCreationText=System.nanoTime()-tmpslide.timeCreationText;
    		
    		tmpslide.Notes=tmpslide.SubTitle;
    		tmpslide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    		
    		tmpslide.timeCreationAudio=System.nanoTime();
    		AudioMgr.CreateSound(tmpslide.Notes, tmpslide.getAudio().getFileName());
    		tmpslide.timeCreationAudio=System.nanoTime()-tmpslide.timeCreationAudio;
    		
    		tmpslide.timeCreation=System.nanoTime()-strTime;
    		act.addEpisode(tmpslide);
		}
    	//System.out.println("TimesIN:"+timesIN);
    }*/
        
   /*private int getIndexOfSigma(ArrayList<String[]> sigmaExpressions,String gamma_dim) {
		int ret_value=-1;
		int i=0;
		for(String[] sigma : sigmaExpressions ){
			if(sigma[0].split("\\.")[0].equals(gamma_dim)) ret_value=i;
			i++;
		}
		return ret_value;
	}*/
    
    void setupTextAct1(Act act,CubeQuery origCubeQuery){
    	boolean ActHasWriteHiglights=false;
    	for(int j=0;j<act.getNumEpisodes();j++){
    		PptxSlide currentSlide=(PptxSlide) act.getEpisode(j);
    		if(j==0) {
    			currentSlide.timeCreationText=System.nanoTime();
    			currentSlide.Title+=": Putting results in context";
    			currentSlide.SubTitle="In this series of slides we put the original result in context, by comparing the behavior of its defining values with the behavior of values that are similar to them.";
    			currentSlide.Notes=currentSlide.Title+"\n"+currentSlide.SubTitle;
    			currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    			
    			currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    			
    			currentSlide.timeCreationAudio=System.nanoTime();
    			AudioMgr.CreateAudio(currentSlide.Notes, currentSlide.getAudio().getFileName());
    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
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
		        	currentSlide.Title="Assessing the behavior of ";
		        	currentSlide.Title+=currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].replace("_dim", "");
		        	currentSlide.getVisual().getPivotTable()[0][0]=" Summary for "+currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].split("_")[0];		        	
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct1(currentCubeQuery.GammaExpressions,
																	        			origCubeQuery.SigmaExpressions,
																	        			currentCubeQuery.SigmaExpressions,
																	        			currentSqlQuery.Res.getResultArray(),
																	        			currentSlide.highlight,
																	        			subtsk.getDifferenceFromOrigin(1),
																	        			currentCubeQuery.AggregateFunction,
																	        			currentCubeQuery.Msr.get(0).name);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	String add_to_notes="";
		        	if(gamma_index_change==0) {
		        		long strTimeTxt=System.nanoTime();
		        		add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn(tbl.getPivotTable(),currentSlide.highlight.get(2));
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        	}
		        	else {
		        		long strTimeTxt=System.nanoTime();
		        		add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingRow(tbl.getPivotTable(),currentSlide.highlight.get(3));
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        	}
		        	currentSlide.Notes+="\n"+add_to_notes;
	        		
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
	        		currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
	    			currentSlide.timeCreationAudio=System.nanoTime();
	    			AudioMgr.CreateAudio(currentSlide.Notes, currentSlide.getAudio().getFileName());
	    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    			}
    			else{
    				slideToEnd.add(currentSlide);
		        	numSlideToRemove.add(j);
		        	currentSlide.timeCreationText=System.nanoTime();
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
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    			}
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
    			currentSlide.Title+=": Explaining results";
    			currentSlide.Notes=currentSlide.Title;
    			currentSlide.SubTitle="In this series of slides we will present a detailed analysis of the values involved in the result of the original query. To this end, " +
        				"we drill-down the hierarchy of grouping levels of the result to one level of aggregation lower, whenever this is possible.";
    			currentSlide.Notes=currentSlide.Title+"\n"+currentSlide.SubTitle;
    			currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    		}
    		else if(subtsk.getDifferencesFromOrigin().size()>1){
    			
    			long start_creation_domination=System.nanoTime();
    			hltbl.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
    			currentSlide.timeComputeHighlights+=System.nanoTime()-start_creation_domination;
    			
    			String add_to_notes="";
    			currentSlide.Notes="";
    			if(subtsk.getDifferencesFromOrigin().get(0)==-4){
    				currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Drilling down the Rows of the Original Result";		        	
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   0,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getRowPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2)));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v2(tbl.getPivotTable());
		        	add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,currentSlide.highlight,false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.Notes+=add_to_notes;
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-5){
		        	/*this.transoper(tbl.getPivotTable());
		        	this.transoper(tbl.colortable);*/
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Drilling down the Columns of the Original Result";
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   1,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getColPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2)));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v3(tbl.getPivotTable());
		        	
		        	add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,currentSlide.highlight,false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.Notes+=add_to_notes;
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
        		currentSlide.Title="Answer to the original question";
        		currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct2(currentCubeQuery.GammaExpressions,
        																					   currentCubeQuery.SigmaExpressions,
        																					   currentSqlQuery.Res.getResultArray()).replace("  ", " ");
        		currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
    		}
    		/*====== Add Audio To Episode  =======*/
    		currentSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
    		currentSlide.timeCreationAudio=System.nanoTime();
			AudioMgr.CreateAudio(currentSlide.Notes, currentSlide.getAudio().getFileName());
			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    	}
    }
   
    /*void SetupTextOfEpisodes(Act act, int num_act,SubTask origSubtsk,CubeQuery origCubeQuery){
    	/*SubTask origSubtsk=new SubTask();   
    	CubeQuery origCubeQuery = null;* /
    	//int i_cubequery=0;
    	boolean ActHasWriteHiglights=false;
    	for(int j=0;j<act.getNumEpisodes();j++){
    		if((num_act==1 || num_act==2) && j==0) {
    			//i_cubequery++;
    			continue;
    		}
    		PptxSlide currentSlide=(PptxSlide) act.getEpisode(j);
    		SubTask subtsk=currentSlide.getSubTasks().get(0);
    		CubeQuery currentCubeQuery=currentSlide.CbQOfSlide.get(0);
			SqlQuery currentSqlQuery=(SqlQuery) subtsk.getExtractionMethod();
			Tabular tbl=(Tabular) currentSlide.getVisual();
			if(tbl==null || tbl.getPivotTable()==null){
				//i_cubequery++;
    			continue;
			}
    		/*HighlightTable htable=(HighlightTable) act.getTask().highlights.get(i_cubequery-1);* /
    		HighlightTable htable=(HighlightTable) subtsk.getHighlight();
    		
    		if(subtsk.getDifferencesFromOrigin().size()>0 && subtsk.getDifferenceFromOrigin(0)==-1){
	    		int tmp_it=this.getIndexOfSigma(origCubeQuery.SigmaExpressions,currentCubeQuery.GammaExpressions.get(subtsk.getDifferenceFromOrigin(1))[0]);
	    		long start_creation_of_Bold=System.nanoTime();
	    		if(tmp_it>-1 && subtsk.getDifferenceFromOrigin(1)==0) htable.setBoldColumn(subtsk.getExtractionMethod().Res.getColPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
	    		else htable.setBoldRow(subtsk.getExtractionMethod().Res.getRowPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
	    		currentSlide.timeComputeHighlights+=System.nanoTime()-start_creation_of_Bold;
	    		tbl.boldColumn=htable.boldColumn;
	    		tbl.boldRow=htable.boldRow;
	    	}
    		long start_creation_domination=System.nanoTime();
			htable.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
			if(tbl.boldColumn>-1) htable.ComparingToSiblingColumn_v1(tbl.getPivotTable());
			if(tbl.boldRow>-1)    htable.ComparingToSiblingRow_v1(tbl.getPivotTable());
			currentSlide.timeComputeHighlights+=System.nanoTime()-start_creation_domination;
    		if(subtsk.getDifferencesFromOrigin().size()>1 && tbl.colortable!=null && (subtsk.getDifferencesFromOrigin().get(0)==-4 || subtsk.getDifferencesFromOrigin().get(0)==-5)){
    			
    			currentSlide.Notes="";
    			if(subtsk.getDifferencesFromOrigin().get(0)==-4){
    				currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Drilling down the Rows of the Original Result";		        	
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   htable,
							   0,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getRowPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2)));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v2(tbl.getPivotTable());
		        	String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,htable,false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.Notes+=add_to_notes;
		        	if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
	        			ActHasWriteHiglights=true;
	        			act.ActHighlights+="When we analyzed the results by drilling down one level in the hierarchy we found out the following facts:\n\t";
	        		}
		        	act.ActHighlights+=add_to_notes.split(":")[1].replace("\n", "\n\t").replace("\t\t", "\t");
		        	
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-5){
		        	/*this.transoper(tbl.getPivotTable());
		        	this.transoper(tbl.colortable);* /
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Drilling down the Columns of the Original Result";
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct2(origCubeQuery.GammaExpressions,
							   origCubeQuery.SigmaExpressions,
							   currentSlide.getVisual().getPivotTable(),
							   htable,
							   1,
							   origCubeQuery.AggregateFunction,
							   origCubeQuery.Msr.get(0).name,
							   origSubtsk.getExtractionMethod().Res.getColPivot().size(),
							   currentCubeQuery.GammaExpressions.get(currentSlide.getSubTasks().get(0).getDifferenceFromOrigin(2)));
		        	//currentSlide.Notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v3(tbl.getPivotTable());
		        	String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForDominatedRowColumns(tbl.getPivotTable(),tbl.colortable,htable,false,true);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	currentSlide.Notes+=add_to_notes;
		        	if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
	        			ActHasWriteHiglights=true;
	        			act.ActHighlights+="When we analyzed the results by drilling down one level in the hierarchy we found out the following facts:\n\t";
	        		}
		        	act.ActHighlights+=add_to_notes.split(":")[1].replace("\n", "\n\t").replace("\t\t", "\t");
		        	
		        }
    			//i_cubequery+=currentSlide.getSubTasks().size();
    			currentSlide.timeCreationAudio=System.nanoTime();
    			AudioMgr.CreateSound(currentSlide.Notes, currentSlide.getAudio().getFileName());
    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    		}
    		else{
    			//htable.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
    			currentSlide.Notes="";
    			
    			currentSlide.TitleColumn=new String(currentSqlQuery.Res.TitleOfColumns);
    			currentSlide.TitleRow=new String(currentSqlQuery.Res.TitleOfRows);
    			if(subtsk.getDifferencesFromOrigin().size()==0){
		        	if(num_act==20) {
		        		currentSlide.timeCreationText=System.nanoTime();
		        		currentSlide.Title="Answer to the original question";
		        		currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct1(currentCubeQuery.GammaExpressions, 
		        																						currentCubeQuery.SigmaExpressions, 
		        																						currentCubeQuery.AggregateFunction,
		        																						currentCubeQuery.Msr.get(0).name,
		        																						htable);
		        		
		        		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtForColumnsDominate(tbl.getPivotTable(),htable);
		        		add_to_notes+="\n"+((TextExtractionPPTX)this.TxtMgr).createTxtForRowsDominate(tbl.getPivotTable(),htable);
		        		currentSlide.Notes+="\n"+add_to_notes;
		        		currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        		if(add_to_notes.length()>0) act.ActHighlights="Concerning the original query, some interesting findings include:\n\t";
		        		act.ActHighlights+=add_to_notes.replace("\n", "\n\t");
		        		
		        	}
		        	else if(num_act==2){
		        		//System.err.println(currentCubeQuery.toString());
		        		currentSlide.timeCreationText=System.nanoTime();
		        		currentSlide.Title="Answer to the original question";
		        		currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForOriginalAct2(currentCubeQuery.GammaExpressions, currentCubeQuery.SigmaExpressions, currentSqlQuery.Res.getResultArray(),htable);
		        		currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	}
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-1){
		        	int gamma_index_change=subtsk.getDifferenceFromOrigin(1);
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Assessing the behavior of ";
		        	currentSlide.Title+=currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].replace("_dim", "");
		        	currentSlide.getVisual().getPivotTable()[0][0]=" Summary for "+currentCubeQuery.GammaExpressions.get(gamma_index_change)[0].split("_")[0];		        	
		        	currentSlide.Notes=((TextExtractionPPTX)this.TxtMgr).createTextForAct1(currentCubeQuery.GammaExpressions,
																	        			origCubeQuery.SigmaExpressions,
																	        			currentCubeQuery.SigmaExpressions,
																	        			currentSqlQuery.Res.getResultArray(),
																	        			htable,
																	        			subtsk.getDifferenceFromOrigin(1),
																	        			currentCubeQuery.AggregateFunction,
																	        			currentCubeQuery.Msr.get(0).name);
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        	
		        	if(gamma_index_change==0) {
		        		//long tim_highlight=System.nanoTime();
		        		//htable.ComparingToSiblingColumn_v1(tbl.getPivotTable());
		        		//currentSlide.timeComputeHighlights+=System.nanoTime()-tim_highlight;
		        		long strTimeTxt=System.nanoTime();
		        		//currentSlide.Title="Answer to the original question";
		        		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingColumn_v1(tbl.getPivotTable(),htable.boldColumn,htable);
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        		currentSlide.Notes+="\n"+add_to_notes;
		        		if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
		        			ActHasWriteHiglights=true;
		        			act.ActHighlights+="@When we tried to put the original result in context, by comparing its defining values with similar ones, we discovered the following:\n\t";
		        		}
		        		act.ActHighlights+=add_to_notes.replace("\n", "\n\t");
		        	}
		        	else {
		        		//long tim_highlight=System.nanoTime();
		        		//htable.ComparingToSiblingRow_v1(tbl.getPivotTable());
		        		//currentSlide.timeComputeHighlights+=System.nanoTime()-tim_highlight;
		        		long strTimeTxt=System.nanoTime();
		        		String add_to_notes=((TextExtractionPPTX)this.TxtMgr).createTxtComparingToSiblingRow_v1(tbl.getPivotTable(),htable);
		        		currentSlide.timeCreationText+=System.nanoTime()-strTimeTxt;
		        		currentSlide.Notes+="\n"+add_to_notes;
		        		if(ActHasWriteHiglights==false && add_to_notes.length()>0) {
		        			ActHasWriteHiglights=true;
		        			act.ActHighlights+="@When we tried to put the original result in context, by comparing its defining values with similar ones, we discovered the following:\n\t";
		        		}
		        		act.ActHighlights+=add_to_notes.replace("\n", "\n\t");
		        	}
		        }
		       /* else if(subtsk.getDifferencesFromOrigin().get(0)==-2){
		        	currentSlide.timeCreationText=System.nanoTime();
		        	currentSlide.Title="Drill In Slide For Row "+String.valueOf(i_cubequery-1)+" of Original";
		        	currentSlide.timeCreationText+=System.nanoTime();
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-3){
		        	currentSlide.Title="Drill In Slide For Row "+String.valueOf(i_cubequery-1)+" of Original";
		        }* /
		        else {
		        	slideToEnd.add(currentSlide);
		        	numSlideToRemove.add(j);
		        	currentSlide.timeCreationText=System.nanoTime();
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
		        	currentSlide.timeCreationText=System.nanoTime()-currentSlide.timeCreationText;
		        }
    			long replace_time=System.nanoTime();
    			currentSlide.Notes=currentSlide.Notes.replace("_dim.", " at ").replace("_dim", " ").replace("lvl", " level ").replace("  ", " ");
    			currentSlide.timeCreationText+=System.nanoTime()-replace_time;
    			currentSlide.timeCreationAudio=System.nanoTime();
    			AudioMgr.CreateSound(currentSlide.Notes, currentSlide.getAudio().getFileName());
    			currentSlide.timeCreationAudio=System.nanoTime()-currentSlide.timeCreationAudio;
    			//i_cubequery++;
    		}
    		
    	}
    	
    	
    	if(slideToEnd.size()>0){
    		/*======== Act 3 =============* /
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
    
    void customCopyArray(String[][] SlideTable,String[][] currentTable,Color[][] colorTable,Color[][] currentColorTable,String [][] newTable,Color[][] newColorTable){
    	
    	int col_width=SlideTable[0].length;
    	int rows_width=SlideTable.length+currentTable.length;
    	TreeSet<String> cols=new  TreeSet<String>();
    	
    	for(int i=2;i<SlideTable[0].length;i++) cols.add(SlideTable[0][i]);
    	for(int i=2;i<currentTable[0].length;i++) cols.add(currentTable[0][i]);
    	
    	if(SlideTable[0].length<currentTable[0].length){
    		col_width=currentTable[0].length;
    	}
    	
    	//newTable=new String[rows_width+1][cols.size()+2];
    	//newColorTable=new Color[rows_width+1][cols.size()+2];
    	
    	for(int i=0;i<SlideTable.length;i++){
    		if(i==0){
    			newTable[i][0]=SlideTable[i][0];
    			newTable[i][1]=SlideTable[i][1];
    			newColorTable[i][0]=colorTable[i][0];
    			newColorTable[i][1]=colorTable[i][1];
    			for(int j=0;j<cols.size();j++) {
    				newTable[i][j+2]=cols.toArray()[j].toString();
        			newColorTable[i][j+2]=colorTable[i][0];
    			}
    		}
    		else {
    			newTable[i][0]=SlideTable[i][0];
    			newTable[i][1]=SlideTable[i][1];
    			newColorTable[i][0]=colorTable[i][0];
    			newColorTable[i][1]=colorTable[i][1];
    			for(int j=2;j<newTable[i].length;j++) {
    				newTable[i][j]="-";
    				newColorTable[i][j]=colorTable[0][0];
    				for(int k=0;k<SlideTable[i].length;k++){
    					if(newTable[0][j].equals(SlideTable[0][k])) {
    						newTable[i][j]=SlideTable[i][k];
    		    			newColorTable[i][j]=colorTable[i][k];
    					}
    				}
    			}
    		}
    	}
    	
    	for(int cols_index=0;cols_index<col_width;cols_index++) {
    		newTable[SlideTable.length][cols_index]="";
    		newColorTable[colorTable.length][cols_index]=Color.black;
    	}
    	
    	for(int i=0;i<currentTable.length;i++){
    		if(i==0){
    			newTable[SlideTable.length+i][0]=currentTable[i][0];
    			newTable[SlideTable.length+i][1]=currentTable[i][1];
    			newColorTable[SlideTable.length+i][0]=colorTable[i][0];
    			newColorTable[SlideTable.length+i][1]=colorTable[i][1];
    			for(int j=0;j<cols.size();j++) {
    				newTable[i][j+2]=cols.toArray()[j].toString();
        			newColorTable[i][j+2]=colorTable[i][0];
    			}
    		}
    		else {
    			newTable[SlideTable.length+i][0]=currentTable[i][0];
    			newTable[SlideTable.length+i][1]=currentTable[i][1];
    			newColorTable[SlideTable.length+i][0]=currentColorTable[i][0];
    			newColorTable[SlideTable.length+i][1]=currentColorTable[i][1];
    			for(int j=2;j<newTable[i].length;j++) {
    				newTable[SlideTable.length+i][j]="-";
    				newColorTable[SlideTable.length+i][j]=currentColorTable[0][0];
    				for(int k=0;k<currentTable[i].length;k++){
    					if(newTable[0][j].equals(currentTable[0][k])) {
    						newTable[SlideTable.length+i][j]=currentTable[i][k];
    		    			newColorTable[SlideTable.length+i][j]=currentColorTable[i][k];
    					}
    				}
    			}
    		}
    	}
    	
    	for(int k=0;k<newTable.length;k++){
    		for(int l=0;l<newTable[k].length;l++) {
    			if(newTable[k][l]==null) {
    				newTable[k][l]="";
    				newColorTable[k][l]=Color.black;
    			}
    		}
    	}
    }*/
    
  /*  public File GetFileCmds(){
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
    }*/
        
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
   	
	/*public void ParseCreation(String Inpstr){
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
	}*/
	
	/* This Function Parse this String :
	 * 	CREATE CUBE "custom name" RELATED SQL_TABLE "name of table"
	 *  REFERENCES DIMENSION "name_dimension",......,"name_dimension";
	 */
	/*public void ParseCrtCube(String[] crt_cb){
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
	}*/
	
	/*void printParseError(String[] token){
		System.out.println("Parse Error In Statment:");
		for(int i=0;i<token.length;i++) System.out.println(i+": "+token[i]);
	}*/
	
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
	
	/* This Function Parse this String :
	 * 	CREATE DIMENSION "custom name" 
	 *  RELATED SQL_TABLE "name of table"
	 *  LIST OF LEVEL { "field_table" as "custom_name",
	 *  				.
	 *  				.
	 *  				.
	 *  				,"field_table" as "custom_name" }
	 *  HIERARCHY "custom_name">"custom_name" .... >"custom_name";
	 */
	/*public void ParseCrtLevels(String[] crt_lvl){
		String DimensionName = null;
		String DimensionTbl = null;
		ArrayList<String> Fld_Name=new ArrayList<String>();
		ArrayList<String> CustomFld_Name=new ArrayList<String>();
		
		String[] Hierarchy = null;
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
											Hierarchy=crt_lvl[i+1].split(">");
											System.out.print("Hierarchy:");
											for(int j=0;j<Hierarchy.length;j++) System.out.print(Hierarchy[j]+">");
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
	*/
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
    
    void constructDimension(){
    	/*/var/www/html/components/com_sqlform/assets/pptx/*/
        this.ParseFile(getClass().getClassLoader().getResourceAsStream("resources/beta.txt"));/*Create Dimension,Cube*/
    }
    
    public static void main(String[] args) {
        MainEngine MainEng=new MainEngine();
        MainEng.InitializeCubeMgr();
        MainEng.createDefaultFolders();
        MainEng.CubeManager.CreateCubeBase(MainEng.InsertFromKeyboardDBInfos());        
        //Me.ParseFile(Me.GetFileCmds());
        MainEng.constructDimension();
        //MainEng.NewRequestSqlQuery("");
        MainEng.getCubeQueriesFromFile(new File("InputFiles/test.ini"));/*Create Stories*/
        //MainEng.getCubeQueriesFromFile(new File(args[0]));
        //MainEng.getCubeQueriesFromFile(new File("InputFiles/NativeAge.ini"));
//      MainEng.newRequestCubeQuery(null);
        
        System.out.println("=======Finish======");
        //MTTS.server.Mary.shutdown();
        System.exit(0);
    }
    
    public String InsertFromKeyboardDBInfos(){
    	dbname="adult_no_dublic";
    	return dbname;
    }
}
