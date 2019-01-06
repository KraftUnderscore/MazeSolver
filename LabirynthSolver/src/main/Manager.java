package main;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import algorithms.DFSSolve;
import algorithms.SolvingAlgorithm;
import data.Maze;
import data.MazeCreator;
import data.SolutionData;
import data.SolutionRequest;
import data.Timer;
import graphics.GUI;

public class Manager {
	
	public static void main(String args[]) {
		/*String[] fs = {"10x10.png"};
		String[] fs = {"5x5.png", "10x10.png", "tiny.png", "small.png", "normal.png", "50x50.png", "100x100.png"};
		String[] fs = {"perfect2k.png"};
		boolean Debug=true;
		String _debug;
		
		Timer t = new Timer();
		
		SolvingAlgorithm dfs = new DFSSolve();
		MazeCreator mc = new MazeCreator();
		_debug="Start\n";
		for(int i=0; i<fs.length; i++) {
			_debug+="-------------------\n";
			_debug+="Tworze mape "+fs[i]+"\n";
			t.start();
			Maze m = mc.createMaze(fs[i]);
			_debug+="Stworzono w "+t.elapsedms()+"ms\nRozwiazuje DFS.\n";
			t.start();
			m = dfs.SolveMaze(m);
			_debug+="Rozwiazano w "+t.elapsedms()+"ms\n";
			mc.saveMaze("D:\\Oracle\\workspace\\LabirynthSolver\\Solutions\\DFS"+fs[i], m);
		}
		if(Debug)
			System.out.println(_debug);*/
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI.showGUI();
			}
		});
	}
	
	public static SolutionData request(SolutionRequest req) {
		
		MazeCreator mc = new MazeCreator();
		SolvingAlgorithm dfs = new DFSSolve();
		Timer t = new Timer();
		
		int totalNodes=0;
		double[] solveTime = new double[3];
		double mapCreationTime;
		String[] algorithm = req.algo;
		BufferedImage[] solution = new BufferedImage[3];

		
		t.start();
		Maze m = mc.createMaze(req.path);
		mapCreationTime=t.elapsedms();
		totalNodes = m.getGraph().getTotalNodes();
		for(int i=0; i<req.algo.length; i++) {
        	switch(req.algo[i]) {
        	case "DFS":
        		t.start();
        		Maze s = dfs.SolveMaze(m);
        		solveTime[i]= t.elapsed();
        		solution[i] = mc.saveMaze(req.path, req.algo[i], s);
        		break;
        	case "BFS":
        		t.start();
        		Maze s1 = dfs.SolveMaze(m);
        		solveTime[i]= t.elapsed();
        		solution[i] = mc.saveMaze(req.path, req.algo[i], s1);
        		break;
        	case "Djikstra":
        		t.start();
        		Maze s2 = dfs.SolveMaze(m);
        		solveTime[i]= t.elapsed();
        		solution[i] = mc.saveMaze(req.path, req.algo[i], s2);
        		break;
        	}
        }
		
		return new SolutionData(totalNodes, solveTime, mapCreationTime, algorithm, solution);
	}
}