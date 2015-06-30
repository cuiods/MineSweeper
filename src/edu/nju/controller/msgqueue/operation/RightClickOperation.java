package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;

public class RightClickOperation extends MineOperation{

	private int x;
	private int y;
	public RightClickOperation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public void execute() {
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		if(!isClient){
			chess.mark(x, y);
		}else{
			chess.mark(x, y, true);
		}
		
	}

}
