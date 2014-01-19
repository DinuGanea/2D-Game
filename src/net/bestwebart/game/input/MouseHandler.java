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

    @Override
    public void mousePressed(MouseEvent e) {
	x = e.getX();
	y = e.getY();
	button = e.getButton();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	x = e.getX();
	y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	x = e.getX();
	y = e.getY();
    }

    @Override
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

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
