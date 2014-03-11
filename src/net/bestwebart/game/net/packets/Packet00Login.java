package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet00Login extends Packet {
    
    private String username;
    private double x, y;
    private int uniqueID;
    private int hp;
    
    public Packet00Login(byte[] data) {
	super(00);
	String[] dataArray = readData(data).split(",");
	username = dataArray[0];
	try {
	    x = Double.parseDouble(dataArray[1]);
	    y = Double.parseDouble(dataArray[2]);
	    hp = Integer.parseInt(dataArray[3]);
	    uniqueID = Integer.parseInt(dataArray[4]);
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
    }
    
    public Packet00Login(String username, double x, double y, int hp, int uniqueID) {
	super(00);
	this.username = username;
	this.x = x;
	this.y = y;
	this.hp = hp;
	this.uniqueID = uniqueID;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }
    
    public byte[] getData() {
	return ("00" + username + "," + x + "," + y + "," + hp + "," + uniqueID).getBytes();
    }
    
    public String getUsername() {
	return username;
    }
    
    public double getX() {
	return x;
    }
    
    public double getY() {
	return y;
    }
    
    public int getHP() {
	return hp;
    }
    
    public int getUniqueID() {
	return uniqueID;
    }

}
