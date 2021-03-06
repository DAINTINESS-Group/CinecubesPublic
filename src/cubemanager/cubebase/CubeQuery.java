package cubemanager.cubebase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import exctractionmethod.SqlQuery;

public class CubeQuery extends Cube {

	/**
	 * @uml.property  name="gammaExpressions"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="[Ljava.lang.String;"
	 */
	private ArrayList<String[]> GammaExpressions; // 0->dimension_name, 1->level
													// of dimension
	/**
	 * @uml.property  name="sigmaExpressions"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="[Ljava.lang.String;"
	 */
	private ArrayList<String[]> SigmaExpressions; // 0->dimension_name.level, 1->
													// operator , 2->VALUE
	/**
	 * @uml.property  name="aggregateFunction"
	 */
	private String AggregateFunction;
	/**
	 * @uml.property  name="sqlQuery"
	 * @uml.associationEnd  
	 */
	private SqlQuery sqlQuery;
	/**
	 * @uml.property  name="referCube"
	 * @uml.associationEnd  
	 */
	private BasicStoredCube referCube;

	public void setBasicStoredCube(BasicStoredCube referCube){
		this.referCube = referCube;
	}
		
	public void setAggregateFunction(String AggregateFunction){
		this.AggregateFunction = AggregateFunction;
	}
	
	public String getAggregateFunction() {
		return AggregateFunction;
	}
	
	public ArrayList<String[]> getGammaExpressions() {
		return GammaExpressions;
	}
	
	public ArrayList<String[]> getSigmaExpressions() {
		return SigmaExpressions;
	}
	
	public Level getParentLevel(String dimension, String lvlname) {
		return referCube.getParentLevel(dimension,lvlname);
	}
	
	public String getDimensionRefField(int index){
		return referCube.getDimensionRefField().get(index);
	}
 
	public CubeQuery(String Name) {
		super(Name);
		GammaExpressions = new ArrayList<String[]>();
		SigmaExpressions = new ArrayList<String[]>();
	}

	public void addGammaExpression(String table, String field) {
		String toadd[] = new String[2];
		toadd[0] = table;
		toadd[1] = field;
		GammaExpressions.add(toadd);
	}

	public void addSigmaExpression(String tablefield, String operator,
			String value) {
		String toadd[] = new String[3];
		toadd[0] = tablefield;
		toadd[1] = operator;
		toadd[2] = value;
		SigmaExpressions.add(toadd);
	}

	
	public String toString() {
		String ret_value = "Name:" + this.name + "\nAggregate Function : "
				+ AggregateFunction + "\n";
		if (this.Msr.size() > 0 && this.Msr.get(0) != null
				&& this.Msr.get(0).getAttribute() != null)
			ret_value += "Measure : " + this.Msr.get(0).getAttribute().getName() + "\n";
		ret_value += "Gamma Expression: ";
		for (int i = 0; i < GammaExpressions.size(); i++) {
			if (i > 0)
				ret_value += " , ";
			if (GammaExpressions.get(i)[0].length() > 0)
				ret_value += GammaExpressions.get(i)[0] + ".";
			ret_value += GammaExpressions.get(i)[1];
		}

		ret_value += "\nSigma Expression: ";
		for (int i = 0; i < SigmaExpressions.size(); i++) {
			if (i > 0)
				ret_value += " AND ";
			ret_value += SigmaExpressions.get(i)[0]
					+ SigmaExpressions.get(i)[1] + SigmaExpressions.get(i)[2];
		}
		return ret_value;
	}
	
	public String createQuery(int i, Level parentLvl) {
		String dimension = SigmaExpressions.get(i)[0].split("\\.")[0];
		String lvlname = SigmaExpressions.get(i)[0].split("\\.")[1];
		String table = referCube.getSqlTableByDimensionName(dimension);
		String field = referCube.getSqlFieldByDimensionLevelName(dimension, lvlname);
		String field2 = parentLvl.getAttributeName(0);
		String tmp_query="SELECT DISTINCT "+field2+ " FROM "+table+" WHERE "+field+"="+ SigmaExpressions.get(i)[2];
		return tmp_query;
	}
	
