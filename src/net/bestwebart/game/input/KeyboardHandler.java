package net.bestwebart.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    public boolean up, down, left, right;
    public boolean invisible;
    public boolean pause, menu;

    private boolean keys[] = new boolean[260];

    private int[] keyCodes = new int[] { KeyEvent.VK_PAUSE, KeyEvent.VK_ESCAPE };

    public void update() {
	up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];

	invisible = keys[KeyEvent.VK_I];

	pause = keys[KeyEvent.VK_PAUSE];

	menu = keys[KeyEvent.VK_ESCAPE];
    }

    public boolean inArray(int keyCode) {
	for (int i = 0; i < keyCodes.length; i++) {
	    if (keyCode == keyCodes[i]) {
		return true;
	    }
	}
	return false;
    }

    public void keyPressed(KeyEvent e) {
	if (!inArray(e.getKeyCode())) {
	    keys[e.getKeyCode()] = true;
	} else {
	    if (keys[e.getKeyCode()]) {
		keys[e.getKeyCode()] = false;
	    } else {
		keys[e.getKeyCode()] = true;
	    }
	}
    }

    public void keyReleased(KeyEvent e) {
	if (!inArray(e.getKeyCode())) {
	    keys[e.getKeyCode()] = false;
	}
    }

    public void keyTyped(KeyEvent e) {
    }

}
