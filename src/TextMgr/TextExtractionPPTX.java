package TextMgr;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

import HighlightMgr.HighlightTable;

public class TextExtractionPPTX extends TextExtraction {
    
   /* public String textForNotes;/*Maybe I delete this valuess* /
    public String textForTitle;
    public String textToBeSound;*/
   
    public TextExtractionPPTX(){
    	super();
    	
        /*textForNotes=null;
        textForTitle=null;
        textToBeSound=null;*/
    }
    
   /* public String getTextForNotes(){
        return this.textForNotes;
    }
    
    public String getTextForTitle(){
        return this.textForTitle;
    }
    
    public String getTextToBeSound(){
        return this.textToBeSound;
    }
    
    public void setTextForNotes(String value){
        this.textForNotes=value;
    }
    
    public void getTextForTitle(String value){
        this.textForTitle=value;
    }
    
    public void getTextToBeSound(String value){
        this.textToBeSound=value;
    }*/
    
    public String createTextForOriginalAct1(ArrayList<String[]> Gamma,ArrayList<String[]> Sigma,String[][] Result,HighlightTable htable){
    	String dimensionText="Here, you can see the answer of the original query. You have specified : ";
    	String maxText="You can observe the results in this table. We highlight the largest value";
    	String minText="We also highlight the lower value";
    	int j=0;
    	for(String[] sigma:Sigma){
    		if(j>0) dimensionText+=", ";
    		if(j==Sigma.size()-1) dimensionText+=" and ";
    		dimensionText+=sigma[0]+" to be equal to "+sigma[2]+"";
    		j++;
    	}
    	
    	/*maxText+=createTextForMinOrMaxArraylist(htable.maxValues,htable.max_index,Gamma,Result);
    	maxText+=") " +*/
    	if(htable.maxValues.size()>1) maxText+="s";
    	maxText+=" with color "+htable.maxcolor_name+" color "+". "; 
    	
    	/*minText+=createTextForMinOrMaxArraylist(htable.minValues,htable.min_index,Gamma,Result);
    	minText+=") " +*/
    	if(htable.minValues.size()>1) minText+="s";
    	minText+=" with color "+htable.mincolor_name+". "; 
		return dimensionText+" .\n"+maxText+"\n"+minText;
    }
    
    public String createTextForOriginalAct2(ArrayList<String[]> Gamma,ArrayList<String[]> Sigma,String[][] Result,HighlightTable htable){
    	String dimensionText="In this slide we remind to you the result of the original query. \nNow we are going to explain the internal breakdown of the defining values " +
    			"of the original query";
    	dimensionText+="by drilling down in two dimensions : ";
    	
    	int j=0;
    	for(String[] gamma:Gamma){
    		if(j>0) dimensionText+=", ";
    		if(j==Sigma.size()-1) dimensionText+=" and ";    		
    		dimensionText+=gamma[0]+"."+gamma[1];
    		j++;
    	}
    	dimensionText+="\n In the first of the following two slides we will drill-in dimension "+Gamma.get(0)[0]+"."+Gamma.get(0)[1]+". ";
    	dimensionText+="Then we will drill-in dimension "+Gamma.get(1)[0]+"."+Gamma.get(1)[1]+". ";
    	 
		return dimensionText+"\n";
    }
    
    public String createTextForAct1(ArrayList<String[]> GammaCompareTo,
			ArrayList<String[]> SigmaCompareTo,
			ArrayList<String[]> SigmaCurrent,
			String[][] Result,
			HighlightTable htable,
			int diffGamma,
			String Aggregate,
			String Measure){
    	
    	String txtNotes="In this graphic, we put the original request in context by comparing the value";
    	String[] gammaChange=GammaCompareTo.get(diffGamma);
    	String[] sigmaOriginal=SigmaCompareTo.get(this.getIndexOfSigma(SigmaCompareTo, gammaChange[0]));
    	
    	txtNotes+=" "+sigmaOriginal[2]+" for "+sigmaOriginal[0]+" with its sibling values. We highlight the reference cells with bold.\n" +
    			"We calculate the "+Aggregate+" of "+Measure.replace("\r", "")+" while fixing ";
    	int j=0;
    	for(String[] sigma:SigmaCurrent){
    		if(j>0) txtNotes+=", ";
    		if(j==SigmaCurrent.size()-1) txtNotes+=" and ";
    		txtNotes+=sigma[0]+" to be equal to '"+sigma[2]+"'";
    		j++;
    	}
		return txtNotes.replace("  ", " ");
    	
    }
    
