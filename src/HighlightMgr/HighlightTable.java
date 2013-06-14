package HighlightMgr;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class HighlightTable extends Highlight {
	
	public ArrayList<String> maxValues;
	public ArrayList<String> minValues;
	public ArrayList<String> middleValues;
	public ArrayList<Integer> index_checked;
	public ArrayList<Integer> max_index;
	public ArrayList<Integer> min_index;
	public Color maxcolor;
	public String maxcolor_name;
	public Color mincolor;
	public String mincolor_name;
	public Color middlecolor;
	public String middlecolor_name;
	
	public HighlightTable(){
		super();
		maxValues=new ArrayList<String>();
    	minValues=new ArrayList<String>();
    	middleValues=new ArrayList<String>();
    	index_checked=new ArrayList<Integer>();
    	max_index=new ArrayList<Integer>();
    	min_index=new ArrayList<Integer>();
    	maxcolor=Color.RED;
    	maxcolor_name="red";
    	mincolor=new Color(172,4,148);//chocolate1 purple
    	mincolor_name="purple";
    	middlecolor=Color.blue;
    	middlecolor_name="blue";
    	
	}
	
	public void createMinHightlight(String[][] table){
		int num_of_msrs_in_table=table.length-2;    	
    	int minLenght=(int) Math.floor(num_of_msrs_in_table*0.25);
    	
    	String[] tmp_minValues=new String[minLenght];
    	int[] tmp_indexValues=new int[minLenght];
    	DecimalFormat df = new DecimalFormat("#.##");
    	df.setMinimumFractionDigits(2);
    	
    	for(int j=0;j<minLenght;j++) tmp_indexValues[j]=0;
    	
    	for(int j=2;j<num_of_msrs_in_table+2;j++){
    		for(int k=0;k<minLenght;k++){
    			if(returnConditionForMaxMin(tmp_indexValues[k],tmp_minValues[k],table[j][2],0) && isTableMaxValue(tmp_minValues,tmp_minValues[k])){
    				tmp_minValues[k]=df.format(Float.parseFloat(table[j][2]));
    				tmp_indexValues[k]=j;
    				break;
    			}
    		}
    	 }
    	
    	for(int j=0;j<minLenght;j++) {
    		minValues.add(tmp_minValues[j]);
    		index_checked.add(tmp_indexValues[j]);
    		min_index.add(tmp_indexValues[j]);
    	}
	}
	
	boolean isTableMaxValue(String[] table,String value){
		
		if(value==null) return true;
		String max_value=table[0];
		for(int i=1;i<table.length;i++){
			if(table[i]==null) return false;
			if(Float.parseFloat(table[i])>Float.parseFloat(max_value)) max_value=table[i];
		}
		return (value.equals(max_value));
	}
	
	boolean isTableMinValue(String[] table,String value){
		
		if(value==null) return true;
		String min_value=table[0];
		for(int i=1;i<table.length;i++){
			if(table[i]==null) return false;
			if(Float.parseFloat(table[i])<Float.parseFloat(min_value)) min_value=table[i];
		}
		return (value.equals(min_value));
	}
	
	boolean returnConditionForMaxMin(int index,String tmp_value,String table_value,int mode/* 0 min,1 max */){
		if(mode==0) return (index==0 || Float.parseFloat(tmp_value)>Float.parseFloat(table_value));
		return (index==0 || Float.parseFloat(tmp_value)<Float.parseFloat(table_value));
	}
	
	public void createMaxHightlight(String[][] table){
		int num_of_msrs_in_table=table.length-2;
    	int maxLenght=(int) Math.floor(num_of_msrs_in_table*0.25);
    	 	
        String[] tmp_Values=new String[maxLenght];
    	int[] tmp_indexValues=new int[maxLenght];
    	DecimalFormat df = new DecimalFormat("#.##");
		 df.setMinimumFractionDigits(2);
		
		 for(int j=0;j<maxLenght;j++) tmp_indexValues[j]=0;
		 for(int j=2 ; j<num_of_msrs_in_table+2;j++ ){
			for(int k=0;k<maxLenght;k++) {
				if(returnConditionForMaxMin(tmp_indexValues[k],tmp_Values[k],table[j][2],1)  && isTableMinValue(tmp_Values,tmp_Values[k])){
					tmp_Values[k]=df.format(Float.parseFloat(table[j][2]));
					tmp_indexValues[k]=j;
					break;
				}
			}
		 }
    	for(int j=0;j<maxLenght;j++) {
    		maxValues.add(tmp_Values[j]);
    		index_checked.add(tmp_indexValues[j]);
    		max_index.add(tmp_indexValues[j]);
    	}
	}
	
	public void createMiddleHightlight(String[][] table){
		DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
    	for(int i=2;i<table.length;i++){
    		if(!index_checked.contains(i)){
    			String toadd=df.format(Float.parseFloat(table[i][2]));
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
