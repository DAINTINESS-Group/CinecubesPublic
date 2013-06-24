package CubeMgr.StarSchema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ErrorsEngine.ErrorClass;

public class Database {
	private String DBName;
    public List<Table> Tbl;
    
    private String ConnectionString;
    private String DBMS;
    private Connection connect;
    private String Username;
    private String Password;
    
    public Database(){
       setConnectionString("jdbc:mysql://localhost:3306/adult_no_dublic");
       DBMS="com.mysql.jdbc.Driver";
       Tbl=new ArrayList<>();
    }
    
   	public Database(String ConnStr,String dbtype){
        setConnectionString(ConnStr);
        DBMS=dbtype;
        Tbl=new ArrayList<>();
    }
    
    public void registerDatabase(){
        try{
            Class.forName(DBMS).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return;
    	}
        try{
            setConnection(DriverManager.getConnection(ConnectionString,Username,Password));
        } catch (SQLException ex) {
        	System.out.println("Connection Failed! Check output console");
        	//(new ErrorClass()).printErrorMessage(ex.getMessage());
        	System.out.println("SQLState: " + ex.getSQLState());
        	System.out.println("LocalState: " + ex.getLocalizedMessage());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    public void GenerateTableList(){
        try {
            DatabaseMetaData Metadata = connect.getMetaData();
            ResultSet rs = Metadata.getTables(null, null, "%", null);;
            while (rs.next()){
            	Table tmp=new Table(rs.getString(3));
            	tmp.setAttribute(connect);
                this.Tbl.add(tmp);
                
            }
        } catch (SQLException ex) {
        	ex.printStackTrace();
            (new ErrorClass()).printErrorMessage(ex.getMessage());
        }
    }
    
    public void PrintTableList(){
    	for(Table item : this.Tbl){
    		System.out.println(item.TblName);
    		item.printColumns();
    	}
    }
    
	public String getConnectionString() {
		return ConnectionString;
	}

	public void setConnectionString(String connectionString) {
		ConnectionString = connectionString;
	}

	public Connection getConnection() {
		return connect;
	}

	public void setConnection(Connection connect) {
		this.connect = connect;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}
	
	public String getDBMS() {
		return DBMS;
	}

	public void setDBMS(String dbms) {
		DBMS = dbms;
	}
	
	public Table getDBTableInstance(String nameTbl){
		Table retTbl = null;
		 for(int i=0;i<this.Tbl.size();i++){
			 if(this.Tbl.get(i).TblName.equals(nameTbl)) retTbl=this.Tbl.get(i);
		 }
		 if(retTbl==null){
			 System.err.println("Sql Table no exist");
			 System.exit(1);
		 }
		 return retTbl;
	}

	public Attribute getFieldOfSqlTable(String table, String field) {
		return this.getDBTableInstance(table).getAttribute(field);
		
	}
	
	public ResultSet executeSql(String query){
		ResultSet res = null;
		try {
			Statement createStatement = connect.createStatement();
			res = createStatement.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
}
