// $ANTLR 3.4 D:\\workspace\\Master\\src\\CubeSql.g 2013-03-23 17:26:27

  package ParserMgr;
  
  import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CubeSqlParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "AND", "AS", "AT", "AVG", "B", "BY", "C", "CHILDOF", "COMMA", "COUNT", "CREATE", "CUBE", "D", "DIMENSION", "DIMENSION_TABLE", "DOT", "Digit", "E", "F", "FROM", "G", "GROUP", "H", "HIERARCHY", "I", "J", "K", "L", "LBRACE", "LEVEL", "LIST", "Letter", "M", "MAX", "MIN", "N", "NAME", "O", "OF", "OR", "P", "Q", "QUESTMARK", "R", "RBRACE", "REFERENCES", "RELATED", "S", "SELECT", "SQL_TABLE", "SUM", "T", "U", "UNDERSCORE", "V", "W", "WHERE", "WS", "X", "Y", "Z", "'\"'", "'('", "')'", "'<'", "'<='", "'='", "'>='", "'LIKE'", "'\\''"
    };

    public static final int EOF=-1;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int A=4;
    public static final int AND=5;
    public static final int AS=6;
    public static final int AT=7;
    public static final int AVG=8;
    public static final int B=9;
    public static final int BY=10;
    public static final int C=11;
    public static final int CHILDOF=12;
    public static final int COMMA=13;
    public static final int COUNT=14;
    public static final int CREATE=15;
    public static final int CUBE=16;
    public static final int D=17;
    public static final int DIMENSION=18;
    public static final int DIMENSION_TABLE=19;
    public static final int DOT=20;
    public static final int Digit=21;
    public static final int E=22;
    public static final int F=23;
    public static final int FROM=24;
    public static final int G=25;
    public static final int GROUP=26;
    public static final int H=27;
    public static final int HIERARCHY=28;
    public static final int I=29;
    public static final int J=30;
    public static final int K=31;
    public static final int L=32;
    public static final int LBRACE=33;
    public static final int LEVEL=34;
    public static final int LIST=35;
    public static final int Letter=36;
    public static final int M=37;
    public static final int MAX=38;
    public static final int MIN=39;
    public static final int N=40;
    public static final int NAME=41;
    public static final int O=42;
    public static final int OF=43;
    public static final int OR=44;
    public static final int P=45;
    public static final int Q=46;
    public static final int QUESTMARK=47;
    public static final int R=48;
    public static final int RBRACE=49;
    public static final int REFERENCES=50;
    public static final int RELATED=51;
    public static final int S=52;
    public static final int SELECT=53;
    public static final int SQL_TABLE=54;
    public static final int SUM=55;
    public static final int T=56;
    public static final int U=57;
    public static final int UNDERSCORE=58;
    public static final int V=59;
    public static final int W=60;
    public static final int WHERE=61;
    public static final int WS=62;
    public static final int X=63;
    public static final int Y=64;
    public static final int Z=65;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public CubeSqlParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public CubeSqlParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return CubeSqlParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\workspace\\Master\\src\\CubeSql.g"; }


      Integer mode;
      String name_creation;
      String sql_table;
      ArrayList<String> dimensionlst;
      ArrayList<String> hierachylst;
      ArrayList<String> originallvllst;
      ArrayList<String> customlvllst;
      ArrayList<String> conditionlst;
      ArrayList<String> tablelst;
      ArrayList<String> groupperlst;
      String aggregatefunc;
      String tmp_con;
      boolean group;



    // $ANTLR start "start"
    // D:\\workspace\\Master\\src\\CubeSql.g:40:1: start : parse ;
    public final void start() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:40:7: ( parse )
            // D:\\workspace\\Master\\src\\CubeSql.g:40:8: parse
            {

                mode=0;
                tmp_con="";
                group=false;
                dimensionlst=new ArrayList<String>();
                hierachylst=new ArrayList<String>();
                originallvllst=new ArrayList<String>();
                customlvllst=new ArrayList<String>();
                conditionlst=new ArrayList<String>();
                groupperlst=new ArrayList<String>();
                tablelst=new ArrayList<String>();
              

            pushFollow(FOLLOW_parse_in_start131);
            parse();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "start"



    // $ANTLR start "parse"
    // D:\\workspace\\Master\\src\\CubeSql.g:53:1: parse : ( ( creation_statement )* | sql_query );
    public final void parse() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:53:7: ( ( creation_statement )* | sql_query )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==EOF||LA2_0==CREATE) ) {
                alt2=1;
            }
            else if ( (LA2_0==SELECT) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:53:10: ( creation_statement )*
                    {
                    // D:\\workspace\\Master\\src\\CubeSql.g:53:10: ( creation_statement )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==CREATE) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // D:\\workspace\\Master\\src\\CubeSql.g:53:11: creation_statement
                    	    {
                    	      mode=0;
                    	                name_creation="";
                    	                sql_table="";
                    	                if(dimensionlst.size()>0)  dimensionlst.clear();
                    	                if(hierachylst.size()>0)  hierachylst.clear();
                    	                if(originallvllst.size()>0)  originallvllst.clear();
                    	                if(customlvllst.size()>0)  customlvllst.clear();
                    	               

                    	    pushFollow(FOLLOW_creation_statement_in_parse156);
                    	    creation_statement();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:62:11: sql_query
                    {
                    pushFollow(FOLLOW_sql_query_in_parse172);
                    sql_query();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "parse"



    // $ANTLR start "creation_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:65:1: creation_statement : creation QUESTMARK ;
    public final void creation_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:65:20: ( creation QUESTMARK )
            // D:\\workspace\\Master\\src\\CubeSql.g:65:22: creation QUESTMARK
            {
            pushFollow(FOLLOW_creation_in_creation_statement184);
            creation();

            state._fsp--;


            match(input,QUESTMARK,FOLLOW_QUESTMARK_in_creation_statement186); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "creation_statement"



    // $ANTLR start "creation"
    // D:\\workspace\\Master\\src\\CubeSql.g:67:1: creation : ( creation_cube | creation_dimension );
    public final void creation() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:67:9: ( creation_cube | creation_dimension )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==CREATE) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==CUBE) ) {
                    int LA3_2 = input.LA(3);

                    if ( (LA3_2==NAME) ) {
                        int LA3_4 = input.LA(4);

                        if ( (LA3_4==RELATED) ) {
                            int LA3_6 = input.LA(5);

                            if ( (LA3_6==SQL_TABLE) ) {
                                int LA3_7 = input.LA(6);

                                if ( (LA3_7==NAME) ) {
                                    int LA3_8 = input.LA(7);

                                    if ( (LA3_8==REFERENCES) ) {
                                        alt3=1;
                                    }
                                    else if ( (LA3_8==LIST) ) {
                                        alt3=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 3, 8, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 3, 7, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 6, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 4, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 2, input);

                        throw nvae;

                    }
                }
                else if ( (LA3_1==DIMENSION) ) {
                    int LA3_3 = input.LA(3);

                    if ( (LA3_3==NAME) ) {
                        int LA3_5 = input.LA(4);

                        if ( (LA3_5==RELATED) ) {
                            int LA3_6 = input.LA(5);

                            if ( (LA3_6==SQL_TABLE) ) {
                                int LA3_7 = input.LA(6);

                                if ( (LA3_7==NAME) ) {
                                    int LA3_8 = input.LA(7);

                                    if ( (LA3_8==REFERENCES) ) {
                                        alt3=1;
                                    }
                                    else if ( (LA3_8==LIST) ) {
                                        alt3=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 3, 8, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 3, 7, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 6, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 5, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 3, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:67:11: creation_cube
                    {
                    pushFollow(FOLLOW_creation_cube_in_creation193);
                    creation_cube();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:67:27: creation_dimension
                    {
                    pushFollow(FOLLOW_creation_dimension_in_creation197);
                    creation_dimension();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "creation"



    // $ANTLR start "creation_cube"
    // D:\\workspace\\Master\\src\\CubeSql.g:69:1: creation_cube : create_statement related_statement referdimension_statement ;
    public final void creation_cube() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:69:15: ( create_statement related_statement referdimension_statement )
            // D:\\workspace\\Master\\src\\CubeSql.g:69:17: create_statement related_statement referdimension_statement
            {
            pushFollow(FOLLOW_create_statement_in_creation_cube206);
            create_statement();

            state._fsp--;


            pushFollow(FOLLOW_related_statement_in_creation_cube208);
            related_statement();

            state._fsp--;


            pushFollow(FOLLOW_referdimension_statement_in_creation_cube210);
            referdimension_statement();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "creation_cube"



    // $ANTLR start "creation_dimension"
    // D:\\workspace\\Master\\src\\CubeSql.g:71:1: creation_dimension : create_statement related_statement level_statement hierarchy_statement ;
    public final void creation_dimension() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:71:20: ( create_statement related_statement level_statement hierarchy_statement )
            // D:\\workspace\\Master\\src\\CubeSql.g:71:22: create_statement related_statement level_statement hierarchy_statement
            {
            pushFollow(FOLLOW_create_statement_in_creation_dimension218);
            create_statement();

            state._fsp--;


            pushFollow(FOLLOW_related_statement_in_creation_dimension220);
            related_statement();

            state._fsp--;


            pushFollow(FOLLOW_level_statement_in_creation_dimension222);
            level_statement();

            state._fsp--;


            pushFollow(FOLLOW_hierarchy_statement_in_creation_dimension224);
            hierarchy_statement();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "creation_dimension"



    // $ANTLR start "create_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:73:1: create_statement : ( CREATE CUBE NAME | CREATE DIMENSION NAME );
    public final void create_statement() throws RecognitionException {
        Token NAME1=null;
        Token NAME2=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:73:17: ( CREATE CUBE NAME | CREATE DIMENSION NAME )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CREATE) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==CUBE) ) {
                    alt4=1;
                }
                else if ( (LA4_1==DIMENSION) ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:73:19: CREATE CUBE NAME
                    {
                    match(input,CREATE,FOLLOW_CREATE_in_create_statement231); 

                    match(input,CUBE,FOLLOW_CUBE_in_create_statement233); 

                    mode=1;

                    NAME1=(Token)match(input,NAME,FOLLOW_NAME_in_create_statement237); 

                    name_creation=(NAME1!=null?NAME1.getText():null);

                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:75:19: CREATE DIMENSION NAME
                    {
                    match(input,CREATE,FOLLOW_CREATE_in_create_statement280); 

                    match(input,DIMENSION,FOLLOW_DIMENSION_in_create_statement282); 

                    mode=2;

                    NAME2=(Token)match(input,NAME,FOLLOW_NAME_in_create_statement286); 

                    name_creation=(NAME2!=null?NAME2.getText():null);

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "create_statement"



    // $ANTLR start "related_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:77:1: related_statement : RELATED SQL_TABLE NAME ;
    public final void related_statement() throws RecognitionException {
        Token NAME3=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:77:19: ( RELATED SQL_TABLE NAME )
            // D:\\workspace\\Master\\src\\CubeSql.g:77:21: RELATED SQL_TABLE NAME
            {
            match(input,RELATED,FOLLOW_RELATED_in_related_statement296); 

            match(input,SQL_TABLE,FOLLOW_SQL_TABLE_in_related_statement298); 

            NAME3=(Token)match(input,NAME,FOLLOW_NAME_in_related_statement300); 

            sql_table=(NAME3!=null?NAME3.getText():null);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "related_statement"



    // $ANTLR start "referdimension_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:79:1: referdimension_statement : REFERENCES DIMENSION dimensions ;
    public final void referdimension_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:79:26: ( REFERENCES DIMENSION dimensions )
            // D:\\workspace\\Master\\src\\CubeSql.g:79:28: REFERENCES DIMENSION dimensions
            {
            match(input,REFERENCES,FOLLOW_REFERENCES_in_referdimension_statement311); 

            match(input,DIMENSION,FOLLOW_DIMENSION_in_referdimension_statement313); 

            pushFollow(FOLLOW_dimensions_in_referdimension_statement315);
            dimensions();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "referdimension_statement"



    // $ANTLR start "level_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:81:1: level_statement : LIST OF LEVEL LBRACE levels RBRACE ;
    public final void level_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:81:16: ( LIST OF LEVEL LBRACE levels RBRACE )
            // D:\\workspace\\Master\\src\\CubeSql.g:81:18: LIST OF LEVEL LBRACE levels RBRACE
            {
            match(input,LIST,FOLLOW_LIST_in_level_statement322); 

            match(input,OF,FOLLOW_OF_in_level_statement324); 

            match(input,LEVEL,FOLLOW_LEVEL_in_level_statement326); 

            match(input,LBRACE,FOLLOW_LBRACE_in_level_statement328); 

            pushFollow(FOLLOW_levels_in_level_statement330);
            levels();

            state._fsp--;


            match(input,RBRACE,FOLLOW_RBRACE_in_level_statement332); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "level_statement"



    // $ANTLR start "hierarchy_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:83:1: hierarchy_statement : HIERARCHY hierarchy ;
    public final void hierarchy_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:83:21: ( HIERARCHY hierarchy )
            // D:\\workspace\\Master\\src\\CubeSql.g:83:23: HIERARCHY hierarchy
            {
            match(input,HIERARCHY,FOLLOW_HIERARCHY_in_hierarchy_statement341); 

            pushFollow(FOLLOW_hierarchy_in_hierarchy_statement343);
            hierarchy();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "hierarchy_statement"



    // $ANTLR start "dimensions"
    // D:\\workspace\\Master\\src\\CubeSql.g:85:1: dimensions : dimension ( comma_statement dimension )* ;
    public final void dimensions() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:85:12: ( dimension ( comma_statement dimension )* )
            // D:\\workspace\\Master\\src\\CubeSql.g:85:14: dimension ( comma_statement dimension )*
            {
            pushFollow(FOLLOW_dimension_in_dimensions351);
            dimension();

            state._fsp--;


            // D:\\workspace\\Master\\src\\CubeSql.g:85:25: ( comma_statement dimension )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA||LA5_0==WS) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:85:26: comma_statement dimension
            	    {
            	    pushFollow(FOLLOW_comma_statement_in_dimensions355);
            	    comma_statement();

            	    state._fsp--;


            	    pushFollow(FOLLOW_dimension_in_dimensions357);
            	    dimension();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "dimensions"



    // $ANTLR start "dimension"
    // D:\\workspace\\Master\\src\\CubeSql.g:87:1: dimension : NAME AT sqlfield ;
    public final void dimension() throws RecognitionException {
        Token NAME4=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:87:11: ( NAME AT sqlfield )
            // D:\\workspace\\Master\\src\\CubeSql.g:87:13: NAME AT sqlfield
            {
            NAME4=(Token)match(input,NAME,FOLLOW_NAME_in_dimension368); 

            dimensionlst.add((NAME4!=null?NAME4.getText():null));

            match(input,AT,FOLLOW_AT_in_dimension372); 

            pushFollow(FOLLOW_sqlfield_in_dimension374);
            sqlfield();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "dimension"



    // $ANTLR start "levels"
    // D:\\workspace\\Master\\src\\CubeSql.g:89:1: levels : level ( comma_statement level )* ;
    public final void levels() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:89:7: ( level ( comma_statement level )* )
            // D:\\workspace\\Master\\src\\CubeSql.g:89:9: level ( comma_statement level )*
            {
            pushFollow(FOLLOW_level_in_levels381);
            level();

            state._fsp--;


            // D:\\workspace\\Master\\src\\CubeSql.g:89:15: ( comma_statement level )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA||LA6_0==WS) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:89:16: comma_statement level
            	    {
            	    pushFollow(FOLLOW_comma_statement_in_levels384);
            	    comma_statement();

            	    state._fsp--;


            	    pushFollow(FOLLOW_level_in_levels386);
            	    level();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "levels"



    // $ANTLR start "comma_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:91:1: comma_statement : ( WS )* COMMA ( WS )* ;
    public final void comma_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:91:16: ( ( WS )* COMMA ( WS )* )
            // D:\\workspace\\Master\\src\\CubeSql.g:91:18: ( WS )* COMMA ( WS )*
            {
            // D:\\workspace\\Master\\src\\CubeSql.g:91:18: ( WS )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==WS) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:91:18: WS
            	    {
            	    match(input,WS,FOLLOW_WS_in_comma_statement395); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            match(input,COMMA,FOLLOW_COMMA_in_comma_statement398); 

            // D:\\workspace\\Master\\src\\CubeSql.g:91:28: ( WS )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==WS) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:91:28: WS
            	    {
            	    match(input,WS,FOLLOW_WS_in_comma_statement400); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "comma_statement"



    // $ANTLR start "level"
    // D:\\workspace\\Master\\src\\CubeSql.g:93:1: level : ( sqlfield AS name3= NAME | sqlfield );
    public final void level() throws RecognitionException {
        Token name3=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:93:7: ( sqlfield AS name3= NAME | sqlfield )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==NAME) ) {
                switch ( input.LA(2) ) {
                case DOT:
                    {
                    int LA9_2 = input.LA(3);

                    if ( (LA9_2==NAME) ) {
                        int LA9_5 = input.LA(4);

                        if ( (LA9_5==AS) ) {
                            alt9=1;
                        }
                        else if ( (LA9_5==COMMA||LA9_5==RBRACE||LA9_5==WS) ) {
                            alt9=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 9, 5, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 2, input);

                        throw nvae;

                    }
                    }
                    break;
                case AS:
                    {
                    alt9=1;
                    }
                    break;
                case COMMA:
                case RBRACE:
                case WS:
                    {
                    alt9=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:93:10: sqlfield AS name3= NAME
                    {
                    pushFollow(FOLLOW_sqlfield_in_level410);
                    sqlfield();

                    state._fsp--;


                    match(input,AS,FOLLOW_AS_in_level412); 

                    name3=(Token)match(input,NAME,FOLLOW_NAME_in_level416); 

                    customlvllst.add((name3!=null?name3.getText():null));

                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:94:11: sqlfield
                    {
                    pushFollow(FOLLOW_sqlfield_in_level430);
                    sqlfield();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "level"



    // $ANTLR start "hierarchy"
    // D:\\workspace\\Master\\src\\CubeSql.g:96:1: hierarchy : name1= NAME ( CHILDOF name2= NAME )+ ;
    public final void hierarchy() throws RecognitionException {
        Token name1=null;
        Token name2=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:96:10: (name1= NAME ( CHILDOF name2= NAME )+ )
            // D:\\workspace\\Master\\src\\CubeSql.g:96:12: name1= NAME ( CHILDOF name2= NAME )+
            {
            name1=(Token)match(input,NAME,FOLLOW_NAME_in_hierarchy440); 

            dimensionlst.add((name1!=null?name1.getText():null));

            // D:\\workspace\\Master\\src\\CubeSql.g:96:56: ( CHILDOF name2= NAME )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==CHILDOF) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:96:57: CHILDOF name2= NAME
            	    {
            	    match(input,CHILDOF,FOLLOW_CHILDOF_in_hierarchy445); 

            	    name2=(Token)match(input,NAME,FOLLOW_NAME_in_hierarchy449); 

            	    dimensionlst.add((name2!=null?name2.getText():null));

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "hierarchy"



    // $ANTLR start "sqlfield"
    // D:\\workspace\\Master\\src\\CubeSql.g:98:1: sqlfield : (name1= NAME DOT name2= NAME | NAME );
    public final void sqlfield() throws RecognitionException {
        Token name1=null;
        Token name2=null;
        Token NAME5=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:98:10: (name1= NAME DOT name2= NAME | NAME )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==NAME) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==DOT) ) {
                    alt11=1;
                }
                else if ( (LA11_1==EOF||(LA11_1 >= AND && LA11_1 <= AS)||(LA11_1 >= CHILDOF && LA11_1 <= COMMA)||LA11_1==GROUP||LA11_1==OR||LA11_1==QUESTMARK||LA11_1==RBRACE||LA11_1==WS||(LA11_1 >= 68 && LA11_1 <= 73)) ) {
                    alt11=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:98:12: name1= NAME DOT name2= NAME
                    {
                    name1=(Token)match(input,NAME,FOLLOW_NAME_in_sqlfield464); 

                    match(input,DOT,FOLLOW_DOT_in_sqlfield466); 

                    name2=(Token)match(input,NAME,FOLLOW_NAME_in_sqlfield470); 

                     if(mode==1 || mode==2) originallvllst.add((name1!=null?name1.getText():null)+"."+(name2!=null?name2.getText():null));
                                                          tmp_con+=(name1!=null?name1.getText():null)+"."+(name2!=null?name2.getText():null);


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:101:11: NAME
                    {
                    NAME5=(Token)match(input,NAME,FOLLOW_NAME_in_sqlfield484); 

                    tmp_con+= (NAME5!=null?NAME5.getText():null);if(mode==1 || mode==2) {originallvllst.add((NAME5!=null?NAME5.getText():null));}

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "sqlfield"



    // $ANTLR start "sql_query"
    // D:\\workspace\\Master\\src\\CubeSql.g:104:1: sql_query : select_statement from_statement where_statement group_statement ;
    public final void sql_query() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:104:10: ( select_statement from_statement where_statement group_statement )
            // D:\\workspace\\Master\\src\\CubeSql.g:104:12: select_statement from_statement where_statement group_statement
            {
            pushFollow(FOLLOW_select_statement_in_sql_query502);
            select_statement();

            state._fsp--;


            pushFollow(FOLLOW_from_statement_in_sql_query516);
            from_statement();

            state._fsp--;


            pushFollow(FOLLOW_where_statement_in_sql_query529);
            where_statement();

            state._fsp--;


            pushFollow(FOLLOW_group_statement_in_sql_query542);
            group_statement();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "sql_query"



    // $ANTLR start "select_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:109:1: select_statement : SELECT grouppers COMMA aggregate_statement ;
    public final void select_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:109:17: ( SELECT grouppers COMMA aggregate_statement )
            // D:\\workspace\\Master\\src\\CubeSql.g:109:19: SELECT grouppers COMMA aggregate_statement
            {
            match(input,SELECT,FOLLOW_SELECT_in_select_statement560); 

            pushFollow(FOLLOW_grouppers_in_select_statement562);
            grouppers();

            state._fsp--;


            match(input,COMMA,FOLLOW_COMMA_in_select_statement564); 

            pushFollow(FOLLOW_aggregate_statement_in_select_statement566);
            aggregate_statement();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "select_statement"



    // $ANTLR start "aggregate_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:111:1: aggregate_statement : aggregate_func '(' sqlfield ')' ;
    public final void aggregate_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:111:21: ( aggregate_func '(' sqlfield ')' )
            // D:\\workspace\\Master\\src\\CubeSql.g:111:23: aggregate_func '(' sqlfield ')'
            {
            tmp_con="";

            pushFollow(FOLLOW_aggregate_func_in_aggregate_statement576);
            aggregate_func();

            state._fsp--;


            match(input,67,FOLLOW_67_in_aggregate_statement578); 

            pushFollow(FOLLOW_sqlfield_in_aggregate_statement580);
            sqlfield();

            state._fsp--;


            match(input,68,FOLLOW_68_in_aggregate_statement582); 

            aggregatefunc=tmp_con;tmp_con="";

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "aggregate_statement"



    // $ANTLR start "aggregate_func"
    // D:\\workspace\\Master\\src\\CubeSql.g:113:1: aggregate_func : ( SUM | AVG | COUNT | MAX | MIN );
    public final void aggregate_func() throws RecognitionException {
        Token SUM6=null;
        Token AVG7=null;
        Token COUNT8=null;
        Token MAX9=null;
        Token MIN10=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:113:16: ( SUM | AVG | COUNT | MAX | MIN )
            int alt12=5;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt12=1;
                }
                break;
            case AVG:
                {
                alt12=2;
                }
                break;
            case COUNT:
                {
                alt12=3;
                }
                break;
            case MAX:
                {
                alt12=4;
                }
                break;
            case MIN:
                {
                alt12=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }

            switch (alt12) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:113:18: SUM
                    {
                    SUM6=(Token)match(input,SUM,FOLLOW_SUM_in_aggregate_func592); 

                    tmp_con=(SUM6!=null?SUM6.getText():null)+"~";

                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:114:19: AVG
                    {
                    AVG7=(Token)match(input,AVG,FOLLOW_AVG_in_aggregate_func614); 

                    tmp_con=(AVG7!=null?AVG7.getText():null)+"~";

                    }
                    break;
                case 3 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:115:19: COUNT
                    {
                    COUNT8=(Token)match(input,COUNT,FOLLOW_COUNT_in_aggregate_func637); 

                    tmp_con=(COUNT8!=null?COUNT8.getText():null)+"~";

                    }
                    break;
                case 4 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:116:19: MAX
                    {
                    MAX9=(Token)match(input,MAX,FOLLOW_MAX_in_aggregate_func659); 

                    tmp_con=(MAX9!=null?MAX9.getText():null)+"~";

                    }
                    break;
                case 5 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:117:19: MIN
                    {
                    MIN10=(Token)match(input,MIN,FOLLOW_MIN_in_aggregate_func681); 

                    tmp_con=(MIN10!=null?MIN10.getText():null)+"~";

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "aggregate_func"



    // $ANTLR start "from_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:119:1: from_statement : FROM tables ;
    public final void from_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:119:16: ( FROM tables )
            // D:\\workspace\\Master\\src\\CubeSql.g:119:18: FROM tables
            {
            match(input,FROM,FOLLOW_FROM_in_from_statement691); 

            pushFollow(FOLLOW_tables_in_from_statement693);
            tables();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "from_statement"



    // $ANTLR start "tables"
    // D:\\workspace\\Master\\src\\CubeSql.g:121:1: tables : table ( COMMA table )* ;
    public final void tables() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:121:8: ( table ( COMMA table )* )
            // D:\\workspace\\Master\\src\\CubeSql.g:121:10: table ( COMMA table )*
            {
            pushFollow(FOLLOW_table_in_tables701);
            table();

            state._fsp--;


            // D:\\workspace\\Master\\src\\CubeSql.g:121:16: ( COMMA table )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==COMMA) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:121:17: COMMA table
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tables704); 

            	    pushFollow(FOLLOW_table_in_tables706);
            	    table();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "tables"



    // $ANTLR start "table"
    // D:\\workspace\\Master\\src\\CubeSql.g:123:1: table : (name1= NAME AS name2= NAME |name1= NAME name2= NAME | NAME );
    public final void table() throws RecognitionException {
        Token name1=null;
        Token name2=null;
        Token NAME11=null;

        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:123:7: (name1= NAME AS name2= NAME |name1= NAME name2= NAME | NAME )
            int alt14=3;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==NAME) ) {
                switch ( input.LA(2) ) {
                case AS:
                    {
                    alt14=1;
                    }
                    break;
                case NAME:
                    {
                    alt14=2;
                    }
                    break;
                case COMMA:
                case GROUP:
                case WHERE:
                    {
                    alt14=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;

            }
            switch (alt14) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:123:9: name1= NAME AS name2= NAME
                    {
                    name1=(Token)match(input,NAME,FOLLOW_NAME_in_table718); 

                    match(input,AS,FOLLOW_AS_in_table720); 

                    name2=(Token)match(input,NAME,FOLLOW_NAME_in_table724); 

                    tablelst.add((name1!=null?name1.getText():null)+"~"+(name2!=null?name2.getText():null));

                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:124:9: name1= NAME name2= NAME
                    {
                    name1=(Token)match(input,NAME,FOLLOW_NAME_in_table739); 

                    name2=(Token)match(input,NAME,FOLLOW_NAME_in_table743); 

                    tablelst.add((name1!=null?name1.getText():null)+"~"+(name2!=null?name2.getText():null));

                    }
                    break;
                case 3 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:125:9: NAME
                    {
                    NAME11=(Token)match(input,NAME,FOLLOW_NAME_in_table755); 

                    tablelst.add((NAME11!=null?NAME11.getText():null));

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "table"



    // $ANTLR start "where_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:127:1: where_statement : ( WHERE cond_statement ( boolean_expr cond_statement )* |);
    public final void where_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:127:17: ( WHERE cond_statement ( boolean_expr cond_statement )* |)
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE) ) {
                alt16=1;
            }
            else if ( (LA16_0==GROUP) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:127:19: WHERE cond_statement ( boolean_expr cond_statement )*
                    {
                    match(input,WHERE,FOLLOW_WHERE_in_where_statement765); 

                    tmp_con="";

                    pushFollow(FOLLOW_cond_statement_in_where_statement769);
                    cond_statement();

                    state._fsp--;


                    conditionlst.add(tmp_con);

                    // D:\\workspace\\Master\\src\\CubeSql.g:127:83: ( boolean_expr cond_statement )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==AND||LA15_0==OR) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // D:\\workspace\\Master\\src\\CubeSql.g:127:84: boolean_expr cond_statement
                    	    {
                    	    pushFollow(FOLLOW_boolean_expr_in_where_statement774);
                    	    boolean_expr();

                    	    state._fsp--;


                    	    tmp_con="";

                    	    pushFollow(FOLLOW_cond_statement_in_where_statement778);
                    	    cond_statement();

                    	    state._fsp--;


                    	    conditionlst.add(tmp_con);

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:128:21: 
                    {
                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "where_statement"



    // $ANTLR start "cond_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:129:1: cond_statement : ( sqlfield operator sqlfield | sqlfield operator quote_statement NAME quote_statement | sqlfield operator quote_statement ( Digit )+ NAME quote_statement | sqlfield operator ( Digit )+ | sqlfield operator quote_statement quote_statement );
    public final void cond_statement() throws RecognitionException {
        Token NAME14=null;
        Token Digit16=null;
        Token NAME17=null;
        Token Digit19=null;
        CubeSqlParser.operator_return operator12 =null;

        CubeSqlParser.operator_return operator13 =null;

        CubeSqlParser.operator_return operator15 =null;

        CubeSqlParser.operator_return operator18 =null;

        CubeSqlParser.operator_return operator20 =null;


        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:129:16: ( sqlfield operator sqlfield | sqlfield operator quote_statement NAME quote_statement | sqlfield operator quote_statement ( Digit )+ NAME quote_statement | sqlfield operator ( Digit )+ | sqlfield operator quote_statement quote_statement )
            int alt19=5;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==NAME) ) {
                int LA19_1 = input.LA(2);

                if ( (LA19_1==DOT) ) {
                    int LA19_2 = input.LA(3);

                    if ( (LA19_2==NAME) ) {
                        int LA19_4 = input.LA(4);

                        if ( (LA19_4==CHILDOF||(LA19_4 >= 69 && LA19_4 <= 73)) ) {
                            switch ( input.LA(5) ) {
                            case NAME:
                                {
                                alt19=1;
                                }
                                break;
                            case 66:
                            case 74:
                                {
                                switch ( input.LA(6) ) {
                                case NAME:
                                    {
                                    alt19=2;
                                    }
                                    break;
                                case Digit:
                                    {
                                    alt19=3;
                                    }
                                    break;
                                case 66:
                                case 74:
                                    {
                                    alt19=5;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 19, 6, input);

                                    throw nvae;

                                }

                                }
                                break;
                            case Digit:
                                {
                                alt19=4;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 19, 3, input);

                                throw nvae;

                            }

                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 19, 4, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 2, input);

                        throw nvae;

                    }
                }
                else if ( (LA19_1==CHILDOF||(LA19_1 >= 69 && LA19_1 <= 73)) ) {
                    switch ( input.LA(3) ) {
                    case NAME:
                        {
                        alt19=1;
                        }
                        break;
                    case 66:
                    case 74:
                        {
                        switch ( input.LA(4) ) {
                        case NAME:
                            {
                            alt19=2;
                            }
                            break;
                        case Digit:
                            {
                            alt19=3;
                            }
                            break;
                        case 66:
                        case 74:
                            {
                            alt19=5;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 19, 6, input);

                            throw nvae;

                        }

                        }
                        break;
                    case Digit:
                        {
                        alt19=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 3, input);

                        throw nvae;

                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }
            switch (alt19) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:129:18: sqlfield operator sqlfield
                    {
                    pushFollow(FOLLOW_sqlfield_in_cond_statement810);
                    sqlfield();

                    state._fsp--;


                    pushFollow(FOLLOW_operator_in_cond_statement812);
                    operator12=operator();

                    state._fsp--;


                    tmp_con+="~"+(operator12!=null?input.toString(operator12.start,operator12.stop):null)+"~";

                    pushFollow(FOLLOW_sqlfield_in_cond_statement816);
                    sqlfield();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:130:19: sqlfield operator quote_statement NAME quote_statement
                    {
                    pushFollow(FOLLOW_sqlfield_in_cond_statement837);
                    sqlfield();

                    state._fsp--;


                    pushFollow(FOLLOW_operator_in_cond_statement839);
                    operator13=operator();

                    state._fsp--;


                    tmp_con+="~"+(operator13!=null?input.toString(operator13.start,operator13.stop):null)+"~";

                    pushFollow(FOLLOW_quote_statement_in_cond_statement844);
                    quote_statement();

                    state._fsp--;


                    NAME14=(Token)match(input,NAME,FOLLOW_NAME_in_cond_statement846); 

                    pushFollow(FOLLOW_quote_statement_in_cond_statement848);
                    quote_statement();

                    state._fsp--;


                    tmp_con+="'"+(NAME14!=null?NAME14.getText():null)+"'";

                    }
                    break;
                case 3 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:131:19: sqlfield operator quote_statement ( Digit )+ NAME quote_statement
                    {
                    pushFollow(FOLLOW_sqlfield_in_cond_statement870);
                    sqlfield();

                    state._fsp--;


                    pushFollow(FOLLOW_operator_in_cond_statement872);
                    operator15=operator();

                    state._fsp--;


                    tmp_con+="~"+(operator15!=null?input.toString(operator15.start,operator15.stop):null)+"~";

                    pushFollow(FOLLOW_quote_statement_in_cond_statement877);
                    quote_statement();

                    state._fsp--;


                    // D:\\workspace\\Master\\src\\CubeSql.g:131:89: ( Digit )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==Digit) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // D:\\workspace\\Master\\src\\CubeSql.g:131:90: Digit
                    	    {
                    	    Digit16=(Token)match(input,Digit,FOLLOW_Digit_in_cond_statement880); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);


                    NAME17=(Token)match(input,NAME,FOLLOW_NAME_in_cond_statement884); 

                    pushFollow(FOLLOW_quote_statement_in_cond_statement887);
                    quote_statement();

                    state._fsp--;


                    tmp_con+="'"+(Digit16!=null?Digit16.getText():null)+(NAME17!=null?NAME17.getText():null)+"'";

                    }
                    break;
                case 4 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:132:19: sqlfield operator ( Digit )+
                    {
                    pushFollow(FOLLOW_sqlfield_in_cond_statement909);
                    sqlfield();

                    state._fsp--;


                    pushFollow(FOLLOW_operator_in_cond_statement911);
                    operator18=operator();

                    state._fsp--;


                    tmp_con+="~"+(operator18!=null?input.toString(operator18.start,operator18.stop):null)+"~";

                    // D:\\workspace\\Master\\src\\CubeSql.g:132:72: ( Digit )+
                    int cnt18=0;
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==Digit) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // D:\\workspace\\Master\\src\\CubeSql.g:132:73: Digit
                    	    {
                    	    Digit19=(Token)match(input,Digit,FOLLOW_Digit_in_cond_statement916); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt18 >= 1 ) break loop18;
                                EarlyExitException eee =
                                    new EarlyExitException(18, input);
                                throw eee;
                        }
                        cnt18++;
                    } while (true);


                    tmp_con+=(Digit19!=null?Digit19.getText():null);

                    }
                    break;
                case 5 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:133:19: sqlfield operator quote_statement quote_statement
                    {
                    pushFollow(FOLLOW_sqlfield_in_cond_statement940);
                    sqlfield();

                    state._fsp--;


                    pushFollow(FOLLOW_operator_in_cond_statement942);
                    operator20=operator();

                    state._fsp--;


                    tmp_con+="~"+(operator20!=null?input.toString(operator20.start,operator20.stop):null)+"~";

                    pushFollow(FOLLOW_quote_statement_in_cond_statement947);
                    quote_statement();

                    state._fsp--;


                    pushFollow(FOLLOW_quote_statement_in_cond_statement950);
                    quote_statement();

                    state._fsp--;


                    tmp_con+="'"+"'";

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "cond_statement"



    // $ANTLR start "boolean_expr"
    // D:\\workspace\\Master\\src\\CubeSql.g:136:1: boolean_expr : ( OR | AND );
    public final void boolean_expr() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:136:14: ( OR | AND )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)==AND||input.LA(1)==OR ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "boolean_expr"



    // $ANTLR start "quote_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:138:1: quote_statement : ( '\"' | '\\'' );
    public final void quote_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:138:16: ( '\"' | '\\'' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)==66||input.LA(1)==74 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "quote_statement"


    public static class operator_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "operator"
    // D:\\workspace\\Master\\src\\CubeSql.g:140:1: operator : ( '=' | '>=' | '<=' | '>' | '<' | 'LIKE' );
    public final CubeSqlParser.operator_return operator() throws RecognitionException {
        CubeSqlParser.operator_return retval = new CubeSqlParser.operator_return();
        retval.start = input.LT(1);


        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:140:10: ( '=' | '>=' | '<=' | '>' | '<' | 'LIKE' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)==CHILDOF||(input.LA(1) >= 69 && input.LA(1) <= 73) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "operator"



    // $ANTLR start "group_statement"
    // D:\\workspace\\Master\\src\\CubeSql.g:147:1: group_statement : GROUP BY grouppers ;
    public final void group_statement() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:147:17: ( GROUP BY grouppers )
            // D:\\workspace\\Master\\src\\CubeSql.g:147:19: GROUP BY grouppers
            {
            match(input,GROUP,FOLLOW_GROUP_in_group_statement1098); 

            match(input,BY,FOLLOW_BY_in_group_statement1100); 

            group=true;

            pushFollow(FOLLOW_grouppers_in_group_statement1104);
            grouppers();

            state._fsp--;


            group=false;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "group_statement"



    // $ANTLR start "grouppers"
    // D:\\workspace\\Master\\src\\CubeSql.g:149:1: grouppers : sqlfield ( comma_statement sqlfield ) ;
    public final void grouppers() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:149:11: ( sqlfield ( comma_statement sqlfield ) )
            // D:\\workspace\\Master\\src\\CubeSql.g:149:13: sqlfield ( comma_statement sqlfield )
            {
            tmp_con="";

            pushFollow(FOLLOW_sqlfield_in_grouppers1116);
            sqlfield();

            state._fsp--;


            if(group){ groupperlst.add(tmp_con);}

            // D:\\workspace\\Master\\src\\CubeSql.g:149:76: ( comma_statement sqlfield )
            // D:\\workspace\\Master\\src\\CubeSql.g:149:77: comma_statement sqlfield
            {
            pushFollow(FOLLOW_comma_statement_in_grouppers1121);
            comma_statement();

            state._fsp--;


            tmp_con="";

            pushFollow(FOLLOW_sqlfield_in_grouppers1125);
            sqlfield();

            state._fsp--;


            if(group)groupperlst.add(tmp_con);

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "grouppers"

    // Delegated rules


 

    public static final BitSet FOLLOW_parse_in_start131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_creation_statement_in_parse156 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_sql_query_in_parse172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_creation_in_creation_statement184 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_QUESTMARK_in_creation_statement186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_creation_cube_in_creation193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_creation_dimension_in_creation197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_statement_in_creation_cube206 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_related_statement_in_creation_cube208 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_referdimension_statement_in_creation_cube210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_statement_in_creation_dimension218 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_related_statement_in_creation_dimension220 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_level_statement_in_creation_dimension222 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_hierarchy_statement_in_creation_dimension224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_statement231 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_CUBE_in_create_statement233 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_create_statement237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_in_create_statement280 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DIMENSION_in_create_statement282 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_create_statement286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RELATED_in_related_statement296 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_SQL_TABLE_in_related_statement298 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_related_statement300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REFERENCES_in_referdimension_statement311 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DIMENSION_in_referdimension_statement313 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_dimensions_in_referdimension_statement315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LIST_in_level_statement322 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_OF_in_level_statement324 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_LEVEL_in_level_statement326 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACE_in_level_statement328 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_levels_in_level_statement330 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_level_statement332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HIERARCHY_in_hierarchy_statement341 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_hierarchy_in_hierarchy_statement343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dimension_in_dimensions351 = new BitSet(new long[]{0x4000000000002002L});
    public static final BitSet FOLLOW_comma_statement_in_dimensions355 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_dimension_in_dimensions357 = new BitSet(new long[]{0x4000000000002002L});
    public static final BitSet FOLLOW_NAME_in_dimension368 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_AT_in_dimension372 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_sqlfield_in_dimension374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_level_in_levels381 = new BitSet(new long[]{0x4000000000002002L});
    public static final BitSet FOLLOW_comma_statement_in_levels384 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_level_in_levels386 = new BitSet(new long[]{0x4000000000002002L});
    public static final BitSet FOLLOW_WS_in_comma_statement395 = new BitSet(new long[]{0x4000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_comma_statement398 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_WS_in_comma_statement400 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_level410 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_level412 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_level416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_level430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_hierarchy440 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_CHILDOF_in_hierarchy445 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_hierarchy449 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_NAME_in_sqlfield464 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DOT_in_sqlfield466 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_sqlfield470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_sqlfield484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_statement_in_sql_query502 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_from_statement_in_sql_query516 = new BitSet(new long[]{0x2000000004000000L});
    public static final BitSet FOLLOW_where_statement_in_sql_query529 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_group_statement_in_sql_query542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select_statement560 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_grouppers_in_select_statement562 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_select_statement564 = new BitSet(new long[]{0x008000C000004100L});
    public static final BitSet FOLLOW_aggregate_statement_in_select_statement566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aggregate_func_in_aggregate_statement576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_aggregate_statement578 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_sqlfield_in_aggregate_statement580 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_aggregate_statement582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SUM_in_aggregate_func592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AVG_in_aggregate_func614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COUNT_in_aggregate_func637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAX_in_aggregate_func659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_aggregate_func681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FROM_in_from_statement691 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_tables_in_from_statement693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_in_tables701 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_tables704 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_table_in_tables706 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_NAME_in_table718 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_table720 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_table724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_table739 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_table743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_table755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHERE_in_where_statement765 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_cond_statement_in_where_statement769 = new BitSet(new long[]{0x0000100000000022L});
    public static final BitSet FOLLOW_boolean_expr_in_where_statement774 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_cond_statement_in_where_statement778 = new BitSet(new long[]{0x0000100000000022L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement810 = new BitSet(new long[]{0x0000000000001000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_operator_in_cond_statement812 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement837 = new BitSet(new long[]{0x0000000000001000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_operator_in_cond_statement839 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement844 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_cond_statement846 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement870 = new BitSet(new long[]{0x0000000000001000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_operator_in_cond_statement872 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement877 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_Digit_in_cond_statement880 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_NAME_in_cond_statement884 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement909 = new BitSet(new long[]{0x0000000000001000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_operator_in_cond_statement911 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_Digit_in_cond_statement916 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_sqlfield_in_cond_statement940 = new BitSet(new long[]{0x0000000000001000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_operator_in_cond_statement942 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement947 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_quote_statement_in_cond_statement950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GROUP_in_group_statement1098 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_BY_in_group_statement1100 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_grouppers_in_group_statement1104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sqlfield_in_grouppers1116 = new BitSet(new long[]{0x4000000000002000L});
    public static final BitSet FOLLOW_comma_statement_in_grouppers1121 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_sqlfield_in_grouppers1125 = new BitSet(new long[]{0x0000000000000002L});

}