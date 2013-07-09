package TaskMgr;

import java.sql.ResultSet;
import java.util.ArrayList;

import CubeMgr.StarSchema.Database;
import HighlightMgr.Highlight;

public class SubTask {
    
    private Highlight highlight;
    private ExtractionMethod extractionMethod;  
    private ArrayList<Integer> differencesFromOrigin;
    public long timeExecutionQuery;
    public long timeProduceOfCubeQuery;
    public long timeProduceOfExtractionMethod;
    public long timeCreationOfSbTsk;
    
    public SubTask(){
    	differencesFromOrigin=new ArrayList<Integer>();
    }
        
    public boolean execute(Database dB){
    	timeExecutionQuery=System.nanoTime();
    	ResultSet rset=dB.executeSql(extractionMethod.toString());
    	timeExecutionQuery=System.nanoTime()-timeExecutionQuery;
    	return extractionMethod.setResult(rset);
    };
    
    public void computeFinding(Database dB){
    	
    }

	public Highlight getHighlight() {
		return highlight;
	}

	
	public void setHighlight(Highlight Hghlght) {
		highlight = Hghlght;
	}

	public ExtractionMethod getExtractionMethod() {
		return extractionMethod;
	}

	public void setExtractionMethod(ExtractionMethod ExtractionMeth) {
		extractionMethod = ExtractionMeth;
	}
	
	public ArrayList<Integer> getDifferencesFromOrigin() {
		return differencesFromOrigin;
	}

	public int getDifferenceFromOrigin(int i) {
		return differencesFromOrigin.get(i);
	}

	public void setDifferencesFromOrigin(ArrayList<Integer> differencesfromorigin) {
		this.differencesFromOrigin = differencesfromorigin;
	}
	
	public void addDifferenceFromOrigin(int num){
		this.differencesFromOrigin.add(num);
	}
	
	
}
