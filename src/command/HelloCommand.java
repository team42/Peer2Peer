package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import peer.Peer;
import config.Configuration;

public class HelloCommand extends Command {

	Configuration config = Configuration.getConfiguration();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		// Get peerlist and send
		
		ArrayList<Peer> peerList = config.getPeers();
		String strPeerList = "";
		
		for(int i=0; i > peerList.size(); i++) {
			strPeerList += strPeerList + peerList.get(i) + "%";
		}
		
		String reply = "PEERS" + strPeerList;
		
		int peer = receivePacket.getPort();
        
		byte[] replyRaw = new byte[1024];
        replyRaw = reply.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(replyRaw, replyRaw.length, receivePacket.getAddress(), peer);
        
        try {
			peerSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