    public String createTextForAct2(ArrayList<String[]> GammaCompareTo,
    								ArrayList<String[]> SigmaCompareTo,
    								String[][] Result,
    								HighlightTable htable,
    								int diffGamma,
    								String Aggregate,
    								String Measure,
    								int num_values_drill_in, 
    								String[] currentGamma){
    	
    	String txtNotes="In this graphic, we put the original request in context by drilling-down one level for all values of dimension "+GammaCompareTo.get(diffGamma)[0]+"."+GammaCompareTo.get(diffGamma)[1];
    	if(diffGamma==0) txtNotes+="(present in the rows of result). ";
    	else txtNotes+="(present in the columns of result). ";
    	/*txtNotes+="The values ​​in the left corner indicate the start of new table and the values in brackets the number of tuples for each result. Here you can see "+String.valueOf(num_values_drill_in)+" table";
    	if(num_values_drill_in > 1) txtNotes+="s";*/
    	
    	txtNotes+="For each cell we show both the "+Aggregate+" of "+Measure.replace("\r", "")+" and the number of tuples that correspond to it. ";
    	txtNotes+="(The analysis of "+String.valueOf(num_values_drill_in)+" value";
    	if(num_values_drill_in > 1) txtNotes+="s";
    	if(diffGamma==0) txtNotes+=" for the rows ";
    	else txtNotes+=" for the columns ";
    	txtNotes+="of the original table, to the dimension "+currentGamma[0].replace("_class.", " at ").replace(".", " at ").replace("lvl", "level ")+".)\n";
    	
		return txtNotes.replace("  ", " ").replace("_dim.", " at ").replace("_dim", "").replace("lvl", "level ");
    }
    
    public String createTxtForDominatedRowsColumns(String[][] PivotTable,Color[][] ColorTbl,HighlightTable htable){
    	
    	int max_index=0;
    	int min_index=1;
    	int middle_index=2;
    	
    	String textToReturn="We highlight the "+htable.valuePerColor[min_index]+" lowest values in "+htable.mincolor_name+" color and the "
    						+htable.valuePerColor[max_index]+" in "+htable.maxcolor_name+" color.\n" +
    			"Some interesting findings include:\n";
    	for(Integer index :htable.colsDominateMax ){
    		textToReturn+="Column "+PivotTable[0][index]+" has "+htable.colsDominationColor[index][max_index]+" of the "+htable.valuePerColor[max_index]+" highest values.\n";
    	}
    	
    	for(Integer index :htable.colsDominateMin ){
    		textToReturn+="Column "+PivotTable[0][index]+" has "+htable.colsDominationColor[index][min_index]+" of the "+htable.valuePerColor[min_index]+" lowest values.\n";
    	}
    	return textToReturn;
    	  	
    	
    }
    
