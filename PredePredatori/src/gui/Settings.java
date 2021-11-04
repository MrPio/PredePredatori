package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import system.Enviroment;
import system.Pray;
import utilities.Point;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.Utilities;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings {

	private JFrame frame;
	private JButton spawn_pray;
	private JButton pause_resume;
	private JButton spawn_food;
	public static JButton start_stop;
	public static JLabel day_count;
	private JButton btnNewButton;
	private JButton spawn_pray_1;
	private JButton spawn_pray_2;
	private JButton spawn_pray_3;
	BufferedImage[] arrows=new BufferedImage[4];
	BufferedImage[] arrows_down=new BufferedImage[4];

	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Settings() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(160, 140, 294, 238);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JSpinner number_of_pray_to_spawn = new JSpinner();
		number_of_pray_to_spawn.setModel(new SpinnerNumberModel(1, 1, 999, 4));
		number_of_pray_to_spawn.setBounds(133, 79, 46, 20);
		frame.getContentPane().add(number_of_pray_to_spawn);
		
		JSpinner number_of_food_to_spawn = new JSpinner();
		number_of_food_to_spawn.setModel(new SpinnerNumberModel(1, 1, 999, 3));
		number_of_food_to_spawn.setBounds(133, 47, 46, 20);
		frame.getContentPane().add(number_of_food_to_spawn);
		
		spawn_pray = new JButton("Spawn Pray");
		spawn_pray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationGUI.enviroment.addPrayRandomly(
						(int)number_of_pray_to_spawn.getValue());
				//SimulationGUI.enviroment.start();
				//start_stop.setText("STOP");
			}
		});
		spawn_pray.setBounds(10, 76, 115, 23);
		frame.getContentPane().add(spawn_pray);
		
		start_stop = new JButton("START DAY");
		start_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(start_stop.getText().equals("START DAY")) {
					SimulationGUI.enviroment.start();
					start_stop.setText("STOP");
					start_stop.setEnabled(false);
				}
				else if(start_stop.getText().equals("STOP")) {
					SimulationGUI.enviroment.stop();
					start_stop.setText("START DAY");
				}
			}
		});
		start_stop.setBounds(159, 158, 115, 30);
		frame.getContentPane().add(start_stop);
		
		pause_resume = new JButton("PAUSE");
		pause_resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pause_resume.getText().equals("PAUSE")) {
					SimulationGUI.enviroment.pause();
					pause_resume.setText("RESUME");
				}
				else if(pause_resume.getText().equals("RESUME")) {
					SimulationGUI.enviroment.resume();
					pause_resume.setText("PAUSE");
				}
			}
		});
		pause_resume.setBounds(10, 165, 89, 23);
		frame.getContentPane().add(pause_resume);
		
		spawn_food = new JButton("Spawn Food");
		spawn_food.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationGUI.enviroment.addFoodRandomly(
						(int)number_of_food_to_spawn.getValue());
			}
		});
		spawn_food.setBounds(10, 44, 115, 23);
		frame.getContentPane().add(spawn_food);
		
		day_count = new JLabel("Day 1");
		day_count.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		day_count.setBounds(20, 11, 155, 23);
		frame.getContentPane().add(day_count);
		
		btnNewButton = new JButton("SUMMARY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationGUI.enviroment.printSummary();
			}
		});
		btnNewButton.setBounds(10, 131, 115, 23);
		frame.getContentPane().add(btnNewButton);
		
		spawn_pray_1 = new JButton("R");
		spawn_pray_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point point=utilities.Utilities.randomPointOnRectangle(
						SimulationGUI.enviroment.SIZE_X-40, SimulationGUI.enviroment.SIZE_Y-40);
				Pray pray=new Pray(point.X+20, point.Y+20, 
						Math.random()*0.4+0.35,
						1.7,
						200.0,
						Pray.aggression.dove,SimulationGUI.enviroment);
				pray.pray_png_image_icon=
						new ImageIcon(Pray.pray_png_red.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
				pray.START_TOTAL_ENERGY=2.5;
				pray.total_energy=17.5;

				SimulationGUI.enviroment.addPray(pray);
			}
		});
		spawn_pray_1.setBounds(10, 97, 42, 23);
		frame.getContentPane().add(spawn_pray_1);
		
		spawn_pray_2 = new JButton("B");
		spawn_pray_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point point=utilities.Utilities.randomPointOnRectangle(
						SimulationGUI.enviroment.SIZE_X-40, SimulationGUI.enviroment.SIZE_Y-40);
				Pray pray=new Pray(point.X+20, point.Y+20, 
						Math.random()*0.4+0.35,
						1.7,
						200.0,
						Pray.aggression.dove,SimulationGUI.enviroment);
				pray.pray_png_image_icon=
						new ImageIcon(Pray.pray_png_blue.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
				pray.START_TOTAL_ENERGY=2.5;
				pray.total_energy=17.5;
				SimulationGUI.enviroment.addPray(pray);
			}
		});
		spawn_pray_2.setBounds(83, 97, 42, 23);
		frame.getContentPane().add(spawn_pray_2);
		
		spawn_pray_3 = new JButton("G");
		spawn_pray_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point point=utilities.Utilities.randomPointOnRectangle(
						SimulationGUI.enviroment.SIZE_X-40, SimulationGUI.enviroment.SIZE_Y-40);
				Pray pray=new Pray(point.X+20, point.Y+20, 
						Math.random()*0.4+0.35,
						1.7,
						200.0,
						Pray.aggression.dove,SimulationGUI.enviroment);
				pray.pray_png_image_icon=
						new ImageIcon(Pray.pray_png_green.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
				pray.START_TOTAL_ENERGY=2.5;
				pray.total_energy=17.5;

				SimulationGUI.enviroment.addPray(pray);
			}
		});
		spawn_pray_3.setBounds(47, 97, 42, 23);
		frame.getContentPane().add(spawn_pray_3);
		
		JLabel lblNewLabel = new JLabel("");
		
		try {
			arrows[0] = ImageIO.read(getClass().getResource("/arrow_1.png").openStream());
			arrows[1] = ImageIO.read(getClass().getResource("/arrow_2.png").openStream());
			arrows[2] = ImageIO.read(getClass().getResource("/arrow_3.png").openStream());
			arrows[3] = ImageIO.read(getClass().getResource("/arrow_4.png").openStream());
			arrows_down[0] = ImageIO.read(getClass().getResource("/arrow_1_down.png").openStream());
			arrows_down[1] = ImageIO.read(getClass().getResource("/arrow_2_down.png").openStream());
			arrows_down[2] = ImageIO.read(getClass().getResource("/arrow_3_down.png").openStream());
			arrows_down[3] = ImageIO.read(getClass().getResource("/arrow_4_down.png").openStream());
			}
		catch (IOException e1) {e1.printStackTrace();}
		lblNewLabel.setIcon(new ImageIcon(arrows[0].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				utilities.Utilities.playSound(utilities.Utilities.sounds.click_1);
				switch(SimulationGUI.enviroment.selected_enviroment_speed) {
					case 0:
						lblNewLabel.setIcon(new ImageIcon(arrows[1].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						SimulationGUI.enviroment.selected_enviroment_speed=1;
						SimulationGUI.enviroment.enviroment_speed=0.4;
						break;
					case 1:
						lblNewLabel.setIcon(new ImageIcon(arrows[2].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						SimulationGUI.enviroment.selected_enviroment_speed=2;
						SimulationGUI.enviroment.enviroment_speed=0.65;
						break;
					case 2:
						lblNewLabel.setIcon(new ImageIcon(arrows[3].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						SimulationGUI.enviroment.selected_enviroment_speed=3;
						SimulationGUI.enviroment.enviroment_speed=1.2;
						break;
					case 3:
						lblNewLabel.setIcon(new ImageIcon(arrows[0].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						SimulationGUI.enviroment.selected_enviroment_speed=0;
						SimulationGUI.enviroment.enviroment_speed=0.25;
						break;
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				switch(SimulationGUI.enviroment.selected_enviroment_speed) {
					case 0:
						lblNewLabel.setIcon(new ImageIcon(arrows_down[0].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 1:
						lblNewLabel.setIcon(new ImageIcon(arrows_down[1].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 2:
						lblNewLabel.setIcon(new ImageIcon(arrows_down[2].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 3:
						lblNewLabel.setIcon(new ImageIcon(arrows_down[3].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				switch(SimulationGUI.enviroment.selected_enviroment_speed) {
					case 0:
						lblNewLabel.setIcon(new ImageIcon(arrows[0].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 1:
						lblNewLabel.setIcon(new ImageIcon(arrows[1].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 2:
						lblNewLabel.setIcon(new ImageIcon(arrows[2].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
					case 3:
						lblNewLabel.setIcon(new ImageIcon(arrows[3].getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
						break;
				}
			}
		});
		lblNewLabel.setBounds(228, 11, 46, 32);
		frame.getContentPane().add(lblNewLabel);
	}
}
