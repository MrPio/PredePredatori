package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class NewEnviroment extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewEnviroment dialog = new NewEnviroment();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	JComboBox enviromentSize;
	public NewEnviroment() {
		Dimension screen_size=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)screen_size.getWidth()/2-300/2,
				(int)screen_size.getHeight()/2-230/2,
				300,
				230);
		getContentPane().setLayout(null);
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setBackground(SystemColor.textInactiveText);
		getContentPane().setLayout(null);
		
		enviromentSize = new JComboBox();
		enviromentSize.setForeground(Color.WHITE);
		enviromentSize.setBackground(Color.BLACK);
		enviromentSize.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 22));
		enviromentSize.setModel(new DefaultComboBoxModel(EnviromentSize.values()));
		enviromentSize.setSelectedIndex(1);
		enviromentSize.setBounds(85, 40, 117, 47);
		getContentPane().add(enviromentSize);
		
		JButton okButton = new JButton("CREATE \r\nENVIROMENT");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SimulationGUI.enviroment.SIZE_X=(int) getNewEnviromentSize().size.X;
				SimulationGUI.enviroment.SIZE_Y=(int) getNewEnviromentSize().size.Y;
			}
		});
		okButton.setActionCommand("OK");
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(Color.DARK_GRAY);
		okButton.setFont(new Font("Consolas", Font.BOLD, 18));
		okButton.setBounds(10, 123, 267, 47);
		getContentPane().add(okButton);
	}
	public EnviromentSize getNewEnviromentSize() {
		return (EnviromentSize)enviromentSize.getSelectedItem();
	}

}
