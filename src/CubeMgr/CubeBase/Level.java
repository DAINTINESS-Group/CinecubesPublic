package CubeMgr.CubeBase;

import java.util.ArrayList;

public class Level {
    private Hierarchy hierarchy;
    public ArrayList<LevelAttribute> lvlAttributes;
    public Integer id;
    public String name;
    
    public Level(Integer position,String nm,Hierarchy Hier){
    	id=position;
    	name=nm;
    	lvlAttributes=new ArrayList<LevelAttribute>();
    	setHierarchy(Hier);
    }

    public Level(Integer position,String nm){
    	id=position;
    	name=nm;
    	lvlAttributes=new ArrayList<LevelAttribute>();
    }
        
    public void setLevelAttribute(ArrayList<LevelAttribute> levelAttributes){
    	this.lvlAttributes=levelAttributes;
    }

	public Hierarchy getLinearHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Hierarchy hier) {
		hierarchy = hier;
	}

	public void addLevelAttribute(LevelAttribute lvlattribute) {
		lvlAttributes.add(lvlattribute);
	}
}
