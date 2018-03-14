package mainengine;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMainEngine extends Remote {

	void initializeConnection(String schemaName, String login, String passwd,
			 String inputfile, String cubeName) throws RemoteException;
	
	void answerCubeQueriesFromFile(File file) throws RemoteException;
	
	void optionsChoice(boolean audio, boolean word)throws RemoteException;
}