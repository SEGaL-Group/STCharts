package models;

public class Edge
{
	private int user_a;
	private int user_b;
	private float weight;
	private int nid;
	
	public Edge(int user_a, int user_b)
	{
		super();
		this.user_a = user_a;
		this.user_b = user_b;
	}

	public Edge(int user_a, int user_b, float weight)
	{
		super();
		this.user_a = user_a;
		this.user_b = user_b;
		this.weight = weight;
	}

	public Edge(int user_a, int user_b, int nid)
	{
		super();
		this.user_a = user_a;
		this.user_b = user_b;
		this.nid = nid;
	}

	public Edge(int user_a, int user_b, float weight, int nid)
	{
		super();
		this.user_a = user_a;
		this.user_b = user_b;
		this.weight = weight;
		this.nid = nid;
	}

	public int getUser_a()
	{
		return user_a;
	}

	public void setUser_a(int user_a)
	{
		this.user_a = user_a;
	}

	public int getUser_b()
	{
		return user_b;
	}

	public void setUser_b(int user_b)
	{
		this.user_b = user_b;
	}

	public float getWeight()
	{
		return weight;
	}

	public void setWeight(float weight)
	{
		this.weight = weight;
	}

	public int getNid()
	{
		return nid;
	}

	public void setNid(int nid)
	{
		this.nid = nid;
	}
}
