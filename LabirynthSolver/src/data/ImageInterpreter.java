package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageInterpreter {
	
	BufferedImage img;
	
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
		
		return new Maze(maze, ex, maze[0].length-1);
	}
	/*public void SaveImage(String path) {
		try {
			ImageIO.write(img, "png", new File(path));
		}catch(IOException e) {
			System.out.println("Blad podczas zapisywania pliku.\n");
			e.printStackTrace();
		}
	}
	
	
	
	public int GetImgHeight() {
		return img.getHeight();
	}
	
	public int GetImgWidth() {
		return img.getWidth();
	}
	
	public int GetPixel(int x, int y) {
		return img.getRGB(x, y);
	}
	
	public void SetPixel(int x, int y, int vRGB) {
		img.setRGB(x, y, vRGB);
	}*/
}
