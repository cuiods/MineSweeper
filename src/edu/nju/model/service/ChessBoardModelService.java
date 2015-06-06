package edu.nju.model.service;

/**
 * 棋盘Model，负责棋盘的数据层操作
 * @author Wangy
 *
 */
public interface ChessBoardModelService {

	/**
	 * 初始化棋盘，并随机放置mineNum个地雷
	 * @param width 棋盘列数
	 * @param height 棋盘行数
	 * @param mineNum 地雷个数
	 * @return
	 */
	public boolean initialize(int width, int height, int mineNum);
	
	/**
	 * 挖开(x,y)位置
	 * 
	 * 规则：
	 * 		1、若（x，y）位置未挖开，挖开（x，y）位置，并连带挖开附近区域
	 * （由附近雷数大于0的Block所围成的连续区域，具体可参看扫雷游戏）
	 * 若挖到雷则游戏失败
	 * 		2、若（x，Y）位置为其它状态，操作失效
	 * @param x
	 * @param y
	 * @return 操作是否可执行
	 */
	public boolean excavate(int x, int y);
	
	/**
	 * 标记(x,y)位置
	 * 
	 * 规则：
	 * 		1、若（x，y）位置为未挖开，则标记雷
	 * 		2、若（x，y）位置标记雷，则取消标记
	 * 		3、若（x，y）位置为其它状态，操作失效
	 * @param x
	 * @param y
	 * @return 操作是否可执行
	 */
	public boolean mark(int x, int y);
	
	/**
	 * 快速挖开（x,y)附近区域
	 * 
	 * 规则：
	 * 		1、若（x，y）已被挖开，并且显示的数字等于周围被标识的雷数，则挖开周围未被标识的Block
	 * （每次挖开都等效于excavate操作，在雷标识错误的情况下会挖到雷）。
	 * 		2、其它情况下，操作失效。
	 * @param x
	 * @param y
	 * @return 操作是否可执行
	 */
	public boolean quickExcavate(int x, int y);
	
	/**
	 * 传入GameModelService引用，初始化时使用
	 * @param gameModel
	 */
	public void setGameModel(GameModelService gameModel);
}