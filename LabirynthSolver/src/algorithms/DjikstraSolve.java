package algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import data.Graph;
import data.Maze;
import data.Node;

public class DjikstraSolve implements SolvingAlgorithm{
	
	private boolean Debug = false;
	private String _debug = "";
	
	class nodeSort implements Comparator<Node> {
	    @Override
	    public int compare(Node a, Node b) {
	    	return a.getD() - b.getD();
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
		N.get(0).setD(0);
		nodesToGo.add(N.get(0));	//add starting node, ID always 0
		Djikstra();

		solved.solution(visited, solution, length);
		return solved;
	}
	
	private void Djikstra() {
		while(nodesToGo.size()>0) {
			Node c_N = nodesToGo.poll();
			int c_node = c_N.getID();
			visited[c_node] = true;
			//if(c_node%1000==0)
				System.out.println("Djikstra "+c_node);

			_debug="Teraz: "+c_node+" dist: "+c_N.getD()+"\n";
			
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
					if(NN.getD()==Integer.MAX_VALUE) {

						nextDist = c_N.getD()+1;
					}else {
						nextDist = NN.getD()+1;
					}
					
					if(nextDist<NN.getD()) {
						if(!nodesToGo.contains(NN)) {
							nodesToGo.add(NN);
						}
						NN.setD(nextDist);
						solution[NN.getID()]=c_node;
						_debug+="<--";
					}

				}
				_debug+=" Dist: "+NN.getD() + " vis: "+ visited[NN.getID()];
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