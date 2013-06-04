import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import AudioMgr.AudioEngine;
import AudioMgr.FreeTTSAudioEngine;
import CubeMgr.CubeMgr;
import CubeMgr.CubeBase.BasicStoredCube;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Measure;
import CubeMgr.StarSchema.SqlQuery;
import ParserMgr.ParserManager;
import StoryMgr.Act;
import StoryMgr.PptxSlideshow;
import StoryMgr.StoryMgr;
import StoryMgr.Tabular;
import StoryMgr.pptxSlide;
import TaskMgr.SubTask;
import TaskMgr.TaskBrothers;
import TaskMgr.TaskDrillIn;
import TaskMgr.TaskMgr;
import TextMgr.TextExtraction;
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
    	StorMgr.createStory();
        /*StorMgr.createStoryOriginalRequest();*/
    	StorMgr.getStory().createNewAct();
        StorMgr.createTasks(TskMgr);
        TskMgr.createNewTask(new TaskBrothers());
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        if(cubequery==null) cubequery=DefaultCubeQuery();
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        TskMgr.getLastTask().addNewSubTask();
        
        SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(TskMgr.getLastTask().cubeQuery.get(0));
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(newSqlQuery);
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),1));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        
        AudioMgr=new FreeTTSAudioEngine();
        AudioMgr.InitializeVoiceEngine();
        
        SetupSlideEpisodes(StorMgr.getStory().getLastAct());
        
        /* Setup Slide For ACT 2*/
        
        /* CREATE ACT2 
         * (slide==query===task)
         * 	FOR each slide of ACT1
         *  	Create 2 slides :
         *  		1. Drill in for MIN-value
         *  		2. Drill in for MAX-value
         *  		3. Drill in for Closest to AVG of all(MEDIAN) not yet implemend
         */
        StorMgr.getStory().createNewAct();
        TaskDrillIn tskdrillin=new TaskDrillIn();
        TskMgr.createNewTask(tskdrillin);
        
        TskMgr.getLastTask().cubeQuery.add(cubequery);
        StorMgr.addNewTaskToStory(TskMgr.getLastTask());
        
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(createCubeQueryStartOfActSlide(StorMgr.getStory().getLastAct(),2));
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        TskMgr.getLastTask().cubeQuery.remove(0);
        SetupSlideEpisodes(StorMgr.getStory().getLastAct());
       
        StorMgr.getStory().setFinalResult(new PptxSlideshow());
        StorMgr.getStory().getFinalResult().setFilename("ppt/"+cubequery.name+".pptx");
        
        
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
        StorMgr.createStoryOriginalRequest();
        StorMgr.createTasks(TskMgr);
        
        TaskBrothers tskBrths=new TaskBrothers();
        
        TskMgr.createNewTask(tskBrths);
        StorMgr.addNewTaskToStory(tskBrths);
        TskMgr.getLastTask().addNewSubTask();
        TskMgr.getLastTask().getLastSubTask().setExtractionMethod(query);
        
        
        
        TskMgr.getLastTask().getLastSubTask().execute(CubeManager.CBase.DB);
        //TskMgr.getLastTask().getLastSubTask().computeFinding();
        TskMgr.getLastTask().generateSubTasks(CubeManager.CBase);
        AudioMgr=new FreeTTSAudioEngine();
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
    
    public SqlQuery createCubeQueryStartOfActSlide(Act act,int num_act){
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
    
    /* This Function Add Create Slide per ACT*/
    public void SetupSlideEpisodes(Act act){
    	//SqlQuery original=(SqlQuery)act.getTask().getSubTask(0).getExtractionMethod();
    	int timesIN=0;
    	System.out.println("Sum of subtasks:"+act.getTask().getSubTasks().size());
    	for(int j=0;j<act.getTask().getSubTasks().size();j++){
    		SubTask subtsk=act.getTask().getSubTask(j);
    		SqlQuery currentQuery=((SqlQuery)subtsk.getExtractionMethod());
	        if((currentQuery.Res.getResultArray()!=null)){
	        	timesIN++;
	        	pptxSlide newSlide=new pptxSlide();
		        newSlide.setSubTask(subtsk);
		        newSlide.Notes="SQL QUERY:\n"+subtsk.getExtractionMethod().toString()+"\n\nCUBE QUERY:\n"+act.getTask().cubeQuery.get(j).toString();
		        
		        Tabular tbl=new Tabular();
		        tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(), subtsk.getExtractionMethod().Res.getColPivot(), 
						subtsk.getExtractionMethod().Res.getResultArray());
		        newSlide.createVisual(tbl);
		        
		        newSlide.setAudioFile("audio/"+AudioMgr.randomIdentifier());
		        
		        newSlide.TitleColumn=new String(currentQuery.Res.TitleOfColumns);
		        newSlide.TitleRow=new String(currentQuery.Res.TitleOfRows);
		        if(subtsk.getDifferencesFromOrigin().size()==0){
		        	newSlide.Title="Original";
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-1){
		        	newSlide.Title="Summarized Slide for field : ";
		        	newSlide.Title+=act.getTask().cubeQuery.get(0).GammaExpressions.get(subtsk.getDifferenceFromOrigin(1))[0];
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-2){
		        	newSlide.Title="Drill In Slide For Min";
		        }
		        else if(subtsk.getDifferencesFromOrigin().get(0)==-3){
		        	newSlide.Title="Drill In Slide For Max";
		        }
		        else {
		        	newSlide.Title="The ~ which changed @ : ";
		        	for(int i=0;i<subtsk.getDifferencesFromOrigin().size();i++){
			        	if(i>0) newSlide.Title+=" AND ";
			        	newSlide.Title+=act.getTask().cubeQuery.get(0).GammaExpressions.get(subtsk.getDifferenceFromOrigin(i))[0];
			        }
		        	String text_cond="Conditions";
			        String text_are="are";
			        if(subtsk.getDifferencesFromOrigin().size()==1){
			        	text_cond="Condition";
			        	text_are="is";
			        }
		        	newSlide.Title=newSlide.Title.replace("~", text_cond).replace("@", text_are);
		        }
		       // newSlide.Title+="\n At columns are "+newSlide.TitleColumn+" and at rows are "+newSlide.TitleRow;
		        AudioMgr.CreateSound("Text to Create", newSlide.getAudio().getFileName());
		        StorMgr.getStory().getLastAct().addEpisode(newSlide);
	        }
	        else if(currentQuery.Res.TitleOfColumns!=null && currentQuery.Res.TitleOfColumns.contains("Act")) {
	        	pptxSlide newSlide=new pptxSlide();
		        newSlide.setSubTask(subtsk);
	        	newSlide.Title=currentQuery.Res.TitleOfColumns;
	        	StorMgr.getStory().getLastAct().addEpisode(newSlide);	        	
	        }
    	}
    	System.out.println("TimesIN:"+timesIN);
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
    
    public static void main(String[] args) {
        MainEngine MainEng=new MainEngine();
        MainEng.InitializeCubeMgr();
        MainEng.CubeManager.CreateCubeBase(MainEng.InsertFromKeyboardDBInfos());        
        //Me.ParseFile(Me.GetFileCmds());
        
        MainEng.ParseFile(new File("InputFiles/BETA/beta.txt"));/*Create Dimension,Cube*/
        //MainEng.NewRequestSqlQuery("");
        MainEng.getCubeQueriesFromFile(new File("InputFiles/cubeQueries2013_05_31.ini"));/*Create Stories*/
//        MainEng.newRequestCubeQuery(null);
        
        System.out.println("=======Finish======");
    }
    
    public String InsertFromKeyboardDBInfos(){
    	dbname="adult_no_dublic";
    	return dbname;
    }
}
