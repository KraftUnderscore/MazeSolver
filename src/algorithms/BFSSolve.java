package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import data.Graph;
import data.Maze;
import data.Node;

public class BFSSolve implements SolvingAlgorithm{
	
	private Queue<Integer> nodesToGo = new LinkedList<Integer>();
	
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
		
		BFS();
		
		reconstructPath(totalNodes);

		solved.solution(visited, solution, length);
		return solved;
	}
	
	//odtworzenie trasy rozwiazania
	private void reconstructPath(int n) {
		
		LinkedList<Integer>sol = new LinkedList<Integer>();
		int end = n-1;
		int c = end;
		//wrzucenie wszystkiego do linkedlist
		while(c!=0) {
			sol.addFirst(c);
			c=solution[c];
		}
		sol.addFirst(0);
		//konwersja na tablice
		solution = new int[sol.size()];
		for(int i=0; i<sol.size(); i++) 
		{
			length++;
			solution[i]=sol.get(i);
		}
	}
	
	private boolean BFS() {
		boolean isSolved = false;
		//dodanie pierwszego wierzcho³ka
		nodesToGo.add(0);
		//dopoki s¹ wierzcho³ki, do których mo¿na siê udaæ
		while(nodesToGo.size()>0) {
			//zdejmij z kolejki pierwszy wierzcho³ek
			int c_node = nodesToGo.poll();
			Node c_N = N.get(c_node);
			//ustaw go jako odwiedzony
			visited[c_node] = true;
			//jeœli to koñcowy to zakoñcz
			if(c_N.getX() == X && c_N.getY() == Y) {
				isSolved = true;
				break;
			}
			//dla ka¿dego s¹siada aktualnego wierzcho³ka
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				//jeœli nie jest odwiedzony
				if(!visited[NN.getID()]) {
					//ustaw dla aktualnego s¹siada aktualny wierzcho³ek jako poprzedni
					solution[NN.getID()]=c_node;
					//dodaj go do kolejki
					nodesToGo.add(NN.getID());
				}
			}
		}	
		return isSolved;
	}

}
