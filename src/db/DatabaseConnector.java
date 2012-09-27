package db;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Edge;
import models.Network;

import db.util.ISetter;
import db.util.ISetter.IntSetter;
import db.util.PreparedStatementExecutionItem;
import db.util.ISetter.StringSetter;
import db.util.ISetter.FloatSetter;
import db.util.ISetter.TimestampSetter;

import stcharts.Resources;

public class DatabaseConnector extends DbConnection {
	
	/**
	 * This function returns the project ID stored inside the database
	 * for the current program's configuration.
	 * @return
	 */
	public int getProjectID() {
		try {
			String sql = "SELECT pid FROM projects WHERE name=?"; 
			ISetter[] params = {
					new StringSetter(1, Resources.repositoryName)
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			if(rs != null && rs.next())
				return rs.getInt("pid");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * This function returns the interval id of the current programs
	 * configuration if it exists inside the DB.
	 * @param pid
	 * @return
	 */
	public int getIntervalID(int pid) {
		try {
			String sql = "SELECT iid FROM intervals WHERE pid=? AND interval=? AND interval_type=?"; 
			ISetter[] params = {
					new IntSetter(1, pid),
					new IntSetter(2, Resources.interval),
					new StringSetter(3, Resources.intervalType.toString())
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			if(rs != null && rs.next())
				return rs.getInt("iid");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * This function cleans out the old technical networks that are for the
	 * current project and interval period.
	 * @param pid
	 * @param iid
	 */
	public void cleanTechnicalNetworks(int iid) {
		if(iid == -1)
			return;
		try {
			String sql = "DELETE FROM t_networks iid=?"; 
			ISetter[] params = {
					new IntSetter(1, iid)
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function cleans out the old social networks that are for the
	 * current project and interval period.
	 * @param pid
	 * @param iid
	 */
	public void cleanSocialNetworks(int iid) {
		if(iid == -1)
			return;
		try {
			String sql = "DELETE FROM s_networks iid=?"; 
			ISetter[] params = {
					new IntSetter(1, iid)
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This creates a new project inside of the database.
	 * @return
	 */
	public int insertNewProject() {
		String query = "INSERT INTO projects (pid, name) VALUES (default, ?)";
		ISetter[] params = {
				new StringSetter(1, Resources.repositoryName)
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("projects_id_seq");
	}
	
	/**
	 * This creates a new interval inside of the database.
	 * @return
	 */
	public int insertNewInterval(int pid) {
		String query = "INSERT INTO intervals (pid, iid, interval, interval_type) VALUES " +
				"(?, default, ?, ?)";
		ISetter[] params = {
				new IntSetter(1, pid),
				new IntSetter(2, Resources.interval),
				new StringSetter(3, Resources.intervalType.toString())
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("intervals_id_seq");
	}
	
	/**
	 * Get Sequence Id for a sequence table
	 * @param sequence
	 * @return sequence id, -1 of none found or there is exception
	 */
	private int getSequenceValue(String sequence) {
		try {
			String sql = "SELECT currval(?)"; 
			ISetter[] params = {new StringSetter(1, sequence)};
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			if(rs != null && rs.next())
				return rs.getInt("currval");
			return -1;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int exportNetwork(Network network) {
		if(network.getNetworkType() == Network.NetworkType.TECHNICAL)
			return exportTechnicalNetwork(network);
		else if(network.getNetworkType() == Network.NetworkType.TECHNICAL)
			return exportSocialNetwork(network);
		
		return -1;
	}
	
	private int exportTechnicalNetwork(Network network) {
		int nid = insertTechnicalNetwork(network);
		network.setNid(nid);
		
		for(Edge edge: network.getEdges()) {
			edge.setNid(nid);
			insertTechnicalEdge(edge);
		}
		
		return nid;
	}
	
	private int insertTechnicalNetwork(Network network) {
		String query = "INSERT INTO t_networks (iid, nid, commit_date) VALUES (?, default, ?)";
		ISetter[] params = {
				new IntSetter(1, Resources.intervalID),
				new TimestampSetter(2, network.getCommit_date())
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("tnetwork_id_seq");
	}
	
	private void insertTechnicalEdge(Edge edge) {
		String query = "INSERT INTO t_edges (user_a, user_b, weight, nid) VALUES " +
				"(?, ?, ?, ?)";
		ISetter[] params = {
				new IntSetter(1, edge.getUser_a()),
				new IntSetter(2, edge.getUser_b()),
				new FloatSetter(3, edge.getWeight()),
				new IntSetter(4, edge.getNid())
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
	}
	
	private int exportSocialNetwork(Network network) {
		int nid = insertSocialNetwork(network);
		network.setNid(nid);
		
		for(Edge edge: network.getEdges()) {
			edge.setNid(nid);
			insertSocialEdge(edge);
		}
		
		return nid;
	}
	
	private int insertSocialNetwork(Network network) {
		String query = "INSERT INTO s_networks (iid, nid, t_nid) VALUES (?, default, ?)";
		ISetter[] params = {
				new IntSetter(1, Resources.intervalID),
				new IntSetter(2, network.getT_nid())
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("snetwork_id_seq");
	}
	
	private void insertSocialEdge(Edge edge) {
		String query = "INSERT INTO s_edges (user_a, user_b, weight, nid) VALUES " +
				"(?, ?, ?, ?)";
		ISetter[] params = {
				new IntSetter(1, edge.getUser_a()),
				new IntSetter(2, edge.getUser_b()),
				new FloatSetter(3, edge.getWeight()),
				new IntSetter(4, edge.getNid())
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
	}
	
	/**
	 * This function returns the user ID associated with a user's
	 * email in the DB. It will create a new user if it DNE.
	 * @param email
	 * @return
	 */
	public int getUser(String email) {
		try {
			String sql = "SELECT uid FROM users WHERE email=?"; 
			ISetter[] params = {
					new StringSetter(1, email)
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			
			if(rs.next())
				return rs.getInt("uid");
			else
				return insertUser(email);
		}
		catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This function creates a mew user inside the DB.
	 * @param email
	 * @return
	 */
	private int insertUser(String email) {
		String query = "INSERT INTO users (uid, name, email) VALUES (default, ?, ?)";
		ISetter[] params = {
				new StringSetter(1, ""),
				new StringSetter(2, email)
		};
		
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("user_id_seq");
	}
	
}
