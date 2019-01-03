package data;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
	
	private LinkedList<Node> nodes;
	//TODO change linkedlist<node> to integer
	private HashMap<Integer, LinkedList<Node>> graph;
	
	public Graph() {
		nodes = new LinkedList<Node>();
		graph = new HashMap<Integer, LinkedList<Node>>();
	}
	
	@Override
	public String toString() {
		String output="Total nodes: "+nodes.size()+"\n";
		for(int i=0; i<nodes.size(); i++) {
			output+=nodes.get(i)+"\n";
			for(int j=0; j<graph.get(nodes.get(i).getID()).size(); j++) {
				output+="\t"+graph.get(nodes.get(i).getID()).get(j)+"\n";
			}
		}
		return output;
	}
	
	public void addNode(Node n) {
		if(!graph.containsKey(n.getID())) {
			graph.put(n.getID(), new LinkedList<Node>());
			nodes.add(n);
		}
	}
	
	public int getTotalNodes() {
		return nodes.size();
	}
	
	public LinkedList<Node> getNodes(){
		return nodes;
	}
	
	public HashMap<Integer, LinkedList<Node>> getGraph(){
		return graph;
	}
	
	public void addEdge(Node n1, Node n2) {
		if(!n1.equals(n2)) {
			if(graph.containsKey(n1.getID())) {
				graph.get(n1.getID()).add(n2);
				//graph.get(n2.getID()).add(n1);
			}
		}
	}

}
