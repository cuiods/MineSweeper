package edu.nju.model.impl;

import edu.nju.model.data.StatisticData;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	private StatisticData statisticDao;
	
	public StatisticModelImpl(){
		//初始化Dao
	}

	@Override
	public void recordStatistic(GameResultState result, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showStatistics() {
		// TODO Auto-generated method stub
		
	}

}
