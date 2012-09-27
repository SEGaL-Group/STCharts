package stcharts;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Commit;
import models.Edge;
import models.Network;
import models.Pair;

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
		technicalAlgorithm(gatherTechnicalInformation());
		socialAlgorithm();
	}
	
	public void buildTechnicalNetworks() {
		preprocessDB(true, false);
		technicalAlgorithm(gatherTechnicalInformation());
	}
	
	public void buildSocialNetworks() {
		preprocessDB(false, true);
		socialAlgorithm();
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
	
	/***************************** Technical Network Section **************************/
	
	private void technicalAlgorithm(List<Commit> commits) {
		for(Commit commit: commits) {
			Network network = new Network(Network.NetworkType.TECHNICAL,
					Resources.intervalID);
			network.setCommit_date(new Timestamp(commit.getDate().getTime()));
			
			
			List<Commit> intervalCommits = getIntervalCommits(commits, commit.getCommitID());
			List<Pair<String, String>> filePairs = getFilePairs(intervalCommits);
			Map<String, List<Integer>> authors = getAuthorsOfFiles(intervalCommits);
			
			for(Pair<String, String> pair: filePairs) {
				List<Edge> edges = getEdgesForPair(pair, authors);
				for(Edge edge: edges) {
					if(!network.containsEdge(edge)) {
						network.getEdges().add(edge);
					}
				}
			}
			
			db.exportNetwork(network);
		}
	}
	
	private List<Commit> getIntervalCommits(List<Commit> commits, String commitID) {
		
		return commits;
	}
	
	private List<Pair<String, String>> getFilePairs(List<Commit> commits) {
		List<Pair<String, String>> filePairs = new ArrayList<Pair<String, String>>();
		
		applyPairThreshold(filePairs);
		return filePairs;
	}
	
	private void applyPairThreshold(List<Pair<String, String>> filePairs) {
		
	}
	
	private Map<String, List<Integer>> getAuthorsOfFiles(List<Commit> commits) {
		Map<String, List<Integer>> authors = new HashMap<String, List<Integer>>();
		
		return authors;
	}
	
	private List<Edge> getEdgesForPair(Pair<String,String> filePair, Map<String, List<Integer>> authors) {
		List<Edge> edges = new ArrayList<Edge>();
		
		return edges;
	}
	
	/***************************** Social Network Section **************************/
	
	private void socialAlgorithm() {
		Network network = new Network(Network.NetworkType.SOCIAL,
				Resources.intervalID);
		
		
	}
}
