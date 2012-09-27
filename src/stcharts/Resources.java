package stcharts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Resources
{
	public enum IntervalType {
		DAY, WEEK, MONTH, YEAR
	}
	
	public static String dbName;
	public static String repository;
	public static String branch;
	public static String repositoryName;
	public static IntervalType intervalType;
	public static int interval;
	
	// Git log regex
	public static final String gitLogCommit = "commit [a-z0-9]+";
	public static final String gitLogAuthor = "Author:.*\\<(.*)\\>";
	public static final DateFormat gitLogDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z"); 
	public static final String gitLogDate = "Date: (.*)";

	// Git HEAD regex
	public static final String gitHead = "[a-z0-9]+";
}
