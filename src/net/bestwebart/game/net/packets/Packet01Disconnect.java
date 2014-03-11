package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet01Disconnect extends Packet {

    private String username;
    
    public Packet01Disconnect(byte[] data) {
	super(01);
	username = readData(data);
    }
    
    public Packet01Disconnect(String username) {
	super(01);
	this.username = username;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }
    
    public byte[] getData() {
	return ("01" + username).getBytes();
    }
    
    public String getUsername() {
	return username;
    }
}
