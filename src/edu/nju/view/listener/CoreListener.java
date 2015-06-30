package edu.nju.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.nju.controller.impl.GameControllerImpl;
import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.service.GameControllerService;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.view.Location;
import edu.nju.view.MainFrame;
import edu.nju.view.MyButton;

public class CoreListener implements MouseListener, ActionListener {
	
	

	private MainFrame ui;
	MenuControllerService menuController = new MenuControllerImpl();
	GameControllerService mouseController = new GameControllerImpl();


	public CoreListener(MainFrame ui){
		super();
  		this.ui = ui;
	}
	public CoreListener(){
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==ui.getStartButton()){//点击head中间的图标生成新游戏
			System.out.println("CoreAction action performed just for start Button");
			menuController.startGame();			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() > 2) return;
		
		if(e.getButton()==MouseEvent.BUTTON3){//右键相应雷格
			MyButton button = (MyButton) e.getSource();
			Location location = button.getMyLocation();
			mouseController.handleRightClick(location.x, location.y);
		 
		}else if(e.getButton()==MouseEvent.BUTTON1){//左键相应雷格
			if(e.getClickCount()==2){//双击左键
				MyButton button = (MyButton) e.getSource();
				Location location = button.getMyLocation();
				mouseController.handleDoubleClick(location.x, location.y);
			}else{//单击左键					
				//System.out.println("监听到了单击!!!!!!");
				MyButton button = (MyButton) e.getSource();
				Location location = button.getMyLocation();
				mouseController.handleLeftClick(location.x, location.y);
			}
		}else if(e.getButton()==MouseEvent.BUTTON2){//点击滚轮键
			
		}
		

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
