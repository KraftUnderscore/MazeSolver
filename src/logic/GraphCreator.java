package logic;

import data.Graph;
import data.Maze;
import data.Node;

public class GraphCreator {
	
	private Graph G;
	
	private void CreateNodes(Maze M) {
		for(int i=0; i<M.getHeight(); i++) {
			for(int j=0; j<M.getWidth(); j++) {
				if(M.getWall(j, i)!=-1) {
					Node n = new Node(M.getWall(j, i), j, i, M.getValue(j, i));
					G.addNode(n);
					int maxj = j+1;
					int maxi = i+1;
					
					if(maxj>=M.getWidth()-1)maxj=M.getWidth()-1;
					
					if(maxi>=M.getHeight()-1)maxi=M.getHeight()-1;
					
					if(M.getWall(j, maxi)!=-1) {
						Node n1 = new Node(M.getWall(j, maxi), j, maxi, M.getValue(j, i));
						G.addNode(n1);
						G.addEdge(n, n1);
					}
					
					if(M.getWall(maxj, i)!=-1) {
						Node n2 = new Node(M.getWall(maxj, i), maxj, i, M.getValue(j, i));
						G.addNode(n2);
						G.addEdge(n, n2);
					}

				}
			}
		}
	}
	//SUPERNIEWYDAJNE
	/*private void CreateEdges(int width) {
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
	}	*/
	
	public Graph CreateGraph(Maze M) {
		G = new Graph();
		CreateNodes(M);
		G.sortNodes();
		//CreateEdges(M.getWidth());
		//System.out.println(G);
		return G;
	}
}
