package edu.nju.model.impl;

import edu.nju.model.service.ParameterModelService;

public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private static int maxMine;
	private int mineNum;
	private int flagNum;
	private int flagNum2;

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

	@Override
	public boolean canMark() {
		if(mineNum>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addFlagNum() {
		flagNum++;
		super.updateChange(new UpdateMessage("flagNum", flagNum));
		return true;
	}
	

	@Override
	public void setFlagNum(int flagNum) {
		// TODO Auto-generated method stub
		this.flagNum = flagNum;
	}
	
	public int getFlagNum(){
		return flagNum;
	}

	@Override
	public boolean addFlagNum2() {
		flagNum2++;
		super.updateChange(new UpdateMessage("flagNum2", flagNum2));
		return true;
	}

	@Override
	public void setFlagNum2(int flagNum2) {
		// TODO Auto-generated method stub
		this.flagNum2 = flagNum2;
	}
	
	public int getFlagNum2(){
		return flagNum2;
	}

}
