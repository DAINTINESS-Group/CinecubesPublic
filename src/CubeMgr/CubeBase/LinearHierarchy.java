package CubeMgr.CubeBase;

import java.util.ArrayList;
import java.util.List;

public class LinearHierarchy  extends Hierarchy {
	
    public LinearHierarchy(){
    	lvls=new ArrayList<Level>();
    }
    
    public void setDimension(Dimension dimensionToPoint){
    	this.dimension=dimensionToPoint;
    }
     
}
