package CubeMgr.CubeBase;

import java.util.ArrayList;
import java.util.List;
 
public class Cube {
   // public ArrayList<FactTable> SqlTbl;
   //public MetaData MtDt;
	//private Dimension CubDim;
    public String name;
    public List<Level> lvl;
    private List<Measure> Msr;
    public List<Dimension> Dim;
    protected ArrayList<String> DimensionRefField;
    
    public Cube(String NAME){
    	name=NAME;
    	lvl=new ArrayList<>();
    	Msr=new ArrayList<>();
    	Dim=new ArrayList<>();
    	DimensionRefField=new ArrayList<>();
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

	public ArrayList<String> getDimensionRefField() {
		return DimensionRefField;
	}

	public void setDimensionRefField(ArrayList<String> dimemsionRefField) {
		DimensionRefField = dimemsionRefField;
	}
	
	public void addDimension(Dimension dimension) {
		this.Dim.add(dimension);
	}

	public void addDimensionRefField(String namefield) {
		this.DimensionRefField.add(namefield);
	}
    
}
