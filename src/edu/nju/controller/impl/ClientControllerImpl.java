package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.main.JMineSweeper;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.Configure;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.modelProxy.ChessBoardModelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.network.modelProxy.ParameterModelProxy;

public class ClientControllerImpl implements ClientControllerService{

	@Override
	public boolean setupClient(String ip) {
		ClientServiceImpl client = new ClientServiceImpl();
		ClientInHandlerImpl clientH = new ClientInHandlerImpl();
		
		GameModelProxy gameProxy = new GameModelProxy(client);
		ParameterModelProxy parameterProxy = new ParameterModelProxy();
		ChessBoardModelProxy chessBoardProxy = new ChessBoardModelProxy(parameterProxy, client);
		clientH.addObserver(gameProxy);
		clientH.addObserver(chessBoardProxy);
		clientH.addObserver(parameterProxy);
		gameProxy.addObserver(JMineSweeper.getUI());
		chessBoardProxy.addObserver(JMineSweeper.getUI().getMineBoard());
		parameterProxy.addObserver(JMineSweeper.getUI().getMineNumberLabel());
		parameterProxy.addObserver(JMineSweeper.getUI().getFlagNumLabel());
		parameterProxy.addObserver(JMineSweeper.getUI().getFlagNum2Label());
		
		//GameModelImpl game = (GameModelImpl) OperationQueue.getGameModel();
		
		//gameProxy.setUp(game.getStatisticModel(), game.getChessBoardModel());
		
		OperationQueue.setGameModel(gameProxy);
		OperationQueue.setChessBoardModel(chessBoardProxy);
		
		Configure.isClient = true;
		client.init(ip, clientH);
		return true;
	}

}
