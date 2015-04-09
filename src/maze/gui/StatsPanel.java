package maze.gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maze.logic.Game;

import javax.swing.BoxLayout;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

public class StatsPanel extends JPanel {

	Game game;
	
	JPanel swordPanel;
	JPanel shieldPanel;
	JTextField dartsNumber;
	
	
	public void updateStats(){
		if(game.getHero().hasSword())
			swordPanel.setVisible(true);
		else
			swordPanel.setVisible(false);
		
		if(game.getHero().hasShield())
			shieldPanel.setVisible(true);
		else
			shieldPanel.setVisible(false);
		
		dartsNumber.setText("" + game.getHero().getDartsNumber());
	}
	
	public StatsPanel(Game game) {
		this.game = game;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel playerIconPanel = new JPanel(){
			protected void paintComponent(java.awt.Graphics g) {
				try {
					BufferedImage img  = ImageIO.read(getClass().getResource("/images/Hero_ClosePicture.png"));
					g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			};
		};
		add(playerIconPanel);
		playerIconPanel.setPreferredSize(new Dimension(75, 75));
		playerIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
			
		JPanel weaponsPanel = new JPanel();
		add(weaponsPanel);
		this.swordPanel = new JPanel(){
			protected void paintComponent(Graphics g){
				try {
					BufferedImage img = ImageIO.read(new File("src1/images/Sword.png"));
					g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		};
		weaponsPanel.add(swordPanel);
		
		
		this.shieldPanel = new JPanel(){
			protected void paintComponent(Graphics g){
				try {
					BufferedImage img  = ImageIO.read(new File("src1/images/Shield.png"));
					g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		};
		weaponsPanel.add(shieldPanel);
		
		JPanel dartsInfoPanel = new JPanel(){
			protected void paintComponent(Graphics g){
				try {
					BufferedImage img  = ImageIO.read(new File("src1/images/arrow.png"));
					g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		};
		weaponsPanel.add(dartsInfoPanel);
		dartsInfoPanel.setLayout(new BoxLayout(dartsInfoPanel, BoxLayout.X_AXIS));
		JPanel dartPanel = new JPanel();
		dartsInfoPanel.add(dartPanel);
		
		
		this.dartsNumber = new JTextField();
		this.dartsNumber.setText("" + game.getHero().getDartsNumber());
		this.dartsNumber.setEditable(false);
		dartsInfoPanel.add(this.dartsNumber);
	}

}
