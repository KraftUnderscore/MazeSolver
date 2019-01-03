package data;

public class Node {
	private int ID;
	private int x;
	private int y;
		
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
}
