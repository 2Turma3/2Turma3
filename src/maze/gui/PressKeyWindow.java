package maze.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class PressKeyWindow extends JDialog  implements KeyListener{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AssignedKeys keys;
	private AssignedKeys.Key selectedKey;

	public PressKeyWindow(AssignedKeys keys, AssignedKeys.Key selectedKey) {
		this.keys = keys;
		this.selectedKey = selectedKey;
		
		setBounds(625, 400, 200, 50);
		JLabel messageLabel = new JLabel("Press the key!");
		setLayout(new BorderLayout(0,0));
		add(messageLabel, BorderLayout.CENTER);
		setModal(true);
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(selectedKey){
		case UP:
			keys.up = arg0.getKeyCode();
			break;
		case DOWN:
			keys.down = arg0.getKeyCode();
			break;
		case LEFT:
			keys.left = arg0.getKeyCode();
			break;
		case RIGHT:
			keys.right = arg0.getKeyCode();
			break;
		case ATTACK:
			keys.spAttack = arg0.getKeyCode();
			break;
		case SKIP:
			keys.skip = arg0.getKeyCode();
			break;
		}
		dispose();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
