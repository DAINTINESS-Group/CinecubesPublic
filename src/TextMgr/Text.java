package TextMgr;

public class Text {
    /**
	 * @uml.property  name="txt"
	 */
    private String txt;

    public Text(){
    	txt="";
    }
    
    public Text(String text){
    	setText(text);
    }
    
	/**
	 * @return the text
	 */
	public String getText() {
		return txt;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.txt = text;
	}
}
