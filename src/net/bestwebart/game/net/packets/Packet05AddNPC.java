package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet05AddNPC extends Packet {
    
    private int x, y;
    private int uniqueID;
    private int hp;
    private int type;
    
    public Packet05AddNPC(byte[] data) {
	super(05);
	String[] dataArray = readData(data).split(",");
	try {
	    x = Integer.parseInt(dataArray[0]);
	    y = Integer.parseInt(dataArray[1]);
	    hp = Integer.parseInt(dataArray[2]);
	    uniqueID = Integer.parseInt(dataArray[3]);
	    type = Integer.parseInt(dataArray[4]);
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
    }
    
    public Packet05AddNPC(int x, int y, int hp, int uniqueID, int type) {
	super(05);
	this.x = x;
	this.y = y;
	this.hp = hp;
	this.uniqueID = uniqueID;
	this.type = type;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }
    
    public byte[] getData() {
	return ("05" + x + "," + y + "," + hp + "," + uniqueID + "," + type).getBytes();
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
    
    public int getType() {
	return type;
    }

}
