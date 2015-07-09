/*
 *
 TODO to start to program. this program are wrote base on MVC structure
 */
package edu.nju.main;

 
import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.view.MainFrame;

public class JMineSweeper {

	static MenuControllerService menuController = new MenuControllerImpl();
	static MainFrame ui = null;
	static ParameterModelImpl mineNumberModel = null;
	static GameModelImpl gameModel = null;
	static ChessBoardModelImpl mineBoardModel = null;
	public static void main(String[] args) {
		
		ui = new MainFrame();
		StatisticModelImpl statisticModel = new StatisticModelImpl();
 		mineNumberModel = new ParameterModelImpl();
 		mineBoardModel = new ChessBoardModelImpl(mineNumberModel);
		gameModel = new GameModelImpl(statisticModel,mineBoardModel);		
 		
		gameModel.addObserver(ui);
 		mineNumberModel.addObserver(ui.getMineNumberLabel());
 		mineNumberModel.addObserver(ui.getFlagNumLabel());
 		mineNumberModel.addObserver(ui.getFlagNum2Label());
 		mineBoardModel.addObserver(ui.getMineBoard());
 		
 		OperationQueue operationQueue = new OperationQueue(mineBoardModel, gameModel);
 		Thread operationThread = new Thread(operationQueue);
 		operationThread.start();
 	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 		//menuController.startGame();
	}
	
	public static MainFrame getUI(){
		return ui;
	}
	
	public static ParameterModelImpl getMineNumberModel() {
		return mineNumberModel;
	}

	public static void setMineNumberModel(ParameterModelImpl mineNumberModel) {
		JMineSweeper.mineNumberModel = mineNumberModel;
	}

	public static GameModelImpl getGameModel() {
		return gameModel;
	}

	public static void setGameModel(GameModelImpl gameModel) {
		JMineSweeper.gameModel = gameModel;
	}

	public static ChessBoardModelImpl getMineBoardModel() {
		return mineBoardModel;
	}

	public static void setMineBoardModel(ChessBoardModelImpl mineBoardModel) {
		JMineSweeper.mineBoardModel = mineBoardModel;
	}

}

