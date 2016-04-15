import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
	   private static final int PORT = 2020;
	   private static Registry registry;
	   private String name;
	   
	   
	   
	 
	   public static void startRegistry() throws RemoteException {
	       // Create server registry
	       registry =  LocateRegistry.createRegistry(PORT);
	   }
	 
	   public static void registerObject(String name, Remote remoteObj)
	           throws RemoteException, AlreadyBoundException {
	 
	       // Bind the object in the registry.
	       // It is bind with certain name.
	       // Client will lookup on the registration of the name to get object.        
	       registry.bind(name, remoteObj);
	       System.out.println("Registered: " + name + " -> "
	               + remoteObj.getClass().getName() + "[" + remoteObj + "]");
	   }
	 
	   public static void main(String[] args) throws Exception {
	       System.out.println("Server starting...");
	       startRegistry();
	       registerObject(IMainEngine.class.getSimpleName(), new MainEngine());
	 
	       // Server was the start, and was listening to the request from the client.
	       System.out.println("Server started!");
	   }
}
