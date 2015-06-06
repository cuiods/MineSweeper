package edu.nju.network.client;

import edu.nju.network.modelProxy.GameModelProxy;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientServiceImpl client = new ClientServiceImpl();
		ClientInHandlerImpl clientH = new ClientInHandlerImpl();
		
		GameModelProxy gameProxy = new GameModelProxy(client);
		clientH.addObserver(gameProxy);
		
		client.init("127.0.0.1", clientH);
	}

}
