package maze.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuPanel extends JPanel {

	private BufferedImage background;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters            
	}

	public MainMenuPanel() {
		
		try{
			background = ImageIO.read(new File("src1/images/Main Menu Background.png"));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		repaint();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0,100, 100, 300);
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JButton btnNewGame = new JButton("New Game");
		
		panel_1.add(btnNewGame, "name_15052952693513");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
	}


}
