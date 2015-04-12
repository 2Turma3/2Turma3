package maze.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
