package com.kk.reaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.kk.entity.Agent;
import com.kk.entity.Counter;
import com.kk.entity.Direction;
import com.kk.entity.Patch;
import com.kk.entity.State;

/**
 * @author Couble Krise
 * 
 */
public class Gridspace {
	//
	public Counter counter[];
	//
	public int rowSize = 10;
	public int colSize = 10;
	public List<Patch> patches;
	public int patchSize = 100;
	//
	public double k1 = 2.0;
	public double k2 = 2.0;
	public double alpha = 1.01;
	public double beta = 1.50;
	//
	public double lambda0 = 0.003;
	public double theta = 1.00; //0.93; 1.0;
	//
	public double miu0 = 0.002;
	public double epsilon = 0.8; //0.0; 0.8;
	//
	public double[][] scaleMat = new double[30][30];
	public double[][] resMat = new double[30][30];

	//---------------------------------------------------------------------------------------------------------
	{
		BufferedReader buffReader = null;
		try {
			File myFile = new File("files/ScaleMatrix");
			if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
				//将文件字节输入流转换为文件字符输入流并给定编码格式
				buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(myFile), "UTF-8"));
				//通过BuffereReader包装实现高效读取
				String line = null;
				int row = 0;
				while ((line = buffReader.readLine()) != null) {
					//System.out.println(line);
					String[] strs = line.split("\t");
					for (int i = 0; i < strs.length; i++) {
						//System.out.println(strs[i]);
						scaleMat[row][i] = Double.parseDouble(strs[i]);
					}
					row++;
				}
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	//----------------------------------------------------------------------------------------------------------
	{
		BufferedReader buffReader = null;
		try {
			File myFile = new File("files/ResMatrix");
			if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
				//将文件字节输入流转换为文件字符输入流并给定编码格式
				buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(myFile), "UTF-8"));
				//通过BuffereReader包装实现高效读取
				String line = null;
				int row = 0;
				while ((line = buffReader.readLine()) != null) {
					//System.out.println(line);
					String[] strs = line.split("\t");
					for (int i = 0; i < strs.length; i++) {
						//System.out.println(strs[i]);
						resMat[row][i] = Double.parseDouble(strs[i]);
					}
					row++;
				}
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//--------------------------------------------------------------------------------------------------------

