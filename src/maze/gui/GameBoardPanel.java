package maze.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

	BufferedImage img;
	
	protected void paintComponent(Graphics g){
		g.drawImage(img, 0, 0,getWidth(), getHeight(), null);
	}
	
	public void reloadImage(BufferedImage img){
		this.img = img;
		repaint();
	}
	
	public GameBoardPanel(BufferedImage img) {
		this.img = img;
	}

}
