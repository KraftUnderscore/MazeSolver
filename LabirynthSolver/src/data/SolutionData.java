package data;

import java.awt.image.BufferedImage;

public class SolutionData {
	public int totalNodes;
	public double[] solveTime;
	public double mapCreationTime;
	public String[] algorithm;
	public BufferedImage[] solution;

	
	public SolutionData(int totalNodes, double[] solveTime, double mapCreationTime,
			String[] algorithm, BufferedImage[] solution) {
		this.totalNodes = totalNodes;
		this.solveTime = solveTime;
		this.mapCreationTime = mapCreationTime;
		this.algorithm = algorithm;
		this.solution = solution;

	}
}
