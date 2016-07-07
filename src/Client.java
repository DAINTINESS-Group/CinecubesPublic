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
       
       
       //Cube ADULT and queries
/*       service.initialize_connection("adult_no_dublic","CinecubesUser","Cinecubes","adult","adult");
       
       The first arg is audio; the second is for Word 
       service.OptionsChoice(true, true);
       service.AnswerCubeQueriesFromFile(f3);*/
      // service.OptionsChoice(false, true);
      // service.AnswerCubeQueriesFromFile(f); 
       
       
      //Cube LOAN and queries
      service.initialize_connection("pkdd99","CinecubesUser","Cinecubes","pkdd99","loan");
      service.OptionsChoice(false, true); 
      service.AnswerCubeQueriesFromFile(f2);
       
       
       //Cube ORDERS and queries
    /*  service.initialize_connection("pkdd99","CinecubesUser","Cinecubes","pkdd99","orders");
      service.AnswerCubeQueriesFromFile(f4);
      service.OptionsChoice(true, false); */
       
       System.out.println("Execution of client is complete");
    }

}
