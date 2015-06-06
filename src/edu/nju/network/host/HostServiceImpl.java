package edu.nju.network.host;

import java.util.Observable;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;

public class HostServiceImpl extends HostService {

	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		UpdateMessage msge = (UpdateMessage) arg;
		String trigger_class = o.getClass().getName();
		TransformObject obj = new TransformObject(trigger_class, msge);
		this.publishData(obj);
	}

	@Override
	public void publishData(TransformObject o) {
		// TODO Auto-generated method stub
		ServerAdapter.write(o);
	}

	

}
