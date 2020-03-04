package data;

import java.awt.image.BufferedImage;
public class Maze {
	
	private int[][] maze=null;
	private int[][]	values=null;
	private int ex;
	private int ey;
	private Graph mazeG;
	private BufferedImage img;
	private boolean[]visited;
	private int[]solution;
	private int length;
	
	public Maze(int[][] m, int[][] v, int x, int y, BufferedImage i) {
		maze = m;
		values = v;
		ex = x;
		ey = y;
		img = i;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	//ustalenie rozwiazania i informacji o rozwiazaniu
	public void solution(boolean[] v, int[] s, int l) {
		visited = v;
		solution = s;
		length = l;
	}
	
	public void setGraph(Graph G) {
		mazeG = G;
	}
	
	public Graph getGraph() {
		return mazeG;
	}
	
	public int[] getSolution(){
		return solution;
	}
	
	public boolean[] getVisited(){
		return visited;
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public int getWall(int x, int y) {
		return maze[x][y];
	}
	
	public int getValue(int x, int y) {
		return values[x][y];
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
	//dlugosc trasy rozwiazania
	public int getLength() {
		return length;
	}
}
