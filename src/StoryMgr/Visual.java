/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import java.util.HashSet;

/**
 *
 * @author Asterix
 */
public abstract class  Visual {
    
	protected String[][] PivotTable;
	
	public Visual(){
		
	}
	
	abstract public void CreatePivotTable(HashSet<String> RowPivot,HashSet<String> ColPivot,String QueryResult[][]);
	abstract  public String[][] getPivotTable();
	abstract  public void setPivotTable(String[][] pivotTable);
	
}
