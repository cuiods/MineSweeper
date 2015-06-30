package edu.nju.network.modelProxy;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.ParameterModelService;

public class ParameterModelProxy extends ModelProxy implements ParameterModelService{
	
	private int maxMine;
	private int mineNum;
	private int flagNum;

	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}
	
	public int getMineNum(){
		return maxMine;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
		
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		mineNum--;
		
		if(mineNum<0){
			mineNum++;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}
	
	public boolean addFlagNum(){
		flagNum++;
		super.updateChange(new UpdateMessage("flagNum", flagNum));
		return true;
	}

	@Override
	public boolean canMark() {
		if(mineNum>0){
			return true;
		}
		return false;
	}

	public int getFlagNum() {
		return flagNum;
	}

	public void setFlagNum(int flagNum) {
		this.flagNum = flagNum;
	}

	@Override
	public boolean addFlagNum2() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFlagNum2(int flagNum2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFlagNum2() {
		// TODO Auto-generated method stub
		return 0;
	}

}
