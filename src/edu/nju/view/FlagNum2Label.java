package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;

public class FlagNum2Label extends JLabel implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("flagNum2")){
			int flagNum2 = (Integer) updateMessage.getValue();
			this.setText("client:"+flagNum2);
		}
	}

}
