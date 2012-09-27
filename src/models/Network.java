package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Network
{
	public enum NetworkType {
		TECHNICAL, SOCIAL
	}
	
	private NetworkType networkType;
	private int iid;
	private int nid;
	private List<Edge> edges;
	
	private Timestamp commit_date;
	private int t_nid;

	public Network(NetworkType networkType, int iid)
	{
		super();
		this.networkType = networkType;
		this.iid = iid;
		
		edges = new ArrayList<Edge>();
	}

	public Network(NetworkType networkType, int iid, int nid)
	{
		super();
		this.networkType = networkType;
		this.iid = iid;
		this.nid = nid;
		
		edges = new ArrayList<Edge>();
	}

	public Network(NetworkType networkType, int iid, List<Edge> edges)
	{
		super();
		this.networkType = networkType;
		this.iid = iid;
		this.edges = edges;
	}

	public Network(NetworkType networkType, int iid, int nid, List<Edge> edges)
	{
		super();
		this.networkType = networkType;
		this.iid = iid;
		this.nid = nid;
		this.edges = edges;
	}

	public NetworkType getNetworkType()
	{
		return networkType;
	}

	public void setNetworkType(NetworkType networkType)
	{
		this.networkType = networkType;
	}

	public int getIid()
	{
		return iid;
	}

	public void setIid(int iid)
	{
		this.iid = iid;
	}

	public int getNid()
	{
		return nid;
	}

	public void setNid(int nid)
	{
		this.nid = nid;
	}

	public List<Edge> getEdges()
	{
		return edges;
	}

	public void setEdges(List<Edge> edges)
	{
		this.edges = edges;
	}

	public Timestamp getCommit_date()
	{
		return commit_date;
	}

	public void setCommit_date(Timestamp commit_date)
	{
		this.commit_date = commit_date;
	}

	public int getT_nid()
	{
		return t_nid;
	}

	public void setT_nid(int t_nid)
	{
		this.t_nid = t_nid;
	}
}
