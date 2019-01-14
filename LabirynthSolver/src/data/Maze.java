package data;

import java.awt.image.BufferedImage;
//TODO make sure to check if values aren't null
public class Maze {
	
	private int[][] maze=null;
	private int ex;
	private int ey;
	private Graph mazeG;
	private boolean[]visited;
	private int[]solution;
	private BufferedImage img;
	private int length;
	
	public Maze(int[][] m, int x, int y, BufferedImage i) {
		maze = m;
		ex = x;
		ey = y;
		img = i;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

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

	public int getLength() {
		return length;
	}
}
