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
    	File f = new File("InputFiles/cubeQueries.ini"); 
    	File f2 = new File("InputFiles/cubeQueriesloan.ini");
    	File f3 = new File("InputFiles/cubeQueries2013_05_31.ini");
    	File f4 = new File("InputFiles/cubeQueriesorder.ini"); 
        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);
        //LookUp for MainEngine on the registry
       IMainEngine service = (IMainEngine) registry.lookup(IMainEngine.class.getSimpleName());
     // service.initialize_connection("adult_no_dublic","root","gate13","adult","adult");//service.AnswerCubeQueriesFromFile(f3);
     // service.AnswerCubeQueriesFromFile(f);
     //service.initialize_connection("test","root","gate13","pkdd99","loan");service.AnswerCubeQueriesFromFile(f2);
      service.initialize_connection("test","root","gate13","pkdd99","orders");service.AnswerCubeQueriesFromFile(f4);
    }

}
