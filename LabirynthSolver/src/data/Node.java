package data;

public class Node {
	private int ID;
	private int x;
	private int y;
	private int djikstra = Integer.MAX_VALUE;
	private int greedy=0;
	private int astar = Integer.MAX_VALUE;
		
	public Node(int ID, int x, int y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
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
	
	public void setD(int d) {
		djikstra = d;
	}
	
	public int getD() {
		return djikstra;
	}

	public int getGreedy() {
		return greedy;
	}

	public void setGreedy(int greedy) {
		this.greedy = greedy;
	}
	
	public void setAstar(int Astar) {
		astar = Astar;
	}
	
	public int getAstar() {
		return astar;
	}
}
