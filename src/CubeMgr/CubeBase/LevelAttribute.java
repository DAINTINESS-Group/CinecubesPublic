package CubeMgr.CubeBase;

import CubeMgr.StarSchema.Attribute;

public class LevelAttribute{
    
	private String nameLvlAttr;
	private String Moreinfos;
    private Level level;
    private Attribute attribute;
  
    LevelAttribute(String name,String infos){
    	setNameLvlAttr(name);
    	setMoreinfos(infos);
    }
    
	public String getNameLvlAttr() {
		return nameLvlAttr;
	}

	public void setNameLvlAttr(String nameLvlAttr) {
		this.nameLvlAttr = nameLvlAttr;
	}

	public String getMoreinfos() {
		return Moreinfos;
	}

	public void setMoreinfos(String moreinfos) {
		Moreinfos = moreinfos;
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}


	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Attribute getAttribute() {
		return attribute;
	}
    
}
