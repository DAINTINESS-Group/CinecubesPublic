package CubeMgr.CubeBase;

import java.util.ArrayList;
import CubeMgr.StarSchema.SqlQuery;

public class CubeQuery extends Cube {
    
	public ArrayList<String[]> GammaExpressions; //0->dimension_name, 1->level of dimension
	public ArrayList<String[]> SigmaExpressions; //0->dimension_name.level, 1-> operator , 2->VALUE
	public String AggregateFunction;
    public SqlQuery sqlQuery;    
    public BasicStoredCube referCube;
    
    public CubeQuery(String Name) {
		super(Name);
		GammaExpressions=new ArrayList<String[]>();
		SigmaExpressions=new ArrayList<String[]>();
	}
       
    public void addGammaExpression(String table,String field){
    	String toadd[]=new String[2];
    	toadd[0]=table;
    	toadd[1]=field;
    	GammaExpressions.add(toadd);
    }
    
    public void addSigmaExpression(String tablefield,String operator,String value){
    	String toadd[]=new String[3];
    	toadd[0]=tablefield;
    	toadd[1]=operator;
    	toadd[2]=value;
    	SigmaExpressions.add(toadd);
    }
    
    public String toString(){
    	String ret_value="Name:"+this.name+"\nAggregate Function : "+AggregateFunction+"\n";
    	ret_value+="Measure : "+this.Msr.get(0).Attr.name+"\n";
    	ret_value+="Gamma Expression: ";
    	for(int i=0;i<GammaExpressions.size();i++){
    		if(i>0) ret_value+=" , ";
    		if(GammaExpressions.get(i)[0].length() > 0) ret_value+=GammaExpressions.get(i)[0]+".";
    		ret_value+=GammaExpressions.get(i)[1];
    	}
    	
    	ret_value+="\nSigma Expression: ";
    	for(int i=0;i<SigmaExpressions.size();i++){
    		if(i>0) ret_value+=" AND ";
    		ret_value+=SigmaExpressions.get(i)[0]+SigmaExpressions.get(i)[1]+SigmaExpressions.get(i)[2];
    	}
		return ret_value;
    }
    
}
