package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import data.Graph;
import data.Maze;
import data.Node;

public class GreedySolve implements SolvingAlgorithm{
	
	class nodeSort implements Comparator<Node> {
	    @Override
	    public int compare(Node a, Node b) {
	    	return a.getGreedy() - b.getGreedy();
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
		Greedy();
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
	
	private int dist(Node a) {
		return Math.abs(X-a.getX()) + Math.abs(Y-a.getY());
		//return Math.abs((X-a.getX())*(X-a.getX())+(Y-a.getY())*(Y-a.getY()));
	}
	
	private boolean Greedy() {
		//ustawienie poczatkowego Node
		N.get(0).setGreedy(0);
		nodesToGo.add(N.get(0));
		
		boolean isSolved = false;
		//dopoki sa nastepne wierzcholki
		while(nodesToGo.size()>0) {
			//pobrac pierwszy z kolejki
			Node currentNode = nodesToGo.poll();
			int currentNodeID = currentNode.getID();
			//oznaczyc jako odwiedzony
			visited[currentNodeID] = true;
			//jesli rowna sie wspolrzednym koncowym to zakoncz
			if(currentNode.getX() == X && currentNode.getY() == Y) {
				isSolved=true;
				break;
			}
			//dla kazdego sasiada
			for(int i=0; i<graph.get(currentNodeID).size(); i++) {
				Node nextNode = graph.get(currentNodeID).get(i);
				//jesli nie byl odwiedzony
				if(!visited[nextNode.getID()]) {
					//liczyc dystans
					int nextDist = dist(nextNode);		
					//jesli nie ma go w kolejce to dodajemy
					if(!nodesToGo.contains(nextNode)) {
						nodesToGo.add(nextNode);
					}
					//ustawiamy jego dystans na nowy
					nextNode.setGreedy(nextDist);
					//poprzednim wierzcholkiem NN jest c_node
					solution[nextNode.getID()]=currentNodeID;
				}
			}			
		}
		return isSolved;
	}
}
