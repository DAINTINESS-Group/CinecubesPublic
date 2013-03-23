/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMgr;

/**
 *
 * @author Asterix
 */
public class Text {
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
