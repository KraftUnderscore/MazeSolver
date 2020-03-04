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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import data.SolutionData;
import data.SolutionRequest;
import logic.ImageInterpreter;
import main.Manager;
 
public class GUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
		
	private Border emptyBorder = BorderFactory.createEmptyBorder(10,10,10,10);
	
	private JTabbedPane tabbedPane;
	
	private JFileChooser fileChooser;
	
	private JLabel fileOpenPath;
	private JLabel fileSavePath;
	private JLabel chosenAlgorithm;
	private JLabel mapTime;
	private JLabel mapInfo;
	
	private JCheckBox dfs;
	private JCheckBox bfs;
	private JCheckBox djikstra;
	private JCheckBox greedy;
	private JCheckBox astar;
	private JCheckBox showVis;
	
	private JButton solve, openFile, saveFile;
	
	private String[] algoToUse = {"", "", "", "", ""};
	private String path="";
	private String savePath="";
	
    public GUI() {
    	
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
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
			    updateOpenFilePath();
			    updateTabbedPane();
			}
        }else if (e.getSource() == saveFile) {
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    savePath = selectedFile.getAbsolutePath();
			    updateSaveFilePath();
			}
        }else if(e.getSource() == solve) {
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
        		algoToUse[2]="Dijkstra";
        	}else {
        		algoToUse[2]="";
        	}
        	if(greedy.isSelected()) {
        		algoToUse[3]="Greedy";
        	}else {
        		algoToUse[3]="";
        	}
        	if(astar.isSelected()) {
        		algoToUse[4]="AStar";
        	}else {
        		algoToUse[4]="";
        	}

        	if(!path.equals("")) {
            	SolutionData s = Manager.request(new SolutionRequest(path, algoToUse), showVis.isSelected());
            	saveImages(s.algorithm, s.solution);
            	updateInfoLabels(s.mapCreationTime, s.totalNodes, s.solveTime, s.algorithm, s.pathLengths);
                updateTabbedPane(algoToUse, s.solution); 
                savePath="";
                updateSaveFilePath();
        	}
        }
	}
	 
    private JTabbedPane makeTabbedPane() {
    	JTabbedPane tabbedPane = new JTabbedPane();
    	JPanel originalImg;
    	
    	originalImg = new JPanel();
    	
		tabbedPane.addTab("Origina³", null, originalImg, null);
        
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
    	
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.CENTER, dataPanel);
    	result.setBorder(emptyBorder);

    	return result;
    }
    
    private JPanel makeDataPanel() {
    	JPanel result = new JPanel();
    	JPanel filePanel = makeFilePanel();
    	JPanel selectPanel = makeSelectPanel();
    	JPanel infoPanel = makeInfoPanel();
    	result.setLayout(new GridLayout(3,1));
    	
    	result.add(filePanel);
    	result.add(selectPanel);
    	result.add(infoPanel);
    	
    	return result;
    }
    
    private JPanel makeInfoPanel() {
    	JPanel result = new JPanel();
    	mapInfo = new JLabel("Iloœæ wierzcho³ków: 0");
    	mapTime = new JLabel("Mapa stworzona w 0 ms");
    	chosenAlgorithm = new JLabel("Algorytm: None");
    	chosenAlgorithm.setPreferredSize(new Dimension(300, 500));
    	result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
    	result.add(mapInfo);
    	result.add(mapTime);
    	result.add(chosenAlgorithm);
    	
    	return result;
    }
    
    private JPanel makeSelectPanel() {
    	JPanel result = new JPanel();
    	JPanel checkboxes = new JPanel();
    	JPanel solvePanel = new JPanel();
    	JLabel text = new JLabel("Wybierz algorytmy:");
    	solve = new JButton("Rozwi¹¿!");
    	dfs = new JCheckBox("DFS Algorithm");
    	bfs = new JCheckBox("BFS Algorithm");
    	djikstra = new JCheckBox("Dijkstra Algorithm");
    	greedy = new JCheckBox("Greedy Algorithm");
    	astar = new JCheckBox("A* Algorithm");
    	showVis = new JCheckBox("Poka¿ odwiedzone wierzcho³ki");
    	
    	checkboxes.setLayout(new GridLayout(5, 1));
    	checkboxes.add(dfs);
    	checkboxes.add(bfs);
    	checkboxes.add(djikstra);
    	checkboxes.add(greedy);
    	checkboxes.add(astar);
    	
    	solvePanel.setLayout(new GridLayout(2,1));
    	solvePanel.add(solve);
    	solvePanel.add(showVis);
    	
    	solve.addActionListener(this);
    	
    	result.setLayout(new BorderLayout());
    	result.add(BorderLayout.NORTH, text);
    	result.add(BorderLayout.CENTER, checkboxes);
    	result.add(BorderLayout.SOUTH, solvePanel);

    	
    	return result;
    }
    
    private JPanel makeFilePanel() {
    	JPanel result = new JPanel();
    	
    	JLabel title = new JLabel("Maze Solver");
    	JLabel author = new JLabel("by Sebastian Pieta");
    	
    	JPanel titlePanel = new JPanel();
    	titlePanel.setLayout(new GridLayout(2, 1));
    	titlePanel.add(title);
    	titlePanel.add(author);
    	
    	JPanel pathPanel = new JPanel();
    	pathPanel.setLayout(new GridLayout(4, 1));
    	
    	fileOpenPath = new JLabel("Œcie¿ka do pliku...");
    	fileSavePath = new JLabel("Œcie¿ka do zapisu...");
    	openFile = new JButton("Wybierz plik"); 
    	saveFile = new JButton("Zapisz do...");
    	
    	openFile.addActionListener(this);
    	saveFile.addActionListener(this);
    	
    	pathPanel.add(openFile);
    	pathPanel.add(fileOpenPath);
    	pathPanel.add(saveFile);
    	pathPanel.add(fileSavePath);

    	result.setLayout(new GridLayout(2, 1));
    	result.add(titlePanel);
    	result.add(pathPanel);
    	return result;
    }


    //needs some redesign
    private void updateOpenFilePath() {
    	fileOpenPath.setText(path);
    }
    
    private void updateSaveFilePath() {
    	fileSavePath.setText(savePath);
    }
    
    private void updateTabbedPane() {
		tabbedPane.setSelectedIndex(0);
		int t=tabbedPane.getTabCount();
    	for(int i=0; i<t; i++) {
    		tabbedPane.remove(0);
    	}
    	
    	JPanel originalImg = makeImagePanel();
		tabbedPane.addTab("Original", null, originalImg, null);

    }
    
    private void updateTabbedPane(String[] toAdd, BufferedImage[] solutions) {
		tabbedPane.setSelectedIndex(0);
		int t=tabbedPane.getTabCount();
        for(int i=0; i<t; i++) {
        	tabbedPane.remove(0);
        }
    	JPanel originalImg = makeImagePanel();
		tabbedPane.addTab("Original", null, originalImg, null);
        for(int i=0; i<toAdd.length; i++) {

        	if(solutions[i]!=null) {
	        	switch(toAdd[i]) {
	        	case "DFS":
	                JPanel dfsImg = makeImagePanel(solutions[i]);
	        		tabbedPane.addTab(toAdd[i], null, dfsImg, null);
	        		break;
	        	case "BFS":
	                JPanel bfsImg = makeImagePanel(solutions[i]);
	        		tabbedPane.addTab(toAdd[i], null, bfsImg, null);
	        		break;
	        	case "Dijkstra":
	                JPanel djikstraImg = makeImagePanel(solutions[i]);
	        		tabbedPane.addTab(toAdd[i], null, djikstraImg, null);
	        		break;
	        	case "Greedy":
	                JPanel greedyImg = makeImagePanel(solutions[i]);
	        		tabbedPane.addTab(toAdd[i], null, greedyImg, null);
	        		break;
	        	case "AStar":
	                JPanel astarImg = makeImagePanel(solutions[i]);
	        		tabbedPane.addTab(toAdd[i], null, astarImg, null);
	        		break;
	        	}	
        	}
        	
        }
    }
    
    private void saveImages(String[] names, BufferedImage[] imgs) {
    	if(!savePath.equals("")) {
    		for(int i=0; i<names.length; i++) {
    			if(!names[i].equals(""))ImageInterpreter.saveImage(savePath, names[i], imgs[i]);
    		}
    	}
    }
    
    private void updateInfoLabels(double mapTime, int nodes, double[] solveTime, String[] algorithms, int[] pathLengths) {
    	this.mapTime.setText("Mapa stworzona w: "+mapTime);
    	this.mapInfo.setText("Iloœæ wierzcho³ków: "+nodes);
    	String tmp = "Rozwiazania:\n";
    	for(int i=0; i<algorithms.length; i++) {
    		if(algorithms[i]!="") {
    			tmp+=algorithms[i]+" w "+solveTime[i]+" ms. Dystans "+pathLengths[i]+"\n";
    		}
    	}
    	System.out.println(tmp);
    	chosenAlgorithm.setText("<html>" + tmp.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
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