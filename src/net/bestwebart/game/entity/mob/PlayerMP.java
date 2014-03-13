package net.bestwebart.game.entity.mob;

import java.net.InetAddress;

import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;

public class PlayerMP extends Player {
    
    private InetAddress address;
    private int port;
    private boolean main;
    
    public PlayerMP(int x, int y, String username, KeyboardHandler key, MouseHandler mouse, InetAddress address, int port, boolean main) {
	super(x, y, key, mouse, username);
	this.address = address;
	this.port = port;
	this.main = main;
    }
    
    public PlayerMP(int x, int y, String username, InetAddress address, int port, boolean main) {
	super(x, y, null, null, username);
	this.address = address;
	this.port = port;
	this.main = main;
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
    
    public boolean isMain() {
	return main;
    }
    
    public void update() {
	super.update();
    }
}
