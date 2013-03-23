/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 *
 * @author Asterix
 */
public class Result {
    
	private String [][] resultArray;
	private int maxValueIndex;
    private HashSet<String> ColPivot;
    private HashSet<String> RowPivot;
    
	public Result(){
		setRowPivot(new HashSet<String>());
		setColPivot(new HashSet<String>());
		setMaxValueIndex(0);
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
	
	public void createResultArray(ResultSet resultSet){			
			int columns;
	        int rows;
	        float temp;
	        
			try {
				resultSet.last();
				rows=resultSet.getRow();
				columns=resultSet.getMetaData().getColumnCount();
				temp=resultSet.getFloat(columns);
				setMaxValueIndex(resultSet.getRow());
		        //back to first line
		        resultSet.first();
		        resultSet.beforeFirst();
		        resultArray=new String[rows+2][columns];
		        for(int i=1;i<=columns;i++) {
		        	resultArray[0][i-1]=resultSet.getMetaData().getColumnName(i);
		        	resultArray[1][i-1]=resultSet.getMetaData().getColumnLabel(i);
		        }
		        while(resultSet.next()){
		           for(int i=0;i<columns;i++){
		        	   resultArray[resultSet.getRow()+1][i]=resultSet.getString(i+1);
		        	   this.ColPivot.add(resultSet.getString(2));
	                   this.RowPivot.add(resultSet.getString(1));
		           }
		           
		           if(temp<resultSet.getFloat(columns)){
		               temp=resultSet.getFloat(columns);
		               setMaxValueIndex(resultSet.getRow());
		           }
		        }
		       // printStringArray(resultArray);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public int getMaxValueIndex() {
		return maxValueIndex;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValueIndex(int index) {
		this.maxValueIndex = index;
	}

	/**
	 * @return the colPivot
	 */
	public HashSet<String> getColPivot() {
		return ColPivot;
	}

	/**
	 * @param colPivot the colPivot to set
	 */
	public void setColPivot(HashSet<String> colPivot) {
		ColPivot = colPivot;
	}

	/**
	 * @return the rowPivot
	 */
	public HashSet<String> getRowPivot() {
		return RowPivot;
	}

	/**
	 * @param rowPivot the rowPivot to set
	 */
	public void setRowPivot(HashSet<String> rowPivot) {
		RowPivot = rowPivot;
	}	
}
