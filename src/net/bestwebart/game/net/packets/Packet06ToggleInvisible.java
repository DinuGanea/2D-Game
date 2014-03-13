package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet06ToggleInvisible extends Packet {
    
    private int uniqueID;
    private boolean invisible;
    
    public Packet06ToggleInvisible(byte[] data) {
	super(06);
	String[] dataArray = readData(data).split(",");
	try {
	    uniqueID = Integer.parseInt(dataArray[0]);
	    invisible = (Integer.parseInt(dataArray[1]) == 1);
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
    }
    
    public Packet06ToggleInvisible(int uniqueID, boolean invisible) {
	super(06);
	this.uniqueID = uniqueID;
	this.invisible = invisible;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }
    
    public byte[] getData() {
	return ("06" + uniqueID + "," + (invisible ? 1 : 0)).getBytes();
    }
        

    public int getUniqueID() {
	return uniqueID;
    }
    
    public boolean isInvisible() {
	return invisible;
    }

}
