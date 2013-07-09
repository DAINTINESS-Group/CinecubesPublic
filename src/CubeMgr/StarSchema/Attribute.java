package CubeMgr.StarSchema;

public class Attribute {
	/**
	 * @uml.property  name="name"
	 */
	public String name;
	/**
	 * @uml.property  name="datatype"
	 */
	public String datatype;
	/**
	 * @uml.property  name="constraints"
	 */
	public String constraints;
    
    public Attribute(String nm,String dt){
    	name = nm;
    	datatype=dt;    			
    }
    
}
