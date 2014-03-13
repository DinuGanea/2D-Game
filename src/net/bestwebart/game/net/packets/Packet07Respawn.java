package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet07Respawn extends Packet {
    
    private String username;
    private int x, y;
    private int uniqueID;
    private int hp;
    
    public Packet07Respawn(byte[] data) {
	super(07);
	String[] dataArray = readData(data).split(",");
	username = dataArray[0];
	try {
	    x = Integer.parseInt(dataArray[1]);
	    y = Integer.parseInt(dataArray[2]);
	    hp = Integer.parseInt(dataArray[3]);
	    uniqueID = Integer.parseInt(dataArray[4]);
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
    }
    
    public Packet07Respawn(String username, int x, int y, int hp, int uniqueID) {
	super(07);
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
	return ("07" + username + "," + x + "," + y + "," + hp + "," + uniqueID).getBytes();
    }
    
    public String getUsername() {
	return username;
    }
    
    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
    
    public int getHP() {
	return hp;
    }
    
    public int getUniqueID() {
	return uniqueID;
    }

}
