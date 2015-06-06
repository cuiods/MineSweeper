package edu.nju.network.client;

import java.io.IOException;
import java.net.UnknownHostException;


public class ClientAdapter {
	protected static ClientThread socket;
	protected static ClientInHandler handler;
	
	static boolean init(String addr,ClientInHandler h){
		try {
			socket = new ClientThread(addr);
			
			handler = h;
			
			socket.start();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	//to write
	public static void write(Object o){
		socket.write(o);
	}
	
	//from read
	public static void readData(Object data){
		handler.inputHandle(data);
	}
	
	public static void close(){
		socket.close();
	}

}
