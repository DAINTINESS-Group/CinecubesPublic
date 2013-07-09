package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Level;
import CubeMgr.StarSchema.SqlQuery;
import HighlightMgr.HighlightCompareColumn;
import HighlightMgr.HighlightCompareRow;
import HighlightMgr.HighlightMax;
import HighlightMgr.HighlightMin;
import HighlightMgr.HighlightTable;
import StoryMgr.Act;
import StoryMgr.PptxSlide;
import StoryMgr.Tabular;

public class TaskActI extends Task {

	public TaskActI() {
		super();
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
        
    /* Cubequery Version to Generate Subtasks
     * 
     */
    public void generateSubTasks(CubeBase cubeBase){
    	for(int i=0;i<this.cubeQuery.get(1).SigmaExpressions.size();i++){
    		//long strTime=System.nanoTime();
    		createSummarizeSubTask(i,cubeBase,this.cubeQuery.get(1));
    		//this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
    		
    		//strTime=System.nanoTime();
    		//createBrothers(i,cubeBase,this.cubeQuery.get(1));
    		//this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
    	}
    	
    	/*for(int i=1;i<this.subTasks.size();i++){
    		SubTask sbtk=this.getSubTask(i);
	    	
	    	//HighlightTable hltbl=new HighlightTable();
	    	//this.highlights.add(hltbl);
	    	/*hltbl.createMinHightlight(current);
	    	hltbl.createMaxHightlight(current);
	    	hltbl.createMiddleHightlight(current);*/
	    	/*if(sbtk.getDifferencesFromOrigin().size()>0 && sbtk.getDifferenceFromOrigin(0)==-1){
	    		int tmp_it=this.getIndexOfSigma(this.cubeQuery.get(1).SigmaExpressions,this.cubeQuery.get(1).GammaExpressions.get(sbtk.getDifferenceFromOrigin(1))[0]);
	    		if(tmp_it>-1 && sbtk.getDifferenceFromOrigin(1)==0) hltbl.setBoldColumn(sbtk.getExtractionMethod().Res.getColPivot(),this.cubeQuery.get(1).SigmaExpressions.get(tmp_it)[2]);
	    		else hltbl.setBoldRow(sbtk.getExtractionMethod().Res.getRowPivot(),this.cubeQuery.get(1).SigmaExpressions.get(tmp_it)[2]);
	    	}* /
    	}*/ 
    	//printBorderLine();
    	//printBorderLine();
    }
    
    @Override
	public void constructActEpidoses(Act currentAct,Act OriginalAct) {
		CubeQuery origCubeQuery=((PptxSlide)OriginalAct.getEpisode(0)).CbQOfSlide.get(0);
		//boolean ActHasWriteHiglights=false;
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
    		
		    newSlide.addSubTask(subtsk);
		    
		    if((currentSqlQuery.Res.getResultArray()!=null)){
		    	/*====== Compute Pivot Table =======*/
		    	newSlide.timeCreationTabular=System.nanoTime();
			    tbl.CreatePivotTable(subtsk.getExtractionMethod().Res.getRowPivot(),
			    		 			  subtsk.getExtractionMethod().Res.getColPivot(),
			    		 			  subtsk.getExtractionMethod().Res.getResultArray(),
			    		 			  extraPivot);
			    newSlide.timeCreationTabular=System.nanoTime()-newSlide.timeCreationTabular;
			    currentAct.addEpisode(newSlide);
			    
			    if(subtsk.getHighlight()==null) subtsk.setHighlight(new HighlightTable());
			   // HighlightTable hltbl=(HighlightTable) subtsk.getHighlight();
			    HighlightMin hlmin=new HighlightMin();
		    	HighlightMax hlmax=new HighlightMax();
		    	HighlightCompareColumn hlcmpcol=new HighlightCompareColumn();
		    	HighlightCompareRow hlcmprow=new HighlightCompareRow();
		    	newSlide.highlight.add(hlmin);
			    newSlide.highlight.add(hlmax);
			    newSlide.highlight.add(hlcmpcol);
			    newSlide.highlight.add(hlcmprow);
			    
	        	if(subtsk.getDifferenceFromOrigin(0)==-1){
	        		
		        	int tmp_it=this.getIndexOfSigma(origCubeQuery.SigmaExpressions,currentCubeQuery.GammaExpressions.get(subtsk.getDifferenceFromOrigin(1))[0]);
		        	/*====== Calculate Highlioghts =======*/
		        	newSlide.timeComputeHighlights=System.nanoTime();
		        	hlmin.execute(subtsk.getExtractionMethod().Res.getResultArray());
		        	hlmax.execute(subtsk.getExtractionMethod().Res.getResultArray());
		        	tbl.boldColumn=getBoldColumn(subtsk.getExtractionMethod().Res.getColPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
		    		tbl.boldRow=getBoldRow(subtsk.getExtractionMethod().Res.getRowPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
		        	
		    		hlcmpcol.bold=tbl.boldColumn;
		        	if(tbl.boldColumn>-1) hlcmpcol.execute(tbl.getPivotTable());
		        	
		        	hlcmprow.bold=tbl.boldRow;
		        	if(tbl.boldRow>-1) hlcmprow.execute(tbl.getPivotTable());
		        	/*OLD WAY
		        	hltbl.createMinHightlight(subtsk.getExtractionMethod().Res.getResultArray());
			    	hltbl.createMaxHightlight(subtsk.getExtractionMethod().Res.getResultArray());
			    	hltbl.createMiddleHightlight(subtsk.getExtractionMethod().Res.getResultArray());
			    	if(tmp_it>-1 && subtsk.getDifferenceFromOrigin(1)==0) hltbl.setBoldColumn(subtsk.getExtractionMethod().Res.getColPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
		    		else hltbl.setBoldRow(subtsk.getExtractionMethod().Res.getRowPivot(),origCubeQuery.SigmaExpressions.get(tmp_it)[2]);
			    	//htable.findDominatedRowsColumns(tbl.getPivotTable(), tbl.colortable);
			    	tbl.boldColumn=hltbl.boldColumn;
		    		tbl.boldRow=hltbl.boldRow;
			    	if(tbl.boldColumn>-1) hltbl.ComparingToSiblingColumn_v1(tbl.getPivotTable());
					if(tbl.boldRow>-1)    hltbl.ComparingToSiblingRow_v1(tbl.getPivotTable());*/
			    	newSlide.timeComputeHighlights=System.nanoTime()-newSlide.timeComputeHighlights;
	        	}
		    	//newSlide.highlight.add(hltbl);
		    	
		    	/*====== Compute Color Table =======*/
		    	newSlide.timeCreationColorTable=System.nanoTime();
		    	tbl.setColorTable(newSlide.highlight);
	        	newSlide.timeCreationColorTable=System.nanoTime()-newSlide.timeCreationColorTable;        	
			    
		    }
		    else if(j==0){
		    	newSlide.timeCreationText=System.nanoTime();
        		newSlide.Title=currentSqlQuery.Res.TitleOfColumns;
        		newSlide.timeCreationText=System.nanoTime()-newSlide.timeCreationText;
        		currentAct.addEpisode(newSlide);
		    }
		    
		}
	}
    
    private void createSummarizeSubTask(int i,CubeBase cubeBase,CubeQuery startQuery){
    	String dimension=startQuery.SigmaExpressions.get(i)[0].split("\\.")[0];
		String lvlname=startQuery.SigmaExpressions.get(i)[0].split("\\.")[1];
		String table=startQuery.referCube.getSqlTableByDimensionName(dimension);
		String field=startQuery.referCube.getSqlFieldByDimensionLevelName(dimension, lvlname);
		
		Level parentLvl=startQuery.referCube.getParentLevel(dimension,lvlname);
		if(parentLvl==null) return;
		String field2=parentLvl.lvlAttributes.get(0).getAttribute().name;
		
		String tmp_query="SELECT DISTINCT "+field2+ " FROM "+table+" WHERE "+field+"="+startQuery.SigmaExpressions.get(i)[2];
		ResultSet rs=cubeBase.DB.executeSql(tmp_query);
		try {
			rs.beforeFirst();
			while(rs.next()){
				String newValue="'"+rs.getString(1)+"'";
				if(tryParseInt(rs.getString(1))) newValue=rs.getString(1);				
				createSubTask(startQuery,newValue,i,1,parentLvl.name,cubeBase);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
    
    private int getIndexOfSigma(ArrayList<String[]> sigmaExpressions,String gamma_dim) {
		int ret_value=-1;
		int i=0;
		for(String[] sigma : sigmaExpressions ){
			if(sigma[0].split("\\.")[0].equals(gamma_dim)) ret_value=i;
			i++;
		}
		return ret_value;
	}
    
    /*	
     * This function return Parent Level Attribute name
     * Parameters
     * 		cubeBase such that to get Dimensions of Cube
     * 		table 	 dimension table name
     * 		field 	 name of field witch need to find Parent
     *
     */
    /*@SuppressWarnings("unused")
	private String getParentLevel_version_sqltable(CubeBase cubeBase,String table,String field){
    	Attribute attr=cubeBase.DB.getFieldOfSqlTable(table,field);
		List<Dimension> dimensions=cubeBase.dimensions;
		for(int j=0;j<dimensions.size();j++){ //for each dimension
			ArrayList<Hierarchy> current_hierachy=dimensions.get(j).getHier();
			for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
				List<Level> current_lvls=current_hierachy.get(k).lvls;
				for(int l=0;l<current_lvls.size();l++){ 
					for(int m=0;m<current_lvls.get(l).lvlAttributes.size();m++){
						if(current_lvls.get(l).lvlAttributes.get(m).getAttribute().equals(attr)){
							return current_lvls.get(l+1).lvlAttributes.get(m).getAttribute().name;
						}
					}
				}
			}
		}
		return "";
    }*/
        
    /*void createBrothers(int i,CubeBase cubeBase,CubeQuery startQuery){
    	
    	String dimension=startQuery.SigmaExpressions.get(i)[0].split("\\.")[0];
		String lvlname=startQuery.SigmaExpressions.get(i)[0].split("\\.")[1];
		String table=startQuery.referCube.getSqlTableByDimensionName(dimension);
		String field=startQuery.referCube.getSqlFieldByDimensionLevelName(dimension, lvlname);
		
		String tmp_query="SELECT DISTINCT "+field+ " FROM "+table+" WHERE "+field+"!="+startQuery.SigmaExpressions.get(i)[2];
		ResultSet rs=cubeBase.DB.executeSql(tmp_query);
								
		try {
			rs.beforeFirst();
			while(rs.next()){
				String newValue="'"+rs.getString(1)+"'";
				if(tryParseInt(rs.getString(1))) newValue=rs.getString(1);				
				createSubTask(startQuery,newValue,i,-1,null,cubeBase);
				//this.getLastSubTask().execute(cubeBase.DB);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }*/
            
    private void createSubTask(CubeQuery startQuery,String value,int toChange,int toReplace,String changevalue,CubeBase cubeBase){
    				
		/* This peace of code to see again */
		
		/* That check if a sigma expression is in gamma then add sigma to gamma
		 * and do two insertion of Subtask . Each subtask was one gamma same and
		 * change the other. 
		 * I must check if the gamma which add is correct  
		 */
		if(checkIfSigmaExprIsInGamma(toChange,startQuery)==false){
			for(int i=0;i<startQuery.GammaExpressions.size();i++){
				long strTime=System.nanoTime();
				CubeQuery newQuery1=new CubeQuery("");
				copyListofArrayString(startQuery.GammaExpressions, newQuery1.GammaExpressions);
				copyListofArrayString(startQuery.SigmaExpressions, newQuery1.SigmaExpressions);
				newQuery1.AggregateFunction=startQuery.AggregateFunction;
				newQuery1.referCube=startQuery.referCube;
				newQuery1.setMsr(startQuery.getMsr());
				String [] tmp=newQuery1.SigmaExpressions.get(toChange)[0].split("\\.");
				newQuery1.GammaExpressions.get(i)[0]=tmp[0];
				newQuery1.GammaExpressions.get(i)[1]=tmp[1];
				if(toReplace==1){
					newQuery1.SigmaExpressions.get(toChange)[0]=tmp[0]+"."+changevalue;
					newQuery1.SigmaExpressions.get(toChange)[2]=value;
				}
				/*System.out.println(newQuery1.toString());*/
				long endTime=System.nanoTime();
				addSubTask(newQuery1,i,toReplace);
				this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
				this.getLastSubTask().timeProduceOfCubeQuery=endTime-strTime;
				this.getLastSubTask().execute(cubeBase.DB);
		        
			}
        }
		else {
			long strTime=System.nanoTime();
			CubeQuery newQuery=new CubeQuery("");
			copyListofArrayString(startQuery.GammaExpressions, newQuery.GammaExpressions);
			copyListofArrayString(startQuery.SigmaExpressions, newQuery.SigmaExpressions);
			newQuery.AggregateFunction=startQuery.AggregateFunction;
			newQuery.referCube=startQuery.referCube;
			newQuery.setMsr(startQuery.getMsr());		
			
			newQuery.SigmaExpressions.get(toChange)[2]=value;
			
			if(toReplace==1){			
				String[] tobeGamma=newQuery.SigmaExpressions.get(toChange)[0].split("\\.");
				for(int i=0;i<newQuery.GammaExpressions.size();i++){
					if(newQuery.GammaExpressions.get(i)[0].equals(tobeGamma[0])){
						newQuery.GammaExpressions.get(i)[1]=tobeGamma[1];
					}
				}
				newQuery.SigmaExpressions.get(toChange)[0]=tobeGamma[0]+"."+changevalue;
			}
			long endTime=System.nanoTime();
			addSubTask(newQuery,getGammaPositionOfSigma(toChange,newQuery),toReplace);
			this.getLastSubTask().timeProduceOfCubeQuery=endTime-strTime;
			this.getLastSubTask().timeCreationOfSbTsk=System.nanoTime()-strTime;
			this.getLastSubTask().execute(cubeBase.DB);
		}
    }
    
    private void addSubTask(CubeQuery cubequery,int difference,int replace){
    	this.addNewSubTask();
		this.cubeQuery.add(cubequery);
        SqlQuery newSqlQuery=new SqlQuery();
        long strTime=System.nanoTime();
        newSqlQuery.produceExtractionMethod(cubequery);
        this.getLastSubTask().timeProduceOfExtractionMethod=System.nanoTime()-strTime;
        cubequery.sqlQuery=newSqlQuery;
        this.getLastSubTask().setExtractionMethod(newSqlQuery);
        if(replace==1) this.getLastSubTask().addDifferenceFromOrigin(-1);
        
        HighlightTable hltbl=new HighlightTable();
        this.getLastSubTask().setHighlight(hltbl);
        
    	this.getLastSubTask().addDifferenceFromOrigin(difference);
    }
    
    private boolean checkIfSigmaExprIsInGamma(int toChange, CubeQuery newQuery) {
			boolean ret_value=false;
			String [] tmp=newQuery.SigmaExpressions.get(toChange)[0].split("\\.");
			for(String [] gammaExpr : newQuery.GammaExpressions){
				if(gammaExpr[0].equals(tmp[0])) ret_value=true; 
			}
			return ret_value;
	}

    private int getGammaPositionOfSigma(int toChange, CubeQuery newQuery) {
		int ret_value=0;
		String [] tmp=newQuery.SigmaExpressions.get(toChange)[0].split("\\.");
		for(int i=0; i< newQuery.GammaExpressions.size();i++){
			String [] gammaExpr=newQuery.GammaExpressions.get(i);
			if(gammaExpr[0].equals(tmp[0])) {
				ret_value=i;
				break;
			}
		}
		return ret_value;
    }
    
	/*private void createSubTask(SqlQuery Sbsql,Database DB,String [] condA,String [] condB){
    	SubTask sbtsk=new SubTask();
		ArrayList<String []> newWhere=new ArrayList<String []>();
		copyListofArrayString(Sbsql.WhereClause, newWhere);
		newWhere.get(Integer.parseInt(condA[1]))[2]=condA[0];
		
		if(condB!=null){
			if(Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])){
				newWhere.get(Integer.parseInt(condB[1]))[2]=condB[0];
			}
		}
		
		SqlQuery newsql=new SqlQuery(Sbsql.SelectClauseMeasure,Sbsql.FromClause,newWhere,Sbsql.GroupByClause);
		sbtsk.setExtractionMethod(newsql);
		sbtsk.addDifferenceFromOrigin(Integer.parseInt(condA[1]));
		
		if(condB!=null){
			if(Integer.parseInt(condB[1])!=Integer.parseInt(condA[1])){
				sbtsk.addDifferenceFromOrigin(Integer.parseInt(condB[1]));
			}
		}
		
		if(sbtsk.execute(DB)) subTasks.add(sbtsk);
    }*/
     
	/*public ArrayList<String[]> findBrothers(CubeBase cubebase, SqlQuery Original) {
		printBorderLine();
		//System.out.println("Generated Queries");
		printBorderLine();
		ArrayList<String[]> finds=new ArrayList<String[]>();
		for( int i=0;i<Original.WhereClause.size();i++){
			String[] condition=Original.WhereClause.get(i);
			if(condition[2].contains("'") || tryParseInt(condition[2])){
				String[] tmp2=condition[0].split("\\."); //0->table  ,1--> field name
				String table=tmp2[0];
				for(String[] fromTable : Original.FromClause){
					if(fromTable.length==1 && table.equals(fromTable[0])) break;
					else if(table.equals(fromTable[1]) || table.equals(fromTable[0])) {
							table=fromTable[0];
							
					}
				}
				if(cubebase.returnIfTableIsDimensionTbl(table)) {/*1->Fact Table, 2->DimensionTable* /
					String tmp_query="SELECT DISTINCT "+tmp2[1]+ " FROM "+table+" WHERE "+tmp2[1]+"!="+condition[2];
					ResultSet rs=cubebase.DB.executeSql(tmp_query);
										
					try {
						rs.beforeFirst();
						while(rs.next()){
							String newValue="'"+rs.getString(1)+"'";
							if(tryParseInt(rs.getString(1))) newValue=rs.getString(1);
							String[] toAdd={newValue,Integer.toString(i)};
							finds.add(toAdd);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return finds;
	}*/
	
	/*void printSqlQueryArrayList(ArrayList<SqlQuery> toprint){
		for(SqlQuery x : toprint) x.printQuery();
	}*/
	
	/*void printStringArrayList(ArrayList<String> toprint){
		printBorderLine();
		for(String x: toprint) {
			System.out.println(x);
		}
		printBorderLine();
	}*/
	
	boolean tryParseInt(String value){
		boolean ret_value=true;
	    try{
	    	Integer.parseInt(value);
	    }catch(NumberFormatException nfe){  
	          ret_value=false;
	    }  
	     	return ret_value;  
	}
	
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
	
	/*void createSummarizedQuery(ArrayList<String[]> lst,SqlQuery Original,Database DB,int tmp){
		//ArrayList<String> numOffieldsToSummarized=getUniqueValueInListOfArrayString(lst,1);
		//System.out.println("Summarized Queries");
		printBorderLine();
		String condConnected=getConnectedCondition(Original.WhereClause,tmp);
			
		ArrayList<String []> newWhere=new ArrayList<String []>();
		ArrayList<String[]> newGrouper=new ArrayList<String[]>();
		copyListofArrayString(Original.GroupByClause, newGrouper);
		copyListofArrayString(Original.WhereClause, newWhere);
		
		for(int j=0;j<newGrouper.size();j++){
			/*System.out.println(newGrouper.get(j)[0]);
			System.out.println("CondConn:"+condConnected);
			//System.out.println(newGrouper.get(j)[0].replace(condConnected, Original.WhereClause.get(tmp)[0]));* /
			if(newGrouper.get(j)[0].equals(condConnected)){
				newGrouper.get(j)[0]=Original.WhereClause.get(tmp)[0];
			}
		}
		
		newWhere.remove(tmp);
		
		SqlQuery newsql=new SqlQuery(Original.SelectClauseMeasure,Original.FromClause,newWhere,newGrouper);
		SubTask sbtsk=new SubTask();
		sbtsk.setExtractionMethod(newsql);
		sbtsk.addDifferenceFromOrigin(-1);
		sbtsk.addDifferenceFromOrigin(tmp);
		sbtsk.execute(DB);
		subTasks.add(sbtsk);
		newsql.printQuery();
	}*/
	
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
	
	private int getBoldColumn(TreeSet<String> Columns,String nameColumnToBold){
		for(int j=0;j<Columns.size();j++){
			if(("'"+Columns.toArray()[j].toString()+"'").equals(nameColumnToBold)) return j+1;
		}
		return -1;
	}
	
	private int getBoldRow(TreeSet<String> Rows,String nameRowToBold){
		for(int i=0;i<Rows.size();i++){
			if(("'"+Rows.toArray()[i].toString()+"'").equals(nameRowToBold)) return i+1;
		}
		return -1;
	}
}
