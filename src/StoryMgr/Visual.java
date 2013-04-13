package StoryMgr;

import java.util.HashSet;

public abstract class  Visual {
    
	protected String[][] PivotTable;
	
	public Visual(){
		
	}
	
	abstract public void CreatePivotTable(HashSet<String> RowPivot,HashSet<String> ColPivot,String QueryResult[][]);
	abstract  public String[][] getPivotTable();
	abstract  public void setPivotTable(String[][] pivotTable);
	
}
