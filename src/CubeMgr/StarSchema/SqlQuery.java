package CubeMgr.StarSchema;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Dimension;
import CubeMgr.CubeBase.Hierarchy;
import CubeMgr.CubeBase.Level;
import CubeMgr.CubeBase.LinearHierarchy;
import TaskMgr.ExtractionMethod;
import TaskMgr.Result;

public class SqlQuery extends ExtractionMethod {
    
    public String[] SelectClauseMeasure;  	/* 0-->AggregateFuncName, 1--> field */  
    public ArrayList<String[]> FromClause; 	/* 0-->TABLE, 1-->customName */
    public ArrayList<String[]> WhereClause;	/* 0-->sqlfld1,1-->op,2-->sqlfld2 */
    public ArrayList<String[]> GroupByClause;
    
    public SqlQuery(){
    	init();
    }
    
    private void init(){
    	FromClause=new ArrayList<>();
    	WhereClause=new ArrayList<>();
    	GroupByClause=new ArrayList<>();
    	SelectClauseMeasure=new String[2];
    }
    
    public SqlQuery(String Aggregatefunc,ArrayList<String> Tables,ArrayList<String> Conditions,ArrayList<String> GroupAttr){
    	init();
    	SelectClauseMeasure=Aggregatefunc.split("~");
        for(String table:Tables){
        	FromClause.add(table.split("~"));
        }
        for(String condition:Conditions){
        	WhereClause.add(condition.split("~"));
        }
        for(String grouper:GroupAttr){
        	GroupByClause.add(grouper.split("~"));
        }
        
    }
    
    public SqlQuery(String[] Aggregatefunc,ArrayList<String[]> Tables,ArrayList<String[]> Conditions,ArrayList<String[]> GroupAttr){
    	init();
    	SelectClauseMeasure[0]=Aggregatefunc[0];
    	SelectClauseMeasure[1]=Aggregatefunc[1];
    	FromClause.addAll(Tables);
    	WhereClause.addAll(Conditions);
    	GroupByClause.addAll(GroupAttr);
    }
    public String getSelectClause(){
    	String ret_value="";
    	
    	ret_value+=getGroupClause()+",";
    	ret_value+= SelectClauseMeasure[0]+"("+SelectClauseMeasure[1]+") as measure,COUNT(*) as cnt";
    	return ret_value;
    }
       
    public String getFromClause(){
    	int i=0;
    	String ret_value="";
    	
    	for(String[] x : FromClause ) {    
    		if(i>0) ret_value+=",";
    		ret_value+=mergeStringTable(x);
    		i++;
    	}
    	return ret_value;
    }
    
    public String getWhereClause(){
    	int i=0;
    	String ret_value="";
    	
    	for(String[] x : WhereClause ) {    
    		if(i>0) ret_value+=" AND ";
    		ret_value+=mergeStringTable(x);
    		i++;
    	}
    	return ret_value;
    }
    
    public String getGroupClause(){/*add this to play groupper=1 to select clause*/
    	String ret_value="";
    	int i=0;
    	
    	for(String[] x : GroupByClause ){
    		if(i>0) ret_value+=",";
    		ret_value+=mergeStringTable(x);
    		i++;
    	}
    	
    	return ret_value;
    }
    
    public void printQuery(){
    	System.out.println(toString());
    	printBorderLine();
    }
    
    
    void printBorderLine(){
    	System.out.println("=====================================");
    }
    /*
     *  
     */
    public String toString(){
    	String ret_value="SELECT "+getSelectClause();
    	ret_value+="\nFROM "+ getFromClause();
    	ret_value+="\nWHERE "+getWhereClause();
    	ret_value+="\nGROUP BY "+getGroupClause(); 
    	ret_value+="\n"+getOrderClauseByMeasure(0);
    	if(getGroupClause().length()>0)	return ret_value ;
    	else return "SELECT '"+this.SelectClauseMeasure[0]+"'";
    }
    
    /*  order_type=0 -> ASCENING
     *  order_type=1 -> DESCENDING
     */
    public String getOrderClauseByMeasure(int order_type){
    	if(order_type==0) return " ORDER BY measure ASC";
    	return " ORDER BY measure DESC";
    }
    
    private String mergeStringTable(String[] tomerge){
    	String ret_value="";
    	for(int i=0;i<tomerge.length;i++){
    		ret_value+=" "+tomerge[i];
    	}
    	return ret_value;
    }
    
    @Override
    public boolean setResult(ResultSet result){
    	Res=new Result();
    	return Res.createResultArray(result);
    }

