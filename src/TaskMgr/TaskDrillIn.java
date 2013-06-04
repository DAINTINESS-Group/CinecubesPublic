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

	public TaskDrillIn() {
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
    
   
    /* Cubequery Version to Generate Subtasks
     * 
     */
    public void generateSubTasks(CubeBase cubeBase){
    	
    	SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(this.cubeQuery.get(0));
        SubTask tmp=new SubTask(); 
        tmp.setExtractionMethod(newSqlQuery);
        tmp.execute(cubeBase.DB);
        
        String[] valueOfMin=new String[2];
        valueOfMin[0]=newSqlQuery.Res.getResultArray()[2][0]; /*Gamma La value*/
        valueOfMin[1]=newSqlQuery.Res.getResultArray()[2][1]; /*Gamma Lb balue*/
        String[] valueOfMax=new String[2];
        valueOfMax[0]=newSqlQuery.Res.getResultArray()[newSqlQuery.Res.getResultArray().length-1][0]; /*Gamma La value*/
        valueOfMax[1]=newSqlQuery.Res.getResultArray()[newSqlQuery.Res.getResultArray().length-1][1]; /*Gamma Lb balue*/
        
        
        doDrillIn(cubeBase,valueOfMin,-2);
        doDrillIn(cubeBase,valueOfMax,-3);
        String x=null;
    }
    
    
    private void doDrillIn(CubeBase cubeBase,String[] GammaLaLb,int mode){
    	for(int i=0;i<this.cubeQuery.get(0).GammaExpressions.size();i++){
    		String[] gamma_tmp=this.cubeQuery.get(0).GammaExpressions.get(i); /*get Gamma Li*/
    		int index_sigma_todelete=this.getIndexOfSigmaToDelete(this.cubeQuery.get(0).SigmaExpressions,gamma_tmp[0]);
    		String child_level_of_gamma=this.getChildOfGamma(cubeBase,gamma_tmp);
    		if(child_level_of_gamma!=null) {
    			this.createSubTask(this.cubeQuery.get(0), GammaLaLb[i], index_sigma_todelete,child_level_of_gamma,i,mode);
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
		addSubTask(newQuery,mode);
    }
    
    private void addSubTask(CubeQuery cubequery,int difference){
    	this.addNewSubTask();
		this.cubeQuery.add(cubequery);
        SqlQuery newSqlQuery=new SqlQuery();
        newSqlQuery.produceExtractionMethod(cubequery);
        System.out.println(newSqlQuery.toString());
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
}
