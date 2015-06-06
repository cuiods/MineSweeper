package edu.nju.network.modelProxy;

import java.util.Observable;
import java.util.Observer;

import edu.nju.model.impl.BaseModel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;
import edu.nju.network.client.ClientService;

/**
 * 所有的代理类的基类。
 * @author 晨晖
 *
 */
public class ModelProxy extends BaseModel implements Observer{
	protected ClientService net;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		TransformObject obj = (TransformObject) arg;
		String trigger_class = obj.getSource();
		UpdateMessage msg = obj.getMsg();
		System.out.println("GameModelProxy get the UpdateMessage! - " + msg.getKey());
		System.out.println("Triiget class is " + trigger_class);
		Class<?> super_class = this.getClass().getInterfaces()[0];
		System.out.println("super class is : " + super_class.getName());
		try {
			if(super_class.isAssignableFrom(Class.forName(trigger_class))){
				System.out.println(this.getClass().getName()+" get the UpdateMessage!");
				this.updateChange(msg);
				System.out.println("UpdateMessage send!!!");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
