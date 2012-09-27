package stcharts;

import git.GitController;
import db.DatabaseConnector;

public class NetworkGenerator
{
	private DatabaseConnector 	db;
	private GitController 		gc;
	
	public NetworkGenerator(DatabaseConnector db) {
		this.db = db;
		
		gc = new GitController();
	}
	
	public void buildNetworks() {
		cleanNetworks();
		
	}
	
	private void cleanNetworks() {
		
	}
}
