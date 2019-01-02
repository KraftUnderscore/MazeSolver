package data;

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
}
