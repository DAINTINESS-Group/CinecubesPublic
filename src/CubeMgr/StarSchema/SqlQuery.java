package CubeMgr.StarSchema;

import java.sql.ResultSet;
import java.util.ArrayList;

import CubeMgr.CubeBase.CubeQuery;
import CubeMgr.CubeBase.Dimension;
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
    	ret_value+= SelectClauseMeasure[0]+"("+SelectClauseMeasure[1]+") ";
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
    
    public String getGroupClause(){
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
    	return ret_value ;
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
		this.SelectClauseMeasure[1]=cubeQuery.Msr.get(0).Attr.name;
		
		/*Create Fromclause*/
		String[] tbl_tmp=new String[1];
		tbl_tmp[0]=cubeQuery.referCube.FactTable().TblName;
		this.FromClause.add(tbl_tmp);
		for(int i=0;i<cubeQuery.GammaExpressions.size();i++){
			String[] toAdd=new String[1];
			toAdd[0]=cubeQuery.GammaExpressions.get(i)[0];
			this.FromClause.add(toAdd);
		}
		
		/*Create WhereClausse */
		for(String[] sigmaExpr: cubeQuery.SigmaExpressions){
			for(int i=0;i<cubeQuery.referCube.Dim.size();i++){
				Dimension dimension=cubeQuery.referCube.Dim.get(i);
				String[] tmp=sigmaExpr[0].split("\\.");
				if(dimension.getDimTbl().TblName.equals(tmp[0])){
					 String toadd[]=new String[3];
					 toadd[0]=cubeQuery.referCube.getDimensionRefField().get(i);
					 toadd[1]="=";
					 toadd[2]=tmp[0]+"."+((LinearHierarchy)dimension.getHier().get(0)).lvls.get(0).lvlAttributes.get(0).getAttribute().name;
					 
					 this.WhereClause.add(toadd);
					 this.WhereClause.add(sigmaExpr);					 
				}
			}
		}
		
		/*Create groupClausse*/
		for(String[] sigmaExpr: cubeQuery.GammaExpressions){
			String[] toadd=new String[1];
			toadd[0]=sigmaExpr[0]+"."+sigmaExpr[1];
			this.GroupByClause.add(toadd);
		}
	}
	
}
