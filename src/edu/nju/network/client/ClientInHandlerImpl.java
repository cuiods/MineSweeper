package edu.nju.network.client;

import java.util.Observable;

import edu.nju.network.TransformObject;

public class ClientInHandlerImpl extends Observable implements ClientInHandler{

	@Override
	public void inputHandle(Object data) {
		// TODO Auto-generated method stub
		//understand data and handle them;
		
		TransformObject obj = (TransformObject) data;
			
		this.setChanged();
		this.notifyObservers(obj);
		
	}

}
