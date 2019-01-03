package main;

import algorithms.*;
import data.Maze;
import data.MazeCreator;
import data.Timer;

public class TEST {
	

	
	public static void main(String args[]) {
		String[] fs = {"10x10.png"};
		//String[] fs = {"5x5.png", "10x10.png", "tiny.png", "small.png", "normal.png", "50x50.png", "100x100.png"};
		//String[] fs = {"perfect2k.png"};
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
			System.out.println(_debug);
		
	}
}
