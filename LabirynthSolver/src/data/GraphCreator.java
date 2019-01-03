package data;

import java.util.LinkedList;

public class GraphCreator {
	
	private Graph G;
	private Timer t = new Timer();
	
	private void CreateNodes(Maze M) {
		int counter=0;
		for(int i=0; i<M.getHeight(); i++) {
			for(int j=0; j<M.getWidth(); j++) {
				if(M.getValue(j, i)==0) {
					G.addNode(new Node(counter++, j, i));
				}
			}
		}
	}
	//TODO too slow, takes ages with larger mazes
	private void CreateEdges(int width) {
		int n;	//ilosc sasiadow, nie wieksza niz 4
		LinkedList<Node> N = G.getNodes();
		for(int i=0; i<N.size(); i++) {
			n=0;
			if(i%1000 == 0) {
				double tmp = t.elapsedms();
				t.start();
				System.out.println("Node "+i+" out of "+N.size()+" in "+tmp+"ms.");				
			}
			Node n1 = N.get(i);
			int x = n1.getX();
			int y = n1.getY();
			
			//check only rows of nodes above or below, no need to check all nodes
			int max = i+width;
			if(max>=N.size())max=N.size();
			int min = i-width;
			if(min<0)min=0;
			
			//System.out.println(min+" "+max);
			for(int j=min; j<max; j++) {
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
		CreateEdges(M.getWidth());
		//System.out.println(G);
		return G;
	}
}
