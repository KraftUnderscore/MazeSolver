package data;

public class Node {
	private int ID;
	private int x;
	private int y;
	private int value=1;
	private int djikstra = Integer.MAX_VALUE;
	private int greedy=0;
	private int astar = Integer.MAX_VALUE;
		
	public Node(int ID, int x, int y, int v) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		value = v;
	}
	
	@Override
	public String toString() {
		return "Node: "+ID+" ("+x+", "+y+")";
	}
	
	public boolean equals(Node n) {
		return this.ID == n.getID();
	}
	
	public int getID() {
		return ID;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setDjikstra(int d) {
		djikstra = d;
	}
	
	public int getDjikstra() {
		return djikstra;
	}
	
	public void setGreedy(int g) {
		greedy = g;
	}

	public int getGreedy() {
		return greedy;
	}
	
	public void setAstar(int Astar) {
		astar = Astar;
	}
	
	public int getAstar() {
		return astar;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
