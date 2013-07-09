package CubeMgr.CubeBase;

import CubeMgr.StarSchema.Attribute;

public class Measure{
    /**
	 * @uml.property  name="id"
	 */
    public Integer id;
    /**
	 * @uml.property  name="name"
	 */
    public String name;
    /**
	 * @uml.property  name="attr"
	 * @uml.associationEnd  
	 */
    public Attribute Attr;
    
    public Measure(){    	
    }
}
