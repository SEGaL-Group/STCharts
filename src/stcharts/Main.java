package stcharts;

import java.util.List;

import models.Commit;
import git.GitController;

public class Main
{
	public static void main(String[] args) {
		Resources.repository = "/home/jordan/Documents/database";
		
		GitController gc = new GitController();
		List<Commit> commits = gc.getAllCommits();
		int x = 0;
		x = x + x;
	}
}
