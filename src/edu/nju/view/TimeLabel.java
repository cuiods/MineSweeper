package edu.nju.view;

import java.awt.Graphics;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.GameModelService;

public class TimeLabel extends JLabel implements Runnable {

	private static long startTime;
	private Thread timeThread;
	private static boolean isRun;
	
	public TimeLabel() {
		startTime = Calendar.getInstance().getTimeInMillis();
		timeThread = new Thread(this);
		timeThread.start();
	}
	
	private static void reset(){
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public static void setRun(boolean isrun){
		reset();
		isRun = isrun;
	}

	@Override
	public void run() {
		while(true){
			if(isRun){
				this.setText((int)((Calendar.getInstance().getTimeInMillis()-startTime)/1000+1)+"");
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
}
