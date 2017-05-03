package com.github.fdxxw.mitmstu.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPThread extends Thread {
	
	String target_ip;
	
	String message = "hello";

	public UDPThread(String target_ip) {
		this.target_ip = target_ip;
	}
	public UDPThread(String target_ip,String message) {
		this.target_ip = target_ip;
		this.message = message;
	}

	public void run() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			// Log.d(TAG, target_ip);
			InetAddress address = InetAddress.getByName(target_ip);
			DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 137);
			socket.setSoTimeout(200);
			socket.send(packet);
			socket.close();
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}
