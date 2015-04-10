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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Component;

public class StatsPanel extends JPanel {

	Game game;
	
	JPanel swordPanel;
	JPanel shieldPanel;
	JTextField dartsNumber;
	
	
	public void updateStats(){
		swordPanel.setVisible(game.getHero().hasSword());

		shieldPanel.setVisible(game.getHero().hasShield());
	
		dartsNumber.setText("" + game.getHero().getDartsNumber());
	}
	
	public StatsPanel(Game game) {
		this.game = game;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{145, 150, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
				JPanel playerIconPanel = new JPanel(){
					protected void paintComponent(java.awt.Graphics g) {
						try {
							BufferedImage img  = ImageIO.read(getClass().getResource("/images/Hero_ClosePicture.png"));
							g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
						} catch (IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
					};
				};
				GridBagConstraints gbc_playerIconPanel = new GridBagConstraints();
				gbc_playerIconPanel.fill = GridBagConstraints.BOTH;
				gbc_playerIconPanel.insets = new Insets(0, 0, 5, 0);
				gbc_playerIconPanel.gridx = 0;
				gbc_playerIconPanel.gridy = 0;
				add(playerIconPanel, gbc_playerIconPanel);
				playerIconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				playerIconPanel.setPreferredSize(new Dimension(getWidth(), getWidth()));
				
		
					
				JPanel weaponsPanel = new JPanel();
				GridBagConstraints gbc_weaponsPanel = new GridBagConstraints();
				gbc_weaponsPanel.fill = GridBagConstraints.BOTH;
				gbc_weaponsPanel.gridx = 0;
				gbc_weaponsPanel.gridy = 1;
				add(weaponsPanel, gbc_weaponsPanel);
				weaponsPanel.setLayout(new GridLayout(0, 1, 0, 0));
				this.swordPanel = new JPanel(){
					protected void paintComponent(Graphics g){
						try {
							BufferedImage img = ImageIO.read(new File("src1/images/Sword.png"));
							g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
						} catch (IOException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
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
							System.exit(1);
						}
					}
				};
				weaponsPanel.add(shieldPanel);
				
				JPanel dartsInfoPanel = new JPanel();
				weaponsPanel.add(dartsInfoPanel);
				dartsInfoPanel.setLayout(new BoxLayout(dartsInfoPanel, BoxLayout.X_AXIS));
				JPanel dartPanel = new JPanel(){
					protected void paintComponent(Graphics g){
						try {
							BufferedImage img  = ImageIO.read(new File("src1/images/arrow.png"));
							g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
						} catch (IOException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
					}
				};
				dartPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				dartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
				dartsInfoPanel.add(dartPanel);
				
				JPanel panel_1 = new JPanel();
				dartsInfoPanel.add(panel_1);
				panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				
				this.dartsNumber = new JTextField();
				panel_1.add(dartsNumber);
				//this.dartsNumber.setMaximumSize(new Dimension( dartsInfoPanel.getWidth() / 4, dartsInfoPanel.getHeight() / 4) );
				this.dartsNumber.setText("" + game.getHero().getDartsNumber());
				this.dartsNumber.setEditable(false);
				
				updateStats();
	}

}
