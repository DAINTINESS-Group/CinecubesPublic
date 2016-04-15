
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMainEngine extends Remote {

	
	public  void SetQueryFile(File qFile) throws RemoteException;
	public ArrayList<String> execute()throws RemoteException; 
	public void SetUsername(String uname)throws RemoteException;    
	public void SetPassword(String pw)throws RemoteException;    
	public void SetName(String name)throws RemoteException;
	  
}