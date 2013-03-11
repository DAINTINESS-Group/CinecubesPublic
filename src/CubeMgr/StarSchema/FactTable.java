/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.StarSchema;

import java.util.List;

/**
 *
 * @author Asterix
 */
public class FactTable extends Table {
    
	private List<DimensionTable> DimTable;
	//public SqlField fld;
    
    public FactTable(String name) {
		super(name);
	}
    
	public List<DimensionTable> getDimTable() {
		return DimTable;
	}
	
	public void setDimTable(List<DimensionTable> dimTable) {
		DimTable = dimTable;
	}

}