	public Level getNameParentLevel(int i){
		String dimension = SigmaExpressions.get(i)[0].split("\\.")[0];
		String level = SigmaExpressions.get(i)[0].split("\\.")[1];
		return referCube.getParentLevel(dimension, level);
	}

	 public int getIndexOfSigma( String gamma_dim) {
		 	int ret_value=-1;
			int i=0;
			for(String[] sigma : SigmaExpressions ){
				if(sigma[0].split("\\.")[0].equals(gamma_dim)) ret_value=i;
				i++;
			}
			return ret_value;
		}
	
	public void addMeasure(int id, String name){
		Measure msrToAdd = new Measure(id, name, null);
		Msr.add( msrToAdd);
	}
		  
    public boolean checkIfSigmaExprIsInGamma(int toChange) {
			boolean ret_value=false;
			String [] tmp = SigmaExpressions.get(toChange)[0].split("\\.");
			for(String [] gammaExpr : GammaExpressions){
				if(gammaExpr[0].equals(tmp[0]))
					ret_value=true; 
			}
			return ret_value;
	}

    public int getGammaPositionOfSigma(int toChange) {
		int ret_value=0;
		String [] tmp = SigmaExpressions.get(toChange)[0].split("\\.");
		for(int i=  0; i < GammaExpressions.size(); i++) {
			String[] gammaExpr= GammaExpressions.get(i);
			if(gammaExpr[0].equals(tmp[0])) {
				ret_value=i;
				break;
			}
		}
		return ret_value;
    }
    
    private void copyGammaExpressions(CubeQuery oldQuery){
		for(int i = 0; i < oldQuery.GammaExpressions.size(); i++){
			String[] old = oldQuery.GammaExpressions.get(i);
			String[] toadd = new String[old.length];
			for(int j = 0;j < old.length; j++){
				toadd[j] = old[j];
			}
			GammaExpressions.add(toadd);
		}
	}
    
    
    private void copySigmaExpressions(CubeQuery oldQuery){
		for(int i = 0; i < oldQuery.SigmaExpressions.size(); i++){
			String[] old = oldQuery.SigmaExpressions.get(i);
			String[] toadd = new String[old.length];
			for(int j = 0;j < old.length; j++){
				toadd[j] = old[j];
			}
			SigmaExpressions.add(toadd);
		}
	}
    
    public CubeQuery(CubeQuery oldQuery){
    	super("");
    	GammaExpressions = new ArrayList<String[]>();
		SigmaExpressions = new ArrayList<String[]>();
    	 copyGammaExpressions(oldQuery);
    	 copySigmaExpressions(oldQuery);
    	this.AggregateFunction = (oldQuery.AggregateFunction);
		this.referCube = oldQuery.referCube;
		this.Msr = oldQuery.Msr;
    }
    
    public boolean doDrillInColVersion(CubeBase cubeBase, String[] valuesToChange,
			HashSet<String> row_per_col, CubeQuery newQuery) {/*  valuesToChange[0]->Row
										   * Value,valuesToChange[1]->Column Value */
		
		String[] gamma_tmp = GammaExpressions.get(0); // Row Dimension

		int index_sigma_change_bygamma = getIndexOfSigmaToDelete(gamma_tmp[0]);// getRow Sigma							
		if (index_sigma_change_bygamma > -1) {
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[0] = newQuery.GammaExpressions
					.get(0)[0] + "." + newQuery.GammaExpressions.get(0)[1];
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[2] = "'"
					+ valuesToChange[1] + "'";
		}
		String child_level_of_gamma = cubeBase.getChildOfGamma(gamma_tmp);
		if (child_level_of_gamma != null) {
			newQuery.GammaExpressions.get(0)[1] = child_level_of_gamma;
			newQuery.GammaExpressions.set(0, newQuery.GammaExpressions
					.set(1,	newQuery.GammaExpressions.get(0)));
		} else 
			return false;
		return true;
	}
    