	@Override
	public Result getResult() {
		return this.Res;
	}

	@Override
	public boolean compareExtractionMethod(ExtractionMethod toCompare) {
		int count=0;
		SqlQuery tocomp=(SqlQuery)toCompare;
		for(int i=0;i<this.WhereClause.size();i++){
			if(tocomp.WhereClause.get(i)[0]==this.WhereClause.get(i)[0]){
				if(tocomp.WhereClause.get(i)[1]==this.WhereClause.get(i)[1]){
					if(tocomp.WhereClause.get(i)[2]==this.WhereClause.get(i)[2]){
						count++;
					}
				}
			}
		}
		return (count == this.WhereClause.size() ? true : false);
	}

	
	
	@Override
	public void produceExtractionMethod(CubeQuery cubeQuery) {
		this.SelectClauseMeasure[0]=cubeQuery.AggregateFunction;
		if(cubeQuery.Msr.get(0).Attr!=null ) this.SelectClauseMeasure[1]=cubeQuery.Msr.get(0).Attr.name;
		else this.SelectClauseMeasure[1]="";
		HashSet<String> FromTables=new HashSet<String>();
		
		/*Create WhereClausse */
		for(String[] sigmaExpr: cubeQuery.SigmaExpressions){
			for(int i=0;i<cubeQuery.referCube.Dim.size();i++){
				Dimension dimension=cubeQuery.referCube.Dim.get(i);
				String[] tmp=sigmaExpr[0].split("\\.");
				if(dimension.name.equals(tmp[0])){
					 /* FOR JOIN WITH Basic CUBE*/
					 String toaddJoin[]=new String[3];
					 toaddJoin[0]=cubeQuery.referCube.getDimensionRefField().get(i);
					 toaddJoin[1]="=";
					 toaddJoin[2]=dimension.getDimTbl().TblName+"."+((LinearHierarchy)dimension.getHier().get(0)).lvls.get(0).lvlAttributes.get(0).getAttribute().name;
					 this.WhereClause.add(toaddJoin);
					 
					 FromTables.add(dimension.getDimTbl().TblName);
					 
					 /* Add the Sigma Expression */
					 ArrayList<Hierarchy> current_hierachy=dimension.getHier();
					 String toaddSigma[]=new String[3];
					 toaddSigma[0]=dimension.getDimTbl().TblName+".";
					 for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
						List<Level> current_lvls=current_hierachy.get(k).lvls;
						for(int l=0;l<current_lvls.size();l++){
							if(current_lvls.get(l).name.equals(tmp[1])){
								toaddSigma[0]+=current_lvls.get(l).lvlAttributes.get(0).getAttribute().name;
							}
						}
					}
					toaddSigma[1]=sigmaExpr[1];
					toaddSigma[2]=sigmaExpr[2];
					this.WhereClause.add(toaddSigma);					 
				}
			}
		}
		
		/*Create From clause */
		String[] tbl_tmp=new String[1];
		tbl_tmp[0]="";
		if(cubeQuery.referCube!=null) tbl_tmp[0]=cubeQuery.referCube.FactTable().TblName;
		this.FromClause.add(tbl_tmp);
		
		for(int i=0;i<FromTables.size();i++){
			String[] toAdd=new String[1];
			toAdd[0]=(String) FromTables.toArray()[i];
			this.FromClause.add(toAdd);
		}
		
		
		/*Create groupClausse*/
		for(String[] gammaExpr: cubeQuery.GammaExpressions){
			if(gammaExpr[0].length()==0) {
				String[] toadd=new String[1];
				toadd[0]=gammaExpr[1];
				this.GroupByClause.add(toadd);
			}
			else{
				for(int i=0;i<cubeQuery.referCube.Dim.size();i++){
					Dimension dimension=cubeQuery.referCube.Dim.get(i);
					if(dimension.name.equals(gammaExpr[0])){
						String[] toadd=new String[1];
						toadd[0]=dimension.getDimTbl().TblName+".";
						ArrayList<Hierarchy> current_hierachy=dimension.getHier();
						for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
							List<Level> current_lvls=current_hierachy.get(k).lvls;
							for(int l=0;l<current_lvls.size();l++){
								if(current_lvls.get(l).name.equals(gammaExpr[1])){
									toadd[0]+=current_lvls.get(l).lvlAttributes.get(0).getAttribute().name;
								}
							}
						}
						
						this.GroupByClause.add(toadd);
					}
				}
			}
		}
	}
	
}
