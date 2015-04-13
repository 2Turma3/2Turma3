package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import maze.cli.CommandLine;
import maze.logic.Dragon;
import maze.logic.Game;
import maze.logic.Game.Command;
import maze.logic.Weapon;

public class GameGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			if(assignedKeys.up == e.getKeyCode()){
				play(action, Game.Direction.UP);
			}
			else if(assignedKeys.down == e.getKeyCode())
				play(action, Game.Direction.DOWN);
			else if(assignedKeys.left == e.getKeyCode())
				play(action, Game.Direction.LEFT);
			else if(assignedKeys.right == e.getKeyCode())
				play(action, Game.Direction.RIGHT);
			else if(assignedKeys.skip == e.getKeyCode())
				play(Game.Action.STOP, null);
			else if(assignedKeys.spAttack == e.getKeyCode())
				attackKeyHold = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(assignedKeys.spAttack == e.getKeyCode())
				attackKeyHold = false;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
	};
	private GameBoardPanel gameImagePanel;
	private StatsPanel statsPanel;
	
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
		
		CommandLine cli = new CommandLine();
		
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
		
		img.setExit(game.isExitOpen());
		return img.getImage();		
	}
	
	private void play(Game.Action action, Game.Direction direction){
		this.game.heroTurn(new Command(action, direction));
		this.game.dragonsTurn();
		this.game.resolutionPhase();
		this.gameImagePanel.reloadImage(getGameImage(this.game));
		this.statsPanel.updateStats();
		if(this.game.isFinished()){
			this.removeKeyListener(keyListener);
			
			JOptionPane.showMessageDialog(this, game.getEndOfGameMessage(), game.isWon() ? "Hurray" : "OHH NO!", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private void saveGame(){
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Specify a file to save");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Illuminati MLG Files", "mlg");
		chooser.setFileFilter(filter);
		if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			String ext;
			if(chooser.getSelectedFile().getName().lastIndexOf('.') == -1)
				ext = ".mlg";
			else
				ext = (chooser.getSelectedFile().getName().substring(chooser.getSelectedFile().getName().lastIndexOf('.'))).equals(".mlg") ? "" : ".mlg";
			File file = new File(chooser.getSelectedFile().getParentFile(), chooser.getSelectedFile().getName() + ext);			
			if(file.exists()){
				int option = JOptionPane.showConfirmDialog(this, "There is already a file with that name. Do you wish to overwrite?", "Error",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.NO_OPTION){
					GameGui.this.requestFocus();
					return;
				}
			}
			
			ObjectOutputStream os = null;
			
			try{
				os = new ObjectOutputStream(new FileOutputStream(file));
				os.writeObject(game);
			}catch(IOException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "An error occurred. File not saved", "Error",JOptionPane.ERROR_MESSAGE);
			}
			finally{ if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					GameGui.this.requestFocus();
				}}
			
			GameGui.this.requestFocus();
		}
		
	}
	
	/**
	 * Create the frame.
	 */
	public GameGui(final JFrame parentFrame, Game game, AssignedKeys assignedKeys) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.assignedKeys = assignedKeys;
		this.game = game;
		
		this.setFocusable(true);
		this.requestFocus();
		
		
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
				
				int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to save?","Caution!", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION){
					
					saveGame();
				}
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
		
		this.statsPanel = new StatsPanel(game);
		statsPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.15),this.getHeight()-buttonPane.getHeight()));
		contentPane.add(statsPanel, BorderLayout.EAST);
		
		
		this.gameImagePanel = new GameBoardPanel(getGameImage(game));
		System.out.print(this.getKeyListeners().length);
		addKeyListener(keyListener);
		
		
		if(game != null)
			gameImagePanel.setBounds(SIDE_BORDERS_SIZE,0, MazeImage.CELL_WIDTH*game.getBoard().getMap().getCols(), MazeImage.CELL_WIDTH*game.getBoard().getMap().getRows());
		else
			gameImagePanel.setBounds(SIDE_BORDERS_SIZE, 0, 100, 100);
		contentPane.add(gameImagePanel, BorderLayout.CENTER);
		
		
	}

}
