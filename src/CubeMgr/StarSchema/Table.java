package CubeMgr.StarSchema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    /**
	 * @uml.property  name="lstAttr"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="CubeMgr.StarSchema.Attribute"
	 */
    public List<Attribute> LstAttr;
    /**
	 * @uml.property  name="tblName"
	 */
    public String TblName;
    
    public Table(String name){
    	TblName=name;
    	LstAttr=new ArrayList<Attribute>();
    }
    
    
    public void setAttribute(Connection con){
    	try {
    		DatabaseMetaData meta = con.getMetaData();
    	    ResultSet rsColumns = meta.getColumns(null, null, TblName, null);
    	    while (rsColumns.next()) {
    	    	LstAttr.add(new Attribute(rsColumns.getString("COLUMN_NAME"),rsColumns.getString("TYPE_NAME")));
    	      }
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void printColumns(){
    	for(Attribute item : LstAttr){
    		System.out.println("----"+item.name+":"+item.datatype);
    	}
    }
    
    public Attribute getAttribute(String name){
    	for(Attribute item : LstAttr){
    		if (item.name.equals(name)) return item;
    	}
    	return null;
    }
}

