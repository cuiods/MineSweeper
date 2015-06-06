package edu.nju.controller.msgqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;

/**
 * 操作队列，所有的操作需要加入队列，该队列自行按操作到达的先后顺序处理操作。
 * @author 晨晖
 *
 */
public class OperationQueue implements Runnable{
	
	private static BlockingQueue<MineOperation> queue;
	
	public static boolean isRunning;
	
	public static boolean singleUpdateSwitch = true;
	
	private static ChessBoardModelService chessBoard;
	private static GameModelService gameModel;
	
	public OperationQueue(ChessBoardModelService chess, GameModelService game){
		queue = new ArrayBlockingQueue<MineOperation>(1000);
		isRunning = true;
		
		chessBoard = chess;
		gameModel = game;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning){
			MineOperation operation = getNewMineOperation();
			operation.execute();
		}
	}
	
	
	public static boolean addMineOperation (MineOperation operation){
		try {
			queue.put(operation);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private static MineOperation getNewMineOperation (){
		MineOperation  operation = null;
		try {
			operation = queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return operation;
	}
	
	public static ChessBoardModelService getChessBoardModel(){
		return chessBoard;
	}
	
	public static GameModelService getGameModel(){
		return gameModel;
	}

}
