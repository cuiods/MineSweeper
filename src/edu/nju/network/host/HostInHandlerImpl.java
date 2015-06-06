package edu.nju.network.host;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.MineOperation;


public class HostInHandlerImpl implements HostInHandler{
	/**
	 * 主服务器端，得到MineOperaion，并把它加入到OperationQueue.
	 */
	@Override
	public void inputHandle(Object data) {
		// TODO Auto-generated method stub
		MineOperation op = (MineOperation) data;
		OperationQueue.addMineOperation(op);
	}
}
