package TaskMgr;

import java.sql.ResultSet;
import java.util.ArrayList;

import CubeMgr.StarSchema.Database;

public class SubTask {
    private KeyFinding keyfinding;
    private ExtractionMethod extractionMethod;  
    private ArrayList<Integer> differencesFromOrigin;
    public long timeExecutionQuery;
    public long timeProduceOfCubeQuery;
    public long timeProduceOfExtractionMethod;
    public long timeCreationOfSbTsk;
    
    public SubTask(){
    	differencesFromOrigin=new ArrayList<Integer>();
    	setKeyFinding(new KeyFinding());
    }
        
    public boolean execute(Database dB){
    	timeExecutionQuery=System.nanoTime();
    	ResultSet rset=dB.executeSql(extractionMethod.toString());
    	timeExecutionQuery=System.nanoTime()-timeExecutionQuery;
    	return extractionMethod.setResult(rset);
    };
    
    public void computeFinding(Database dB){
    	
    }

	public KeyFinding getKeyFinding() {
		return keyfinding;
	}

	public void setKeyFinding(KeyFinding keyFinding) {
		keyfinding = keyFinding;
	}

	public ExtractionMethod getExtractionMethod() {
		return extractionMethod;
	}

	public void setExtractionMethod(ExtractionMethod ExtractionMeth) {
		extractionMethod = ExtractionMeth;
	}
	
	/**
	 * @return the differencesCondition
	 */
	public ArrayList<Integer> getDifferencesFromOrigin() {
		return differencesFromOrigin;
	}

	/**
	 * @return the i differencesFromOrigin
	 */
	public int getDifferenceFromOrigin(int i) {
		return differencesFromOrigin.get(i);
	}

	/**
	 * @param differencesfromorigin the differencesFromOrigin to set
	 */
	public void setDifferencesFromOrigin(ArrayList<Integer> differencesfromorigin) {
		this.differencesFromOrigin = differencesfromorigin;
	}
	
	public void addDifferenceFromOrigin(int num){
		this.differencesFromOrigin.add(num);
	}
	
	
}
