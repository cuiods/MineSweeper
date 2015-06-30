package edu.nju.model.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import edu.nju.model.data.StatisticData;
import edu.nju.model.po.StatisticPO;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	private StatisticData statisticDao;
	
	public StatisticModelImpl(){
		//初始化Dao
		statisticDao = new StatisticData();
	}

	@Override
	public void recordStatistic(GameResultState result, int time) {
		
	}

	@Override
	public void showStatistics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordStatistic(GameResultState result, int time, String level) {
		//StatisticPO statistic = new StatisticPO();
		BufferedWriter bw = null;
		BufferedReader br = null;
		String[] names = new String[3];
		String[] scores = new String[3];
		String[] temp1 = new String[3];
		String[] temp2 = new String[2];
		boolean isWin = result.equals(GameResultState.SUCCESS);
		int levelIndex = 0;
		if(level.equals("中")){levelIndex=1;}
		else if(level.equals("大")){levelIndex=2;}
		try {
			File recordFile = new File("save.data");
			if(!recordFile.exists()){
				recordFile.createNewFile();
				BufferedWriter temp = new BufferedWriter(new FileWriter("save.data"));
				temp.write("UnKnown 0/0 Easy"+"\n"+"UnKnown 0/0 Hard"+"\n"+"UnKnown 0/0 Hell"+"\n");
				temp.close();
			}
			
			
			br = new BufferedReader(new FileReader("save.data"));
			for(int i = 0; i < 3; i++){
				temp1 = br.readLine().split(" ");
				names[i] = temp1[0];
				scores[i] = temp1[1];
			}
			br.close();
			
			
			temp2 = scores[levelIndex].split("/");
			if(isWin){
				temp2[0] = Integer.parseInt(temp2[0])+1+"";
			}
			temp2[1] = Integer.parseInt(temp2[1])+1+"";
			scores[levelIndex] = temp2[0]+"/"+temp2[1];
			
			bw = new BufferedWriter(new FileWriter("save.data"));
			bw.write(names[0]+" "+scores[0]+" "+"Easy"+"\n");
			bw.write(names[1]+" "+scores[1]+" "+"Hard"+"\n");
			bw.write(names[2]+" "+scores[2]+" "+"Hell"+"\n");
			bw.close();
			
//			System.out.println(names[0]+" "+scores[0]+" "+"Easy"+"\n");
//			System.out.println(names[1]+" "+scores[1]+" "+"Hard"+"\n");
//			System.out.println(names[2]+" "+scores[2]+" "+"Hell"+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
