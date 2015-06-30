package edu.nju.model.service;

/**
 * 负责控制游戏参数Model，现有参数：剩余雷数
 * @author Wangy
 *
 */
public interface ParameterModelService {

	/**
	 * 设置剩余类数量
	 * @param num
	 * @return
	 */
	public boolean setMineNum(int num);
	
	/**
	 * 减少雷数
	 * @return
	 */
	public boolean minusMineNum();
	
	/**
	 * 增加雷数
	 * @return
	 */
	public boolean addMineNum();
	
	/**
	 * 是否可以标记
	 * @return
	 */
	public boolean canMark();
	
	public int getMineNum();
	public boolean addFlagNum();
	public int getFlagNum();
	public void setFlagNum(int flagNum);
	public boolean addFlagNum2();
	public void setFlagNum2(int flagNum2);
	public int getFlagNum2();
	
}
