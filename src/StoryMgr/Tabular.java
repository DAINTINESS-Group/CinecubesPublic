package StoryMgr;

import java.util.TreeSet;

public class Tabular extends Visual {
	
	public Tabular() {
		super();
		PivotTable=null;
	}

	 /**
     * This function works fine when we have in the result two columns only.
     * If we have more than two columns, then the bellow function ignore the columns.
     * We using HashSet<String> because in hash set you have unique values.
     * 
     * Parameters
     *      Rowpivot have the name of each row for PivotTable  
     *      Colpivot have the name of each column for PivotTable
     *      QueryResult have the result of the query
     */
    public void CreatePivotTable(TreeSet<String> RowPivot,TreeSet<String> ColPivot,String QueryResult[][]){
        this.PivotTable=new String[RowPivot.size()+1][ColPivot.size()+1];
        this.PivotTable[0][0]="";
        int i=0;
        int j=0;
        for(String x : RowPivot) {
            this.PivotTable[i+1][0]=x;
            i++;
        }
        
        for(String y:ColPivot){
            this.PivotTable[0][j+1]=y;
            j++;
        }
        i=1;
        for(String x:RowPivot){
            j=1;
            for(String y:ColPivot){
                for (int r=0;r<QueryResult.length;r++){
                        if((QueryResult[r][0].equals(x) && QueryResult[r][1].equals(y)) || (QueryResult[r][0].equals(y) && QueryResult[r][1].equals(x))){
                            this.PivotTable[i][j]=QueryResult[r][2];
                        }
                        if(this.PivotTable[i][j]==null) {
                        	this.PivotTable[i][j]="-";
                        }
                }
                j++;
            }
            i++;
        }
        
    }

	@Override
	public String[][] getPivotTable() {
		return this.PivotTable;
	}

	@Override
	public void setPivotTable(String[][] pivotTable) {
		this.PivotTable=pivotTable;
	}
}
