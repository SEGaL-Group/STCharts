package models;

import java.util.Date;
import java.util.List;

public class Commit implements Comparable<Commit>
{
	private String commitID;
	private String email;
	private Date date;
	
	private List<String> files;
	
	public Commit() {
		
	}

	public Commit(String commitID, String email, Date date)
	{
		super();
		this.commitID = commitID;
		this.email = email;
		this.date = date;
	}

	public Commit(String commitID, String email, Date date, List<String> files)
	{
		super();
		this.commitID = commitID;
		this.email = email;
		this.date = date;
		this.files = files;
	}
	
	@Override
	public int compareTo(Commit arg0)
	{
		return this.date.compareTo(arg0.getDate());
	}

	public String getCommitID()
	{
		return commitID;
	}

	public void setCommitID(String commitID)
	{
		this.commitID = commitID;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public List<String> getFiles()
	{
		return files;
	}

	public void setFiles(List<String> files)
	{
		this.files = files;
	}
	
}
