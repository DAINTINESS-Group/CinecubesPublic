/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

import CubeMgr.StarSchema.Attribute;
import CubeMgr.getTblAttr;

/**
 *
 * @author Asterix
 */
public class Measure implements getTblAttr{
    public Integer id;
    public String name;
    public Attribute Attr;
    
    @Override
    public void getTable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getAttribute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
