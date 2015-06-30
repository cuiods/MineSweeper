package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;

public class FlagNumLabel extends JLabel implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("flagNum")){
			int flagNum = (Integer) updateMessage.getValue();
			this.setText("host:"+flagNum);
		}
	}

}
