package main;

import algorithms.*;
import data.Maze;
import data.MazeCreator;

public class TEST {
	public static void main(String args[]) {
		SolvingAlgorithm dfs = new DFSSolve();
		MazeCreator mc = new MazeCreator();
		Maze m1 = dfs.SolveMaze(mc.createMaze("10x10.png"));
		mc.saveMaze("10x10S.png", m1);
		Maze m2 = dfs.SolveMaze(mc.createMaze("normal.png"));
		mc.saveMaze("normalS.png", m2);
		Maze m3 = dfs.SolveMaze(mc.createMaze("5x5.png"));
		mc.saveMaze("5x5s.png", m3);
	}
}
