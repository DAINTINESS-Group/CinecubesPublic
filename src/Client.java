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
         
         
    	File f = new File("InputFiles/cubeQueries.ini"); 
    	//File f2 = new File("InputFiles/cubeQueriesloan.ini"); 
        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);
        //LookUp for MainEngine on the registry
       IMainEngine service = (IMainEngine) registry.lookup(IMainEngine.class.getSimpleName());
       
       service.initialize_connection("adult_no_dublic","root","gate13");
       //service.initialize_connection("test","root","gate13");
       service.AnswerCubeQueriesFromFile(f);
       
    }

}
