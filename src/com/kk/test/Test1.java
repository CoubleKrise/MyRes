package com.kk.test;

import org.junit.Test;

import com.kk.entity.Agent;
import com.kk.entity.Patch;
import com.kk.entity.State;
import com.kk.reaction.Gridspace;
import com.kk.swing.MainFrame;

/**
 * @author Krise
 *
 */
public class Test1 {

	/**
	 * test1
	 * 
	 */
	@Test
	public void test1() {
		Gridspace gp = new Gridspace();
		//迭代
		for (int count = 0; count < 1000; count++) {
			gp.moveAndReaction();
			System.out.println("reaction.................." + count);
			int sumS = 0;
			int sumI = 0;
			int sumR = 0;
			for (Patch patch : gp.patches) {
				for (Agent agent : patch.getAgents()) {
					if (agent.getState() == State.S) {
						sumS++;
					} else if (agent.getState() == State.I) {
						sumI++;
					}
				}
			}
			System.out.println("sumS=" + sumS + "     sumI=" + sumI + "     sumR=" + sumR);
		}
		System.out.println(gp);
	}

	/**
	 * test2
	 * 
	 */
	@Test
	public void test2() {
		Gridspace gp = new Gridspace();
		//
		MainFrame gmf = new MainFrame();

		int cnt = 1;
		for (Patch patch : gp.patches) {
			System.out.print((int) (gp.k1 * patch.getScale() + gp.k2 * Math.pow(patch.getRes(), gp.alpha)) + "\t");
			cnt++;
			if (cnt > gp.colSize) {
				System.out.println();
				cnt = 1;
			}
		}
		//
		while (true) {
			//迭代
			//
			gp.moveAndReaction();
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			gmf.jp_left.repaint();

		}
	}//test2

	@Test
	public void test3_statics() {
		Gridspace gp = new Gridspace();
		//迭代
		for (int count = 0; count < 1000; count++) {
			gp.moveAndReaction();
			//System.out.println("reaction.................." + count);
			int sumS = 0;
			int sumI = 0;
			for (Patch patch : gp.patches) {
				for (Agent agent : patch.getAgents()) {
					if (agent.getState() == State.S) {
						sumS++;
					} else if (agent.getState() == State.I) {
						sumI++;
					}
				}
			}//for
				//System.out.println("sumS=" + sumS + "     sumI=" + sumI );
			System.out.println(count + "\t" + sumS + "\t" + sumI);
		}
		//System.out.println(gp);

	}//test3

	/**
	 * test4
	 * 
	 */
	@Test
	public void test4() {
		Gridspace gp = new Gridspace();
		//
		MainFrame gmf = new MainFrame();
		//
		while (true) {
			//迭代
			//
			gp.moveAndReaction();
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			gmf.jp_left.repaint();

		}
	}//test4

}//Test1
