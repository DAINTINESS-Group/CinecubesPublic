package CubeMgr.CubeBase;

import CubeMgr.StarSchema.DimensionTable;

import java.util.ArrayList;
import java.util.List;

public class Dimension{
    private ArrayList<Hierarchy> hierachy;
    private DimensionTable DimTbl;
    public String name;
    
    public Dimension(String nameDim){
    	name=nameDim;
    	hierachy=new ArrayList<Hierarchy>();
    }
    
    void getLevels(){
        
    }
    

    public DimensionTable getDimTbl() {
            return DimTbl;
    }

    public void setDimTbl(DimensionTable dimTbl) {
            DimTbl = dimTbl;
    }

    public ArrayList<Hierarchy> getHier() {
            return hierachy;
    }

    public void setHier(ArrayList<Hierarchy> hier) {
    	hierachy = hier;
    }
    
    public void addHierachyWithLevels(){
    	
    }
    
}
