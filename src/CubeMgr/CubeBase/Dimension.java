package CubeMgr.CubeBase;

import CubeMgr.StarSchema.DimensionTable;

import java.util.ArrayList;

public class Dimension{
    /**
	 * @uml.property  name="hierachy"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="CubeMgr.CubeBase.Level"
	 */
    private ArrayList<Hierarchy> hierachy;
    /**
	 * @uml.property  name="dimTbl"
	 * @uml.associationEnd  
	 */
    private DimensionTable DimTbl;
    /**
	 * @uml.property  name="name"
	 */
    public String name;
    
    public Dimension(String nameDim){
    	name=nameDim;
    	hierachy=new ArrayList<Hierarchy>();
    }
    
    /**
	 * @return
	 * @uml.property  name="dimTbl"
	 */
    public DimensionTable getDimTbl() {
            return DimTbl;
    }

    /**
	 * @param dimTbl
	 * @uml.property  name="dimTbl"
	 */
    public void setDimTbl(DimensionTable dimTbl) {
            DimTbl = dimTbl;
    }

    public ArrayList<Hierarchy> getHier() {
            return hierachy;
    }

    public void setHier(ArrayList<Hierarchy> hier) {
    	hierachy = hier;
    }    
}
