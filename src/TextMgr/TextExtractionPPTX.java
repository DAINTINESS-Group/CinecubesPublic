package TextMgr;

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

}
