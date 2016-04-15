import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;




public class Client {
	// Host or IP of Server
    private static final String HOST = "localhost";
    private static final int PORT = 2020;
    private static Registry registry;
    

    
    public static void main(String[] args) throws Exception {
    	ArrayList<String> pptlist = new ArrayList<String>();
    	//Search InputFiles directory
    	 File dir = new File("InputFiles");
         String[] children = dir.list();
         if (children == null) {
            System.out.println("does not exist or is not a directory");
         }
         else {
            for (int i = 0; i < children.length; i++) {
               String filename = children[i];
               System.out.println(filename);
            }
         }
         

   	
    	String dbname,username,password;
    	File f = new File("InputFiles/cubeQueries.ini"); 
    	Scanner in = new Scanner(System.in);
        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);
        //LookUp for MainEngine on the registry
       IMainEngine service = (IMainEngine) registry.lookup(IMainEngine.class.getSimpleName());
       service.SetQueryFile(f);
       System.out.println("Give me your database name");
       dbname = in.nextLine();
       service.SetName(dbname);
       System.out.println("Give me your database username");
       username = in.nextLine();
       service.SetUsername(username);
       System.out.println("Give me your database password");
       password = in.nextLine();
       service.SetPassword(password);
       pptlist = service.execute();
       for(String x: pptlist){
    	   System.out.println(x);
       }
    }

}
