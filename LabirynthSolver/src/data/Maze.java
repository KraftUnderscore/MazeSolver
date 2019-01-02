package data;

public class Maze {
	
	private int[][] maze=null;
	private int ex;
	private int ey;
	private Graph mazeG;
	private boolean[]visited;
	private int[]solution;
	
	public Maze(int[][] m, int x, int y) {
		maze = m;
		ex = x;
		ey = y;
	}
	
	public void Solution(boolean[] v, int[] s) {
		visited = v;
		solution = s;
	}
	
	public void setGraph(Graph G) {
		mazeG = G;
	}
	
	public Graph getGraph() {
		return mazeG;
	}
	
	public int getValue(int x, int y) {
		return maze[x][y];
	}
	
	public int getEX() {
		return ex;
	}
	
	public int getEY() {
		return ey;
	}
	
	public int getWidth() {
		return maze[0].length;
	}
	
	public int getHeight() {
		return maze.length;
	}
}
