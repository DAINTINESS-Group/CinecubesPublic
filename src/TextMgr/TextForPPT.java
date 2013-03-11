/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMgr;

import java.io.File;
import java.util.HashSet;

/**
 *
 * @author Asterix
 */
public class TextForPPT extends TextExtraction {
    
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    
    public String[][] PivotTable=null;
    public String textForNotes=null;
    public String textForTitle=null;
    public String textToBeSound=null;
    
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
    public void CreatePivotTable(HashSet<String> RowPivot,HashSet<String> ColPivot,String QueryResult[][]){
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
                        if(QueryResult[r][0].equals(x) && QueryResult[r][1].equals(y)){
                            this.PivotTable[i][j]=QueryResult[r][2];
                        }
                }
                j++;
            }
            i++;
        }
        
    }
    
    private String randomIdentifier(String checkedfolder) {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++)
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            if((new File(checkedfolder+"/"+builder.toString()+".wav")).exists()) 
                builder = new StringBuilder();
        }
        return builder.toString();
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

}
