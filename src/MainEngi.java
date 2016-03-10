import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;




public interface MainEngi extends Remote {
	
	 // Method to retrieve queries from file example for RMI.
	public  void getCubeQueriesFromFile(File file)
	          throws RemoteException;
}
