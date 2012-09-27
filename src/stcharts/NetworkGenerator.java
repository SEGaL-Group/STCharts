package stcharts;

import java.util.List;

import models.Commit;
import models.Network;

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
		db.exportNetwork(
				technicalAlgorithm(gatherTechnicalInformation()));
		db.exportNetwork(socialAlgorithm());
	}
	
	public void buildTechnicalNetworks() {
		preprocessDB(true, false);
		db.exportNetwork(
				technicalAlgorithm(gatherTechnicalInformation()));
	}
	
	public void buildSocialNetworks() {
		preprocessDB(false, true);
		db.exportNetwork(socialAlgorithm());
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
	
	private List<Commit> gatherTechnicalInformation() {
		List<Commit> commits = gc.getAllCommits();
		for(Commit commit: commits)
			commit.setFiles(gc.getFilesOfCommit(commit.getCommitID()));
		
		return commits;
	}
	
	private Network technicalAlgorithm(List<Commit> commits) {
		Network network = new Network(Network.NetworkType.TECHNICAL,
				Resources.intervalID);
		
		return network;
	}
	
	private Network socialAlgorithm() {
		Network network = new Network(Network.NetworkType.SOCIAL,
				Resources.intervalID);
		
		return network;
	}
}