    public String createTxtComparingToSiblingColumn_version_for_sum(String[][] PivotTable,int ColumnOfOurValue){
    	double[] sumUp=new double[PivotTable[0].length-1];
    	String[] columnValues=new String[PivotTable[0].length-1];
    	String ret_value="";
    	DecimalFormat df = new DecimalFormat("#.##");
    	
    	df.setMaximumFractionDigits(2);
    	df.setMinimumFractionDigits(2);
    	
    	
    	for(int j=1;j<PivotTable[0].length;j++){
    		sumUp[j-1]=0;
    		columnValues[j-1]=PivotTable[0][j];
    		for(int i=1;i<PivotTable.length;i++){
    			if(!PivotTable[i][j].equals("-")){
    				sumUp[j-1]+=Double.parseDouble(PivotTable[i][j]);
    			}
    				
    		}
    	}
    	
    	sortDescDoubleTable(sumUp,columnValues);
    	
    	ret_value+="Compared to its sibling we have got that ";
    	if(sumUp.length==2){
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work more hours per week from "+columnValues[1]+"("+df.format(sumUp[1])+").");
    	}
    	else if(sumUp.length==3){
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work the most hours per week from all. ");
    		ret_value+=("While "+columnValues[1]+"("+df.format(sumUp[1])+") work the most hours per week from "+columnValues[2]+"("+df.format(sumUp[2])+"). ");
    	}
    	else{
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work the most hours per week from all.");
    		ret_value+=(" Contrary, "+columnValues[sumUp.length-1]+"("+df.format(sumUp[sumUp.length-1])+") work the less hours per week from all. ");
    		ret_value+=(" While the other siblings have the following descending order: ");
	    	for(int i=1;i<sumUp.length-1;i++){
	    		if(i>1) ret_value+=(", ");	    	
	    		ret_value+=(columnValues[i]+"("+df.format(sumUp[i])+")");
	    	}
	    	ret_value+=(". ");
    	}
    	
    	return ret_value;
    }
    
    
    public String createTxtForIntroSlide(ArrayList<String[]> Gamma,ArrayList<String[]> Sigma,String Aggregate,String Measure){
    	String ret_txt="Report for the query "+ Aggregate+" of "+Measure.replace("\r", "")+" by ";
    	for(String [] gamma: Gamma){
    		ret_txt+=gamma[0].replace("_dim"," at ")+gamma[1].replace("lvl", "level ")+" and ";
    	}
    	int i=0;
    	ret_txt+=" for ";
    	for(String [] sigma: Sigma){
    		if(i==Sigma.size()-1) ret_txt+=" and ";
    		else if(i>0) ret_txt+=", ";
    		ret_txt+=sigma[0].replace("_dim."," at ").replace("lvl", "level ")+" fixing to "+sigma[2];
    		i++;
    	}
    	return ret_txt.replace("  ", " ").replace("\n", "").replace("\r", "");
    }
    
    
    public String createTxtComparingToSiblingColumn_v1(String[][] PivotTable,int ColumnOfOurValue,HighlightTable htable){
    	
    	String numOfcases=String.valueOf(PivotTable.length-1);
    	String ret_value="";
    	if(htable.countHigherPerColumn.length==2){
    		ret_value="Compared to its sibling we observe that in ";
    		for(int j=0;j<htable.countHigherPerColumn.length;j++){
    			if(j!=ColumnOfOurValue-1){
        			if(htable.countHigherPerColumn[j]>0) {
            			ret_value+=String.valueOf(htable.countHigherPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has higher value than "+htable.valuesOfColumn[ColumnOfOurValue-1]+".\n";
            			if(htable.countLowerPerColumn[j]>0 || htable.countEqualPerColumn[j]>0 || htable.nullValuesPerColumn[j]>0)  ret_value+="In ";
            		}
            		
            		if(htable.countLowerPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.countLowerPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has lower value than "+htable.valuesOfColumn[j]+".\n";
            			if(htable.countEqualPerColumn[j]>0 || htable.nullValuesPerColumn[j]>0) ret_value+="In ";;
            		}
            		
            		if(htable.countEqualPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.countLowerPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has equal value than "+htable.valuesOfColumn[j]+".\n";
            			if(htable.nullValuesPerColumn[j]>0) ret_value+="In ";
            		}
            		
            		if(htable.nullValuesPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.nullValuesPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[j]+" has null value.\n";
            		}
    			}
    		}
    	}
    	else{
    		ret_value="Compared to its sibling we observe the following:\n ";
    		for(int j=0;j<htable.countHigherPerColumn.length;j++){
    			if(j!=ColumnOfOurValue-1){
    				if(htable.countHigherPerColumn[j]>0) {
            			ret_value+=String.valueOf(htable.countHigherPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has higher value than "+htable.valuesOfColumn[j]+".\n";
            			if(htable.countLowerPerColumn[j]>0 || htable.countEqualPerColumn[j]>0 || htable.nullValuesPerColumn[j]>0) ret_value+="In ";
            		}
            		
            		if(htable.countLowerPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.countLowerPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has lower value than "+htable.valuesOfColumn[j]+".\n";
            			if(htable.countEqualPerColumn[j]>0 || htable.nullValuesPerColumn[j]>0) ret_value+="In ";
            		}
            		
            		if(htable.countEqualPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.countEqualPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[ColumnOfOurValue-1]+" has equal value than "+htable.valuesOfColumn[j]+".\n";
            			if(htable.nullValuesPerColumn[j]>0) ret_value+="In ";
            		}
            		
            		if(htable.nullValuesPerColumn[j]>0){
            			ret_value+=String.valueOf(htable.nullValuesPerColumn[j])+" out of "+numOfcases+" cases "+htable.valuesOfColumn[j]+" has null value.\n";
            		}
    			}
    		}
    	}
    	
    	return ret_value;
    }
    
    public String createTxtComparingToSiblingColumn_v2(String[][] PivotTable){
    	double[] sumUp=new double[PivotTable[0].length-2];
    	String[] columnValues=new String[PivotTable[0].length-2];
    	String ret_value="";
    	DecimalFormat df = new DecimalFormat("#.##");
    	
    	df.setMaximumFractionDigits(2);
    	df.setMinimumFractionDigits(2);
    	
    	
    	for(int j=2;j<PivotTable[0].length;j++){
    		sumUp[j-2]=0;
    		columnValues[j-2]=PivotTable[0][j];
    		for(int i=1;i<PivotTable.length;i++){
    			String[] vals=PivotTable[i][j].split(" ");
    			if(tryParseFloat(vals[0])){
    				sumUp[j-2]+=Double.parseDouble(vals[0]);
    			}
    		}
    	}
    	sortDescDoubleTable(sumUp,columnValues);
    	ret_value+="Compared to its sibling we have got that ";
    	if(sumUp.length==2){
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work more hours per week from "+columnValues[1]+"("+df.format(sumUp[1])+").");
    	}
    	else if(sumUp.length==3){
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work the most hours per week from all. ");
    		ret_value+=("While "+columnValues[1]+"("+df.format(sumUp[1])+") work the most hours per week from "+columnValues[2]+"("+df.format(sumUp[2])+"). ");
    	}
    	else{
    		ret_value+=(columnValues[0]+"("+df.format(sumUp[0])+") work the most hours per week from all.");
    		ret_value+=(" Contrary, "+columnValues[sumUp.length-1]+"("+df.format(sumUp[sumUp.length-1])+") work the less hours per week from all. ");
    		ret_value+=(" While the other siblings have the following descending order: ");
	    	for(int i=1;i<sumUp.length-1;i++){
	    		if(i>1) ret_value+=(", ");	    	
	    		ret_value+=(columnValues[i]+"("+df.format(sumUp[i])+")");
	    	}
	    	ret_value+=(". ");
    	}
    	
    	return ret_value;
    }
    
    /*public String createTxtComparingToSiblingColumn_v3(String[][] PivotTable){
    	String ret_value="";
    	ArrayList<Integer> rowsToSplit=new ArrayList<Integer>();
    	ArrayList<String[][]> PivotsTbl=new ArrayList<String[][]>();
    	
    	for(int i=0;i<PivotTable.length;i++){
    		boolean split_row=true;
    		for(int j=0;j<PivotTable[0].length;j++){
    			if(!PivotTable[i][j].equals("")) split_row=false;
    		}
    		if(split_row) rowsToSplit.add(i);
    	}
    	for(int i=0;i<rowsToSplit.size();i++){
    		if(i==0){
    			String[][] toadd=new String[rowsToSplit.get(i)][PivotTable[0].length];
    			System.arraycopy(PivotTable, 0, toadd, 0, rowsToSplit.get(i));
    			PivotsTbl.add(toadd);
    		}
    		else {
    			String[][] toadd=new String[rowsToSplit.get(i)-rowsToSplit.get(i-1)-1][PivotTable[0].length];
    			System.arraycopy(PivotTable, rowsToSplit.get(i-1)+1, toadd, 0, rowsToSplit.get(i)-rowsToSplit.get(i-1)-1);
    			PivotsTbl.add(toadd);
    		}
    	}
    	
    	String[][] toadd=new String[PivotTable.length-rowsToSplit.get(rowsToSplit.size()-1)-1][PivotTable[0].length];
		System.arraycopy(PivotTable, rowsToSplit.get(rowsToSplit.size()-1)+1, toadd, 0, PivotTable.length-rowsToSplit.get(rowsToSplit.size()-1)-1);
		PivotsTbl.add(toadd);
    	
    	for(int i=0;i<PivotsTbl.size();i++){    		
    		ret_value+="For "+String.valueOf(i+1);
    		if(i==0) ret_value+="st ";
    		else if(i==1) ret_value+="nd ";
    		else if(i==2) ret_value+="rd ";
    		else ret_value+="th ";
    		ret_value+="table we have that:\n"+createTxtComparingToSiblingColumn_v2(PivotsTbl.get(i))+"\n";
    		ret_value+=getCountTuplesInPivotTable(PivotsTbl.get(i))+"\n";
    	}
    	ret_value+="==================================";
    	return ret_value;
    }*/
    
    public String createTxtComparingToSiblingColumn_v4(String[][] PivotTable){
    	String ret_value="";
    	ArrayList<Integer> rowsToSplit=new ArrayList<Integer>();
    	ArrayList<String[][]> PivotsTbl=new ArrayList<String[][]>();
    	
    	for(int i=0;i<PivotTable.length;i++){
    		boolean split_row=true;
    		for(int j=0;j<PivotTable[0].length;j++){
    			if(!PivotTable[i][j].equals("")) split_row=false;
    		}
    		if(split_row) rowsToSplit.add(i);
    	}
    	for(int i=0;i<rowsToSplit.size();i++){
    		if(i==0){
    			String[][] toadd=new String[rowsToSplit.get(i)][PivotTable[0].length];
    			System.arraycopy(PivotTable, 0, toadd, 0, rowsToSplit.get(i));
    			PivotsTbl.add(toadd);
    		}
    		else {
    			String[][] toadd=new String[rowsToSplit.get(i)-rowsToSplit.get(i-1)-1][PivotTable[0].length];
    			System.arraycopy(PivotTable, rowsToSplit.get(i-1)+1, toadd, 0, rowsToSplit.get(i)-rowsToSplit.get(i-1)-1);
    			PivotsTbl.add(toadd);
    		}
    	}
    	
    	String[][] toadd=new String[PivotTable.length-rowsToSplit.get(rowsToSplit.size()-1)-1][PivotTable[0].length];
		System.arraycopy(PivotTable, rowsToSplit.get(rowsToSplit.size()-1)+1, toadd, 0, PivotTable.length-rowsToSplit.get(rowsToSplit.size()-1)-1);
		PivotsTbl.add(toadd);
    	
    	for(int i=0;i<PivotsTbl.size();i++){    		
    		ret_value+="For "+String.valueOf(i+1);
    		if(i==0) ret_value+="st ";
    		else if(i==1) ret_value+="nd ";
    		else if(i==2) ret_value+="rd ";
    		else ret_value+="th ";
    		ret_value+="table we have that:\n"+createTxtComparingToSiblingColumn_v2(PivotsTbl.get(i))+"\n";
    		ret_value+=getCountTuplesInPivotTable(PivotsTbl.get(i))+"\n";
    	}
    	ret_value+="==================================";
    	return ret_value;
    }
    
    public String getCountTuplesInPivotTable(String[][] PivotTable){
    	int[] sumUp=new int[PivotTable[0].length-2];
    	int sum_all=0;
    	String[] columnValues=new String[PivotTable[0].length-2];
    	for(int j=2;j<PivotTable[0].length;j++){
    		sumUp[j-2]=0;
    		columnValues[j-2]=PivotTable[0][j];
    		for(int i=1;i<PivotTable.length;i++){
    			String[] vals=PivotTable[i][j].split(" ");
    			if(tryParseFloat(vals[0])){
    				int count_value=Integer.parseInt(vals[1].replace("(", "").replace(")","" ));
    				sumUp[j-2]+=count_value;
    				sum_all+=count_value;
    			}
    		}
    	}
    	String ret_value="In this table we have "+String.valueOf(sum_all)+" tuples from these ";
    	for(int j=0;j<sumUp.length;j++){
    		if(j>0) ret_value+=", ";
    		if(j==sumUp.length-1) ret_value+="and ";
    		ret_value+="the "+String.valueOf(sumUp[j])+" are at "+columnValues[j]+" column";
    	}
    	ret_value+=". ";
    	
    	return ret_value;
    }
    /*    
    public String createTxtComparingToSiblingRow_version_sum(String[][] PivotTable){
    	double[] sumUp=new double[PivotTable.length-1];
    	String[] rowValues=new String[PivotTable.length-1];
    	String ret_value="";
    	DecimalFormat df = new DecimalFormat("#.##");
    	
    	df.setMaximumFractionDigits(2);
    	df.setMinimumFractionDigits(2);
    	
    	
    	for(int i=1;i<PivotTable.length;i++){
    		sumUp[i-1]=0;
    		rowValues[i-1]=PivotTable[i][0];
    		for(int j=1;j<PivotTable[0].length;j++){
    			if(!PivotTable[i][j].equals("-"))sumUp[i-1]+=Double.parseDouble(PivotTable[i][j]);
    		}
    	}
    	sortDescDoubleTable(sumUp,rowValues);
    	ret_value+="Compared to its sibling we have got that ";
    	if(sumUp.length==2){
    		ret_value+=(rowValues[0]+"("+df.format(sumUp[0])+") work more hours per week from "+rowValues[1]+"("+df.format(sumUp[1])+").");
    	}
    	else if(sumUp.length==3){
    		ret_value+=(rowValues[0]+"("+df.format(sumUp[0])+"). ");
    		ret_value+=("While "+rowValues[1]+"("+df.format(sumUp[1])+") work the most hours per week from "+rowValues[2]+"("+df.format(sumUp[2])+"). ");
    	}
    	else{
    		ret_value+=(rowValues[0]+"("+df.format(sumUp[0])+") work the most hours per week from all.");
    		ret_value+=(" Contrary, "+rowValues[sumUp.length-1]+"("+df.format(sumUp[sumUp.length-1])+") work the less hours per week from all. ");
    		ret_value+=(" While the other siblings have the following descending order: ");
	    	for(int i=1;i<sumUp.length-1;i++){
	    		if(i>1) ret_value+=(", ");	    	
	    		ret_value+=(rowValues[i]+"("+df.format(sumUp[i])+")");
	    	}
	    	ret_value+=(". ");
    	}
    	
    	return ret_value;
    }*/
    
    public String createTxtComparingToSiblingRow_v1(String[][] PivotTable,HighlightTable httable){
    	
    	String numOfcases=String.valueOf(PivotTable[0].length-1);
    	String ret_value="";
    	
    	if(httable.countHigherPerRow.length==2){
    		ret_value="Compared to its sibling we observe that in ";
    		for(int j=0;j<httable.countHigherPerRow.length;j++){
    			if(j!=httable.boldRow-1){
        			if(httable.countHigherPerRow[j]>0) {
            			ret_value+=String.valueOf(httable.countHigherPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has higher value than "+httable.valuesOfRow[j]+".\n";
            			if(httable.countLowerPerRow[j]>0 || httable.countEqualPerRow[j]>0 || httable.nullValuesPerRow[j]>0) ret_value+="In ";
            		}
            		
            		if(httable.countLowerPerRow[j]>0){
            			ret_value+=String.valueOf(httable.countLowerPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has lower value than "+httable.valuesOfRow[j]+".\n";
            			if(httable.countEqualPerRow[j]>0 || httable.nullValuesPerRow[j]>0) ret_value+="In ";
            		}
            		
            		if(httable.countEqualPerRow[j]>0){
            			ret_value+=String.valueOf(httable.countEqualPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has equal value than "+httable.valuesOfRow[j]+".\n";
            			if(httable.nullValuesPerRow[j]>0) ret_value+="In ";
            		}
            		
            		if(httable.nullValuesPerRow[j]>0){
            			ret_value+=String.valueOf(httable.nullValuesPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[j]+" has null value.\n";
            		}
    			}
    		}
    	}
    	else{
    		ret_value="Compared to its sibling we observe the following:\n ";
    		for(int j=0;j<httable.countHigherPerRow.length;j++){
    			if(j!=httable.boldRow-1){
        			if(httable.countHigherPerRow[j]>0) {
            			ret_value+="In "+String.valueOf(httable.countHigherPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has higher value than "+httable.valuesOfRow[j]+".\n";
            		}
            		
            		if(httable.countLowerPerRow[j]>0){
            			ret_value+="In "+String.valueOf(httable.countLowerPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has lower value than "+httable.valuesOfRow[j]+".\n";
            		}
            		
            		if(httable.countEqualPerRow[j]>0){
            			ret_value+="In "+String.valueOf(httable.countEqualPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfRow[httable.boldRow-1]+" has equal value than "+httable.valuesOfRow[j]+".\n";
            		}
            		
            		if(httable.nullValuesPerRow[j]>0){
            			ret_value+="In "+String.valueOf(httable.nullValuesPerRow[j])+" out of "+numOfcases+" cases "+httable.valuesOfColumn[j]+" has null value.\n";
            		}
    			}
    		}
    	}
    	
    	return ret_value;
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
    
    private void sortDescDoubleTable(double[] tableToSort,String[] tableWithNamesForTableToSort){/*bubble Sort*/
    	int lenD = tableToSort.length;
    	double tmp = 0;
    	String tmp_str="";
    	for(int i = 0;i<lenD;i++){
    		for(int j = (lenD-1);j>=(i+1);j--){
    			if(tableToSort[j]>tableToSort[j-1]){
    				tmp = tableToSort[j];
    				tableToSort[j]=tableToSort[j-1];
    				tableToSort[j-1]=tmp;
    				tmp_str=tableWithNamesForTableToSort[j];
    				tableWithNamesForTableToSort[j]=tableWithNamesForTableToSort[j-1];
    				tableWithNamesForTableToSort[j-1]=tmp_str;
    			}
    		}
    	}
    }
        
    private void produceTxtForDominationCols(ArrayList<Integer> dominateList,ArrayList<Integer> toCompareForDomination1,ArrayList<Integer> toCompareForDomination2,ArrayList<Integer> checkedSoFar,String[][] PivotTable,String whereToproduce/*row or column*/, String[] whichColor){
    	if(dominateList.size()==0) return;
    	for(Integer index:dominateList){
    		if(!checkedSoFar.contains(index)){
	    		if(toCompareForDomination1.contains(index) && toCompareForDomination2.contains(index)) {
	    			System.out.println("We have domination of max,min and middle values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[0][index]);
	    			checkedSoFar.add(index);
	    		}
	    		else if(toCompareForDomination1.contains(index)){
	    			System.out.println("We have domination of "+whichColor[0]+" and "+whichColor[1]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[0][index]);
	    			checkedSoFar.add(index);
	    		}
	    		else if(toCompareForDomination2.contains(index)){
	    			System.out.println("We have domination of "+whichColor[0]+" and "+whichColor[2]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[0][index]);
	    			checkedSoFar.add(index);
	    		}
	    		else {
	    			System.out.println("We have domination of "+whichColor[0]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[0][index]);
	    			checkedSoFar.add(index);
	    		}
    		}
    		
    	}
    }
    
    private void produceTxtForDominationRows(ArrayList<Integer> dominateList,ArrayList<Integer> toCompareForDomination1,ArrayList<Integer> toCompareForDomination2,ArrayList<Integer> checkedSoFar,String[][] PivotTable,String whereToproduce/*row or column*/, String[] whichColor){
    	if(dominateList.size()==0) return;
    	for(Integer index:dominateList){
    		if(!checkedSoFar.contains(index)){
	    		if(toCompareForDomination1.contains(index) && toCompareForDomination2.contains(index)) {
	    			System.out.println("We have domination of max,min and middle values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[index][0]);
	    			checkedSoFar.add(index);
	    		}
	    		else if(toCompareForDomination1.contains(index)){
	    			System.out.println("We have domination of "+whichColor[0]+" and "+whichColor[1]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[index][0]);
	    			checkedSoFar.add(index);
	    		}
	    		else if(toCompareForDomination2.contains(index)){
	    			System.out.println("We have domination of "+whichColor[0]+" and "+whichColor[2]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[index][0]);
	    			checkedSoFar.add(index);
	    		}
	    		else {
	    			System.out.println("We have domination of "+whichColor[0]+" values on "+whereToproduce+" "+String.valueOf(index+1)+" which value is "+PivotTable[index][0]);
	    			checkedSoFar.add(index);
	    		}
    		}
    		
    	}
    }
        
    private int maxValueInTableColumn(Integer[][] table,int column){
    	int max_value=table[0][column];
    	for(int i=1;i<table.length;i++){
    		if(max_value<table[i][column]) max_value=table[i][column];
    	}
    	return max_value;
    }
    
    private int getIndexOfSigma(ArrayList<String[]> sigmaExpressions,String gamma_dim) {
		int ret_value=-1;
		int i=0;
		for(String[] sigma : sigmaExpressions ){
			if(sigma[0].split("\\.")[0].equals(gamma_dim)) ret_value=i;
			i++;
		}
		return ret_value;
	}
    
    private String createTextForMinOrMaxArraylist(ArrayList<String> listValues,ArrayList<Integer> listIndex,ArrayList<String[]> Gamma,String[][] Result){
    	String ret_value="";
    	if(listValues.size()>1) ret_value+="s (";
    	for(int i=0;i<listValues.size();i++){
    		if(i>0) ret_value+=", ";
    		if(i==listValues.size()-1) ret_value+=" and ";
    		ret_value+=Gamma.get(0)[0]+"."+Gamma.get(0)[1]+" is "+Result[listIndex.get(i)][0];
    		ret_value+=" and ";
    		ret_value+=Gamma.get(1)[0]+"."+Gamma.get(1)[1]+" is "+Result[listIndex.get(i)][1];    		
    	}
    	return ret_value;
    }
}
