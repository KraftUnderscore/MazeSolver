package main;

import algorithms.*;
import data.MazeCreator;

public class TEST {
	public static void main(String args[]) {
		SolvingAlgorithm dfs = new DFSSolve();
		MazeCreator mc = new MazeCreator();
		dfs.SolveMaze(mc.createMaze("10x10.png"));
		//dfs.SolveMaze(mc.createMaze("normal.png"));
	}
}
