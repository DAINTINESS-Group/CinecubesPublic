/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.util.ArrayList;

import CubeMgr.StarSchema.Database;

/**
 *
 * @author Asterix
 */
public class SubTask {
    private KeyFinding keyfinding;
    private ExtractionMethod extractionMethod;    
    
    public SubTask(){
    	setKeyFinding(new KeyFinding());
    }
        
    public void execute(Database dB){
    	extractionMethod.setResult(dB.executeSql(extractionMethod.returnQuery()));
    };
    
    public ArrayList<ArrayList<String>> computeFinding(Database dB){
    	return extractionMethod.findBrothers(dB);
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
	};
}
