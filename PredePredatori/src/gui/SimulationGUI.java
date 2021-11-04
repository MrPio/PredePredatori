package gui;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.TabableView;
import javax.swing.text.AttributeSet.ColorAttribute;

import system.Enviroment;
import system.Food;
import system.Pray;
import java.awt.Window.Type;
import java.awt.*;
import java.util.HashMap;

public class SimulationGUI {
	public static JFrame frame=new JFrame();
	static Enviroment enviroment=new Enviroment();
	private static Settings settings=new Settings();
	
	
    public SimulationGUI() {
		initialize();
	}
	public static void main(String[] args) {
		//TODO set Enviroment Size
		NewEnviroment newenv=new NewEnviroment();
		newenv.setVisible(true);
		while(newenv.isVisible()) {
			try {Thread.sleep(100);} catch (InterruptedException e1) {}
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulationGUI window = new SimulationGUI();
					window.frame.setVisible(true);
				}
				catch (Exception e) {e.printStackTrace();}
				
				settings.main(null);
			}
		});
	}

	private void initialize() {		
		try {
			Pray.pray_png=ImageIO.read(getClass().getResource("/pray.png").openStream());
			Food.food_png=ImageIO.read(getClass().getResource("/food.png").openStream());
			Pray.pray_png_red=ImageIO.read(getClass().getResource("/pray_red.png").openStream());
			Pray.pray_png_green=ImageIO.read(getClass().getResource("/pray_green.png").openStream());
			Pray.pray_png_blue=ImageIO.read(getClass().getResource("/pray_blue.png").openStream());
		} catch (IOException e) {e.printStackTrace();};
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setResizable(false);
		frame.getContentPane().setBackground( Color.DARK_GRAY );
		frame.getContentPane().setLayout(null);
		
		Dimension screen_size=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds( 
				(int)screen_size.getWidth()/2-enviroment.SIZE_X/2,
				(int)screen_size.getHeight()/2-enviroment.SIZE_Y/2,
				enviroment.SIZE_X,
				enviroment.SIZE_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}

	public static void setJLabelLocationGui(JLabel image,int X,int Y) {
		int new_pos_y=(int)(((double)Y/enviroment.SIZE_Y)*((double)enviroment.SIZE_Y-40));
		int new_pos_x=(int)(((double)X/enviroment.SIZE_X)*((double)enviroment.SIZE_X-10));
		image.setLocation(new_pos_x-(enviroment.SIZE_X)/2,new_pos_y-(enviroment.SIZE_Y)/2);
	}

	
}
