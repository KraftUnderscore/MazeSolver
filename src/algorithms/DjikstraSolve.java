package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import data.Graph;
import data.Maze;
import data.Node;

public class DjikstraSolve implements SolvingAlgorithm{

	class nodeSort implements Comparator<Node> {
	    @Override
	    public int compare(Node a, Node b) {
	    	return a.getDjikstra() - b.getDjikstra();
	    }
	}
	
	//private Stack<Integer> nodesToGo = new Stack<Integer>();
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
		
		Djikstra();
		
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
	
	private boolean Djikstra() {
		boolean isSolved = false;
		//ustaw warto�� 0 dla pocz�tkowego wierzcho�ka
		N.get(0).setDjikstra(0);
		//dodaj pierwszy wierzcho�ek do kolejki priorytetowej
		nodesToGo.add(N.get(0));
		//dop�ki s� wierzcho�ki w kolejce
		while(nodesToGo.size()>0) {
			//wez wierzcho�ek o najmniejszej warto�ci
			Node c_N = nodesToGo.poll();
			int c_node = c_N.getID();
			//oznacz jako odwiedzony
			visited[c_node] = true;
			//je�li ko�cowy zako�cz
			if(c_N.getX() == X && c_N.getY() == Y) {
				isSolved = true;
				break;
			}
			//dla ka�dego s�siada
			for(int i=0; i<graph.get(c_node).size(); i++) {
				Node NN = graph.get(c_node).get(i);
				//je�li nie by� odwiedzony
				if(!visited[NN.getID()]) {
					int nextDist;		
					//je�li droga do niego to niesko�czono��
					if(NN.getDjikstra()==Integer.MAX_VALUE) {
						//ustaw warto�� jako warto�� do poprzedniego wierzcho�ka + warto�� aktualnego wierzcho�ka
						nextDist = c_N.getDjikstra()+NN.getValue();
					}else {
						//zwi�ksz warto�� o warto�� aktualnego wierzcho�ka
						nextDist = NN.getDjikstra()+NN.getValue();
					}
					//je�li dystans jest kr�tszy od aktualnego dystansu
					if(nextDist<NN.getDjikstra()) {
						//je�li jeszcze nie ma wierzcho�ka to dodaj
						if(!nodesToGo.contains(NN)) {
							nodesToGo.add(NN);
						}
						//ustaw jego warto�� na aktualny dystans
						NN.setDjikstra(nextDist);
						//ustaw jego poprzedni wierzcho�ek
						solution[NN.getID()]=c_node;
					}
				}
			}
		}
		return isSolved;
	}

}
