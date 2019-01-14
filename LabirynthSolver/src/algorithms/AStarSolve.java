package algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import data.Graph;
import data.Maze;
import data.Node;

public class AStarSolve implements SolvingAlgorithm{
	
	private boolean Debug = false;
	private String _debug = "";
	
	class nodeSort implements Comparator<Node> {
	    @Override
	    public int compare(Node a, Node b) {
	    	return a.getAstar() - b.getAstar();
	    }
	}
	
	//private Stack<Integer> nodesToGo = new Stack<Integer>();
	private PriorityQueue<Node> nodesToGo = new PriorityQueue<Node>(new nodeSort());
	
	
	private Graph G;
	private LinkedList<Node> N;
	private HashMap<Integer, LinkedList<Node>> graph;
	
	private boolean[] visited;
	private int[] solution;
	private int length=0;
	
	private int X;
	private int Y;	
	
	//Manhattan distance
	private int dist(Node a) {
		return Math.abs(X-a.getX()) + Math.abs(Y-a.getY());
	}
	
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
		N.get(0).setAstar(0);
		nodesToGo.add(N.get(0));	//add starting node, ID always 0
		AStar();

		solved.solution(visited, solution, length);
		return solved;
	}
	
	private void AStar() {
		while(nodesToGo.size()>0) {
			Node c_N = nodesToGo.poll();
			int c_node = c_N.getID();
			visited[c_node] = true;
			//if(c_node%1000==0)
				System.out.println("A* "+c_node);
			_debug="Teraz: "+c_node+" dist: "+c_N.getAstar()+"\n";
			
			if(c_N.getX() == X && c_N.getY() == Y) {
				_debug+="KONIEC\n";
				break;
			}
			_debug+="  Sasiedzi:\n";
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				_debug+="\t"+NN.getID();
				if(!visited[NN.getID()]) {
					int nextDist;		
					if(NN.getAstar()==Integer.MAX_VALUE) {

						nextDist = c_N.getAstar()+1+dist(NN);
					}else {
						nextDist = NN.getAstar()+1+dist(NN);
					}
					
					if(nextDist<NN.getAstar()) {
						if(!nodesToGo.contains(NN)) {
							nodesToGo.add(NN);
						}
						NN.setAstar(nextDist);
						solution[NN.getID()]=c_node;
						_debug+="<--";
					}

				}
				_debug+=" Dist: "+NN.getAstar() + " vis: "+ visited[NN.getID()];
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
			length++;
			solution[i]=sol.get(i);
			if(Debug)
				System.out.println(sol.get(i));
		}
		
	}

}
