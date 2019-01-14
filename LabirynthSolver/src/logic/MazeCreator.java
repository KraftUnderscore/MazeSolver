package logic;

import java.awt.image.BufferedImage;

import data.Maze;

public class MazeCreator {
	
	private GraphCreator gc;
	private ImageInterpreter ii;
	
	public MazeCreator() {
		gc = new GraphCreator();
		ii = new ImageInterpreter();
	}
	
	public Maze createMaze(String path) {
		Maze m = ii.ImageToMap(path);
		m.setGraph(gc.CreateGraph(m));
		return m;
	}
	
	public BufferedImage saveMaze(String path, String algorithm, Maze maze) {
		return ii.MazeToImage(path, algorithm, maze);
	}

}
