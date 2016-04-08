import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;




public class Client {
	// Host or IP of Server
    private static final String HOST = "localhost";
    private static final int PORT = 2020;
    private static Registry registry;
    

    
    public static void main(String[] args) throws Exception {
    	String dbname,username,password;
    	File f = new File("InputFiles/cubeQueries.ini"); 
    	Scanner in = new Scanner(System.in);
        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);
        //LookUp for MainEngine on the registry
        MainEngi service = (MainEngi) registry.lookup(MainEngi.class.getSimpleName());
       System.out.println("Give me your database name");
       dbname = in.nextLine();
       System.out.println("Give me your database username");
       username = in.nextLine();
       System.out.println("Give me your database password");
       password = in.nextLine();
       service.run(dbname,f,username,password);
    	
       
    }

}
