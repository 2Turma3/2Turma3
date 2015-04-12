package maze.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Gui {

	private JFrame frmDungeon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frmDungeon.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	
	 
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeon = new JFrame();
		MainMenuPanel panel = new MainMenuPanel(frmDungeon);

		frmDungeon.setTitle("Dungeon Frontiers");

		frmDungeon.setBounds(10, 10, panel.background.getWidth(), panel.background.getHeight());
		frmDungeon.setResizable(false);
		frmDungeon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.background.getHeight();

		frmDungeon.getContentPane().add(panel);
		
		
		
	}
}
