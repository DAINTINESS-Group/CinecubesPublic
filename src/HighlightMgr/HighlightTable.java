package HighlightMgr;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HighlightTable extends Highlight {
	
	public ArrayList<Float> maxValues;
	public ArrayList<Float> minValues;
	public ArrayList<Float> middleValues;
	public ArrayList<Integer> index_checked;
	public Color maxcolor;
	public Color mincolor;
	public Color middlecolor;
	
	public HighlightTable(){
		super();
		maxValues=new ArrayList<Float>();
    	minValues=new ArrayList<Float>();
    	middleValues=new ArrayList<Float>();
    	index_checked=new ArrayList<Integer>();
    	maxcolor=new Color(72,61,139);
    	mincolor=new Color(255,127,36);
    	middlecolor=new Color(245,245,220);
    	
	}
	
	public void createMinHightlight(String[][] table){
		int num_of_msrs_in_table=table.length-2;    	
    	int minLenght=(int) Math.floor(num_of_msrs_in_table*0.25);
    	DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
    	int i=2;
    	while(minLenght>=minValues.size() && i<num_of_msrs_in_table){
    		Float toadd=Float.parseFloat(df.format(Float.parseFloat(table[i][2])));
    		minValues.add(toadd);
    		index_checked.add(i);
    		i++;
    	}
	}
	
	public void createMaxHightlight(String[][] table){
		int num_of_msrs_in_table=table.length-2;
    	int maxLenght=(int) Math.floor(num_of_msrs_in_table*0.25);
    	DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);  	
    	int i=table.length-1;
    	while(maxLenght>=maxValues.size() && i>1){
    		Float toadd=Float.parseFloat(df.format(Float.parseFloat(table[i][2])));
    		maxValues.add(toadd);
    		index_checked.add(i);
    		i--;
    	}
	}
	
	public void createMiddleHightlight(String[][] table){
		DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
    	for(int i=2;i<table.length;i++){
    		if(!index_checked.contains(i)){
    			Float toadd=Float.parseFloat(df.format(Float.parseFloat(table[i][2])));
    			middleValues.add(toadd);
    		}
    	}
	}
	
	 boolean tryParseFloat(String value){
	    	try{
	    		Float.parseFloat(value);
	    	}
	    	catch(Exception ex){
	    		return false;
	    	}
	    	return true;
	  }
	
}
