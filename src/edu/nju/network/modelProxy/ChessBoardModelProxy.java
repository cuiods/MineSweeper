package edu.nju.network.modelProxy;

import java.util.ArrayList;
import java.util.List;

import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.client.ClientService;

public class ChessBoardModelProxy extends ModelProxy implements ChessBoardModelService{

	private GameModelService gameModel;
	private ParameterModelService parameterModel;

	private BlockPO[][] blockMatrix;
	
	public ChessBoardModelProxy(ParameterModelService parameterModel,ClientService client){
		this.parameterModel = parameterModel;
		this.net = client;
	}
	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);
		
		this.printBlockMatrix();
		return true;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new LeftClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new RightClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new DoubleClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		
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
			
			
			return false;
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
	@Override
	public boolean mark(int x, int y, boolean isClient) {
		MineOperation op = new RightClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}
	@Override
	public boolean excavate(int x, int y, boolean isClient) {
		MineOperation op = new LeftClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}
	@Override
	public boolean quickExcavate(int x, int y, boolean isClient) {
		MineOperation op = new DoubleClickOperation(x, y);
		op.isClient = true;
		net.submitOperation(op);
		return true;
	}
	@Override
	public ParameterModelService getParameterModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
