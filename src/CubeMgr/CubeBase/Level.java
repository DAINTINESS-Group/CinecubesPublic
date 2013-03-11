/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

/**
 *
 * @author Asterix
 */
public class Level {
    private LinearHierarchy LinHier;
    public LevelAttribute lvlAttribute;
    public Integer id;
    public String name;
    
    public Level(Integer position,String nm,LinearHierarchy Hier){
    	id=position;
    	name=nm;
    	setLinearHierarchy(Hier);
    }

    public Level(Integer position,String nm){
    	id=position;
    	name=nm;
    }
    
    void getTableAttribute(){
        
    }
    
    public void setLevelAttribute(LevelAttribute levelAttribute){
    	this.lvlAttribute=levelAttribute;
    }

	public LinearHierarchy getLinearHierarchy() {
		return LinHier;
	}

	public void setLinearHierarchy(LinearHierarchy linHier) {
		LinHier = linHier;
	}
}
