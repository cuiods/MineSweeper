package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.Configure;

public class MineNumberLabel extends JLabel implements Observer {

	
	public MineNumberLabel(){
		
	}
	
	private int reamainMinesNumber;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("mineNum")){
			
			int remainMines = (Integer) updateMessage.getValue();
			this.setReamainMinesNumber(remainMines);
			if(Configure.isClient||Configure.isServer){
				this.setText("余："+remainMines);
			}else{
				this.setText(remainMines+"");
			}
		}

	}
	public int getReamainMinesNumber() {
		return reamainMinesNumber;
	}
	public void setReamainMinesNumber(int reamainMinesNumber) {
		this.reamainMinesNumber = reamainMinesNumber;
	}

}
