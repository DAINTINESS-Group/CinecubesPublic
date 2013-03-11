/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asterix
 */
public class LinearHierarchy  extends Hierarchy {
	public List<Level> lvls;
    public Dimension dimension;

    public LinearHierarchy(){
    	lvls=new ArrayList<>();
    }
    
    public void setDimension(Dimension dimensionToPoint){
    	this.dimension=dimensionToPoint;
    }
    
    
    @Override
    public void getTable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getAttribute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        
}
