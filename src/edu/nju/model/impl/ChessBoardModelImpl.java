package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.Configure;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	private ParameterModelService parameterModel;

	private BlockPO[][] blockMatrix;

	
	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);
		this.parameterModel.setFlagNum(0);
		
		this.printBlockMatrix();
		
		return true;
	}

	@Override
	public boolean excavate(int x, int y) {

		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		if(block.getState() == BlockState.FLAG){
			this.parameterModel.addMineNum();
		}
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL,false);
		}
		
		int testNum = 0;
		for(int i = 0; i < blockMatrix.length; i++){
			for(int j = 0; j < blockMatrix[0].length; j++){
				if(blockMatrix[i][j].getState().equals(BlockState.UNCLICK)&&!blockMatrix[i][j].isMine()){
					testNum++;
				}
			}
		}
		if(testNum==0){
			if(Configure.isClient||Configure.isServer){
				gameState = GameState.OVER;
				if(whetherServerWin()){
					this.gameModel.gameOver(GameResultState.SUCCESS,false);
				}else{
					this.gameModel.gameOver(GameResultState.SUCCESS,true);
				}
			}else{
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.SUCCESS,false);
			}
		}		

		if(block.getMineNum()==0){
			for(int i = x-1; i <= x+1; i++){
				for(int j = y-1; j <= y+1; j++){
					if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&blockMatrix[i][j].getState()==BlockState.UNCLICK){
						excavateSmall(i, j,blocks);
					}
				}
			}
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));	
		return true;
	}
	
	private void excavateSmall(int x, int y, List<BlockPO> blocks){
		BlockPO block = blockMatrix[x][y];
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		if(block.getMineNum()==0){
			for(int i = x-1; i <= x+1; i++){
				for(int j = y-1; j <= y+1; j++){
					if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&blockMatrix[i][j].getState()==BlockState.UNCLICK){
						excavateSmall(i, j,blocks);
					}
				}
			}
		}
	}
	
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		if(blockMatrix == null)
			return false;
		
		if(this.parameterModel.canMark()){
			GameState gameState = GameState.RUN;
			List<BlockPO> blocks = new ArrayList<BlockPO>();
			BlockPO block = blockMatrix[x][y];
			 
			BlockState state = block.getState();
			if(state == BlockState.UNCLICK){
				block.setState(BlockState.FLAG);
				this.parameterModel.minusMineNum();
				this.parameterModel.addFlagNum();
				
			}
			else if(state == BlockState.FLAG){
				block.setState(BlockState.UNCLICK);
				this.parameterModel.addMineNum();
			}
			
			blocks.add(block);
			
			//whether win
			int testNum = 0;
			for(int i = 0; i < blockMatrix.length; i++){
				for(int j = 0; j < blockMatrix[0].length; j++){
					if(blockMatrix[i][j].isMine()&&(blockMatrix[i][j].getState().equals(BlockState.FLAG)||blockMatrix[i][j].getState()==BlockState.FLAG2)){
						testNum++;
					}
				}
			}
			if(testNum==parameterModel.getMineNum()){
				if(Configure.isClient||Configure.isServer){
					gameState = GameState.OVER;
					if(whetherServerWin()){
						this.gameModel.gameOver(GameResultState.SUCCESS,false);
					}else{
						this.gameModel.gameOver(GameResultState.SUCCESS,true);
					}
				}else{
					gameState = GameState.OVER;
					this.gameModel.gameOver(GameResultState.SUCCESS,false);
				}
			}
			if(Configure.isServer&&!block.isMine()){
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.FAIL,false);
			}
			
			super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		/***********请在此处完成快速挖开方法实现****************/
		if(blockMatrix == null)
			return false;
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		GameState gameState = GameState.RUN;
		if(block.getState()==BlockState.CLICK){
			int flagNum = 0;
			for(int i = x-1;i <= x+1;i++){
				for(int j = y-1; j <= y+1; j++){
					if(!(i==x&&j==y)){
						if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&(blockMatrix[i][j].getState()==BlockState.FLAG||blockMatrix[i][j].getState()==BlockState.FLAG2)){
							flagNum++;
						}
					}
				}
			}
			if(block.getMineNum()==flagNum){
				for(int i = x-1;i <= x+1;i++){
					for(int j = y-1; j <= y+1; j++){
						if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&blockMatrix[i][j].getState()==BlockState.UNCLICK){
//							blockMatrix[i][j].setState(BlockState.CLICK);
//							blocks.add(blockMatrix[i][j]);
//							
//							if(blockMatrix[i][j].isMine()){
//								this.gameModel.gameOver(GameResultState.FAIL);
//								gameState = GameState.OVER;
//							}
							excavate(i, j);
						}
					}
				}
				super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int index = 0;
		
		//初始化及布雷
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
				//放置雷，并设定block附近雷数，现有放置方法为固定方法，请添加随机实现
