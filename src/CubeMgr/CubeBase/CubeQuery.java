/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeMgr.CubeBase;

import CubeMgr.StarSchema.SqlQuery;

/**
 *
 * @author Asterix
 */
public class CubeQuery extends Cube {
    
	public String expression;
    public SqlQuery sqlQ;
    
    public CubeQuery(String NAME) {
		super(NAME);
		// TODO Auto-generated constructor stub
	}
}
