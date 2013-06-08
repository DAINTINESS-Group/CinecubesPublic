package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CubeMgr.CubeBase.CubeBase;
import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Dimension;
import CubeMgr.CubeBase.Hierarchy;
import CubeMgr.StarSchema.SqlQuery;

public class TaskDrillIn extends Task {

	ArrayList<SubTask> substodelete;
	ArrayList<SubTask> subschecked;
	
	public TaskDrillIn() {
		super();
		substodelete=new ArrayList<SubTask>();
		subschecked=new ArrayList<SubTask>();
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
    
   
    /* Cubequery Version to Generate Subtasks
     * 
     */
    public void generateSubTasks(CubeBase cubeBase){
    	
    	SqlQuery newSqlQuery=this.cubeQuery.get(1).sqlQuery;
    	    	
    	int[] minRowPerValue=new int[newSqlQuery.Res.getRowPivot().size()];
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
    		minRowPerValue[i]=this.getMinRow(newSqlQuery.Res.getResultArray(), newSqlQuery.Res.getRowPivot().toArray()[i].toString(), 1);
    	}
    	
    	int[] maxRowPerValue=new int[newSqlQuery.Res.getRowPivot().size()];
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
    		maxRowPerValue[i]=this.getMaxRow(newSqlQuery.Res.getResultArray(), newSqlQuery.Res.getRowPivot().toArray()[i].toString(), 1);
    	}
    	
    	    	
    	/* First Approach*/
    	for(int i=0;i<newSqlQuery.Res.getRowPivot().size();i++){
	        String[] valueOfMin=new String[2];
	        valueOfMin[0]=newSqlQuery.Res.getResultArray()[minRowPerValue[i]][0]; /*Gamma La value*/
	        valueOfMin[1]=newSqlQuery.Res.getResultArray()[minRowPerValue[i]][1]; /*Gamma Lb balue*/
	        String[] valueOfMax=new String[2];
	        valueOfMax[0]=newSqlQuery.Res.getResultArray()[maxRowPerValue[i]][0]; /*Gamma La value*/
	        valueOfMax[1]=newSqlQuery.Res.getResultArray()[maxRowPerValue[i]][1]; /*Gamma Lb balue*/
	        
	        
	       /* doDrillIn(cubeBase,valueOfMin,-2);/*MIN*/
	       /* doDrillIn(cubeBase,valueOfMax,-3);/*MAX*/
	        System.out.println("Row Min Drill In : "+String.valueOf(i+1));
	        doDrillInRowVersion(cubeBase,valueOfMin,-2);
	        this.getLastSubTask().addDifferenceFromOrigin(i);
	        
	        System.out.println("Row Max Drill In : "+String.valueOf(i+1));
	        doDrillInRowVersion(cubeBase,valueOfMax,-3);
	        this.getLastSubTask().addDifferenceFromOrigin(i);
	        
	        recreateResultArrayForMinMaxOfArray_v1();
    	}
    	//for(SubTask dlt:substodelete) this.subTasks.remove(dlt);
        
    }
    
    void doDrillInRowVersion(CubeBase cubeBase,String[] GammaLaLb,int mode){
    	
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
		if(mode==-2) toadd[1]="'min'";
		else toadd[1]="'max'";
		
		newQuery.GammaExpressions.add(toadd);
		
		addSubTask(newQuery,mode);
		
		this.getLastSubTask().execute(cubeBase.DB);
    }
    
    @SuppressWarnings("unused")
	private void doDrillIn(CubeBase cubeBase,String[] GammaLaLb,int mode){
    	for(int i=0;i<this.cubeQuery.get(1).GammaExpressions.size();i++){
    		String[] gamma_tmp=this.cubeQuery.get(1).GammaExpressions.get(i); /*get Gamma Li*/
    		int index_sigma_todelete=this.getIndexOfSigmaToDelete(this.cubeQuery.get(1).SigmaExpressions,gamma_tmp[0]);
    		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
    		if(child_level_of_gamma!=null) {
    			this.createSubTask(this.cubeQuery.get(1), GammaLaLb[i], index_sigma_todelete,child_level_of_gamma,i,mode);
    			this.getLastSubTask().execute(cubeBase.DB);
    			
    		}
    	}
    }
    
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

	private void createSubTask(CubeQuery startQuery, String value, int index_sigma_todelete, String child_level_of_gamma, int index_gamma, int mode){
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
		
    }
    
    private void addSubTask(CubeQuery cubequery,int difference){
    	this.addNewSubTask();
		this.cubeQuery.add(cubequery);
        SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(cubequery);
        System.out.println(cubequery.toString());
        printBorderLine();
        this.getLastSubTask().setExtractionMethod(newSqlQuery);
        this.getLastSubTask().addDifferenceFromOrigin(difference);
    }
    
   
	public ArrayList<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(ArrayList<SubTask> arrayList) {
		this.subTasks = arrayList;
	};
 
	
	public ArrayList<String[]> findBrothers(CubeBase cubebase, SqlQuery Original) {
		printBorderLine();
		//System.out.println("Generated Queries");
		printBorderLine();
		ArrayList<String[]> finds=new ArrayList<>();
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
				if(cubebase.returnIfTableIsDimensionTbl(table)) {/*1->Fact Table, 2->DimensionTable*/
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
	}
	
	void printSqlQueryArrayList(ArrayList<SqlQuery> toprint){
		//for(SqlQuery x : toprint) x.printQuery();
	}
	
	void printStringArrayList(ArrayList<String> toprint){
		printBorderLine();
		for(String x: toprint) {
			System.out.println(x);
		}
		printBorderLine();
	}
	
	boolean tryParseInt(String value){
		boolean ret_value=true;
	    try{
	    	Integer.parseInt(value);
	    }catch(NumberFormatException nfe){  
	          ret_value=false;
	    }  
	     	return ret_value;  
	}
	
	void printBorderLine(){
    	System.out.println("=====================================");
    }
	
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
	
	ArrayList<String> getUniqueValueInListOfArrayString(ArrayList<String[]> lst,int column){
		ArrayList<String> ret_value=new ArrayList<>();
		for(int i=0;i<lst.size();i++){
			if(ret_value.contains(lst.get(i)[column])==false){
				ret_value.add(new String(lst.get(i)[column]));
			}
		}
		return ret_value;
	}
	
	String getConnectedCondition(ArrayList<String[]> WhereClause,int condTocheckID){
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
	}
	
	/*this maybe help for highlight*/
	int getMinRow(String[][] table,String Value,int column){ /*For result Array Not Pivot Table*/
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
	
	int getMaxRow(String[][] table,String Value,int column){ /*For result Array Not Pivot Table*/
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
	}
	
	private void recreateResultArrayForMinMaxOfArray_v1(){
		
        for(int i=2;i<this.subTasks.size();i++){
        	for(int j=i+1;j<this.subTasks.size();j++){
        		if(this.cubeQuery.get(i).GammaExpressions.get(0)[0].equals(this.cubeQuery.get(j).GammaExpressions.get(0)[0])){
        			if(this.subTasks.get(i).getDifferenceFromOrigin(0)!=this.subTasks.get(j).getDifferenceFromOrigin(0) 
        					&& !substodelete.contains(this.subTasks.get(j)) && !subschecked.contains(this.subTasks.get(i))){
        				String[][] tmp_i=this.subTasks.get(i).getExtractionMethod().Res.getResultArray();
        				String[][] tmp_j=this.subTasks.get(j).getExtractionMethod().Res.getResultArray();
        				
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
        				subschecked.add(this.subTasks.get(i));
        			}
        		}
        	}
        }
        for(SubTask dlt:substodelete) this.subTasks.remove(dlt);
        
	}
}
