package net.bestwebart.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.entity.mob.Player;
import net.bestwebart.game.entity.mob.PlayerMP;
import net.bestwebart.game.net.packets.Packet;
import net.bestwebart.game.net.packets.Packet.PacketType;
import net.bestwebart.game.net.packets.Packet00Login;
import net.bestwebart.game.net.packets.Packet01Disconnect;
import net.bestwebart.game.net.packets.Packet02Move;
import net.bestwebart.game.net.packets.Packet03Projectile;
import net.bestwebart.game.net.packets.Packet04Tiles;
import net.bestwebart.game.net.packets.Packet05AddNPC;
import net.bestwebart.game.net.packets.Packet06ToggleInvisible;
import net.bestwebart.game.net.packets.Packet07Respawn;

public class Server extends Thread {

    private Game game;
    private DatagramSocket socket;
    private int port;
    
    private List<PlayerMP> connectedPlayers;
    
    public Server(Game game) {
	this.game = game;
	connectedPlayers = new ArrayList<PlayerMP>();
	port = 1303;
	try {	
	    socket = new DatagramSocket(port);
	} catch(SocketException e) {
	    e.printStackTrace();
	}
	System.out.println("SERVER STARTED!");
    }
    
    public void run() {
	while (true) {
	    byte data[] = new byte[5000];
	    DatagramPacket packet = new DatagramPacket(data, data.length);
	    try {
		socket.receive(packet);
		parseData(packet.getData(), packet.getAddress(), packet.getPort());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
    
    private void parseData(byte[] data, InetAddress address, int port) {
	String message = new String(data).trim();
	PacketType type = Packet.getPacketType(message.substring(0, 2));
	Packet packet = null;
	switch(type) {
	    case INVALID:
		break;
	    case LOGIN:
		packet = new Packet00Login(data);
		handleLogin((Packet00Login) packet, address, port);
		break;
	    case DISCONNECT:
		packet = new Packet01Disconnect(data);
		System.out.println(((Packet01Disconnect) packet).getUsername() + " [" + address.getHostAddress() + ":" + port + "] has diconnected!");
		disconnect((Packet01Disconnect) packet);
		break;
	    case MOVE:
		packet = new Packet02Move(data);
		handleMove((Packet02Move) packet);
		break;
	    case PROJECTILE:
		packet = new Packet03Projectile(data);
		addProjectile((Packet03Projectile) packet);
		break;
	    case ADD_NPC:
		packet = new Packet05AddNPC(data);
		addNPC((Packet05AddNPC) packet);
		break;
	    case INVISIBLE:
		packet = new Packet06ToggleInvisible(data);
		handleInvisibility((Packet06ToggleInvisible) packet);
		break;
	    case RESPAWN:
		packet = new Packet07Respawn(data);
		respawn((Packet07Respawn) packet);
	    default:
		break;
		
		
	}
    }
    
    private void respawn(Packet07Respawn packet) {
	packet.writeData(this);
    }

    private void handleInvisibility(Packet06ToggleInvisible packet) {
	packet.writeData(this);
    }

    private void addNPC(Packet05AddNPC packet) {
	packet.writeData(this);
    }
    
    private void addProjectile(Packet03Projectile packet) {
	packet.writeData(this);
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
	System.out.println(packet.getUsername() + " [" + address.getHostAddress() + ":" + port + "] has connected!");
	PlayerMP player = new PlayerMP((int) packet.getX(), (int) packet.getY(), packet.getUsername(), address, port, false);
	addConnections(player, packet);
    }

    private void handleMove(Packet02Move packet) {
	packet.writeData(this);
    }

    public void disconnect(Packet01Disconnect packet) {
	int index = getPlayerMPIndex(packet.getUsername());
	connectedPlayers.remove(index);
	packet.writeData(this);
    }

    public void addConnections(PlayerMP player, Packet00Login packet) {
	boolean alreadyExist = false;
	for (PlayerMP p : connectedPlayers) {
	    if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
		
		if (p.getIp() == null) {
		    p.setIp(player.getIp());
		}
		
		if (p.getPort() == -1) {
		    p.setPort(player.getPort());
		}
		
		alreadyExist = true;
	    } else {
		sendData(packet.getData(), p.getIp(), p.getPort());
		
		packet = new Packet00Login(p.getUsername(), p.getX(), p.getY(), p.getHP(), p.getUniqueID());
		sendData(packet.getData(), player.getIp(), player.getPort());
	    }
	}
	
	if (!alreadyExist) {
	    connectedPlayers.add(player);
	    if (player.getIp() != null) {
		Packet04Tiles packetTiles = new Packet04Tiles(game.getLevel().getTiles());
		sendData(packetTiles.getData(), player.getIp(), player.getPort());
		
		Packet05AddNPC packetNPC = null;
		for (Mob m : game.getLevel().getMobs()) {
		    if (!(m instanceof Player)) {
			packetNPC = new Packet05AddNPC((int) m.getX(), (int) m.getY(), m.getHP(), m.getUniqueID(), m.NPCType );
			sendData(packetNPC.getData(), player.getIp(), player.getPort());
		    }
		}
		
	    }
	}
    }

    public void sendData(byte[] data, InetAddress address, int port) {
	DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
	try {
	    socket.send(packet);
	} catch(IOException e) {
	    e.printStackTrace();
	}
    }

    public void sendToAll(byte[] data) {
	for (PlayerMP p : connectedPlayers) {
	    if (p.getIp() != null) {
		sendData(data, p.getIp(), p.getPort());
	    }
	}
    }
    
    public int getPlayerMPIndex(String username) {
	for (int i = 0; i < connectedPlayers.size(); i++) {
	    if (connectedPlayers.get(i).getUsername().equalsIgnoreCase(username)) {
		return i;
	    }
	}
	return -1;
    }
    
}
