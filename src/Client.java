import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	// Host or IP of Server
    private static final String HOST = "localhost";
    private static final int PORT = 2020;
    private static Registry registry;
    
    public static void main(String[] args) throws Exception {
    	 
        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);
        //LookUp for MainEngine on the registry
        MainEngi service = (MainEngi) registry.lookup(MainEngi.class.getSimpleName());
        
    }

}
