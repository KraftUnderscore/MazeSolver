package GUI_przyk³ad1;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProstyGUI1 
{
	
public static void main(String[] args) 
{
	
	JFrame ramka = new JFrame();
	
	
	JButton przycisk1 = new JButton("Kliknij mnie 1");
	JButton przycisk2 = new JButton("Kliknij mnie 2");
	JButton przycisk3 = new JButton("Kliknij mnie 3");
	JButton przycisk4 = new JButton("Kliknij mnie 4");
	JButton przycisk5 = new JButton("Kliknij mnie 5");
	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JPanel panel5 = new JPanel();
	
	
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	ramka.getContentPane().add(BorderLayout.NORTH, panel1);
	ramka.getContentPane().add(BorderLayout.WEST, panel2);
	
	//Tu dodamy dwa kolejne komponenty - tj. do panelu 3, w centralnej czêœci
	ramka.getContentPane().add(BorderLayout.CENTER, panel3);
	
	ramka.getContentPane().add(BorderLayout.EAST, panel4);
	ramka.getContentPane().add(BorderLayout.SOUTH, panel5);
	
/*	
	Zmiana mened¿era uk³adu dla panela 3 - czyli elementy na panelu 3, 
	bêd¹ zarz¹dzane przez mened¿er BoxLayout - pionowo*/
	panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
	
	//Dodajemy JLabel
	panel3.add(new JLabel("Etykieta"));
	
	//Dodajemy JLabel
	JTextField text = new JTextField(20);
	panel3.add(text);
	text.setText("Jakiœ tekst");
	
	
	panel1.add(przycisk1);
	panel2.add(przycisk2);
	panel3.add(przycisk3);
	panel4.add(przycisk4);
	panel5.add(przycisk5);
	
	ramka.setSize(300,120);
	ramka.pack();	
	ramka.setResizable(false);
	ramka.setVisible(true);
	}

}