    public boolean doDrillInRowVersion(CubeBase cubeBase, String[] valuesToChange,
			HashSet<String> cols, CubeQuery newQuery) { /* valuesToChange[0]->Row
									 * Value,valuesToChange[1]->Column Value */
		
		String[] gamma_tmp = GammaExpressions.get(1); //column dimension
		int index_sigma_change_bygamma = getIndexOfSigmaToDelete(gamma_tmp[0]);
		String child_level_of_gamma = cubeBase.getChildOfGamma( gamma_tmp);
		
			if (index_sigma_change_bygamma > -1) {
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[0] = newQuery.GammaExpressions
					.get(1)[0] + "." + newQuery.GammaExpressions.get(1)[1];
			newQuery.SigmaExpressions.get(index_sigma_change_bygamma)[2] = "'"
					+ valuesToChange[1] + "'";
		}

		if (child_level_of_gamma != null) {
			newQuery.GammaExpressions.get(1)[1] = child_level_of_gamma;
			
		} else
			return false;
		return true;
		}
    
	public int getIndexOfSigmaToDelete(String gamma_dim) {
		int ret_value = -1;
		int i = 0;
		for (String[] sigma : SigmaExpressions) {
			if (sigma[0].split("\\.")[0].equals(gamma_dim))
				ret_value = i;
			i++;
		}
		return ret_value;
	}
    
	public String getSigmaTextForIntro(){
		String retTxt= "";
		int i=0;
    	for(String [] sigma: SigmaExpressions) {
    		if(i == SigmaExpressions.size() - 1)
    			retTxt += " and ";
    		else if(i>0)
    			retTxt+=", ";
    		retTxt += sigma[0].split("\\.")[0].replace("_dim","").replace("lvl", "level ")+" is fixed to "+sigma[2].replace("*", "ALL");
    		i++;
    	}
    	return retTxt;
	}
	
	public String getSigmaTextForOriginalAct1(){
		String dimensionText= "";
		int j = 0;
    	for(String[] sigma: SigmaExpressions){
    		if(j > 0)
    			dimensionText += ", ";
    		if(j == SigmaExpressions.size() - 1) 
    			dimensionText+=" and ";
    		dimensionText += sigma[0].split("\\.")[0].replace("_dim", "")+" to be equal to "+sigma[2].replace("*", "ALL")+"";
    		j++;
    	}
    	return dimensionText;
	}
	
	public String getGammaTextForOriginalAct1(){
		String dimensionText= "";
		int j = 0;
    	for(String[] gamma: GammaExpressions){
    		if(j > 0)
    			dimensionText += ", ";
    		if(j==GammaExpressions.size()-1)
    			dimensionText += " and ";
    		dimensionText+=gamma[0].replace("_dim", "")+" at "+gamma[1].replace("lvl", "level ");
    		j++;
    	}
    	return dimensionText;
	}
	
