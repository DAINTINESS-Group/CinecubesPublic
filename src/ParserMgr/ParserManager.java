package ParserMgr;


import java.util.ArrayList;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class ParserManager {
	
	public Integer mode;
	public String name_creation;
	public String sqltable;
	public ArrayList<String> dimensionlst;
	public ArrayList<String> hierachylst;
	public ArrayList<String> originallvllst;
	public ArrayList<String> customlvllst;
	public ArrayList<String> conditionlst;
	public ArrayList<String> tablelst;
	public ArrayList<String> groupperlst;
	public String aggregatefunc;
	
	public ParserManager() {
		dimensionlst=new ArrayList<String>();
		hierachylst=new ArrayList<String>();
		originallvllst=new ArrayList<String>();
		customlvllst=new ArrayList<String>();
		conditionlst=new ArrayList<String>();
		tablelst=new ArrayList<String>();
		groupperlst=new ArrayList<String>();
	}
	
	public void parse(String toParse){
		CharStream stream =	new ANTLRStringStream(toParse);
		CubeSqlLexer lexer = new CubeSqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		CubeSqlParser parser = new CubeSqlParser(tokenStream);
		dimensionlst.clear();
		hierachylst.clear();
		originallvllst.clear();
		customlvllst.clear();
		try {
			parser.start();	
			dimensionlst.addAll(parser.dimensionlst);
			hierachylst.addAll(parser.hierachylst);
			originallvllst.addAll(parser.originallvllst);
			customlvllst.addAll(parser.customlvllst);
			mode=parser.mode;
			name_creation=parser.name_creation;
			sqltable=parser.sql_table;
			aggregatefunc=parser.aggregatefunc;
			conditionlst.addAll(parser.conditionlst);
			tablelst.addAll(parser.tablelst);
			groupperlst.addAll(parser.groupperlst);
			//System.out.println("ok");
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
