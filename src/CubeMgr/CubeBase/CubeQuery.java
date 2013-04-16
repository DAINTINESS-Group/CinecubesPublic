package CubeMgr.CubeBase;

import java.util.ArrayList;

import CubeMgr.StarSchema.SqlQuery;

public class CubeQuery extends Cube {
    
	public ArrayList<String[]> GammaExpressions; //0->table, 1->field of table
	public ArrayList<String[]> SigmaExpressions; //0->TABLE.field, 1-> operator , 2->VALUE
	public String AggregateFunction;
    public SqlQuery sqlQuery;    
    public BasicStoredCube referCube;
    
    public CubeQuery(String NAME) {
		super(NAME);
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
    
}
