package TextMgr;

import java.util.ArrayList;

import HighlightMgr.HighlightTable;

public class TextExtractionPPTX extends TextExtraction {
    
    public String textForNotes;
    public String textForTitle;
    public String textToBeSound;
   
    public TextExtractionPPTX(){
    	super();
    	
        textForNotes=null;
        textForTitle=null;
        textToBeSound=null;
    }
    
    public String getTextForNotes(){
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
    }
    
    public String createTextForOriginal(ArrayList<String[]> Gamma,ArrayList<String[]> Sigma,String[][] Result,HighlightTable htable){
    	String dimensionText="Here, you can see the answer of the original query. You have specified : ";
    	String maxText="You can observe the results in this table. We highlight the maximum value";
    	String minText=" and the minimum value";
    	int j=0;
    	for(String[] sigma:Sigma){
    		if(j>0) dimensionText+=", ";
    		dimensionText+=sigma[0]+" to be equal to '"+sigma[2]+"'";
    		j++;
    	}
    	
    	if(htable.maxValues.size()>1) maxText+="s (";
    	for(int i=0;i<htable.maxValues.size();i++){
    		if(i>0) maxText+=", ";
    		maxText+=Gamma.get(0)[0]+"."+Gamma.get(0)[1]+" is "+Result[htable.max_index.get(i)][0];
    		maxText+=" and ";
    		maxText+=Gamma.get(1)[0]+"."+Gamma.get(1)[1]+" is "+Result[htable.max_index.get(i)][1];
    	}
    	maxText+=") with color "+htable.maxcolor_name+" and "; 
    	if(htable.minValues.size()>1) minText+="s (";
    	for(int i=0;i<htable.minValues.size();i++){
    		if(i>0) minText+=", ";
    		minText+=Gamma.get(0)[0]+"."+Gamma.get(0)[1]+" is "+Result[htable.min_index.get(i)][0];
    		minText+=" and ";
    		minText+=Gamma.get(1)[0]+"."+Gamma.get(1)[1]+" is "+Result[htable.min_index.get(i)][1];    		
    	}
    	minText+=") with color "+htable.mincolor_name+"."; 
		return dimensionText+" .\n"+maxText+"\n"+minText;
    }
    
    public String createTextForAct1(ArrayList<String[]> GammaCompareTo,ArrayList<String[]> SigmaCompareTo,ArrayList<String[]> SigmaCurrent,String[][] Result,HighlightTable htable,int diffGamma,String Aggregate,String Measure){
    	String txtNotes="In this graphic, we put the original request in context by comparing the value";
    	String[] gammaChange=GammaCompareTo.get(diffGamma);
    	String[] sigmaOriginal=SigmaCompareTo.get(this.getIndexOfSigma(SigmaCompareTo, gammaChange[0]));
    	
    	txtNotes+=" "+sigmaOriginal[2]+" for "+sigmaOriginal[1]+" with its sibling values.\n" +
    			"We calculate the "+Aggregate+" of "+Measure+"while fixing ";
    	int j=0;
    	for(String[] sigma:SigmaCurrent){
    		if(j>0) txtNotes+=", ";
    		txtNotes+=sigma[0]+" to be equal to '"+sigma[2]+"'";
    		j++;
    	}
		return txtNotes;
    	
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

}