//				index ++;
//				if(index == 2){
//					if(mineNum>0){
//						if(i>3&&j>3){
//							blockMatrix[i-1][j-1].setMine(true);
//						
//							addMineNum(i-1,j-1);
//							mineNum--;
//						}
//					}
//					index = 0;
//				}				
			}
		}
		while(mineNum>0){
			int ramI = (int)(Math.random()*width);
			int ramJ = (int)(Math.random()*height);
			if(!blockMatrix[ramI][ramJ].isMine()){
				blockMatrix[ramI][ramJ].setMine(true);
				addMineNum(ramI, ramJ);
				mineNum--;
			}
		}
		
		
		return true;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}
	
	public ParameterModelService getParameterModel() {
		return parameterModel;
	}

	public void setParameterModel(ParameterModelService parameterModel) {
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean mark(int x, int y, boolean isClient) {

		// TODO Auto-generated method stub
		if(blockMatrix == null)
			return false;
		
		if(this.parameterModel.canMark()){
			GameState gameState = GameState.RUN;
			List<BlockPO> blocks = new ArrayList<BlockPO>();
			BlockPO block = blockMatrix[x][y];
			 
			BlockState state = block.getState();
			if(state == BlockState.UNCLICK){
				block.setState(BlockState.FLAG2);
				this.parameterModel.minusMineNum();
				this.parameterModel.addFlagNum2();
			}
			else if(state == BlockState.FLAG2){
				block.setState(BlockState.UNCLICK);
				this.parameterModel.addMineNum();
			}
			
			blocks.add(block);
			
			//whether win
			int testNum = 0;
			for(int i = 0; i < blockMatrix.length; i++){
				for(int j = 0; j < blockMatrix[0].length; j++){
					if(blockMatrix[i][j].isMine()&&blockMatrix[i][j].getState().equals(BlockState.FLAG)){
						testNum++;
					}
				}
			}
			if(testNum==parameterModel.getMineNum()){
				if(Configure.isClient||Configure.isServer){
					gameState = GameState.OVER;
					if(whetherServerWin()){
						this.gameModel.gameOver(GameResultState.SUCCESS,false);
					}else{
						this.gameModel.gameOver(GameResultState.SUCCESS,true);
					}
				}else{
					gameState = GameState.OVER;
					this.gameModel.gameOver(GameResultState.SUCCESS,false);
				}
			}
			if(Configure.isServer&&!block.isMine()){
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.FAIL,true);
			}
			
			super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
			return true;
		}
		
		return false;
	
	}

	@Override
	public boolean excavate(int x, int y, boolean isClient) {


		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		if(block.getState() == BlockState.FLAG){
			this.parameterModel.addMineNum();
		}
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL,true);
		}
		
		int testNum = 0;
		for(int i = 0; i < blockMatrix.length; i++){
			for(int j = 0; j < blockMatrix[0].length; j++){
				if(blockMatrix[i][j].getState().equals(BlockState.UNCLICK)&&!blockMatrix[i][j].isMine()){
					testNum++;
				}
			}
		}
		if(testNum==0){
			if(Configure.isClient||Configure.isServer){
				gameState = GameState.OVER;
				if(whetherServerWin()){
					this.gameModel.gameOver(GameResultState.SUCCESS,false);
				}else{
					this.gameModel.gameOver(GameResultState.SUCCESS,true);
				}
			}else{
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.SUCCESS,false);
			}
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			

		if(block.getMineNum()==0){
			for(int i = x-1; i <= x+1; i++){
				for(int j = y-1; j <= y+1; j++){
					if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&blockMatrix[i][j].getState()==BlockState.UNCLICK){
						excavate(i, j);
					}
				}
			}
		}
		return true;
	
	}

	@Override
	public boolean quickExcavate(int x, int y, boolean isClient) {

		// TODO Auto-generated method stub
		/***********请在此处完成快速挖开方法实现****************/
		if(blockMatrix == null)
			return false;
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		GameState gameState = GameState.RUN;
		if(block.getState()==BlockState.CLICK){
			int flagNum = 0;
			for(int i = x-1;i <= x+1;i++){
				for(int j = y-1; j <= y+1; j++){
					if(!(i==x&&j==y)){
						if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&(blockMatrix[i][j].getState()==BlockState.FLAG||blockMatrix[i][j].getState()==BlockState.FLAG2)){
							flagNum++;
						}
					}
				}
			}
			if(block.getMineNum()==flagNum){
				for(int i = x-1;i <= x+1;i++){
					for(int j = y-1; j <= y+1; j++){
						if(i>=0&&i<blockMatrix.length&&j>=0&&j<blockMatrix[0].length&&blockMatrix[i][j].getState()==BlockState.UNCLICK){
//							blockMatrix[i][j].setState(BlockState.CLICK);
//							blocks.add(blockMatrix[i][j]);
//							
//							if(blockMatrix[i][j].isMine()){
//								this.gameModel.gameOver(GameResultState.FAIL);
//								gameState = GameState.OVER;
//							}
							excavate(i, j,true);
						}
					}
				}
				super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
				return true;
			}
		}
		
		return false;
	
	}
	
	private boolean whetherServerWin(){
		if(parameterModel.getFlagNum()>parameterModel.getFlagNum2()){
			return true;
		}
		return false;
	}
}
