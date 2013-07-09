package TaskMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class Result {
    
	/**
	 * @uml.property  name="resultArray"
	 */
	private String [][] resultArray; /* 1st row has Column Name, 
										2nd row has Column Labels, 
										the following rows are data. 
										MIN: resultArray[2][columns-1]
										MAX: resultArray[resultArray.length-1][columns-1]
										*/ 	
    /**
	 * @uml.property  name="colPivot"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private TreeSet<String> ColPivot; /* In all option Colpivot has the values of 1st Column of resultArray exept when merge two tables like max,min comparison*/
    /**
	 * @uml.property  name="rowPivot"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private TreeSet<String> RowPivot; /* In all option RowPivot has the values of 2nd Column of resultArray exept when merge two tables like max,min comparison*/
    /**
	 * @uml.property  name="titleOfColumns"
	 */
    public String TitleOfColumns;
    /**
	 * @uml.property  name="titleOfRows"
	 */
    public String TitleOfRows;
    /**
	 * @uml.property  name="max"
	 */
    public Float max;
	/**
	 * @uml.property  name="min"
	 */
	public Float min;
    
	public Result(){
		setRowPivot(new TreeSet<String>());
		setColPivot(new TreeSet<String>());
		
		resultArray=null;
	}
	
	public Result(int rows,int columns){
		resultArray=new String[rows][columns];
	}
	
	/**
	 * @return
	 * @uml.property  name="resultArray"
	 */
	public String [][] getResultArray() {
		return resultArray;
	}

	/**
	 * @param resultarray
	 * @uml.property  name="resultArray"
	 */
	public void setResultArray(String [][] resultarray) {
		this.resultArray = new String [resultarray.length][resultarray[0].length]; 
		for(int i=0;i<resultarray.length;i++){
			for(int j=0;j<resultarray[i].length;j++) setCellOfResultArray(resultarray[i][j],i,j); 
		}
	}
	
	public void setCellOfResultArray(String setValue,int row,int column){
		resultArray[row][column]=new String(setValue);
	}
	
	public String getCellOfResultArray(int row,int column){
		return resultArray[row][column];
	}
	
	public boolean createResultArray(ResultSet resultSet){			
			int columns;
	        int rows;
	        boolean ret_value=false;
	        /*float temp;*/
	        
			try {
				resultSet.last();
				rows=resultSet.getRow();
				if(rows>0){
					
					ret_value=true;
					columns=resultSet.getMetaData().getColumnCount();
					if(columns==1) {
						resultSet.beforeFirst();
						while(resultSet.next()) TitleOfColumns=resultSet.getString(1);
						return ret_value;
					}
					/*temp=resultSet.getFloat(columns);*/
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
			           }
			           this.ColPivot.add(resultSet.getString(1));
	                   this.RowPivot.add(resultSet.getString(2));
			        }
			       
			        //setMinValue(Float.parseFloat(resultArray[2][2]));
			        //setMaxValue(Float.parseFloat(resultArray[resultArray.length-1][2]));
			        
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
