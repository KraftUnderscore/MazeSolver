package data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImageInterpreter {
	
	private BufferedImage img;
	
	public Maze ImageToMap(String path) {
		try {
			img = ImageIO.read(new File(path));
			return convert();
		}catch(IOException e) {
			System.out.println("Blad podczas wczytywania pliku.\n"
					+ "Upewnij sie, ze sciezka zostala poprawnie wpisana");
		}
		return null;
	}
	
	private Maze convert() {
		int ex=0;
		int[][] maze = new int[img.getWidth()][img.getHeight()];
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[i].length; j++) {
				int rgb = img.getRGB(j, i);
				if(rgb==-1) {
					maze[j][i]=0;
					if(i==maze.length-1)ex=j;

				}else {
					maze[j][i]=1;
				}
			}
		}
		
		return new Maze(maze, ex, maze[0].length-1, img);
	}
	
	public void MazeToImage(String path, Maze maze) {
		LinkedList<Node> nodes = maze.getGraph().getNodes();
		img = maze.getImg();
		int sol[] = maze.getSolution();
		boolean vis[] = maze.getVisited();
		Color solColor = new Color(255,0,0);
		Color visColor = new Color(0, 70, 150);
		for(int i=0; i<vis.length; i++) {
			if(vis[i]) {
				img.setRGB(nodes.get(i).getX(), nodes.get(i).getY(), visColor.getRGB());
			}
		}
		
		for(int i=0; i<sol.length; i++) {
			img.setRGB(nodes.get(sol[i]).getX(), nodes.get(sol[i]).getY(), solColor.getRGB());
		}
		
		img.setRGB(nodes.get(0).getX(), nodes.get(0).getY(), solColor.getRGB());

		
		try {
			ImageIO.write(img, "png", new File(path));
		}catch(IOException e) {
			System.out.println("Blad podczas zapisywania pliku.\n");
			e.printStackTrace();
		}
	}
}
