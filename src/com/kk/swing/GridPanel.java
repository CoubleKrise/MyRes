package com.kk.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import com.kk.reaction.Gridspace;

// �ҵ�panel�࣬����԰���������һ����ͼ��.
// ����ʹ��java����ʱ������������panel�ϻ�ͼ
public class GridPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Gridspace gs;
	private Random rand = new Random();
	private int gridwidth = 72;
	public int sumS = 0;
	public int sumI = 0;

	// �÷�����ϵͳ���ã����Ի�ͼ
	// Graphics g ��������һֻ����
	@Override
	public void paint(Graphics g) {
		// �����÷���
		super.paint(g);
		//������������
		for (int row = 0; row <= gs.rowSize; row++) {
			g.drawLine(0, row * gridwidth, gs.colSize * gridwidth, row * gridwidth);
		}
		//������������
		for (int col = 0; col <= gs.colSize; col++) {
			g.drawLine(col * gridwidth, 0, col * gridwidth, gs.rowSize * gridwidth);
		}
		sumS = 0;
		sumI = 0;
		//������ֲ�
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