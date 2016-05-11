
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import CubeMgr.CubeBase.CubeQuery;

public interface IMainEngine extends Remote {

	public void initialize_connection(String database_name,String login,String passwd) throws RemoteException;
	public void AnswerCubeQueriesFromFile(File file) throws RemoteException; 
	public CubeQuery createCubeQueryFromString(String cubeQstring) throws RemoteException;
}