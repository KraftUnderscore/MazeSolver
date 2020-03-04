package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import data.Graph;
import data.Maze;
import data.Node;

public class DFSSolve implements SolvingAlgorithm{

	private Stack<Integer> nodesToGo = new Stack<Integer>();
	
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
		
		DFS();
		
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
	
	private boolean DFS() {
		boolean isSolved = false;
		//dodaj pierwszy wierzcho³ek do stosu
		nodesToGo.add(0);
		//dopóki s¹ wierzcho³ki w stosie
		while(nodesToGo.size()>0) {
			//zdejmij pierwszy wierzcho³ek
			int c_node = nodesToGo.pop();
			Node c_N = N.get(c_node);
			//ustaw go jako odwiedzony
			visited[c_node] = true;
			//jeœli to koñcowy zakoñcz
			if(c_N.getX() == X && c_N.getY() == Y) {
				isSolved = true;
				break;
			}
			//dla ka¿dego s¹siada
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				//jeœli nie jest odwiedzony
				if(!visited[NN.getID()]) {
					//ustaw dla aktualnego s¹siada aktualny wierzcho³ek jako poprzedni
					solution[NN.getID()]=c_node;
					//dodaj do stosu
					nodesToGo.push(NN.getID());
				}
			}			
		}
		return isSolved;
	}

}
