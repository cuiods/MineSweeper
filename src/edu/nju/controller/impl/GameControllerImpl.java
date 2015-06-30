package edu.nju.controller.impl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.controller.service.GameControllerService;
public class GameControllerImpl implements GameControllerService{

	@Override
	public boolean handleLeftClick(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new LeftClickOperation(x,y);
		OperationQueue.addMineOperation(op);
		return true;
	}

	@Override
	public boolean handleRightClick(int x, int y) {
		MineOperation op = new RightClickOperation(x,y);
		OperationQueue.addMineOperation(op);
		return true;
	}

	@Override
	public boolean handleDoubleClick(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new DoubleClickOperation(x,y);
		OperationQueue.addMineOperation(op);
		return true;
	}

}
