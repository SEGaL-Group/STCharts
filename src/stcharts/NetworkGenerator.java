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
	
	public void buildAllNetworks() {
		preprocessDB(true, true);
	}
	
	public void buildTechnicalNetworks() {
		preprocessDB(true, false);
	}
	
	public void buildSocialNetworks() {
		preprocessDB(false, true);
	}
	
	/**
	 * This function sets the DB up for the new input of
	 * networks.
	 */
	private void preprocessDB(boolean technical, boolean social) {
		Resources.projectID = db.getProjectID();
		Resources.intervalID = db.getIntervalID(Resources.projectID);
		
		// Clean technical
		if(technical)
			db.cleanTechnicalNetworks(Resources.intervalID);
		
		// Clean social
		if(social)
			db.cleanSocialNetworks(Resources.intervalID);
		
		if(Resources.projectID == -1)
			Resources.projectID = db.insertNewProject();
		if(Resources.intervalID == -1)
			Resources.intervalID = db.insertNewInterval(Resources.projectID);
	}
}
