package edu.nju.network.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.nju.network.Configure;

public class ClientThread extends Thread {
	private Socket server;
	private ObjectInputStream reader;
	private ObjectOutputStream out;

	public ClientThread(String addr) throws UnknownHostException, IOException{
		super();
		
		server = new Socket(addr,Configure.PORT);
		out = new ObjectOutputStream(server.getOutputStream());	
		
		reader = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
	}
	//read data
	@Override
	public void run(){
		while(!this.isInterrupted()){
			
			//read from socket;
			try {
				Object obj = reader.readObject();
				
				ClientAdapter.readData(obj);
				
				
			} catch(SocketException se){
				System.out.println("socket connection is closed!!!");
				this.close();
				break;
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void close(){
		
		try {
			reader.close();
			out.close();
			server.close();
			this.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Object write(Object o) {
		try {
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
