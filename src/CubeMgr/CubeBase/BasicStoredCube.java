package CubeMgr.CubeBase;

import CubeMgr.StarSchema.FactTable;

public class BasicStoredCube extends Cube {
    
	private FactTable FCtbl;
        
	public BasicStoredCube(String NAME) {
			super(NAME);
	}
	 
	public void setFactTable(FactTable Factbl){
		FCtbl=Factbl;
	}

	public FactTable FactTable() {
		return FCtbl;
	}

}
