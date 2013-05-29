package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeSet;

public class Result {
    
	private String [][] resultArray; /* 1st row has Column Name, 
										2nd row has Column Labels, 
										the next rows are data. 
										MIN: resultArray[2][columns-1]
										MAX: resultArray[resultArray.length-1][columns-1]
										*/ 	
    private TreeSet<String> ColPivot;
    private TreeSet<String> RowPivot;
    public String TitleOfColumns;
    public String TitleOfRows;
    public Float max,min;
    
	public Result(){
		setRowPivot(new TreeSet<String>());
		setColPivot(new TreeSet<String>());
		
		resultArray=null;
	}
	
	public Result(int rows,int columns){
		resultArray=new String[rows][columns];
	}
	
	public String [][] getResultArray() {
		return resultArray;
	}

	public void setResultArray(String [][] resultArray) {
		this.resultArray = resultArray;
	}
	
	public void setCellOfResultArray(String setValue,int row,int column){
		resultArray[row][column]=setValue;
	}
	
	public String getCellOfResultArray(int row,int column){
		return resultArray[row][column];
	}
	
	public boolean createResultArray(ResultSet resultSet){			
			int columns;
	        int rows;
	        float temp;
	        boolean ret_value=false;
			try {
				resultSet.last();
				rows=resultSet.getRow();
				if(rows>0){
					ret_value=true;
					columns=resultSet.getMetaData().getColumnCount();
					
					temp=resultSet.getFloat(columns);
			        //back to first line
			        resultSet.first();
			        resultSet.beforeFirst();
			        resultArray=new String[rows+2][columns];
			        for(int i=1;i<=columns;i++) {
			        	resultArray[0][i-1]=resultSet.getMetaData().getColumnName(i);
			        	resultArray[1][i-1]=resultSet.getMetaData().getColumnLabel(i);
			        }
			        
			        TitleOfColumns=resultSet.getMetaData().getColumnName(1);
					TitleOfRows=resultSet.getMetaData().getColumnName(2);
					//System.out.println("74:"+TitleOfColumns+" "+TitleOfRows);
			        while(resultSet.next()){
			           for(int i=0;i<columns;i++){
			        	   resultArray[resultSet.getRow()+1][i]=resultSet.getString(i+1);
			        	   this.ColPivot.add(resultSet.getString(1));
		                   this.RowPivot.add(resultSet.getString(2));
			           }
			        }
			       
			        setMinValue(Float.parseFloat(resultArray[2][columns-1]));
			        setMaxValue(Float.parseFloat(resultArray[resultArray.length-1][columns-1]));
			        
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret_value;
		}

	public void printStringArray(String[][] result){
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++) System.out.print(result[i][j]+"|");
            System.out.println();
        }
    }

	/**
	 * @return the maxValue
	 */
	public float getMaxValue() {
		return max;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(float val) {
		this.max = val;
	}

	/**
	 * @return the maxValue
	 */
	public float getMinValue() {
		return min;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMinValue(float val) {
		this.min = val;
	}
	
	/**
	 * @return the colPivot
	 */
	public TreeSet<String> getColPivot() {
		return ColPivot;
	}

	/**
	 * @param colPivot the colPivot to set
	 */
	public void setColPivot(TreeSet<String> colPivot) {
		ColPivot = colPivot;
	}

	/**
	 * @return the rowPivot
	 */
	public TreeSet<String> getRowPivot() {
		return RowPivot;
	}

	/**
	 * @param rowPivot the rowPivot to set
	 */
	public void setRowPivot(TreeSet<String> rowPivot) {
		RowPivot = rowPivot;
	}	
	
	void copyStringHashet(TreeSet<String> from,TreeSet<String> to){
		for(String row : from){
    		String tmp=new String(row);
    		to.add(tmp);
    	}
	}
}
