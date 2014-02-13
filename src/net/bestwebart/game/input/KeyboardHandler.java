package net.bestwebart.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    public boolean up, down, left, right, invisible;
    private boolean keys[] = new boolean[150];

    public void update() {
	up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	
	invisible = keys[KeyEvent.VK_I];
    }

    public void keyPressed(KeyEvent e) {
	keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
	keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {
    }

}
