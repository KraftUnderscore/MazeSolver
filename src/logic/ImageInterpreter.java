package logic;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import data.Maze;
import data.Node;

public class ImageInterpreter {
	
	private BufferedImage img;
	
	//wczytanie pliku
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
	
	//konwersja pliku na argb (bo wiekszosc labiryntow to monochromatyczne mapy bitowe)
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
	
	//konwersja z obrazu na int[][] wartosci
	private Maze convert() {
		//wyjsciowy X (Y to zawsze ostatni wiersz)
		int ex=0;
		//numerowanie dla ID wierzcholkow
		int counter=0;
		int[][] maze = new int[img.getWidth()][img.getHeight()];
		int[][] mazeValues = new int[img.getWidth()][img.getHeight()];
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[i].length; j++) {
				int rgb = img.getRGB(j, i);
				if(rgb==-1) {
					maze[j][i]=counter++;
					if(i==maze.length-1)ex=j;
					mazeValues[j][i]=1;
				}else if(rgb==-4621737){
					maze[j][i]=counter++;
					if(i==maze.length-1)ex=j;
					mazeValues[j][i]=50;
				}else {
					maze[j][i]=-1;
				}
			}
		}
		return new Maze(maze, mazeValues, ex, maze[0].length-1, img);
	}
	
	//kopiowanie obrazu
	private BufferedImage copy(BufferedImage img) {
		ColorModel cm = img.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = img.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	//konwersja z rozwiaznego Maze do obrazu
	public BufferedImage MazeToImage(String path, String algorithm, Maze maze, boolean showVis) {
		//utworzenie ArrayList<Node> z LinkedList w grafie (dla szybkosci)
		ArrayList<Node> n = new ArrayList<Node>(maze.getGraph().getNodes());
		img = copy(maze.getImg());
		
		//najpierw odwiedzone wierzcholki
		if(showVis) {
			boolean vis[] = maze.getVisited();
			Color visColor = new Color(0, 70, 150);
			for(int i=0; i<vis.length; i++) {
				if(vis[i]) {
					int x = n.get(i).getX();
					int y = n.get(i).getY();
					img.setRGB(x, y, visColor.getRGB());
				}
			}
		}
		
		//teraz droga
		int sol[] = maze.getSolution();
		Color solColor = new Color(255,0,0);
		for(int i=0; i<sol.length; i++) {
			int x = n.get(sol[i]).getX();
			int y = n.get(sol[i]).getY();
			img.setRGB(x, y, solColor.getRGB());
		}
		return img;
	}
	
	public static void saveImage(String path, String name, BufferedImage img) {
		try {
			ImageIO.write(img, "png", new File(path+name+".png"));
		}catch(IOException e) {
			System.out.println("Blad podczas zapisywania pliku.\n");
			e.printStackTrace();
		}
	}
}
