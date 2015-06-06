package edu.nju.network.client;

import edu.nju.controller.msgqueue.operation.MineOperation;

public abstract class ClientService {
	public boolean init(String addr,ClientInHandler handler){
		boolean succeed = ClientAdapter.init(addr, handler);
		return succeed;
	}
	
	public void close(){
		ClientAdapter.close();
	}
	/**
	 * 上传从服务器端的相关操作。
	 * @param op
	 */
	public abstract void submitOperation(MineOperation op);
	
}
