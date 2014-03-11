package net.bestwebart.game.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.bestwebart.game.Game;
import net.bestwebart.game.net.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener {
    
    private Game game;
    
    public WindowHandler(Game game) {
	this.game = game;
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
	Packet01Disconnect packet = new Packet01Disconnect(game.getPlayer().getUsername());
	packet.writeData(game.client);
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

}
