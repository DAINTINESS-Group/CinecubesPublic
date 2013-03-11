/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.StarSchema;

/**
 *
 * @author Asterix
 */
public class Attribute {
	public String name;
	public String datatype;
	public String constraints;
    
    public Attribute(String nm,String dt){
    	name = nm;
    	datatype=dt;    			
    }
    
}
