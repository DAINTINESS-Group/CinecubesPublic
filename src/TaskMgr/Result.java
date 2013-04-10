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
    public String TitleOfColumns;
    public String TitleOfRows; 
    
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
		        
		        TitleOfColumns=resultSet.getMetaData().getColumnName(1);
				TitleOfRows=resultSet.getMetaData().getColumnName(2);
				System.out.println("74:"+TitleOfColumns+" "+TitleOfRows);
		        while(resultSet.next()){
		           for(int i=0;i<columns;i++){
		        	   resultArray[resultSet.getRow()+1][i]=resultSet.getString(i+1);
		        	   this.ColPivot.add(resultSet.getString(1));
	                   this.RowPivot.add(resultSet.getString(2));
		           }
		           		           
		           if(temp<resultSet.getFloat(columns)){
		               temp=resultSet.getFloat(columns);
		               setMaxValueIndex(resultSet.getRow());
		           }
		        }
		       
		       if(ColPivot.size()<RowPivot.size()){
		        	HashSet<String> copySet=new HashSet<String>();
		        	copyStringHashet(RowPivot,copySet);
		        	RowPivot.clear();
		        	copyStringHashet(ColPivot,RowPivot);
		        	ColPivot.clear();
		        	copyStringHashet(copySet,ColPivot);
		        	
		        	TitleOfColumns=new String(resultSet.getMetaData().getColumnName(2));
					TitleOfRows=new String(resultSet.getMetaData().getColumnName(1));
					System.out.println("98:"+TitleOfColumns+" "+TitleOfRows);
					
		        }
		       System.out.println("======");
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
	
	void copyStringHashet(HashSet<String> from,HashSet<String> to){
		for(String row : from){
    		String tmp=new String(row);
    		to.add(tmp);
    	}
	}
}
