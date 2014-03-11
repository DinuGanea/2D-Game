package net.bestwebart.game.entity.mob;

import java.net.InetAddress;

import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;

public class PlayerMP extends Player {
    
    private InetAddress address;
    private int port;
    
    public PlayerMP(int x, int y, String username, KeyboardHandler key, MouseHandler mouse, InetAddress address, int port) {
	super(x, y, key, mouse, username);
	this.address = address;
	this.port = port;
    }
    
    public PlayerMP(int x, int y, String username, InetAddress address, int port) {
	super(x, y, null, null, username);
	this.address = address;
	this.port = port;
    }
    
    public InetAddress getIp() {
	return address; 
    }
    
    public int getPort() {
	return port;
    }
    
    public void setIp(InetAddress ip) {
	this.address = ip;
    }
    
    public void setPort(int port) {
	this.port = port;
    }
    
    public void update() {
	super.update();
    }
}
