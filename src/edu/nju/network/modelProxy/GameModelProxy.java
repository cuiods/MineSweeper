package edu.nju.network.modelProxy;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.model.impl.GameLevel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;
import edu.nju.network.client.ClientService;
import edu.nju.view.TimeLabel;
/**
 * GameModel的代理，在网络对战时替代Client端的相应Model。
 * @author 晨晖
 *
 */
public class GameModelProxy extends ModelProxy implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelService chessBoardModel;
	
	private List<GameLevel> levelList;
	
	private GameState gameState;
	private int width;
	private int height;
	private int mineNum;
	private String level;
	
	private GameResultState gameResultStae;
	private int time;
	
	private long startTime;
	
	public GameModelProxy(ClientService client){
		this.net = client;
	}
	
	public void setUp(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
		this.statisticModel = statisticModel;
		this.chessBoardModel = chessBoardModel;
		gameState = GameState.OVER;
		
		chessBoardModel.setGameModel(this);
		
		levelList = new ArrayList<GameLevel>();
		levelList.add(new GameLevel(0,"大",30,16,99));
		levelList.add(new GameLevel(1,"中",16,16,40));
		levelList.add(new GameLevel(2,"小",9,9,10));
	}

	@Override
	public boolean setGameLevel(String level) {
		this.level = level;
		return true;
	}

	@Override
	public boolean startGame() {
		
		MineOperation op = new StartGameOperation();
		net.submitOperation(op);
		return true;
	}

	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return this.levelList;
	}


	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		//输入校验
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		return true;
	}

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height,level, gameResultStae, time);
	}

	@Override
	public boolean gameOver(GameResultState result, boolean isClient) {
		// TODO Auto-generated method stub
		return false;
	}

}
