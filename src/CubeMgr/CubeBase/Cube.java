package CubeMgr.CubeBase;

import java.util.ArrayList;
import java.util.List;
 
public class Cube {
   // public ArrayList<FactTable> SqlTbl;
   //public MetaData MtDt;
	//private Dimension CubDim;
    
    public String name;
    public List<Level> lvl;    
    public List<Measure> Msr;
    public List<Dimension> Dim;    
    protected ArrayList<String> DimensionRefField; /* Name of field where the Dimension is connected */
    
    public Cube(String NAME){
    	name=NAME;
    	lvl=new ArrayList<Level>();
    	Msr=new ArrayList<Measure>();
    	Dim=new ArrayList<Dimension>();
    	DimensionRefField=new ArrayList<String>();
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
    
	/*	
     * 
     * This function return Parent Level
     * Parameters
     * 		dimension 	 dimension name
     * 		level 	     name of level witch need to find Parent
     *
     */
    public Level getParentLevel(String dimension,String level){
		List<Dimension> dimensions=this.Dim;
		for(int j=0;j<dimensions.size();j++){ //for each dimension
			if(dimensions.get(j).name.equals(dimension)){
				ArrayList<Hierarchy> current_hierachy=dimensions.get(j).getHier();
				for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
					List<Level> current_lvls=current_hierachy.get(k).lvls;
					for(int l=0;l<current_lvls.size();l++){
						if(current_lvls.get(l).name.equals(level)){
							if(l<current_lvls.size()-1) return current_lvls.get(l+1);
							else if(l+1<current_lvls.size()) return current_lvls.get(l+1);
						}
					}
				}
			}
		}
		return null;
    }
    
    /*	
     * 
     * This function return Dimension Table
     * Parameters
     * 		dimension 	 dimension name we need the table name
     *
     */
    public String getSqlTableByDimensionName(String dimension){
    	List<Dimension> dimensions=this.Dim;
		for(int j=0;j<dimensions.size();j++){ //for each dimension
			if(dimensions.get(j).name.equals(dimension)){
				return dimensions.get(j).getDimTbl().TblName;
			}
		}
		return "";
    }
    
    /*	
     * 
     * This function return Attribute Name of Level 
     * Parameters
     * 		dimension 	 dimension name we need the table name
     *
     */
    public String getSqlFieldByDimensionLevelName(String dimension,String level){
    	List<Dimension> dimensions=this.Dim;
		for(int j=0;j<dimensions.size();j++){ //for each dimension
			if(dimensions.get(j).name.equals(dimension)){
				ArrayList<Hierarchy> current_hierachy=dimensions.get(j).getHier();
				for(int k=0;k<current_hierachy.size();k++){//for each hierarchy of dimension
					List<Level> current_lvls=current_hierachy.get(k).lvls;
					for(int l=0;l<current_lvls.size();l++){
						if(current_lvls.get(l).name.equals(level)) return current_lvls.get(l).lvlAttributes.get(0).getAttribute().name; 
					}
				}
			}
		}
		return "";
    }
}
