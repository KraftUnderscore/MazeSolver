package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import data.Graph;
import data.Maze;
import data.Node;

public class BFSSolve implements SolvingAlgorithm{
	
	private boolean Debug = false;
	private String _debug = "";

	//private Stack<Integer> nodesToGo = new Stack<Integer>();
	private Queue<Integer> nodesToGo = new LinkedList<Integer>();
	
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
		nodesToGo.add(0);	//add starting node, ID always 0
		
		BFS();

		solved.solution(visited, solution);
		return solved;
	}
	
	private void BFS() {
		while(nodesToGo.size()>0) {
			int c_node = nodesToGo.poll();
			Node c_N = N.get(c_node);
			visited[c_node] = true;
			
			_debug="Teraz: "+c_node+"\n";
			
			if(c_N.getX() == X && c_N.getY() == Y) {
				_debug+="KONIEC\n";
				break;
			}
			_debug+="  Sasiedzi:\n";
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				_debug+="\t"+NN.getID();
				if(!visited[NN.getID()]) {
					solution[NN.getID()]=c_node;
					_debug+="<--";
					nodesToGo.add(NN.getID());
				}
				_debug+="\n";
			}
			
			if(Debug)System.out.println(_debug);
		}
		
		LinkedList<Integer>sol = new LinkedList<Integer>();
		int end = G.getTotalNodes()-1;
		int c = end;
		while(c!=0) {
			sol.addFirst(c);
			c=solution[c];
		}
		
		solution = new int[sol.size()];
		for(int i=0; i<sol.size(); i++) 
		{
			solution[i]=sol.get(i);
			if(Debug)
				System.out.println(sol.get(i));
		}
		
	}

}
