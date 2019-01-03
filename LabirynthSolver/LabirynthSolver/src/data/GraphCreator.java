package data;

import java.util.LinkedList;

public class GraphCreator {
	
	private Graph G;
	
	private void CreateNodes(Maze M) {
		int counter=0;
		for(int i=0; i<M.getHeight(); i++) {
			for(int j=0; j<M.getWidth(); j++) {
				if(M.getValue(j, i)==0) {
					G.addNode(new Node(counter++, j, i));
				}
			}
		}
		//System.out.println("Total nodes: "+counter);
	}
	
	private void CreateEdges() {
		int n;	//ilosc sasiadow, nie wieksza niz 4
		LinkedList<Node> N = G.getNodes();
		for(int i=0; i<N.size(); i++) {
			n=0;
			Node n1 = N.get(i);
			int x = n1.getX();
			int y = n1.getY();
			for(int j=0; j<N.size(); j++) {
				if(j!=i && n<4) {
					Node n2 = N.get(j);
					int x2 = n2.getX();
					int y2 = n2.getY();
					
					if( x == x2 && y-1 == y2 ||
						x-1 == x2 && y == y2 ||
						x == x2 && y+1 == y2 ||
						x+1 == x2 && y == y2) {
						
						G.addEdge(n1, n2);
						n++;
					}
				}
			}
		}
	}	
	
	public Graph CreateGraph(Maze M) {
		G = new Graph();
		CreateNodes(M);
		CreateEdges();
		//System.out.println(G);
		return G;
	}
}
