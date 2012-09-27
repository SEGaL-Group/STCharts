package stcharts;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import db.DatabaseConnector;

public class Main
{
	public static void main(String[] args) {
		System.out.println("STCharts");
		System.out.println();
		
		CommandLineParser parser = new GnuParser();
		try {
			Options options = new Options();
			
			Option repo = OptionBuilder.withArgName("r").hasArg().create("r");
			
			Option days = OptionBuilder.withArgName("d").hasArg().create("d");
			Option weeks = OptionBuilder.withArgName("w").hasArg().create("w");
			Option months = OptionBuilder.withArgName("m").hasArg().create("m");
			Option years = OptionBuilder.withArgName("y").hasArg().create("y");
			
			options.addOption(repo);
			options.addOption(days);
			options.addOption(weeks);
			options.addOption(months);
			options.addOption(years);
			
			CommandLine line = parser.parse(options, args);
			
			// Set up DB resources
			Resources.dbName = "stcharts";
			DatabaseConnector db = new DatabaseConnector();
			db.connect(Resources.dbName);
			
			
			if(line.hasOption("r")) {
				String[] values = line.getOptionValues("r");
				if(values.length != 1) {
					System.out.println("You must specify the path to the given repository with the r flag. " +
							"Example: /home/user/repo");
					return;
				}
				else {
					Resources.repository = values[0];
					Resources.branch = "master";
					setRepositoryName(Resources.repository);
				}
			}
			
			if(line.hasOption("d")) {
				Resources.intervalType = Resources.IntervalType.DAY;
				parseInterval("d", line);
			}
			else if(line.hasOption("w")) {
				Resources.intervalType = Resources.IntervalType.WEEK;
				parseInterval("w", line);
			}
			else if(line.hasOption("m")) {
				Resources.intervalType = Resources.IntervalType.MONTH;
				parseInterval("m", line);
			}
			else if(line.hasOption("y")) {
				Resources.intervalType = Resources.IntervalType.YEAR;
				parseInterval("y", line);
			}
			else {
				System.out.println("Using the default time interval of 1 week.");
				Resources.intervalType = Resources.IntervalType.WEEK;
				Resources.interval = 1;
			}
			
			// All variables have been set up so commence network building
			NetworkGenerator ng = new NetworkGenerator(db);
			ng.buildNetworks();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void parseInterval(String flag, CommandLine line) {
		String[] values = line.getOptionValues(flag);
		if(values.length != 1) {
			System.out.println("You must specify an integer interval for the " + flag + " flag.");
			return;
		}
		Resources.interval = Integer.parseInt(values[0]);
	}
	
	private static void setRepositoryName(String path) {
		Resources.repositoryName = path.substring(path.lastIndexOf("/")+1);
	}
}
