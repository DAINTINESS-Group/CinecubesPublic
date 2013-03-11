// $ANTLR 3.4 D:\\workspace\\Master\\src\\CubeSql.g 2013-03-11 22:55:31

  package ParserMgr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CubeSqlLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public CubeSqlLexer() {} 
    public CubeSqlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CubeSqlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "D:\\workspace\\Master\\src\\CubeSql.g"; }

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:6:7: ( '\"' )
            // D:\\workspace\\Master\\src\\CubeSql.g:6:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:7:7: ( '(' )
            // D:\\workspace\\Master\\src\\CubeSql.g:7:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:8:7: ( ')' )
            // D:\\workspace\\Master\\src\\CubeSql.g:8:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:9:7: ( '<' )
            // D:\\workspace\\Master\\src\\CubeSql.g:9:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:10:7: ( '<=' )
            // D:\\workspace\\Master\\src\\CubeSql.g:10:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:11:7: ( '=' )
            // D:\\workspace\\Master\\src\\CubeSql.g:11:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:12:7: ( '>=' )
            // D:\\workspace\\Master\\src\\CubeSql.g:12:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:13:7: ( 'LIKE' )
            // D:\\workspace\\Master\\src\\CubeSql.g:13:9: 'LIKE'
            {
            match("LIKE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:149:4: ( 'OR' | O R )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='O') ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='R') ) {
                    alt1=1;
                }
                else if ( (LA1_1=='r') ) {
                    alt1=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA1_0=='o') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:149:6: 'OR'
                    {
                    match("OR"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:149:13: O R
                    {
                    mO(); 


                    mR(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:151:5: ( 'AND' | A N D )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='A') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='N') ) {
                    int LA2_3 = input.LA(3);

                    if ( (LA2_3=='D') ) {
                        alt2=1;
                    }
                    else if ( (LA2_3=='d') ) {
                        alt2=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA2_1=='n') ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA2_0=='a') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:151:7: 'AND'
                    {
                    match("AND"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:151:15: A N D
                    {
                    mA(); 


                    mN(); 


                    mD(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "CREATE"
    public final void mCREATE() throws RecognitionException {
        try {
            int _type = CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:153:7: ( 'CREATE' | C R E A T E )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='C') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='R') ) {
                    int LA3_3 = input.LA(3);

                    if ( (LA3_3=='E') ) {
                        int LA3_4 = input.LA(4);

                        if ( (LA3_4=='A') ) {
                            int LA3_5 = input.LA(5);

                            if ( (LA3_5=='T') ) {
                                int LA3_6 = input.LA(6);

                                if ( (LA3_6=='E') ) {
                                    alt3=1;
                                }
                                else if ( (LA3_6=='e') ) {
                                    alt3=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 3, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA3_5=='t') ) {
                                alt3=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA3_4=='a') ) {
                            alt3=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA3_3=='e') ) {
                        alt3=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA3_1=='r') ) {
                    alt3=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA3_0=='c') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:153:9: 'CREATE'
                    {
                    match("CREATE"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:153:20: C R E A T E
                    {
                    mC(); 


                    mR(); 


                    mE(); 


                    mA(); 


                    mT(); 


                    mE(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CREATE"

    // $ANTLR start "CUBE"
    public final void mCUBE() throws RecognitionException {
        try {
            int _type = CUBE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:155:6: ( 'CUBE' | C U B E )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='C') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='U') ) {
                    int LA4_3 = input.LA(3);

                    if ( (LA4_3=='B') ) {
                        int LA4_4 = input.LA(4);

                        if ( (LA4_4=='E') ) {
                            alt4=1;
                        }
                        else if ( (LA4_4=='e') ) {
                            alt4=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA4_3=='b') ) {
                        alt4=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA4_1=='u') ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA4_0=='c') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:155:9: 'CUBE'
                    {
                    match("CUBE"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:155:18: C U B E
                    {
                    mC(); 


                    mU(); 


                    mB(); 


                    mE(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CUBE"

    // $ANTLR start "RELATED"
    public final void mRELATED() throws RecognitionException {
        try {
            int _type = RELATED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:157:9: ( 'RELATED' | R E L A T E D )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='R') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='E') ) {
                    int LA5_3 = input.LA(3);

                    if ( (LA5_3=='L') ) {
                        int LA5_4 = input.LA(4);

                        if ( (LA5_4=='A') ) {
                            int LA5_5 = input.LA(5);

                            if ( (LA5_5=='T') ) {
                                int LA5_6 = input.LA(6);

                                if ( (LA5_6=='E') ) {
                                    int LA5_7 = input.LA(7);

                                    if ( (LA5_7=='D') ) {
                                        alt5=1;
                                    }
                                    else if ( (LA5_7=='d') ) {
                                        alt5=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 5, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA5_6=='e') ) {
                                    alt5=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 5, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA5_5=='t') ) {
                                alt5=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 5, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA5_4=='a') ) {
                            alt5=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 5, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA5_3=='l') ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA5_1=='e') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA5_0=='r') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:157:11: 'RELATED'
                    {
                    match("RELATED"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:157:23: R E L A T E D
                    {
                    mR(); 


                    mE(); 


                    mL(); 


                    mA(); 


                    mT(); 


                    mE(); 


                    mD(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RELATED"

    // $ANTLR start "SQL_TABLE"
    public final void mSQL_TABLE() throws RecognitionException {
        try {
            int _type = SQL_TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:159:10: ( 'SQL_TABLE' | S Q L UNDERSCORE T A B L E )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='S') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='Q') ) {
                    int LA6_3 = input.LA(3);

                    if ( (LA6_3=='L') ) {
                        int LA6_4 = input.LA(4);

                        if ( (LA6_4=='_') ) {
                            int LA6_5 = input.LA(5);

                            if ( (LA6_5=='T') ) {
                                int LA6_6 = input.LA(6);

                                if ( (LA6_6=='A') ) {
                                    int LA6_7 = input.LA(7);

                                    if ( (LA6_7=='B') ) {
                                        int LA6_8 = input.LA(8);

                                        if ( (LA6_8=='L') ) {
                                            int LA6_9 = input.LA(9);

                                            if ( (LA6_9=='E') ) {
                                                alt6=1;
                                            }
                                            else if ( (LA6_9=='e') ) {
                                                alt6=2;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 6, 9, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA6_8=='l') ) {
                                            alt6=2;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 6, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA6_7=='b') ) {
                                        alt6=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 6, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA6_6=='a') ) {
                                    alt6=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 6, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA6_5=='t') ) {
                                alt6=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 6, 5, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 6, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA6_3=='l') ) {
                        alt6=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA6_1=='q') ) {
                    alt6=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA6_0=='s') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:159:12: 'SQL_TABLE'
                    {
                    match("SQL_TABLE"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:159:26: S Q L UNDERSCORE T A B L E
                    {
                    mS(); 


                    mQ(); 


                    mL(); 


                    mUNDERSCORE(); 


                    mT(); 


                    mA(); 


                    mB(); 


                    mL(); 


                    mE(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SQL_TABLE"

    // $ANTLR start "REFERENCES"
    public final void mREFERENCES() throws RecognitionException {
        try {
            int _type = REFERENCES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:161:11: ( 'REFERENCES' | R E F E R E N C E S )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='R') ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1=='E') ) {
                    int LA7_3 = input.LA(3);

                    if ( (LA7_3=='F') ) {
                        int LA7_4 = input.LA(4);

                        if ( (LA7_4=='E') ) {
                            int LA7_5 = input.LA(5);

                            if ( (LA7_5=='R') ) {
                                int LA7_6 = input.LA(6);

                                if ( (LA7_6=='E') ) {
                                    int LA7_7 = input.LA(7);

                                    if ( (LA7_7=='N') ) {
                                        int LA7_8 = input.LA(8);

                                        if ( (LA7_8=='C') ) {
                                            int LA7_9 = input.LA(9);

                                            if ( (LA7_9=='E') ) {
                                                int LA7_10 = input.LA(10);

                                                if ( (LA7_10=='S') ) {
                                                    alt7=1;
                                                }
                                                else if ( (LA7_10=='s') ) {
                                                    alt7=2;
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 7, 10, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA7_9=='e') ) {
                                                alt7=2;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 7, 9, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA7_8=='c') ) {
                                            alt7=2;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 7, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA7_7=='n') ) {
                                        alt7=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 7, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA7_6=='e') ) {
                                    alt7=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 7, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA7_5=='r') ) {
                                alt7=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 7, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA7_4=='e') ) {
                            alt7=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA7_3=='f') ) {
                        alt7=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA7_1=='e') ) {
                    alt7=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA7_0=='r') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }
            switch (alt7) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:161:13: 'REFERENCES'
                    {
                    match("REFERENCES"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:161:28: R E F E R E N C E S
                    {
                    mR(); 


                    mE(); 


                    mF(); 


                    mE(); 


                    mR(); 


                    mE(); 


                    mN(); 


                    mC(); 


                    mE(); 


                    mS(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REFERENCES"

    // $ANTLR start "DIMENSION"
    public final void mDIMENSION() throws RecognitionException {
        try {
            int _type = DIMENSION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:163:11: ( 'DIMENSION' | D I M E N S I O N )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='D') ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1=='I') ) {
                    int LA8_3 = input.LA(3);

                    if ( (LA8_3=='M') ) {
                        int LA8_4 = input.LA(4);

                        if ( (LA8_4=='E') ) {
                            int LA8_5 = input.LA(5);

                            if ( (LA8_5=='N') ) {
                                int LA8_6 = input.LA(6);

                                if ( (LA8_6=='S') ) {
                                    int LA8_7 = input.LA(7);

                                    if ( (LA8_7=='I') ) {
                                        int LA8_8 = input.LA(8);

                                        if ( (LA8_8=='O') ) {
                                            int LA8_9 = input.LA(9);

                                            if ( (LA8_9=='N') ) {
                                                alt8=1;
                                            }
                                            else if ( (LA8_9=='n') ) {
                                                alt8=2;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 8, 9, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA8_8=='o') ) {
                                            alt8=2;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 8, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA8_7=='i') ) {
                                        alt8=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 8, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA8_6=='s') ) {
                                    alt8=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 8, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA8_5=='n') ) {
                                alt8=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 8, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA8_4=='e') ) {
                            alt8=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 8, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA8_3=='m') ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA8_1=='i') ) {
                    alt8=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA8_0=='d') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }
            switch (alt8) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:163:13: 'DIMENSION'
                    {
                    match("DIMENSION"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:163:27: D I M E N S I O N
                    {
                    mD(); 


                    mI(); 


                    mM(); 


                    mE(); 


                    mN(); 


                    mS(); 


                    mI(); 


                    mO(); 


                    mN(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIMENSION"

    // $ANTLR start "DIMENSION_TABLE"
    public final void mDIMENSION_TABLE() throws RecognitionException {
        try {
            int _type = DIMENSION_TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:165:16: ( 'DIMENSION_TABLE' | D I M E N S I O N UNDERSCORE T A B L E )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='D') ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1=='I') ) {
                    int LA9_3 = input.LA(3);

                    if ( (LA9_3=='M') ) {
                        int LA9_4 = input.LA(4);

                        if ( (LA9_4=='E') ) {
                            int LA9_5 = input.LA(5);

                            if ( (LA9_5=='N') ) {
                                int LA9_6 = input.LA(6);

                                if ( (LA9_6=='S') ) {
                                    int LA9_7 = input.LA(7);

                                    if ( (LA9_7=='I') ) {
                                        int LA9_8 = input.LA(8);

                                        if ( (LA9_8=='O') ) {
                                            int LA9_9 = input.LA(9);

                                            if ( (LA9_9=='N') ) {
                                                int LA9_10 = input.LA(10);

                                                if ( (LA9_10=='_') ) {
                                                    int LA9_11 = input.LA(11);

                                                    if ( (LA9_11=='T') ) {
                                                        int LA9_12 = input.LA(12);

                                                        if ( (LA9_12=='A') ) {
                                                            int LA9_13 = input.LA(13);

                                                            if ( (LA9_13=='B') ) {
                                                                int LA9_14 = input.LA(14);

                                                                if ( (LA9_14=='L') ) {
                                                                    int LA9_15 = input.LA(15);

                                                                    if ( (LA9_15=='E') ) {
                                                                        alt9=1;
                                                                    }
                                                                    else if ( (LA9_15=='e') ) {
                                                                        alt9=2;
                                                                    }
                                                                    else {
                                                                        NoViableAltException nvae =
                                                                            new NoViableAltException("", 9, 15, input);

                                                                        throw nvae;

                                                                    }
                                                                }
                                                                else if ( (LA9_14=='l') ) {
                                                                    alt9=2;
                                                                }
                                                                else {
                                                                    NoViableAltException nvae =
                                                                        new NoViableAltException("", 9, 14, input);

                                                                    throw nvae;

                                                                }
                                                            }
                                                            else if ( (LA9_13=='b') ) {
                                                                alt9=2;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 9, 13, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else if ( (LA9_12=='a') ) {
                                                            alt9=2;
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 9, 12, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else if ( (LA9_11=='t') ) {
                                                        alt9=2;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 9, 11, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 9, 10, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA9_9=='n') ) {
                                                alt9=2;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 9, 9, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA9_8=='o') ) {
                                            alt9=2;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 9, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA9_7=='i') ) {
                                        alt9=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 9, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA9_6=='s') ) {
                                    alt9=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 9, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA9_5=='n') ) {
                                alt9=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 9, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA9_4=='e') ) {
                            alt9=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 9, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA9_3=='m') ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA9_1=='i') ) {
                    alt9=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA9_0=='d') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:165:18: 'DIMENSION_TABLE'
                    {
                    match("DIMENSION_TABLE"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:165:38: D I M E N S I O N UNDERSCORE T A B L E
                    {
                    mD(); 


                    mI(); 


                    mM(); 


                    mE(); 


                    mN(); 


                    mS(); 


                    mI(); 


                    mO(); 


                    mN(); 


                    mUNDERSCORE(); 


                    mT(); 


                    mA(); 


                    mB(); 


                    mL(); 


                    mE(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIMENSION_TABLE"

    // $ANTLR start "LIST"
    public final void mLIST() throws RecognitionException {
        try {
            int _type = LIST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:167:5: ( 'LIST' | L I S T )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='L') ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1=='I') ) {
                    int LA10_3 = input.LA(3);

                    if ( (LA10_3=='S') ) {
                        int LA10_4 = input.LA(4);

                        if ( (LA10_4=='T') ) {
                            alt10=1;
                        }
                        else if ( (LA10_4=='t') ) {
                            alt10=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA10_3=='s') ) {
                        alt10=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA10_1=='i') ) {
                    alt10=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA10_0=='l') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }
            switch (alt10) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:167:7: 'LIST'
                    {
                    match("LIST"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:167:16: L I S T
                    {
                    mL(); 


                    mI(); 


                    mS(); 


                    mT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LIST"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:169:3: ( 'OF' | O F )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='O') ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1=='F') ) {
                    alt11=1;
                }
                else if ( (LA11_1=='f') ) {
                    alt11=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA11_0=='o') ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:169:4: 'OF'
                    {
                    match("OF"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:169:11: O F
                    {
                    mO(); 


                    mF(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "LEVEL"
    public final void mLEVEL() throws RecognitionException {
        try {
            int _type = LEVEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:171:6: ( 'LEVEL' | L E V E L )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='L') ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1=='E') ) {
                    int LA12_3 = input.LA(3);

                    if ( (LA12_3=='V') ) {
                        int LA12_4 = input.LA(4);

                        if ( (LA12_4=='E') ) {
                            int LA12_5 = input.LA(5);

                            if ( (LA12_5=='L') ) {
                                alt12=1;
                            }
                            else if ( (LA12_5=='l') ) {
                                alt12=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 12, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA12_4=='e') ) {
                            alt12=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 12, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA12_3=='v') ) {
                        alt12=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA12_1=='e') ) {
                    alt12=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA12_0=='l') ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }
            switch (alt12) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:171:7: 'LEVEL'
                    {
                    match("LEVEL"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:171:17: L E V E L
                    {
                    mL(); 


                    mE(); 


                    mV(); 


                    mE(); 


                    mL(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LEVEL"

    // $ANTLR start "AS"
    public final void mAS() throws RecognitionException {
        try {
            int _type = AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:173:3: ( 'AS' | A S )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='A') ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1=='S') ) {
                    alt13=1;
                }
                else if ( (LA13_1=='s') ) {
                    alt13=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA13_0=='a') ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:173:5: 'AS'
                    {
                    match("AS"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:173:12: A S
                    {
                    mA(); 


                    mS(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AS"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:175:4: ( 'AT' | A T )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='A') ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1=='T') ) {
                    alt14=1;
                }
                else if ( (LA14_1=='t') ) {
                    alt14=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA14_0=='a') ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;

            }
            switch (alt14) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:175:6: 'AT'
                    {
                    match("AT"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:175:13: A T
                    {
                    mA(); 


                    mT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "HIERARCHY"
    public final void mHIERARCHY() throws RecognitionException {
        try {
            int _type = HIERARCHY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:177:10: ( 'HIERARCHY' | H I E R A R C H Y )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='H') ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1=='I') ) {
                    int LA15_3 = input.LA(3);

                    if ( (LA15_3=='E') ) {
                        int LA15_4 = input.LA(4);

                        if ( (LA15_4=='R') ) {
                            int LA15_5 = input.LA(5);

                            if ( (LA15_5=='A') ) {
                                int LA15_6 = input.LA(6);

                                if ( (LA15_6=='R') ) {
                                    int LA15_7 = input.LA(7);

                                    if ( (LA15_7=='C') ) {
                                        int LA15_8 = input.LA(8);

                                        if ( (LA15_8=='H') ) {
                                            int LA15_9 = input.LA(9);

                                            if ( (LA15_9=='Y') ) {
                                                alt15=1;
                                            }
                                            else if ( (LA15_9=='y') ) {
                                                alt15=2;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 15, 9, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA15_8=='h') ) {
                                            alt15=2;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 15, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA15_7=='c') ) {
                                        alt15=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 15, 7, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA15_6=='r') ) {
                                    alt15=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 15, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA15_5=='a') ) {
                                alt15=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 15, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA15_4=='r') ) {
                            alt15=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 15, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA15_3=='e') ) {
                        alt15=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA15_1=='i') ) {
                    alt15=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA15_0=='h') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:177:12: 'HIERARCHY'
                    {
                    match("HIERARCHY"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:177:26: H I E R A R C H Y
                    {
                    mH(); 


                    mI(); 


                    mE(); 


                    mR(); 


                    mA(); 


                    mR(); 


                    mC(); 


                    mH(); 


                    mY(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HIERARCHY"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:179:8: ( 'SELECT' | S E L E C T )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='S') ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1=='E') ) {
                    int LA16_3 = input.LA(3);

                    if ( (LA16_3=='L') ) {
                        int LA16_4 = input.LA(4);

                        if ( (LA16_4=='E') ) {
                            int LA16_5 = input.LA(5);

                            if ( (LA16_5=='C') ) {
                                int LA16_6 = input.LA(6);

                                if ( (LA16_6=='T') ) {
                                    alt16=1;
                                }
                                else if ( (LA16_6=='t') ) {
                                    alt16=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 16, 6, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA16_5=='c') ) {
                                alt16=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 16, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA16_4=='e') ) {
                            alt16=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 16, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA16_3=='l') ) {
                        alt16=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA16_1=='e') ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA16_0=='s') ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:179:10: 'SELECT'
                    {
                    match("SELECT"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:179:21: S E L E C T
                    {
                    mS(); 


                    mE(); 


                    mL(); 


                    mE(); 


                    mC(); 


                    mT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "AVG"
    public final void mAVG() throws RecognitionException {
        try {
            int _type = AVG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:181:5: ( 'AVG' | A V G )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='A') ) {
                int LA17_1 = input.LA(2);

                if ( (LA17_1=='V') ) {
                    int LA17_3 = input.LA(3);

                    if ( (LA17_3=='G') ) {
                        alt17=1;
                    }
                    else if ( (LA17_3=='g') ) {
                        alt17=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 17, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA17_1=='v') ) {
                    alt17=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA17_0=='a') ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }
            switch (alt17) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:181:7: 'AVG'
                    {
                    match("AVG"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:181:15: A V G
                    {
                    mA(); 


                    mV(); 


                    mG(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AVG"

    // $ANTLR start "SUM"
    public final void mSUM() throws RecognitionException {
        try {
            int _type = SUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:183:5: ( 'SUM' | S U M )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='S') ) {
                int LA18_1 = input.LA(2);

                if ( (LA18_1=='U') ) {
                    int LA18_3 = input.LA(3);

                    if ( (LA18_3=='M') ) {
                        alt18=1;
                    }
                    else if ( (LA18_3=='m') ) {
                        alt18=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA18_1=='u') ) {
                    alt18=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA18_0=='s') ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }
            switch (alt18) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:183:7: 'SUM'
                    {
                    match("SUM"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:183:15: S U M
                    {
                    mS(); 


                    mU(); 


                    mM(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SUM"

    // $ANTLR start "MAX"
    public final void mMAX() throws RecognitionException {
        try {
            int _type = MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:185:5: ( 'MAX' | M A X )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='M') ) {
                int LA19_1 = input.LA(2);

                if ( (LA19_1=='A') ) {
                    int LA19_3 = input.LA(3);

                    if ( (LA19_3=='X') ) {
                        alt19=1;
                    }
                    else if ( (LA19_3=='x') ) {
                        alt19=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA19_1=='a') ) {
                    alt19=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA19_0=='m') ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }
            switch (alt19) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:185:7: 'MAX'
                    {
                    match("MAX"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:185:15: M A X
                    {
                    mM(); 


                    mA(); 


                    mX(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MAX"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:187:5: ( 'MIN' | M I N )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='M') ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1=='I') ) {
                    int LA20_3 = input.LA(3);

                    if ( (LA20_3=='N') ) {
                        alt20=1;
                    }
                    else if ( (LA20_3=='n') ) {
                        alt20=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA20_1=='i') ) {
                    alt20=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA20_0=='m') ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }
            switch (alt20) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:187:7: 'MIN'
                    {
                    match("MIN"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:187:15: M I N
                    {
                    mM(); 


                    mI(); 


                    mN(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIN"

    // $ANTLR start "COUNT"
    public final void mCOUNT() throws RecognitionException {
        try {
            int _type = COUNT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:189:7: ( 'COUNT' | C O U N T )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='C') ) {
                int LA21_1 = input.LA(2);

                if ( (LA21_1=='O') ) {
                    int LA21_3 = input.LA(3);

                    if ( (LA21_3=='U') ) {
                        int LA21_4 = input.LA(4);

                        if ( (LA21_4=='N') ) {
                            int LA21_5 = input.LA(5);

                            if ( (LA21_5=='T') ) {
                                alt21=1;
                            }
                            else if ( (LA21_5=='t') ) {
                                alt21=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 21, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA21_4=='n') ) {
                            alt21=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 21, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA21_3=='u') ) {
                        alt21=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 21, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA21_1=='o') ) {
                    alt21=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 21, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA21_0=='c') ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }
            switch (alt21) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:189:9: 'COUNT'
                    {
                    match("COUNT"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:189:19: C O U N T
                    {
                    mC(); 


                    mO(); 


                    mU(); 


                    mN(); 


                    mT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COUNT"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:191:7: ( 'WHERE' | W H E R E )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='W') ) {
                int LA22_1 = input.LA(2);

                if ( (LA22_1=='H') ) {
                    int LA22_3 = input.LA(3);

                    if ( (LA22_3=='E') ) {
                        int LA22_4 = input.LA(4);

                        if ( (LA22_4=='R') ) {
                            int LA22_5 = input.LA(5);

                            if ( (LA22_5=='E') ) {
                                alt22=1;
                            }
                            else if ( (LA22_5=='e') ) {
                                alt22=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 22, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA22_4=='r') ) {
                            alt22=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 22, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA22_3=='e') ) {
                        alt22=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 22, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA22_1=='h') ) {
                    alt22=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA22_0=='w') ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }
            switch (alt22) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:191:9: 'WHERE'
                    {
                    match("WHERE"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:191:19: W H E R E
                    {
                    mW(); 


                    mH(); 


                    mE(); 


                    mR(); 


                    mE(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:193:6: ( 'FROM' | F R O M )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='F') ) {
                int LA23_1 = input.LA(2);

                if ( (LA23_1=='R') ) {
                    int LA23_3 = input.LA(3);

                    if ( (LA23_3=='O') ) {
                        int LA23_4 = input.LA(4);

                        if ( (LA23_4=='M') ) {
                            alt23=1;
                        }
                        else if ( (LA23_4=='m') ) {
                            alt23=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 23, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA23_3=='o') ) {
                        alt23=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA23_1=='r') ) {
                    alt23=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA23_0=='f') ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }
            switch (alt23) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:193:8: 'FROM'
                    {
                    match("FROM"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:193:17: F R O M
                    {
                    mF(); 


                    mR(); 


                    mO(); 


                    mM(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "GROUP"
    public final void mGROUP() throws RecognitionException {
        try {
            int _type = GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:195:7: ( 'GROUP' | G R O U P )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='G') ) {
                int LA24_1 = input.LA(2);

                if ( (LA24_1=='R') ) {
                    int LA24_3 = input.LA(3);

                    if ( (LA24_3=='O') ) {
                        int LA24_4 = input.LA(4);

                        if ( (LA24_4=='U') ) {
                            int LA24_5 = input.LA(5);

                            if ( (LA24_5=='P') ) {
                                alt24=1;
                            }
                            else if ( (LA24_5=='p') ) {
                                alt24=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 24, 5, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA24_4=='u') ) {
                            alt24=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 24, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA24_3=='o') ) {
                        alt24=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 3, input);

                        throw nvae;

                    }
                }
                else if ( (LA24_1=='r') ) {
                    alt24=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA24_0=='g') ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;

            }
            switch (alt24) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:195:9: 'GROUP'
                    {
                    match("GROUP"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:195:19: G R O U P
                    {
                    mG(); 


                    mR(); 


                    mO(); 


                    mU(); 


                    mP(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GROUP"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:197:3: ( 'BY' | B Y )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0=='B') ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1=='Y') ) {
                    alt25=1;
                }
                else if ( (LA25_1=='y') ) {
                    alt25=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA25_0=='b') ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;

            }
            switch (alt25) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:197:5: 'BY'
                    {
                    match("BY"); 



                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:197:12: B Y
                    {
                    mB(); 


                    mY(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:199:5: ( Letter ( Letter | Digit | '_' | '-' )* )
            // D:\\workspace\\Master\\src\\CubeSql.g:199:7: Letter ( Letter | Digit | '_' | '-' )*
            {
            mLetter(); 


            // D:\\workspace\\Master\\src\\CubeSql.g:199:14: ( Letter | Digit | '_' | '-' )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0=='-'||(LA26_0 >= '0' && LA26_0 <= '9')||(LA26_0 >= 'A' && LA26_0 <= 'Z')||LA26_0=='_'||(LA26_0 >= 'a' && LA26_0 <= 'z')) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // D:\\workspace\\Master\\src\\CubeSql.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:201:7: ( '{' )
            // D:\\workspace\\Master\\src\\CubeSql.g:201:8: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:202:7: ( '}' )
            // D:\\workspace\\Master\\src\\CubeSql.g:202:8: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:203:4: ( '.' )
            // D:\\workspace\\Master\\src\\CubeSql.g:203:5: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:204:6: ( ',' )
            // D:\\workspace\\Master\\src\\CubeSql.g:204:7: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "CHILDOF"
    public final void mCHILDOF() throws RecognitionException {
        try {
            int _type = CHILDOF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:205:8: ( '>' )
            // D:\\workspace\\Master\\src\\CubeSql.g:205:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHILDOF"

    // $ANTLR start "QUESTMARK"
    public final void mQUESTMARK() throws RecognitionException {
        try {
            int _type = QUESTMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:206:10: ( ';' )
            // D:\\workspace\\Master\\src\\CubeSql.g:206:11: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUESTMARK"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            int _type = UNDERSCORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:207:11: ( '_' )
            // D:\\workspace\\Master\\src\\CubeSql.g:207:12: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:209:16: ( '0' .. '9' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Digit"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:210:17: ( 'a' .. 'z' | 'A' .. 'Z' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:211:11: ( 'A' | 'a' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:212:11: ( 'B' | 'b' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "C"
    public final void mC() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:213:11: ( 'C' | 'c' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "C"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:214:11: ( 'D' | 'd' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:215:11: ( 'E' | 'e' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "E"

    // $ANTLR start "F"
    public final void mF() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:216:11: ( 'F' | 'f' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "F"

    // $ANTLR start "G"
    public final void mG() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:217:11: ( 'G' | 'g' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "G"

    // $ANTLR start "H"
    public final void mH() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:218:11: ( 'H' | 'h' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "H"

    // $ANTLR start "I"
    public final void mI() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:219:11: ( 'I' | 'i' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "I"

    // $ANTLR start "J"
    public final void mJ() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:220:11: ( 'J' | 'j' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "J"

    // $ANTLR start "K"
    public final void mK() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:221:11: ( 'K' | 'k' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "K"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:222:11: ( 'L' | 'l' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "M"
    public final void mM() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:223:11: ( 'M' | 'm' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "M"

    // $ANTLR start "N"
    public final void mN() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:224:11: ( 'N' | 'n' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "N"

    // $ANTLR start "O"
    public final void mO() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:225:11: ( 'O' | 'o' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "O"

    // $ANTLR start "P"
    public final void mP() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:226:11: ( 'P' | 'p' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "P"

    // $ANTLR start "Q"
    public final void mQ() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:227:11: ( 'Q' | 'q' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Q"

    // $ANTLR start "R"
    public final void mR() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:228:11: ( 'R' | 'r' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "R"

    // $ANTLR start "S"
    public final void mS() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:229:11: ( 'S' | 's' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "S"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:230:11: ( 'T' | 't' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "U"
    public final void mU() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:231:11: ( 'U' | 'u' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "U"

    // $ANTLR start "V"
    public final void mV() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:232:11: ( 'V' | 'v' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "V"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:233:11: ( 'W' | 'w' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:234:11: ( 'X' | 'x' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "Y"
    public final void mY() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:235:11: ( 'Y' | 'y' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Y"

    // $ANTLR start "Z"
    public final void mZ() throws RecognitionException {
        try {
            // D:\\workspace\\Master\\src\\CubeSql.g:236:11: ( 'Z' | 'z' )
            // D:\\workspace\\Master\\src\\CubeSql.g:
            {
            if ( input.LA(1)=='Z'||input.LA(1)=='z' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Z"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\workspace\\Master\\src\\CubeSql.g:238:4: ( ( ( ' ' | '\\t' | '\\f' )+ | ( '\\r\\n' | '\\r' | '\\n' ) ) )
            // D:\\workspace\\Master\\src\\CubeSql.g:238:6: ( ( ' ' | '\\t' | '\\f' )+ | ( '\\r\\n' | '\\r' | '\\n' ) )
            {
            // D:\\workspace\\Master\\src\\CubeSql.g:238:6: ( ( ' ' | '\\t' | '\\f' )+ | ( '\\r\\n' | '\\r' | '\\n' ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='\t'||LA29_0=='\f'||LA29_0==' ') ) {
                alt29=1;
            }
            else if ( (LA29_0=='\n'||LA29_0=='\r') ) {
                alt29=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;

            }
            switch (alt29) {
                case 1 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:239:5: ( ' ' | '\\t' | '\\f' )+
                    {
                    // D:\\workspace\\Master\\src\\CubeSql.g:239:5: ( ' ' | '\\t' | '\\f' )+
                    int cnt27=0;
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0=='\t'||LA27_0=='\f'||LA27_0==' ') ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // D:\\workspace\\Master\\src\\CubeSql.g:
                    	    {
                    	    if ( input.LA(1)=='\t'||input.LA(1)=='\f'||input.LA(1)==' ' ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt27 >= 1 ) break loop27;
                                EarlyExitException eee =
                                    new EarlyExitException(27, input);
                                throw eee;
                        }
                        cnt27++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\workspace\\Master\\src\\CubeSql.g:242:5: ( '\\r\\n' | '\\r' | '\\n' )
                    {
                    // D:\\workspace\\Master\\src\\CubeSql.g:242:5: ( '\\r\\n' | '\\r' | '\\n' )
                    int alt28=3;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0=='\r') ) {
                        int LA28_1 = input.LA(2);

                        if ( (LA28_1=='\n') ) {
                            alt28=1;
                        }
                        else {
                            alt28=2;
                        }
                    }
                    else if ( (LA28_0=='\n') ) {
                        alt28=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 0, input);

                        throw nvae;

                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\workspace\\Master\\src\\CubeSql.g:242:7: '\\r\\n'
                            {
                            match("\r\n"); 



                            }
                            break;
                        case 2 :
                            // D:\\workspace\\Master\\src\\CubeSql.g:243:9: '\\r'
                            {
                            match('\r'); 

                            }
                            break;
                        case 3 :
                            // D:\\workspace\\Master\\src\\CubeSql.g:244:9: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;

            }


             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // D:\\workspace\\Master\\src\\CubeSql.g:1:8: ( T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | OR | AND | CREATE | CUBE | RELATED | SQL_TABLE | REFERENCES | DIMENSION | DIMENSION_TABLE | LIST | OF | LEVEL | AS | AT | HIERARCHY | SELECT | AVG | SUM | MAX | MIN | COUNT | WHERE | FROM | GROUP | BY | NAME | LBRACE | RBRACE | DOT | COMMA | CHILDOF | QUESTMARK | UNDERSCORE | WS )
        int alt30=42;
        alt30 = dfa30.predict(input);
        switch (alt30) {
            case 1 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:10: T__66
                {
                mT__66(); 


                }
                break;
            case 2 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:16: T__67
                {
                mT__67(); 


                }
                break;
            case 3 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:22: T__68
                {
                mT__68(); 


                }
                break;
            case 4 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:28: T__69
                {
                mT__69(); 


                }
                break;
            case 5 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:34: T__70
                {
                mT__70(); 


                }
                break;
            case 6 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:40: T__71
                {
                mT__71(); 


                }
                break;
            case 7 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:46: T__72
                {
                mT__72(); 


                }
                break;
            case 8 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:52: T__73
                {
                mT__73(); 


                }
                break;
            case 9 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:58: OR
                {
                mOR(); 


                }
                break;
            case 10 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:61: AND
                {
                mAND(); 


                }
                break;
            case 11 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:65: CREATE
                {
                mCREATE(); 


                }
                break;
            case 12 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:72: CUBE
                {
                mCUBE(); 


                }
                break;
            case 13 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:77: RELATED
                {
                mRELATED(); 


                }
                break;
            case 14 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:85: SQL_TABLE
                {
                mSQL_TABLE(); 


                }
                break;
            case 15 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:95: REFERENCES
                {
                mREFERENCES(); 


                }
                break;
            case 16 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:106: DIMENSION
                {
                mDIMENSION(); 


                }
                break;
            case 17 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:116: DIMENSION_TABLE
                {
                mDIMENSION_TABLE(); 


                }
                break;
            case 18 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:132: LIST
                {
                mLIST(); 


                }
                break;
            case 19 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:137: OF
                {
                mOF(); 


                }
                break;
            case 20 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:140: LEVEL
                {
                mLEVEL(); 


                }
                break;
            case 21 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:146: AS
                {
                mAS(); 


                }
                break;
            case 22 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:149: AT
                {
                mAT(); 


                }
                break;
            case 23 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:152: HIERARCHY
                {
                mHIERARCHY(); 


                }
                break;
            case 24 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:162: SELECT
                {
                mSELECT(); 


                }
                break;
            case 25 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:169: AVG
                {
                mAVG(); 


                }
                break;
            case 26 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:173: SUM
                {
                mSUM(); 


                }
                break;
            case 27 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:177: MAX
                {
                mMAX(); 


                }
                break;
            case 28 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:181: MIN
                {
                mMIN(); 


                }
                break;
            case 29 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:185: COUNT
                {
                mCOUNT(); 


                }
                break;
            case 30 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:191: WHERE
                {
                mWHERE(); 


                }
                break;
            case 31 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:197: FROM
                {
                mFROM(); 


                }
                break;
            case 32 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:202: GROUP
                {
                mGROUP(); 


                }
                break;
            case 33 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:208: BY
                {
                mBY(); 


                }
                break;
            case 34 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:211: NAME
                {
                mNAME(); 


                }
                break;
            case 35 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:216: LBRACE
                {
                mLBRACE(); 


                }
                break;
            case 36 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:223: RBRACE
                {
                mRBRACE(); 


                }
                break;
            case 37 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:230: DOT
                {
                mDOT(); 


                }
                break;
            case 38 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:234: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 39 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:240: CHILDOF
                {
                mCHILDOF(); 


                }
                break;
            case 40 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:248: QUESTMARK
                {
                mQUESTMARK(); 


                }
                break;
            case 41 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:258: UNDERSCORE
                {
                mUNDERSCORE(); 


                }
                break;
            case 42 :
                // D:\\workspace\\Master\\src\\CubeSql.g:1:269: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA30 dfa30 = new DFA30(this);
    static final String DFA30_eotS =
        "\4\uffff\1\52\1\uffff\1\54\32\41\14\uffff\4\41\1\140\1\141\1\140"+
        "\1\141\1\41\1\144\1\145\2\41\1\144\1\145\35\41\2\u0086\5\41\2\uffff"+
        "\2\u008c\2\uffff\2\u008d\16\41\2\u009c\4\41\2\u00a1\2\u00a2\6\41"+
        "\1\uffff\1\u00a9\2\u00aa\2\41\2\uffff\2\41\2\u00af\12\41\1\uffff"+
        "\4\41\2\uffff\2\41\2\u00c0\2\41\2\uffff\2\u00c3\2\41\1\uffff\2\u00c6"+
        "\14\41\2\u00d3\1\uffff\2\u00d4\1\uffff\2\u00d5\1\uffff\6\41\2\u00dc"+
        "\4\41\3\uffff\2\u00e1\4\41\1\uffff\4\41\1\uffff\12\41\2\u00f4\2"+
        "\u00f6\2\u00f8\2\u00f9\1\uffff\1\41\1\uffff\1\41\2\uffff\10\41\2"+
        "\u0104\1\uffff";
    static final String DFA30_eofS =
        "\u0105\uffff";
    static final String DFA30_minS =
        "\1\11\3\uffff\1\75\1\uffff\1\75\1\105\2\106\2\116\2\117\4\105\2"+
        "\111\1\105\2\111\2\101\2\110\4\122\2\131\14\uffff\1\113\1\126\1"+
        "\123\1\126\4\55\1\104\2\55\1\107\1\104\2\55\1\107\1\105\1\102\1"+
        "\125\1\105\1\102\1\125\2\106\2\114\1\115\2\114\3\115\2\105\1\130"+
        "\1\116\1\130\1\116\2\105\4\117\2\55\1\105\2\124\2\105\2\uffff\2"+
        "\55\2\uffff\2\55\2\101\2\105\2\116\1\101\1\105\1\101\1\105\2\137"+
        "\2\105\2\55\2\105\2\122\4\55\2\122\2\115\2\125\1\uffff\3\55\2\114"+
        "\2\uffff\2\124\2\55\4\124\2\122\2\124\2\103\1\uffff\2\116\2\101"+
        "\2\uffff\2\105\2\55\2\120\2\uffff\2\55\2\105\1\uffff\2\55\4\105"+
        "\2\101\2\124\2\123\2\122\2\55\1\uffff\2\55\1\uffff\2\55\1\uffff"+
        "\2\104\2\116\2\102\2\55\2\111\2\103\3\uffff\2\55\2\103\2\114\1\uffff"+
        "\2\117\2\110\1\uffff\4\105\2\116\2\131\2\123\10\55\1\uffff\1\124"+
        "\1\uffff\1\124\2\uffff\2\101\2\102\2\114\2\105\2\55\1\uffff";
    static final String DFA30_maxS =
        "\1\175\3\uffff\1\75\1\uffff\1\75\1\151\2\162\2\166\2\165\2\145\2"+
        "\165\7\151\2\150\4\162\2\171\14\uffff\1\163\1\166\1\163\1\166\4"+
        "\172\1\144\2\172\1\147\1\144\2\172\1\147\1\145\1\142\1\165\1\145"+
        "\1\142\1\165\4\154\1\155\2\154\3\155\2\145\1\170\1\156\1\170\1\156"+
        "\2\145\4\157\2\172\1\105\2\164\2\145\2\uffff\2\172\2\uffff\2\172"+
        "\2\141\2\145\2\156\1\141\1\145\1\141\1\145\2\137\2\145\2\172\2\145"+
        "\2\162\4\172\2\162\2\155\2\165\1\uffff\3\172\2\154\2\uffff\2\164"+
        "\2\172\4\164\2\162\2\164\2\143\1\uffff\2\156\2\141\2\uffff\2\145"+
        "\2\172\2\160\2\uffff\2\172\2\145\1\uffff\2\172\4\145\2\141\2\164"+
        "\2\163\2\162\2\172\1\uffff\2\172\1\uffff\2\172\1\uffff\2\144\2\156"+
        "\2\142\2\172\2\151\2\143\3\uffff\2\172\2\143\2\154\1\uffff\2\157"+
        "\2\150\1\uffff\4\145\2\156\2\171\2\163\10\172\1\uffff\1\164\1\uffff"+
        "\1\164\2\uffff\2\141\2\142\2\154\2\145\2\172\1\uffff";
    static final String DFA30_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\6\33\uffff\1\42\1\43\1\44\1\45\1"+
        "\46\1\50\1\51\1\52\1\5\1\4\1\7\1\47\63\uffff\1\11\1\23\2\uffff\1"+
        "\25\1\26\40\uffff\1\41\5\uffff\1\12\1\31\16\uffff\1\32\4\uffff\1"+
        "\33\1\34\6\uffff\1\10\1\22\4\uffff\1\14\20\uffff\1\37\2\uffff\1"+
        "\24\2\uffff\1\35\14\uffff\1\36\1\40\1\13\6\uffff\1\30\4\uffff\1"+
        "\15\22\uffff\1\16\1\uffff\1\20\1\uffff\1\27\1\17\12\uffff\1\21";
    static final String DFA30_specialS =
        "\u0105\uffff}>";
    static final String[] DFA30_transitionS = {
            "\2\50\1\uffff\2\50\22\uffff\1\50\1\uffff\1\1\5\uffff\1\2\1\3"+
            "\2\uffff\1\45\1\uffff\1\44\14\uffff\1\46\1\4\1\5\1\6\2\uffff"+
            "\1\12\1\37\1\14\1\22\1\41\1\33\1\35\1\25\3\41\1\7\1\27\1\41"+
            "\1\10\2\41\1\16\1\20\3\41\1\31\3\41\4\uffff\1\47\1\uffff\1\13"+
            "\1\40\1\15\1\23\1\41\1\34\1\36\1\26\3\41\1\24\1\30\1\41\1\11"+
            "\2\41\1\17\1\21\3\41\1\32\3\41\1\42\1\uffff\1\43",
            "",
            "",
            "",
            "\1\51",
            "",
            "\1\53",
            "\1\56\3\uffff\1\55\33\uffff\1\60\3\uffff\1\57",
            "\1\62\13\uffff\1\61\23\uffff\1\64\13\uffff\1\63",
            "\1\64\13\uffff\1\63\23\uffff\1\64\13\uffff\1\63",
            "\1\65\4\uffff\1\66\1\67\1\uffff\1\70\27\uffff\1\71\4\uffff"+
            "\1\72\1\73\1\uffff\1\74",
            "\1\71\4\uffff\1\72\1\73\1\uffff\1\74\27\uffff\1\71\4\uffff"+
            "\1\72\1\73\1\uffff\1\74",
            "\1\77\2\uffff\1\75\2\uffff\1\76\31\uffff\1\102\2\uffff\1\100"+
            "\2\uffff\1\101",
            "\1\102\2\uffff\1\100\2\uffff\1\101\31\uffff\1\102\2\uffff\1"+
            "\100\2\uffff\1\101",
            "\1\103\37\uffff\1\104",
            "\1\104\37\uffff\1\104",
            "\1\106\13\uffff\1\105\3\uffff\1\107\17\uffff\1\111\13\uffff"+
            "\1\110\3\uffff\1\112",
            "\1\111\13\uffff\1\110\3\uffff\1\112\17\uffff\1\111\13\uffff"+
            "\1\110\3\uffff\1\112",
            "\1\113\37\uffff\1\114",
            "\1\114\37\uffff\1\114",
            "\1\60\3\uffff\1\57\33\uffff\1\60\3\uffff\1\57",
            "\1\115\37\uffff\1\116",
            "\1\116\37\uffff\1\116",
            "\1\117\7\uffff\1\120\27\uffff\1\121\7\uffff\1\122",
            "\1\121\7\uffff\1\122\27\uffff\1\121\7\uffff\1\122",
            "\1\123\37\uffff\1\124",
            "\1\124\37\uffff\1\124",
            "\1\125\37\uffff\1\126",
            "\1\126\37\uffff\1\126",
            "\1\127\37\uffff\1\130",
            "\1\130\37\uffff\1\130",
            "\1\131\37\uffff\1\132",
            "\1\132\37\uffff\1\132",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\133\7\uffff\1\134\37\uffff\1\135",
            "\1\136\37\uffff\1\137",
            "\1\135\37\uffff\1\135",
            "\1\137\37\uffff\1\137",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\142\37\uffff\1\143",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\146\37\uffff\1\147",
            "\1\143\37\uffff\1\143",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\147\37\uffff\1\147",
            "\1\150\37\uffff\1\151",
            "\1\152\37\uffff\1\153",
            "\1\154\37\uffff\1\155",
            "\1\151\37\uffff\1\151",
            "\1\153\37\uffff\1\153",
            "\1\155\37\uffff\1\155",
            "\1\157\5\uffff\1\156\31\uffff\1\161\5\uffff\1\160",
            "\1\161\5\uffff\1\160\31\uffff\1\161\5\uffff\1\160",
            "\1\162\37\uffff\1\163",
            "\1\164\37\uffff\1\165",
            "\1\166\37\uffff\1\167",
            "\1\163\37\uffff\1\163",
            "\1\165\37\uffff\1\165",
            "\1\167\37\uffff\1\167",
            "\1\170\37\uffff\1\171",
            "\1\171\37\uffff\1\171",
            "\1\172\37\uffff\1\173",
            "\1\173\37\uffff\1\173",
            "\1\174\37\uffff\1\175",
            "\1\176\37\uffff\1\177",
            "\1\175\37\uffff\1\175",
            "\1\177\37\uffff\1\177",
            "\1\u0080\37\uffff\1\u0081",
            "\1\u0081\37\uffff\1\u0081",
            "\1\u0082\37\uffff\1\u0083",
            "\1\u0083\37\uffff\1\u0083",
            "\1\u0084\37\uffff\1\u0085",
            "\1\u0085\37\uffff\1\u0085",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u0087",
            "\1\u0088\37\uffff\1\u0089",
            "\1\u0089\37\uffff\1\u0089",
            "\1\u008a\37\uffff\1\u008b",
            "\1\u008b\37\uffff\1\u008b",
            "",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u008e\37\uffff\1\u008f",
            "\1\u008f\37\uffff\1\u008f",
            "\1\u0090\37\uffff\1\u0091",
            "\1\u0091\37\uffff\1\u0091",
            "\1\u0092\37\uffff\1\u0093",
            "\1\u0093\37\uffff\1\u0093",
            "\1\u0094\37\uffff\1\u0095",
            "\1\u0096\37\uffff\1\u0097",
            "\1\u0095\37\uffff\1\u0095",
            "\1\u0097\37\uffff\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a\37\uffff\1\u009b",
            "\1\u009b\37\uffff\1\u009b",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u009d\37\uffff\1\u009e",
            "\1\u009e\37\uffff\1\u009e",
            "\1\u009f\37\uffff\1\u00a0",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00a3\37\uffff\1\u00a4",
            "\1\u00a4\37\uffff\1\u00a4",
            "\1\u00a5\37\uffff\1\u00a6",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\37\uffff\1\u00a8",
            "\1\u00a8\37\uffff\1\u00a8",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00ab\37\uffff\1\u00ac",
            "\1\u00ac\37\uffff\1\u00ac",
            "",
            "",
            "\1\u00ad\37\uffff\1\u00ae",
            "\1\u00ae\37\uffff\1\u00ae",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00b0\37\uffff\1\u00b1",
            "\1\u00b1\37\uffff\1\u00b1",
            "\1\u00b2\37\uffff\1\u00b3",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b5",
            "\1\u00b5\37\uffff\1\u00b5",
            "\1\u00b6\37\uffff\1\u00b7",
            "\1\u00b7\37\uffff\1\u00b7",
            "\1\u00b8\37\uffff\1\u00b9",
            "\1\u00b9\37\uffff\1\u00b9",
            "",
            "\1\u00ba\37\uffff\1\u00bb",
            "\1\u00bb\37\uffff\1\u00bb",
            "\1\u00bc\37\uffff\1\u00bd",
            "\1\u00bd\37\uffff\1\u00bd",
            "",
            "",
            "\1\u00be\37\uffff\1\u00bf",
            "\1\u00bf\37\uffff\1\u00bf",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00c1\37\uffff\1\u00c2",
            "\1\u00c2\37\uffff\1\u00c2",
            "",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00c4\37\uffff\1\u00c5",
            "\1\u00c5\37\uffff\1\u00c5",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00c7\37\uffff\1\u00c8",
            "\1\u00c8\37\uffff\1\u00c8",
            "\1\u00c9\37\uffff\1\u00ca",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cc",
            "\1\u00cc\37\uffff\1\u00cc",
            "\1\u00cd\37\uffff\1\u00ce",
            "\1\u00ce\37\uffff\1\u00ce",
            "\1\u00cf\37\uffff\1\u00d0",
            "\1\u00d0\37\uffff\1\u00d0",
            "\1\u00d1\37\uffff\1\u00d2",
            "\1\u00d2\37\uffff\1\u00d2",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\u00d6\37\uffff\1\u00d7",
            "\1\u00d7\37\uffff\1\u00d7",
            "\1\u00d8\37\uffff\1\u00d9",
            "\1\u00d9\37\uffff\1\u00d9",
            "\1\u00da\37\uffff\1\u00db",
            "\1\u00db\37\uffff\1\u00db",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00dd\37\uffff\1\u00de",
            "\1\u00de\37\uffff\1\u00de",
            "\1\u00df\37\uffff\1\u00e0",
            "\1\u00e0\37\uffff\1\u00e0",
            "",
            "",
            "",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\u00e2\37\uffff\1\u00e3",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e5",
            "\1\u00e5\37\uffff\1\u00e5",
            "",
            "\1\u00e6\37\uffff\1\u00e7",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\u00e8\37\uffff\1\u00e9",
            "\1\u00e9\37\uffff\1\u00e9",
            "",
            "\1\u00ea\37\uffff\1\u00eb",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ed",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\37\uffff\1\u00ef",
            "\1\u00ef\37\uffff\1\u00ef",
            "\1\u00f0\37\uffff\1\u00f1",
            "\1\u00f1\37\uffff\1\u00f1",
            "\1\u00f2\37\uffff\1\u00f3",
            "\1\u00f3\37\uffff\1\u00f3",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\u00f5\1\uffff\32"+
            "\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\u00f7\1\uffff\32"+
            "\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\u00fa\37\uffff\1\u00fb",
            "",
            "\1\u00fb\37\uffff\1\u00fb",
            "",
            "",
            "\1\u00fc\37\uffff\1\u00fd",
            "\1\u00fd\37\uffff\1\u00fd",
            "\1\u00fe\37\uffff\1\u00ff",
            "\1\u00ff\37\uffff\1\u00ff",
            "\1\u0100\37\uffff\1\u0101",
            "\1\u0101\37\uffff\1\u0101",
            "\1\u0102\37\uffff\1\u0103",
            "\1\u0103\37\uffff\1\u0103",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\41\2\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | OR | AND | CREATE | CUBE | RELATED | SQL_TABLE | REFERENCES | DIMENSION | DIMENSION_TABLE | LIST | OF | LEVEL | AS | AT | HIERARCHY | SELECT | AVG | SUM | MAX | MIN | COUNT | WHERE | FROM | GROUP | BY | NAME | LBRACE | RBRACE | DOT | COMMA | CHILDOF | QUESTMARK | UNDERSCORE | WS );";
        }
    }
 

}