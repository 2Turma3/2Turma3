package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import maze.cli.CommandLine;
import maze.logic.Dragon;
import maze.logic.Game;
import maze.logic.Hero;
import maze.logic.MazeMap;
import maze.logic.UserInterface;
import maze.logic.Weapon;

public class GameGui extends JFrame {

	private JPanel contentPane;
	private final int SIDE_BORDERS_SIZE = 10;
	private final int BUTTON_PANE_HEIGHT = 35;
	private AssignedKeys assignedKeys;
	private Game game;

	private KeyListener keyListener = new KeyListener(){
		private boolean attackKeyHold = false;
		@Override
		public void keyPressed(KeyEvent e) {
			Game.Action action = attackKeyHold ? Game.Action.ATTACK : Game.Action.MOVE;
			if(assignedKeys.up == e.getKeyCode())
				;
			else if(assignedKeys.down == e.getKeyCode())
				;
			else if(assignedKeys.left == e.getKeyCode())
				;
			else if(assignedKeys.right == e.getKeyCode())
				;
			else if(assignedKeys.skip == e.getKeyCode())
				;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(assignedKeys.spAttack == e.getKeyCode())
				attackKeyHold = false;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args ) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GameGui frame = new GameGui();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		
		Game newGame = new Game(10, 10, 0, false, false, false);
		
		UserInterface cli = new CommandLine();
		
		cli.displayBoard(newGame.getBoard());
		
		MazeImage img = new MazeImage(newGame.getBoard().getMap());
		img.addEntity(newGame.getBoard().getHero());
		
		try {
			ImageIO.write( img.getImage(), "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private BufferedImage getGameImage(Game game){
		MazeImage img = new MazeImage(game.getBoard().getMap());
		
		for(Weapon weapon : game.getBoard().getWeapons()){
			img.addEntity(weapon);
		}
		
		for(Dragon dragon : game.getBoard().getDragons()){
			img.addEntity(dragon);
		}
		
		img.addEntity(game.getBoard().getHero());
		return img.getImage();		
	}
	
	/**
	 * Create the frame.
	 */
	public GameGui(final JFrame parentFrame, Game game, AssignedKeys assignedKeys) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.assignedKeys = assignedKeys;
		this.game = game;
		
		
		
		if(game != null)
			setBounds(100, 100, MazeImage.CELL_WIDTH*game.getBoard().getMap().getCols() + 4*SIDE_BORDERS_SIZE , MazeImage.CELL_HEIGHT*game.getBoard().getMap().getRows() + BUTTON_PANE_HEIGHT);
		else
			setBounds(100,100,100+20,100+ 35);
		
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);
		
		JPanel buttonPane = new JPanel();
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		//buttonPane.setBounds(10, CELL_WIDTH*game.map.getRows(), 35, CELL_WIDTH*game.map.getCols());
		
		
		JButton btnSaveGame = new JButton("Save Game");
		
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(null,"Caution!","Are you sure you want to save?" , JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		buttonPane.add(btnSaveGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				parentFrame.setVisible(true);
			}
			
		});
		buttonPane.add(btnExit);
		
		JPanel statsPanel = new StatsPanel(game);
		statsPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.15),this.getHeight()-buttonPane.getHeight()));
		contentPane.add(statsPanel, BorderLayout.EAST);
		
		
		JPanel gameImagePanel = new GameBoardPanel(getGameImage(game));
		gameImagePanel.addKeyListener(keyListener);
		
		if(game != null)
			gameImagePanel.setBounds(SIDE_BORDERS_SIZE,0, MazeImage.CELL_WIDTH*game.getBoard().getMap().getCols(), MazeImage.CELL_WIDTH*game.getBoard().getMap().getRows());
		else
			gameImagePanel.setBounds(SIDE_BORDERS_SIZE, 0, 100, 100);
		contentPane.add(gameImagePanel, BorderLayout.CENTER);
	}

}