	 public SqlQuery produceExtractionMethod()  {
		 SqlQuery newSqlQuery = new SqlQuery();
			if(Msr.get(0).getAttribute() !=null ) 
				newSqlQuery.addReturnedFields(AggregateFunction,Msr.get(0).getAttribute().getName());
			else
				newSqlQuery.addReturnedFields(AggregateFunction,"");
			HashSet<String> FromTables=new HashSet<String>();
			
			/*Create WhereClausse */
			for(String[] sigmaExpr: SigmaExpressions){
				for(int i = 0;i < referCube.getListDimension().size(); i++) {
					Dimension dimension = referCube.getListDimension().get(i);
					String[] tmp=sigmaExpr[0].split("\\.");
					if(dimension.hasSameName(tmp[0])){
						 /* FOR JOIN WITH Basic CUBE*/
						 String toaddJoin[]=new String[3];
						 toaddJoin[0] = referCube.getDimensionRefField().get(i);
						 toaddJoin[1] = "=";
						 toaddJoin[2] = dimension.getTableName()+"."+((LinearHierarchy)dimension.getHier().get(0)).lvls.get(0).getAttributeName(0);
						 newSqlQuery.addFilter(toaddJoin);
						 
						 FromTables.add(dimension.getTableName());
						 
						 /* Add the Sigma Expression */
						 ArrayList<Hierarchy> current_hierachy=dimension.getHier();
						 String toaddSigma[]=new String[3];
						 toaddSigma[0]=dimension.getTableName()+".";
						 for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
							List<Level> current_lvls=current_hierachy.get(k).lvls;
							for(int l=0;l<current_lvls.size();l++){
								if(current_lvls.get(l).getName().equals(tmp[1])){
									toaddSigma[0]+=current_lvls.get(l).getAttributeName(0);
								}
							}
						}
						toaddSigma[1]=sigmaExpr[1];
						toaddSigma[2]=sigmaExpr[2];
						newSqlQuery.addFilter(toaddSigma);					 
					}
				}
			}
			
			/*Create From clause */
			String[] tbl_tmp = new String[1];
			tbl_tmp[0] = "";
			if(referCube != null) 
				tbl_tmp[0] = referCube.FactTable().getTableName();
			newSqlQuery.addSourceCube(tbl_tmp);
			
			for(int i=0;i<FromTables.size();i++){
				String[] toAdd=new String[1];
				toAdd[0]=(String) FromTables.toArray()[i];
				newSqlQuery.addSourceCube(toAdd);
			}
			
			
			/*Create groupClausse*/
			for(String[] gammaExpr: GammaExpressions){
				if(gammaExpr[0].length()==0) {
					String[] toadd=new String[1];
					toadd[0]=gammaExpr[1];
					newSqlQuery.addGroupers(toadd);
				}
				else{
					for(int i=0;i<referCube.getListDimension().size();i++){
						Dimension dimension= referCube.getListDimension().get(i);
						if(dimension.hasSameName(gammaExpr[0])){
							String[] toadd=new String[1];
							toadd[0]=dimension.getTableName()+".";
							ArrayList<Hierarchy> current_hierachy=dimension.getHier();
							for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
								List<Level> current_lvls=current_hierachy.get(k).lvls;
								for(int l=0;l<current_lvls.size();l++){
									if(current_lvls.get(l).getName().equals(gammaExpr[1])){
										/* FOR JOIN WITH Basic CUBE*/
										 String toaddJoin[]=new String[3];
										 toaddJoin[0]=referCube.getDimensionRefField().get(i);
										 toaddJoin[1]="=";
										 toaddJoin[2]=dimension.getTableName()+"."+((LinearHierarchy)dimension.getHier().get(0)).lvls.get(0).getAttributeName(0);
										 newSqlQuery.addFilter(toaddJoin);
										 String[] toAddfrom=new String[1];
										 toAddfrom[0]=dimension.getTableName();
										 if(FromTables.contains(dimension.getTableName())==false) 
											 newSqlQuery.addSourceCube(toAddfrom);
										
										toadd[0]+=current_lvls.get(l).getAttributeName(0);
									}
								}
							}
							
							newSqlQuery.addGroupers(toadd);
						}
					}
				}
			}
			sqlQuery= newSqlQuery;
			return newSqlQuery;
		}
	 
	 public int getSizeRowPivot() {
		 return sqlQuery.getRowPivot().size();
	 }
	 
	 public String[][] getResultArray() {
		 return sqlQuery.getResultArray();
	 }
	 
	 
	 public String getValueFromRowPivot(int i){
		 return sqlQuery.getRowPivot().toArray()[i].toString();
	 }
	 
	 public int getSizeColPivot() {
		 return sqlQuery.getColPivot().size();
	 }
		 
	 public String getValueFromColPivot(int i){
		 return sqlQuery.getColPivot().toArray()[i].toString();
	 }
	 
}
