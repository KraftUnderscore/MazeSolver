package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import data.Graph;
import data.Maze;
import data.Node;

public class DFSSolve implements SolvingAlgorithm{

	//TODO change to stack
	private Stack<Integer> nodesToGo = new Stack<Integer>();
	//private Queue<Integer> nodesToGo = new LinkedList<>();
	private Stack<Integer> path = new Stack<Integer>();
	
	private Graph G;
	private LinkedList<Node> N;
	private HashMap<Integer, LinkedList<Node>> graph;
	
	private boolean[] visited;
	private int[] solution;
	
	private int X;
	private int Y;
	
	
	@Override
	public Maze SolveMaze(Maze M) {
		Maze solved = M;
		X = M.getEX();
		Y = M.getEY();
		G = M.getGraph();
		N = G.getNodes();
		graph = G.getGraph();
		visited = new boolean[G.getTotalNodes()];
		solution = new int[G.getTotalNodes()];
		path.add(0);
		nodesToGo.add(0);	//add starting node, ID always 0
		DFS();
		for(int i=0; i<path.size(); i++) {
			System.out.print(path.pop()+"\n");
		}
		solved.Solution(visited, solution);
		return solved;
	}
	
	private void DFS() {
		System.out.println(X+" "+Y);
		while(nodesToGo.size()>0) {
			int c_node = nodesToGo.pop();
			Node c_N = N.get(c_node);
			visited[c_node] = true;
			System.out.println("Teraz: "+c_node);
			
			if(c_N.getX() == X && c_N.getY() == Y) {
				System.out.println("JAJ");
				break;
			}
			
			boolean back=true;
			//System.out.println(graph);
			System.out.println("Sasiedzi:");
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				System.out.print("\t"+NN.getID());
				if(!visited[NN.getID()]) {
					System.out.print("<--");
					nodesToGo.push(NN.getID());
					path.push(NN.getID());
					back=false;
					//break;
				}
				System.out.println();
			}
			System.out.println("Czy back: "+back);

			if(back) {
				path.pop();
			}
		}
	}

}
