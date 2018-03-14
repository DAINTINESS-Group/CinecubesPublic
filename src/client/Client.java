package client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import mainengine.IMainEngine;

public class Client {

	// Host or IP of Server
	private static final String HOST = "localhost";
	private static final int PORT = 2020;
	private static Registry registry;

	public static void main(String[] args) throws Exception {
		// Search the registry in the specific Host, Port.
		registry = LocateRegistry.getRegistry(HOST, PORT);
		// LookUp for MainEngine on the registry
		IMainEngine service = (IMainEngine) registry.lookup(IMainEngine.class
				.getSimpleName());
		// The first arg is audio; the second is for Word
		service.optionsChoice(false, true);
		
		// Cube ADULT and queries
		/*service.initializeConnection("adult_no_dublic", "CinecubesUser",
				"Cinecubes", "adult", "adult");
		File f = new File("InputFiles/cubeQueries.ini");
		service.answerCubeQueriesFromFile(f);/**/
				
		// Cube LOAN and queries
		service.initializeConnection("pkdd99", "CinecubesUser",
				"Cinecubes", "pkdd99", "loan");
		File f2 = new File("InputFiles/cubeQueriesloan.ini");
		service.answerCubeQueriesFromFile(f2);/**/
		
		// Cube ORDERS and queries
		/*service.initializeConnection("pkdd99", "CinecubesUser",
				 "Cinecubes", "pkdd99", "orders");
		File f4 = new File("InputFiles/cubeQueriesorder.ini");
		service.answerCubeQueriesFromFile(f4);/**/
		
		System.out.println("Execution of client is complete");
	}

}
