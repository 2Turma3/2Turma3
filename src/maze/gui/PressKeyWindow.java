package maze.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class PressKeyWindow extends JDialog  implements KeyListener{
	
	private boolean validKey(int newKey){
		if(newKey == keys.up && selectedKey != AssignedKeys.Key.UP)
			return false;
		if(newKey == keys.down && selectedKey != AssignedKeys.Key.DOWN)
			return false;
		if(newKey == keys.left && selectedKey != AssignedKeys.Key.LEFT)
			return false;
		if(newKey == keys.right && selectedKey != AssignedKeys.Key.RIGHT)
			return false;
		if(newKey == keys.spAttack && selectedKey != AssignedKeys.Key.ATTACK)
			return false;
		if(newKey == keys.skip && selectedKey != AssignedKeys.Key.SKIP)
			return false;
		return true;
	}
	
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
			if(validKey(arg0.getKeyCode()))
				keys.up = arg0.getKeyCode();
			break;
		case DOWN:
			if(validKey(arg0.getKeyCode()))
				keys.down = arg0.getKeyCode();
			break;
		case LEFT:
			if(validKey(arg0.getKeyCode()))
				keys.left = arg0.getKeyCode();
			break;
		case RIGHT:
			if(validKey(arg0.getKeyCode()))
				keys.right = arg0.getKeyCode();
			break;
		case ATTACK:
			if(validKey(arg0.getKeyCode()))
				keys.spAttack = arg0.getKeyCode();
			break;
		case SKIP:
			if(validKey(arg0.getKeyCode()))
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
