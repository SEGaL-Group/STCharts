package db;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.util.ISetter;
import db.util.ISetter.IntSetter;
import db.util.PreparedStatementExecutionItem;
import db.util.ISetter.StringSetter;
import db.util.ISetter.FloatSetter;

public class DatabaseConnector extends DbConnection {
	
	public void createDatabase(String dbName) {
		if(databaseExist(dbName))
			return;
		try {
			// Drop the DB if it already exists
			String query = "DROP DATABASE IF EXISTS " + dbName;
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();

			// First create the DB.
			query = "CREATE DATABASE " + dbName + ";";
			ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();

			// Reconnect to our new database.
			close();
			connect(dbName.toLowerCase());

			// load our schema			
			runScript(new InputStreamReader(this.getClass().getResourceAsStream("schema.sql")));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean databaseExist(String dbName) {
		try {
			// Drop the DB if it already exists
			String query = "SELECT datname FROM pg_database WHERE datname=?";
			ISetter[] params = {
					new StringSetter(1,dbName)
			};
			
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			
			if(rs.next())
				return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
