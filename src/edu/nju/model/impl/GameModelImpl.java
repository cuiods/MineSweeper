package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;
import edu.nju.view.TimeLabel;

public class GameModelImpl extends BaseModel implements GameModelService{
	
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

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
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
	public boolean startGame() {
		// TODO Auto-generated method stub
		gameState = GameState.RUN;
		startTime = Calendar.getInstance().getTimeInMillis();
		TimeLabel.setRun(true);
		
		GameLevel gl = null;
		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}
		if(gl == null&&width==0&&height == 0)
			gl = levelList.get(2);
		
		if(gl != null){
			height = gl.getWidth();
			width = gl.getHeight();
			mineNum = gl.getMineNum();
		}
		
		this.chessBoardModel.initialize(width, height, mineNum);
		
		super.updateChange(new UpdateMessage("start",this.convertToDisplayGame()));
		return true;
	}
	
	@Override
	public boolean gameOver(GameResultState result,boolean isClient) {
		// TODO Auto-generated method stub
		TimeLabel.setRun(false);
		this.gameState = GameState.OVER;
		this.gameResultStae = result;
		if(isClient){
			if(result.equals(GameResultState.FAIL)){
				this.gameResultStae = GameResultState.SUCCESS;
			}else if(result.equals(GameResultState.SUCCESS)){
				this.gameResultStae = GameResultState.FAIL;
			}
		}
		this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
		this.statisticModel.recordStatistic(result, time, level);
		
		super.updateChange(new UpdateMessage("end",this.convertToDisplayGame()));		
		return true;
	}

	@Override
	public boolean setGameLevel(String level) {
		// TODO Auto-generated method stub
		//输入校验
		this.level = level;
		return true;
	}

	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		//输入校验
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		GameVO gamevo = new GameVO(gameState, width, height,level, gameResultStae, time);
		return gamevo;
	}

	@Override
	public List<GameLevel> getGameLevel() {
		return this.levelList;
	}
	
	@Override
	public int getTime(){
		return (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
	}
	
	public StatisticModelService getStatisticModel() {
		return statisticModel;
	}

	public void setStatisticModel(StatisticModelService statisticModel) {
		this.statisticModel = statisticModel;
	}

	public ChessBoardModelService getChessBoardModel() {
		return chessBoardModel;
	}

	public void setChessBoardModel(ChessBoardModelService chessBoardModel) {
		this.chessBoardModel = chessBoardModel;
	}
}