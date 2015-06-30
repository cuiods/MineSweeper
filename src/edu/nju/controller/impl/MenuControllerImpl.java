package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;
import edu.nju.network.modelProxy.GameModelProxy;

public class MenuControllerImpl implements MenuControllerService{

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new StartGameOperation());
		return true;
	}

}
