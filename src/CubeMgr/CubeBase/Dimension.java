/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

import CubeMgr.StarSchema.DimensionTable;
import java.util.List;

/**
 *
 * @author Asterix
 */
public class Dimension{
    private List<Hierarchy> Hier;
    private DimensionTable DimTbl;
    public String name;
    
    public Dimension(String nameDim){
    	name=nameDim;
    }
    
    void getLevels(){
        
    }
    

    public DimensionTable getDimTbl() {
            return DimTbl;
    }

    public void setDimTbl(DimensionTable dimTbl) {
            DimTbl = dimTbl;
    }

    public List<Hierarchy> getHier() {
            return Hier;
    }

    public void setHier(List<Hierarchy> hier) {
            Hier = hier;
    }
    
    public void addHierachyWithLevels(){
    	
    }
    
}
