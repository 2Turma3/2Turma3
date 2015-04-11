package maze.gui;

import java.awt.event.KeyEvent;

public class AssignedKeys {

	int up;
	int down;
	int left;
	int right;
	int spAttack;
	int skip;
	
	private static final int upDefault = KeyEvent.VK_A;
	private static final int downDefault = KeyEvent.VK_S;
	private static final int leftDefault = KeyEvent.VK_D;
	private static final int rightDefault = KeyEvent.VK_D;
	private static final int spAttackDefault = KeyEvent.VK_CONTROL;
	private static final int skipDefault = KeyEvent.VK_SPACE;

	public AssignedKeys() {
		this.up = upDefault;
		this.down = downDefault;
		this.left = leftDefault;
		this.right = rightDefault;
		this.spAttack = spAttackDefault;
		this.skip = skipDefault;
	}

}
