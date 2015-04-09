package maze.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.logic.*;


public class MazeImage {
	
	public static final int CELL_HEIGHT = 50;
	public static final int CELL_WIDTH = 50;
	
	private static BufferedImage wallImage;
	private static BufferedImage floorImage;
	private static BufferedImage heroImage;
	private static BufferedImage dragonImage;
	private static BufferedImage sleepingDragonImage;
	private static BufferedImage swordImage;
	private static BufferedImage shieldImage;
	private static BufferedImage dartImage;
	private static BufferedImage closedExitImage;
	private static BufferedImage openExitImage;
	
	private Position exitPosition;
	
	private BufferedImage exit;
	private BufferedImage mazeTiles;
	private BufferedImage mazeSprites;
	
	static {
		try {
			wallImage = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			wallImage.getGraphics().drawImage(ImageIO.read(ClassLoader.getSystemResource("images/Mossy_Wall.png")), 0, 0, wallImage.getWidth(), wallImage.getHeight(), null);			
			
			floorImage = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			floorImage.getGraphics().drawImage(ImageIO.read(ClassLoader.getSystemResource("images/Floor.jpg")), 0, 0, floorImage.getWidth(), floorImage.getHeight(), null);
			
			heroImage = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			heroImage.getGraphics().drawImage(ImageIO.read(ClassLoader.getSystemResource("images/Hero.png")), 0, 0, heroImage.getWidth(), heroImage.getHeight(), null);
			
			closedExitImage = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			closedExitImage.getGraphics().drawImage(floorImage, 0, 0, closedExitImage.getWidth(), closedExitImage.getHeight(), null);
			closedExitImage.getGraphics().drawImage(ImageIO.read(ClassLoader.getSystemResource("images/ClosedExit.png")), 0, 0, closedExitImage.getWidth(), closedExitImage.getHeight(), null);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public MazeImage(MazeMap map) {
		this.mazeTiles = mazeToImage(map);
		this.mazeSprites = new BufferedImage(this.mazeTiles.getWidth(), this.mazeTiles.getHeight(), this.mazeTiles.getType());
		this.exitPosition = map.getExit();
		this.exit = this.closedExitImage;
	}
	
	public void clear() {
		this.mazeSprites = new BufferedImage(this.mazeTiles.getWidth(), this.mazeTiles.getHeight(), this.mazeTiles.getType());
	}
	
	public BufferedImage getImage() {
		BufferedImage mazeImage = new BufferedImage(this.mazeTiles.getWidth(), this.mazeTiles.getHeight(), this.mazeTiles.getType());
		Graphics g2 = mazeImage.getGraphics();
		g2.drawImage(this.mazeTiles, 0, 0, null);
		g2.drawImage(this.exit, exitPosition.getCol() * CELL_WIDTH, exitPosition.getRow() * CELL_HEIGHT, null);
		g2.drawImage(this.mazeSprites, 0, 0, null);
		
		return mazeImage;
	}
	
	public void addEntity(Entity entity) {
		BufferedImage img = null;
		
		if (entity instanceof Hero)
			img = heroImage;
		
		if (img == null)
			return;
		
		mazeSprites.getGraphics().drawImage(img, entity.getPos().getCol() * CELL_WIDTH, entity.getPos().getRow() * CELL_HEIGHT, img.getWidth(), img.getHeight(), null);
	}
	
	public void openExit() {
		this.exit = openExitImage;
	}
	
	public void closeExit() {
		this.exit = closedExitImage;
	}
	
	private BufferedImage mazeToImage(MazeMap map) {
		BufferedImage image = new BufferedImage(map.getCols()*CELL_WIDTH, map.getRows()*CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
		
		for(int row = 0; row < map.getRows(); ++row)
			for(int col = 0; col < map.getCols(); ++col){
				if(map.isWalkable(row, col))
					g.drawImage(floorImage, col*CELL_WIDTH, row*CELL_HEIGHT,null);
				else
					g.drawImage(wallImage, col*CELL_WIDTH, row*CELL_HEIGHT,null);
			}
		return image;
	}
}
