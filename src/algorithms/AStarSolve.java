package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import data.Graph;
import data.Maze;
import data.Node;

public class AStarSolve implements SolvingAlgorithm{
	
	class nodeSort implements Comparator<Node> {
	    @Override
	    public int compare(Node a, Node b) {
	    	return a.getAstar() - b.getAstar();
	    }
	}
	
	private PriorityQueue<Node> nodesToGo = new PriorityQueue<Node>(new nodeSort());
	
	private ArrayList<Node> N;
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
		Graph G = M.getGraph();
		int totalNodes = G.getTotalNodes();
		Maze solved = M;
		X = M.getEX();
		Y = M.getEY();
		N = new ArrayList<Node>(G.getNodes());
		graph = G.getGraph();
		visited = new boolean[totalNodes];
		solution = new int[totalNodes];
		System.out.println(AStar());		
		reconstructPath(totalNodes);
		solved.solution(visited, solution, length);
		return solved;
	}
	
	private void reconstructPath(int n) {
		LinkedList<Integer>sol = new LinkedList<Integer>();
		int end = n-1;
		int c = end;
		while(c!=0) {
			sol.addFirst(c);
			c=solution[c];
		}
		sol.addFirst(0);
		solution = new int[sol.size()];
		for(int i=0; i<sol.size(); i++) 
		{
			length++;
			solution[i]=sol.get(i);
		}
	}
	
	//praktycznie taki sam jak Dijkstra
	private boolean AStar() {
		nodesToGo.add(N.get(0));
		N.get(0).setAstar(0);
		boolean isSolved=false;
		while(nodesToGo.size()>0) {
			Node c_N = nodesToGo.poll();
			int c_node = c_N.getID();
			visited[c_node] = true;
			if(c_N.getX() == X && c_N.getY() == Y) {
				isSolved=true;
				break;
			}
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				if(!visited[NN.getID()]) {
					int nextDist;		
					//jedynie przy wyliczaniu tych wartoœci uwzglêdniamy równie¿ dystans do celu
					if(NN.getAstar()==Integer.MAX_VALUE) {
						nextDist = c_N.getAstar()+NN.getValue()+dist(NN);
					}else {
						nextDist = NN.getAstar()+NN.getValue()+dist(NN);
					}					
					if(nextDist<NN.getAstar()) {
						if(!nodesToGo.contains(NN)) {
							nodesToGo.add(NN);
						}
						NN.setAstar(nextDist);
						solution[NN.getID()]=c_node;
					}
				}
			}			
		}
		return isSolved;
	}

}
