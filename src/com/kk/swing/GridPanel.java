package com.kk.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import com.kk.reaction.Gridspace;

// 我的panel类，你可以把它理解成是一个画图板.
// 我们使用java开发时，往往都是在panel上绘图
public class GridPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Gridspace gs;
	private Random rand = new Random();
	private int gridwidth = 72;
	public int sumS = 0;
	public int sumI = 0;

	// 该方法由系统调用，可以绘图
	// Graphics g 可以理解成一只画笔
	@Override
	public void paint(Graphics g) {
		// 保留该方法
		super.paint(g);
		//画横向网格线
		for (int row = 0; row <= gs.rowSize; row++) {
			g.drawLine(0, row * gridwidth, gs.colSize * gridwidth, row * gridwidth);
		}
		//画纵向网格线
		for (int col = 0; col <= gs.colSize; col++) {
			g.drawLine(col * gridwidth, 0, col * gridwidth, gs.rowSize * gridwidth);
		}
		sumS = 0;
		sumI = 0;
		//画个体分布
		for (int x_ = 0; x_ < gs.rowSize; x_++) {
			for (int y_ = 0; y_ < gs.colSize; y_++) {
				int numS_ = gs.counter[x_ * gs.colSize + y_].numS;
				int numI_ = gs.counter[x_ * gs.colSize + y_].numI;
				sumS += numS_;
				sumI += numI_;
				//
				g.setColor(new Color(0, 255, 0));
				for (int i = 0; i < numS_; i++) {
					g.fillOval(y_ * gridwidth + rand.nextInt(gridwidth), //
							x_ * gridwidth + rand.nextInt(gridwidth), //
							5, 5);//
				}
				//
				g.setColor(new Color(255, 0, 0));
				for (int i = 0; i < numI_; i++) {
					g.fillOval(y_ * gridwidth + rand.nextInt(gridwidth), //
							x_ * gridwidth + rand.nextInt(gridwidth), //
							5, 5);//
				}
			}
		}// for
		//
		System.out.println(sumS * 1.0 / (sumS + sumI) + "\t" + sumI * 1.0 / (sumS + sumI));

	}// paint

	@Override
	public void run() {
		this.repaint();
	}

	/**
	 * @param gp
	 * 
	 */
	public GridPanel(Gridspace gp) {
		this.gs = gp;
	}
}