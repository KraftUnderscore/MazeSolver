package main;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import algorithms.AStarSolve;
import algorithms.BFSSolve;
import algorithms.DFSSolve;
import algorithms.DjikstraSolve;
import algorithms.GreedySolve;
import algorithms.SolvingAlgorithm;
import data.Maze;
import data.SolutionData;
import data.SolutionRequest;
import graphics.GUI;
import logic.MazeCreator;
import logic.Timer;

public class Manager {
		
	public static void main(String args[]) {
		//wlaczyc interfejs
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI.showGUI();
			}
		});
		
	}
	//wzywane w gui
	public static SolutionData request(SolutionRequest req, boolean showVis) {
		
		SolvingAlgorithm algo = null;
		//mazecreator - tworzenie maze i ich zapis
		//timer - liczy czas wykonywania zadania
		MazeCreator mc = new MazeCreator();
		Timer t = new Timer();
		
		//to bedzie zwracane
		int totalNodes=0;
		double[] solveTime = new double[req.algo.length];
		double mapCreationTime;
		int[] pathLengths = new int[req.algo.length];
		String[] algorithm = req.algo;
		BufferedImage[] solution = new BufferedImage[req.algo.length];
		t.start();
		Maze m = mc.createMaze(req.path);
		mapCreationTime=t.elapsedms();
		totalNodes = m.getGraph().getTotalNodes();
		
		for(int i=0; i<req.algo.length; i++) {
        	switch(req.algo[i]) {
        	case "DFS":
        		algo = new DFSSolve();
        		break;
        	case "BFS":
        		algo = new BFSSolve();
        		break;
        	case "Dijkstra":
        		algo = new DjikstraSolve();
        		break;
        	case "Greedy":
        		algo = new GreedySolve();
        		break;
        	case "AStar":
        		algo = new AStarSolve();
        		break;
        	}
        	
        	if(!req.algo[i].equals("")) {
            	t.start();
            	Maze s = algo.SolveMaze(m);
            	solveTime[i]= t.elapsedms();
        		solution[i] = mc.saveMaze(req.path, req.algo[i], s, showVis);
        		pathLengths[i] = s.getLength();
        	}
        }
		//zwraca dane wszystkich wybranych rozwiazan
		return new SolutionData(totalNodes, solveTime, mapCreationTime, algorithm, solution, pathLengths);
	}
}