	/**
	 * ReactionProcess
	 * 
	 */
	public Gridspace() {
		//
		patches = new ArrayList<Patch>(rowSize * colSize);
		counter = new Counter[rowSize * colSize];
		//
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				//实例化一个patch    城市规模   城市资源
				Patch patch = new Patch(row, col, scaleMat[row][col], resMat[row][col]);
				//计算patch感染率
				patch.setInfecRate(lambda0 * Math.pow(theta, patch.getScale()));
				//计算patch恢复率
				patch.setRecovRate(1 - Math.pow(1 - miu0, 1 + epsilon * patch.getRes()));
				//
				for (int count = 0; count < patchSize; count++) {
					Agent agent = new Agent();
					if (Math.random() < 0.01) {
						agent.setState(State.I);//初始感染种子
					}
					patch.getAgents().add(agent);
				}
				patches.add(patch);
				counter[row * colSize + col] = new Counter(patch.getAgents().size(), 0);
			}//for in
		}//for out

	}//init

	//--------------------------------------------------------------------------------------------------------
	
	/**
	 * @param rowSize
	 * @param colSize
	 * @param alpha
	 * @param beta
	 * @param lambda0
	 * @param theta
	 * @param miu0
	 * @param epsilon
	 */
	public Gridspace(int rowSize, int colSize, double alpha, double beta, double lambda0, double theta, double miu0, double epsilon) {

		this.rowSize = rowSize;
		this.colSize = colSize;
		this.alpha = alpha;
		this.beta = beta;
		this.lambda0 = lambda0;
		this.theta = theta;
		this.miu0 = miu0;
		this.epsilon = epsilon;
		//
		patches = new ArrayList<Patch>(rowSize * colSize);
		counter = new Counter[rowSize * colSize];
		//
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				//实例化一个patch
				Patch patch = new Patch(row, col, scaleMat[row][col], resMat[row][col]);
				//计算patch感染率         
				patch.setInfecRate(lambda0 * Math.pow(theta, patch.getScale()));
				//计算patch恢复率
				patch.setRecovRate(1 - Math.pow(1 - miu0, 1 + epsilon * patch.getRes()));
				//
				for (int count = 0; count < patchSize; count++) {
					Agent agent = new Agent();
					if (Math.random() < 0.01) {
						agent.setState(State.I);//初始感染种子
					}
					patch.getAgents().add(agent);
				}
				patches.add(patch);
				counter[row * colSize + col] = new Counter(patch.getAgents().size(), 0);
			}//for in
		}//for out

		//打印地点影响力分布
		int cnt = 1;
		for (Patch patch : this.patches) {
			System.out.print((int) (k1 * Math.pow(patch.getScale(), alpha) + k2 * Math.pow(patch.getRes(), beta)) + "\t");
			cnt++;
			if (cnt > this.colSize) {
				System.out.println();
				cnt = 1;
			}
		}
	}

	/**
	 * moveAndReaction
	 * 
	 */
	public void moveAndReaction() {
		//1.move 开始迁移--------------------------------------------------------
		for (Patch patch : patches) {
			Direction[] diff_directions = getDiffuseDirections(patch);
			double[] all = new double[diff_directions.length];
			double sum = 0.0;
			for (int dir = 0; dir < diff_directions.length; dir++) {
				int dx = diff_directions[dir].getDx();
				int dy = diff_directions[dir].getDy();
				//9个潜在目标位置
				int dest_x = patch.getX() + dx;
				int dest_y = patch.getY() + dy;
				//System.out.println("patch.getX()="+ patch.getX()+"   dest_x="+ dest_x + "    dest_y="+dest_y);
				//System.out.println(colSize * dest_x + dest_y);
				Patch destPatch = patches.get(colSize * dest_x + dest_y);
				//计算某个patch的地点影响力--受城市规模，资源因素影响
				double val = k1 * Math.pow(destPatch.getScale(), alpha) + //
						k2 * Math.pow(destPatch.getRes(), beta);
				sum = sum + val;
				all[dir] = sum;
			}
			//
			for (int dir_2 = 0; dir_2 < all.length; dir_2++) {
				all[dir_2] = all[dir_2] / sum;
			}
			//
			List<Agent> tmpList = new LinkedList<Agent>();
			int directionInd = 0;
			for (int i = 0; i < patch.getAgents().size(); i++) {
				double rand = Math.random();
				for (int dir_3 = 0; dir_3 < all.length; dir_3++) {
					if (rand <= all[dir_3]) {
						directionInd = dir_3;
						break;
					}
				}
				//移动
				int dx = diff_directions[directionInd].getDx();
				int dy = diff_directions[directionInd].getDy();
				//目标位置
				int dest_x = patch.getX() + dx;
				int dest_y = patch.getY() + dy;
				Patch destPatch = patches.get(colSize * dest_x + dest_y);
				if (destPatch != patch) {
					Agent agent = patch.getAgents().get(i);
					//加入
					destPatch.getAgents().add(agent);
					//发生移动则从当前patch移除
					tmpList.add(agent);
				}
			}//agent
			if (!tmpList.isEmpty()) {
				patch.getAgents().removeAll(tmpList);
			}
		}//patch-----------------------------------------------------------------

		//2.reaction 全部移动后开始流行病反应 SIR---------------------------------------
		for (Patch patch : patches) {
			for (int i = 0; i < patch.getAgents().size(); i++) {
				Agent agent1 = patch.getAgents().get(i);
				for (int j = i + 1; j < patch.getAgents().size(); j++) {
					Agent agent2 = patch.getAgents().get(j);
					if (agent1.getState() == State.S && agent2.getState() == State.I) {
						if (Math.random() <= patch.getInfecRate()) {
							agent1.setState(State.I);
						}
					} else if (agent1.getState() == State.I && agent2.getState() == State.S) {
						if (Math.random() <= patch.getInfecRate()) {
							agent2.setState(State.I);
						}
					}
				}//for
			}
			//2.2. 恢复过程...............
			for (Agent agent : patch.getAgents()) {
				if (agent.getState() == State.I) {
					if (Math.random() < patch.getRecovRate()) {
						agent.setState(State.S);
					}
				}
			}//for in
		}//----------------------------------------------------------------------

		//更新计数器
		for (Patch patch : patches) {
			int numS_ = 0, numI_ = 0;
			for (Agent agent : patch.getAgents()) {
				if (agent.getState() == State.S) {
					numS_++;
				} else if (agent.getState() == State.I) {
					numI_++;
				}
			}
			//System.out.println(patch.getX()*width + patch.getY());
			counter[patch.getX() * colSize + patch.getY()].numS = numS_;
			counter[patch.getX() * colSize + patch.getY()].numI = numI_;
		}

	}//move and reaction

	/**
	 * @param patch
	 * @return
	 */
	private Direction[] getDiffuseDirections(Patch patch) {
		Direction[] diff_directions = new Direction[9];
		int x = patch.getX();
		int y = patch.getY();
		final int stepLength = 1;
		int west_x = x - stepLength;
		int east_x = x + stepLength;
		int north_y = y - stepLength;
		int south_y = y + stepLength;
		//保持，不移动
		diff_directions[0] = Direction.STATIC;
		//边界条件
		if (west_x >= 0) {
			diff_directions[1] = Direction.WEST;
		} else {
			diff_directions[1] = Direction.STATIC;
		}
		//
		if (east_x < rowSize) {
			diff_directions[2] = Direction.EAST;
		} else {
			diff_directions[2] = Direction.STATIC;
		}
		//
		if (north_y >= 0) {
			diff_directions[3] = Direction.NORTH;
		} else {
			diff_directions[3] = Direction.STATIC;
		}
		//
		if (south_y < colSize) {
			diff_directions[4] = Direction.SOUTH;
		} else {
			diff_directions[4] = Direction.STATIC;
		}
		//
		if (south_y < colSize && west_x >= 0) {
			diff_directions[5] = Direction.SOUTH_WEST;
		} else {
			diff_directions[5] = Direction.STATIC;
		}
		//
		if (south_y < colSize && east_x < rowSize) {
			diff_directions[6] = Direction.SOUTH_EAST;
		} else {
			diff_directions[6] = Direction.STATIC;
		}
		//
		if (north_y >= 0 && west_x >= 0) {
			diff_directions[7] = Direction.NORTH_WEST;
		} else {
			diff_directions[7] = Direction.STATIC;
		}
		//
		if (north_y >= 0 && east_x < rowSize) {
			diff_directions[8] = Direction.NORTH_EAST;
		} else {
			diff_directions[8] = Direction.STATIC;
		}
		//
		return diff_directions;
	}//get direction

}
