package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.Configure;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;

public class HostControllerImpl implements HostControllerService{

	@Override
	public boolean serviceetupHost() {
		HostServiceImpl host = new HostServiceImpl();
		HostInHandlerImpl hostH = new HostInHandlerImpl();
		
		GameModelImpl game = (GameModelImpl) OperationQueue.getGameModel();
		ChessBoardModelImpl chessBoardModelImpl = (ChessBoardModelImpl) game.getChessBoardModel();
		ParameterModelImpl parameterImpl = (ParameterModelImpl) chessBoardModelImpl.getParameterModel();
		game.addObserver(host);
		chessBoardModelImpl.addObserver(host);
		parameterImpl.addObserver(host);
		
		
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
			Configure.isServer = true;
			//game.startGame();
		}
		return true;
	}

}
