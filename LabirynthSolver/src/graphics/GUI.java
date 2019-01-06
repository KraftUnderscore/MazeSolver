package graphics;

 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import data.SolutionData;
import data.SolutionRequest;
import main.Manager;

 
public class GUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
	
	private JFileChooser fileChooser;
	
	private JProgressBar progress;
	private JLabel task;
	
	private JLabel filePath;
	private JLabel chosenAlgorithm;
	private JLabel mapTime;
	private JLabel solveTime;
	private JLabel mapInfo;
	
	JCheckBox dfs;
	JCheckBox bfs;
	JCheckBox djikstra;
	
	private JButton solve, openFile;
	
	private String[] algoToUse = {"", "", ""};
	//TODO might be able to use File everywhere instead of path
	private String path="";
	
    public GUI() {
    	
        super(new BorderLayout());
        
        fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        
        setPreferredSize(new Dimension(1200, 900));
        Dimension imgDim = new Dimension(900, 900);
        
        tabbedPane = makeTabbedPane();      
        tabbedPane.setPreferredSize(imgDim);
        
    	Dimension rightDim = new Dimension(300, 900);
        JPanel rightPanel = makeRightPanel();
        rightPanel.setLayout(new GridLayout(2, 1));
        rightPanel.setPreferredSize(rightDim);
        
        add(BorderLayout.WEST, tabbedPane);
        add(BorderLayout.EAST, rightPanel);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openFile) {
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    path = selectedFile.getAbsolutePath();
			    updateFilePath();
			    updateTabbedPane();
			}
        }else if(e.getSource()==solve) {
        	if(dfs.isSelected()) {
        		algoToUse[0]="DFS";
        	}else {
        		algoToUse[0]="";
        	}
        	if(bfs.isSelected()) {
        		algoToUse[1]="BFS";
        	}else {
        		algoToUse[1]="";
        	}
        	if(djikstra.isSelected()) {
        		algoToUse[2]="Djikstra";
        	}else {
        		algoToUse[2]="";
        	}
        	SolutionData s = Manager.request(new SolutionRequest(path, algoToUse));
        	
            updateTabbedPane(algoToUse, s.solution);  
        }
	}
	 
    private JTabbedPane makeTabbedPane() {
    	JTabbedPane tabbedPane = new JTabbedPane();
    	JPanel originalImg;
    	for(int i=0; i<tabbedPane.getTabCount(); i++) {
        	tabbedPane.remove(i);
        }
    	originalImg = new JPanel();
    	
		tabbedPane.addTab("Original", null, originalImg, null);
        
        return tabbedPane;
    }
     
    private JPanel makeImagePanel() {
    	JPanel result = new JPanel();
    	BufferedImage myPicture=null;
		try {
			myPicture = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon t = new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));
    	JLabel picLabel = new JLabel(t);
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.CENTER, picLabel);
    	return result;
    }
    
    private JPanel makeImagePanel(BufferedImage img) {
    	JPanel result = new JPanel();
		ImageIcon t = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));
    	JLabel picLabel = new JLabel(t);
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.CENTER, picLabel);
    	return result;
    }
    
    private JPanel makeRightPanel() {
    	JPanel result = new JPanel();
    	JPanel dataPanel = makeDataPanel();
    	JPanel progressPanel = makeProgressPanel();
    	progressPanel.setPreferredSize(new Dimension(300, 150));
    	
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.CENTER, dataPanel);
    	result.add(BorderLayout.SOUTH, progressPanel);
    	
    	return result;
    }
    
    private JPanel makeDataPanel() {
    	JPanel result = new JPanel();
    	JPanel filePanel = makeFilePanel();
    	JPanel selectPanel = makeSelectPanel();
    	JPanel infoPanel = makeInfoPanel();
    	
    	result.add(filePanel);
    	result.add(selectPanel);
    	result.add(infoPanel);
    	
    	return result;
    }
    
    private JPanel makeInfoPanel() {
    	JPanel result = new JPanel();
    	mapInfo = new JLabel("Number of nodes: 0");
    	mapTime = new JLabel("Map creation time: 0 ms");
    	chosenAlgorithm = new JLabel("Algorithm: None");
    	solveTime = new JLabel("Solved in 0 ms");
    	
    	result.setLayout(new GridLayout(4,1));
    	result.add(mapInfo);
    	result.add(mapTime);
    	result.add(chosenAlgorithm);
    	result.add(solveTime);
    	
    	return result;
    }
    
    private JPanel makeSelectPanel() {
    	JPanel result = new JPanel();
    	JPanel checkboxes = new JPanel();
    	JLabel text = new JLabel("Pick algorithms:");
    	solve = new JButton("Solve!");
    	dfs = new JCheckBox("DFS Algorithm");
    	bfs = new JCheckBox("BFS Algorithm");
    	djikstra = new JCheckBox("Djikstra Algorithm");
    	
    	checkboxes.setLayout(new GridLayout(3, 1));
    	checkboxes.add(dfs);
    	checkboxes.add(bfs);
    	checkboxes.add(djikstra);
    	
    	solve.addActionListener(this);
    	
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.NORTH, text);
    	result.add(BorderLayout.CENTER, checkboxes);
    	result.add(BorderLayout.SOUTH, solve);
    	
    	return result;
    }
    
    private JPanel makeFilePanel() {
    	JPanel result = new JPanel();
    	
    	JLabel title = new JLabel("Maze Solver");
    	JLabel author = new JLabel("by Sebastian Pieta");
    	
    	JPanel pathPanel = new JPanel();
    	pathPanel.setLayout(new GridLayout(2, 1));
    	
    	filePath = new JLabel("File path");
    	openFile = new JButton("Choose file");    	
    	
    	openFile.addActionListener(this);
    	
    	pathPanel.add(openFile);
    	pathPanel.add(filePath);
    	
    	result.setLayout(new GridLayout(3, 1));
    	result.add(title);
    	result.add(author);
    	result.add(pathPanel);
    	
    	return result;
    }
    
    private JPanel makeProgressPanel() {
    	JPanel result = new JPanel();
    	task = new JLabel("Zadanie");
    	//JProgressbar(currentNode, totalNodes):
    	progress = new JProgressBar(0, 100);
    	progress.setValue(0);
    	progress.setStringPainted(true);
    	
    	result.add(task);
    	result.add(progress);
    	return result;
    }
    
    private void updateProgress(int x) {
    	progress.setValue(x);
    }
    
    private void updateProgress(String x) {
    	task.setText(x);
    }

    //needs some redesign
    private void updateFilePath() {
    	filePath.setText(path);
    }
    
    private void updateTabbedPane() {
    	for(int i=0; i<tabbedPane.getTabCount(); i++) {
    		tabbedPane.remove(i);
    	}
    	JPanel originalImg = makeImagePanel();
		tabbedPane.addTab("Original", null, originalImg, null);

    }
    
    private void updateTabbedPane(String[] toAdd, BufferedImage[] solutions) {
    	JPanel originalImg = makeImagePanel();
        
        for(int i=0; i<tabbedPane.getTabCount(); i++) {
        	tabbedPane.remove(i);
        }
		tabbedPane.addTab("Original", null, originalImg, null);

        for(int i=0; i<toAdd.length; i++) {
        	if(solutions[i]!=null) {
	        	switch(toAdd[i]) {
	        	case "DFS":
	                JPanel dfsImg = makeImagePanel(solutions[0]);
	        		tabbedPane.addTab(toAdd[i], null, dfsImg, null);
	        		System.out.println(tabbedPane.getTabCount());
	        		break;
	        	case "BFS":
	                JPanel bfsImg = makeImagePanel(solutions[1]);
	        		tabbedPane.addTab(toAdd[i], null, bfsImg, null);
	        		break;
	        	case "Djikstra":
	                JPanel djikstraImg = makeImagePanel(solutions[2]);
	        		tabbedPane.addTab(toAdd[i], null, djikstraImg, null);
	        		break;
	        	}	
        	}
        	
        }
    }
    
    public static void showGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MazeSolver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Add content to the window.
        frame.add(new GUI(), BorderLayout.CENTER);
         
        //Display the window.
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

}