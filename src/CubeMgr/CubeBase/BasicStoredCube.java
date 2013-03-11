/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

import CubeMgr.StarSchema.FactTable;

/**
 * @author Asterix
 */

public class BasicStoredCube extends Cube {
    
	private FactTable FCtbl;
        
	public BasicStoredCube(String NAME) {
			super(NAME);
			// TODO Auto-generated constructor stub
	}
	 
	public void setFactTable(FactTable Factbl){
		FCtbl=Factbl;
	}

	public FactTable FactTable() {
		return FCtbl;
	}

	public void addDimension(Dimension dimension) {
		this.Dim.add(dimension);
	}

	public void addDimensionRefField(String namefield) {
		this.DimemsionRefField.add(namefield);
	}

}
