package net.bestwebart.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private int x, y;
    private int button;

    public MouseHandler() {
	x = 0;
	y = 0;
    }

    public void mousePressed(MouseEvent e) {
	x = e.getX();
	y = e.getY();
	button = e.getButton();
    }
    
    public void mouseDragged(MouseEvent e) {
	x = e.getX();
	y = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
	x = e.getX();
	y = e.getY();
    }
    
    
    public void mouseReleased(MouseEvent e) {
	button = -1;
    }

    public int getClickedButton() {
	return button;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
