package git;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import process.Spawner;
import stcharts.Resources;
import models.Commit;

public class GitController
{
	private Spawner spawner;
	
	public GitController() {
		spawner = new Spawner(Resources.repository);
	}
	
	public void reset(String commitID) {
		spawner.spawnProcess(new String[] {"git", "reset", "--hard", commitID});
	}
	
	public List<Commit> getAllCommits() {
		List<Commit> commits = new ArrayList<Commit>();
		parseLogForCommits(commits);
		return commits;
	}
	
	private void parseLogForCommits(List<Commit> commits) {
		String output = spawner.spawnProcess(new String[] {"git", "log", "--reverse", "--no-merges"});

		String[] lines = output.split(System.getProperty("line.separator"));
		Commit currentCommit = null;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].matches(Resources.gitLogCommit)) {
				if(currentCommit != null) {
					commits.add(currentCommit);
					currentCommit = null;
				}
				currentCommit = new Commit();
				String[] split = lines[i].split(" ");
				currentCommit.setCommitID(split[1]);
			}
			else if(lines[i].matches(Resources.gitLogAuthor)) {
				Pattern pattern = Pattern.compile(Resources.gitLogAuthor);
				Matcher matcher = pattern.matcher(lines[i]);
				matcher.find();
				String email = matcher.group(1);
				currentCommit.setEmail(email);
			}
			else if(lines[i].matches(Resources.gitLogDate)) {
				try {
					Pattern pattern = Pattern.compile(Resources.gitLogDate);
					Matcher matcher = pattern.matcher(lines[i]);
					matcher.find();
					Date date = Resources.gitLogDateFormat.parse(matcher.group(1).trim());
					currentCommit.setDate(date);
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error parsing the date for commit.");
				}
			}
		}
		if(currentCommit != null)
			commits.add(currentCommit);
		
		Collections.sort(commits);
	}
	
	public String getAuthorOfCommit(String commit) {
		return spawner.spawnProcess(new String[] {"git", "show", "-s", "--format=%ce", commit})
				.replace("\n", "");
	}
	
	public String getHead() {
		String output = spawner.spawnProcess(new String[] {"git", "rev-parse", "HEAD"});
		String[] lines = output.split(System.getProperty("line.separator"));

		Pattern pattern = Pattern.compile(Resources.gitHead);
		Matcher matcher = pattern.matcher(lines[0]);

		if(matcher.find()) {
			return lines[0];
		}

		return null;
	}
}
