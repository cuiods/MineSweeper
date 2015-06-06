package edu.nju.network.host;

import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;

public class TestServer {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HostServiceImpl host = new HostServiceImpl();
		HostInHandlerImpl hostH = new HostInHandlerImpl();
		
		GameModelImpl game = new GameModelImpl(new StatisticModelImpl(),new ChessBoardModelImpl(new ParameterModelImpl()));
		game.addObserver(host);
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			game.startGame();
		}
			
	}

}
