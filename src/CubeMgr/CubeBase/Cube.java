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
public class Cube {
   // public ArrayList<FactTable> SqlTbl;
   //public MetaData MtDt;
	//private Dimension CubDim;
    public String name;
    public List<Level> lvl;
    private List<Measure> Msr;
    public List<Dimension> Dim;
    protected ArrayList<String> DimemsionRefField;
    
    public Cube(String NAME){
    	name=NAME;
    	lvl=new ArrayList<>();
    	Msr=new ArrayList<>();
    	Dim=new ArrayList<>();
    	DimemsionRefField=new ArrayList<>();
    }
    
    public void getDimensions(){
        ;
    }

	public List<Measure> getMsr() {
		return Msr;
	}

	public void setMsr(List<Measure> msr) {
		Msr = msr;
	}

	public ArrayList<String> getDimemsionRefField() {
		return DimemsionRefField;
	}

	public void setDimemsionRefField(ArrayList<String> dimemsionRefField) {
		DimemsionRefField = dimemsionRefField;
	}
    
}
