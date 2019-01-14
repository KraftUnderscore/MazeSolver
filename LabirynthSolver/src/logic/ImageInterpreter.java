package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import data.Maze;
import data.Node;

public class ImageInterpreter {
	
	private BufferedImage img;
	
	public Maze ImageToMap(String path) {
		try {
			img = ImageIO.read(new File(path));
			img = convertToARGB(img);
			return convert();
		}catch(IOException e) {
			System.out.println("Blad podczas wczytywania pliku.\n"
					+ "Upewnij sie, ze sciezka zostala poprawnie wpisana");
		}
		return null;
	}
	
	private BufferedImage convertToARGB(BufferedImage image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(), image.getHeight(),
	        BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	private Maze convert() {
		int ex=0;
		int counter=0;
		int[][] maze = new int[img.getWidth()][img.getHeight()];
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[i].length; j++) {
				int rgb = img.getRGB(j, i);
				if(rgb==-1) {
					maze[j][i]=counter++;
					if(i==maze.length-1)ex=j;

				}else {
					maze[j][i]=-1;
				}
			}
		}
		return new Maze(maze, ex, maze[0].length-1, img);
	}
	private BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public BufferedImage MazeToImage(String path, String algorithm, Maze maze) {
		LinkedList<Node> nodes = maze.getGraph().getNodes();
		System.out.println("Copying...");
		img = deepCopy(maze.getImg());
		System.out.println("Saving...");
		int sol[] = maze.getSolution();
		boolean vis[] = maze.getVisited();
		Color solColor = new Color(255,0,0);
		Color visColor = new Color(0, 70, 150);
		for(int i=0; i<vis.length; i++) {
			if(vis[i]) {
				int x = nodes.get(i).getX();
				int y = nodes.get(i).getY();
				img.setRGB(x, y, visColor.getRGB());
				if(y%10==0)
					System.out.println("V "+x +" "+y);
			}
		}
		
		for(int i=0; i<sol.length; i++) {
			int x = nodes.get(sol[i]).getX();
			int y = nodes.get(sol[i]).getY();
			img.setRGB(x, y, solColor.getRGB());
			System.out.println("S "+x +" "+y);
		}
		
		img.setRGB(nodes.get(0).getX(), nodes.get(0).getY(), solColor.getRGB());
		//TODO save to file
		/*try {
			ImageIO.write(img, "png", new File(path));
		}catch(IOException e) {
			System.out.println("Blad podczas zapisywania pliku.\n");
			e.printStackTrace();
		}*/
		return img;

	}
}
