package CubeMgr.CubeBase;

import java.util.ArrayList;

public class Level {
    private LinearHierarchy LinHier;
    public ArrayList<LevelAttribute> lvlAttributes;
    public Integer id;
    public String name;
    
    public Level(Integer position,String nm,LinearHierarchy Hier){
    	id=position;
    	name=nm;
    	lvlAttributes=new ArrayList<LevelAttribute>();
    	setLinearHierarchy(Hier);
    }

    public Level(Integer position,String nm){
    	id=position;
    	name=nm;
    	lvlAttributes=new ArrayList<LevelAttribute>();
    }
        
    public void setLevelAttribute(ArrayList<LevelAttribute> levelAttributes){
    	this.lvlAttributes=levelAttributes;
    }

	public LinearHierarchy getLinearHierarchy() {
		return LinHier;
	}

	public void setLinearHierarchy(LinearHierarchy linHier) {
		LinHier = linHier;
	}

	public void addLevelAttribute(LevelAttribute lvlattribute) {
		lvlAttributes.add(lvlattribute);
	}
}
