import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import CubeMgr.CubeMgr;




public interface MainEngi extends Remote {
	
	
	public void run(String dbname,File f,String username,String password) throws RemoteException;
	
	public  void SetM(File s) throws RemoteException;
}
