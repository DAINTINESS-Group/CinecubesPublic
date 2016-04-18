
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMainEngine extends Remote {

	
	public void SetQueryFile(File qFile) throws RemoteException; //Set a file which contains one or more queries for the Cinecubes
	public void initialize_connection(String database_name,String login,String passwd) throws RemoteException;
	  
}