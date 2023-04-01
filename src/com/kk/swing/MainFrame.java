package com.kk.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kk.reaction.Gridspace;

public class MainFrame extends JFrame {
	//
	private static final long serialVersionUID = 1L;
	// define component
	private JLabel jl_width;
	private JLabel jl_height;
	private JLabel jl_alpha;
	private JLabel jl_beta;
	private JLabel jl_lambda0;
	private JLabel jl_theta;
	private JLabel jl_miu0;
	private JLabel jl_epsilon;

	private JTextField jtf_width;
	private JTextField jtf_height;
	private JTextField jtf_alpha;
	private JTextField jtf_beta;
	private JTextField jtf_lambda0;
	private JTextField jtf_theta;
	private JTextField jtf_miu0;
	private JTextField jtf_epsilon;

	private JLabel jl_initial_particles_left;
	private JLabel jl_sumS;
	private JLabel jl_remaining_clusters_left;
	private JLabel jl_sumI;

	private JButton jb_run;
	private JButton jb_suspend;
	private JButton jb_show_graphic;
	private JButton jb_continue;
	private JButton jb_reset;
	private JButton jb_exit;

	// two main Jpanel
	public GridPanel jp_left;
	private JPanel jp_right;

	//
	private Timer timer = new Timer();
	//
	private Gridspace gs;

	/**
	 * 
	 * 构造方法
	 * 
	 */
	public MainFrame() {

		// create component

		jp_right = new JPanel();

		//jp_left.setBackground(Color.WHITE);
		//jp_left.setSize(800, 800);

		// JPanel right
		jl_width = new JLabel("Width", JLabel.RIGHT);
		jl_height = new JLabel("Height", JLabel.RIGHT);
		jl_alpha = new JLabel("α", JLabel.RIGHT);
		jl_beta = new JLabel("β", JLabel.RIGHT);
		jl_lambda0 = new JLabel("λ0", JLabel.RIGHT);
		jl_theta = new JLabel("θ", JLabel.RIGHT);
		jl_miu0 = new JLabel("μ0", JLabel.RIGHT);
		jl_epsilon = new JLabel("ε", JLabel.RIGHT);
		//
		jtf_width = new JTextField("10");
		jtf_height = new JTextField("10");
		jtf_alpha = new JTextField("1.00");
		jtf_beta = new JTextField("1.50");
		jtf_lambda0 = new JTextField("0.001");
		jtf_theta = new JTextField("1");
		jtf_miu0 = new JTextField("0.002");
		jtf_epsilon = new JTextField("0.8");
		//
		jl_initial_particles_left = new JLabel("S :", JLabel.RIGHT);
		jl_sumS = new JLabel("-");
		jl_remaining_clusters_left = new JLabel("I :", JLabel.RIGHT);
		jl_sumI = new JLabel("-");
		//
		jb_run = new JButton("Run");
		jb_run.addMouseListener(new Run_MouseListener());
		// suspend button
		jb_suspend = new JButton("Suspend");
		jb_suspend.setEnabled(false);
		jb_suspend.addMouseListener(new Suspend_MouseListener());
		// continue button
		jb_continue = new JButton("Continue");
		jb_continue.setEnabled(false);
		jb_continue.addMouseListener(new Continue_MouseListener());
		// jb_show_graphic button
		jb_show_graphic = new JButton("     ");
		jb_show_graphic.setEnabled(false);
		jb_show_graphic.addMouseListener(new Graphic_MouseListener());
		//
		jb_reset = new JButton("Reset");
		jb_reset.setEnabled(true);
		jb_reset.addMouseListener(new Reset_MouseListener());
		jb_exit = new JButton("Exit");
		jb_exit.addMouseListener(new Exit_MouseListener());

		jp_right.setLayout(new GridLayout(13, 2, 20, 20)); // jp_right

		jp_right.add(jl_width);
		jp_right.add(jtf_width);
		jp_right.add(jl_height);
		jp_right.add(jtf_height);
		jp_right.add(jl_alpha);
		jp_right.add(jtf_alpha);
		jp_right.add(jl_beta);
		jp_right.add(jtf_beta);
		jp_right.add(jl_lambda0);
		jp_right.add(jtf_lambda0);
		jp_right.add(jl_theta);
		jp_right.add(jtf_theta);
		jp_right.add(jl_miu0);
		jp_right.add(jtf_miu0);
		jp_right.add(jl_epsilon);
		jp_right.add(jtf_epsilon);

		jp_right.add(jl_initial_particles_left);
		jp_right.add(jl_sumS);
		jp_right.add(jl_remaining_clusters_left);
		jp_right.add(jl_sumI);

		jp_right.add(jb_run);
		jp_right.add(jb_suspend);
		jp_right.add(jb_continue);
		jp_right.add(jb_show_graphic);
		jp_right.add(jb_reset);
		jp_right.add(jb_exit);

		//this.add(jp_left, BorderLayout.CENTER);
		this.add(jp_right, BorderLayout.EAST);
		this.setTitle("SIS");
		// this.setResizable(false);
		this.setLocation(100, 100);
		this.setSize(1000, 800);
		this.setResizable(false);
		this.setVisible(true);
		this.setIconImage((new ImageIcon("files/virus.jpg")).getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
	}

	/**
	 * Inner class instance
	 */
	private Runnable runnable = new Runnable() {
		//int count = 0;
		@Override
		public void run() {
			while (true) {
				if (! Thread.currentThread().isInterrupted()) {
					//迭代
					gs.moveAndReaction();
					try {
						MainFrame.this.jp_left.repaint();
						//Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//System.out.println("Reaction................." + count++);
					System.out.println("if   .... "+ Thread.currentThread().isInterrupted());
				}else{
					//System.out.println("else ......."+ Thread.currentThread().isInterrupted());
				}
			}
		}
	};

	private Thread runThread = new Thread(runnable, "reaction thread");

	// MouseListener
	private class Run_MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jb_run.isEnabled()) {
				jb_reset.setEnabled(false);
				jb_run.setEnabled(false);
				jb_suspend.setEnabled(true);
				//
				//gs = new Gridspace();
				//设置参数值
				int rowSize = Integer.parseInt(jtf_height.getText());
				int colSize = Integer.parseInt(jtf_width.getText());
				double alpha = Double.parseDouble(jtf_alpha.getText());
				double beta = Double.parseDouble(jtf_beta.getText());
				double lambda0 = Double.parseDouble(jtf_lambda0.getText());
				double theta = Double.parseDouble(jtf_theta.getText());
				double miu0 = Double.parseDouble(jtf_miu0.getText());
				double epsilon = Double.parseDouble(jtf_epsilon.getText());
				gs = new Gridspace(rowSize, colSize, alpha, beta, lambda0, theta, miu0, epsilon);

				jp_left = new GridPanel(gs);
				jp_left.setBackground(Color.WHITE);
				jp_left.setSize(800, 800);

				//
				MainFrame.this.add(jp_left, BorderLayout.CENTER);
				MainFrame.this.validate();
				runThread.start();

				updateStatics();
				//
				System.out.println("Run.....................");
			}
		}//

	}// inner class Run_MouseListener

	/**
	 * 
	 * @author Krise
	 * 
	 */
	private class Suspend_MouseListener extends MouseAdapter {
		//@SuppressWarnings("deprecation")
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jb_suspend.isEnabled()) {
				jb_suspend.setEnabled(false);
				jb_continue.setEnabled(true);
				runThread.interrupt();
			}
		}
	}// inner class Suspend_MouseListener

	/**
	 * 
	 * @author Krise
	 * 
	 */
	private class Continue_MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jb_continue.isEnabled()) {
				jb_suspend.setEnabled(true);
				jb_continue.setEnabled(false);
				Thread.interrupted();
				System.out.println("continue................");
			}
		}
	}

	/**
	 * 
	 * @author Krise
	 * 
	 */
	private class Graphic_MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jb_show_graphic.isEnabled()) {

			}
		}
	}// inner class Graphic_MouseListener

	/**
	 * 
	 * @author Krise
	 */
	private class Reset_MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jb_reset.isEnabled()) {
				jtf_width.setText("");
				jtf_height.setText("");
				jtf_alpha.setText("");
				jtf_beta.setText("");
				jtf_lambda0.setText("");
				jtf_theta.setText("");
				jtf_miu0.setText("");
				jtf_epsilon.setText("");
			}
		}
	}// inner class Reset_MouseListener

	/**
	 * 
	 * @author Krise
	 * 
	 */
	private class Exit_MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.exit(0);
		}
	}// inner class

	/*
	 * 
	 */
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			MainFrame.this.jl_sumS.setText(jp_left.sumS + "");
			MainFrame.this.jl_sumI.setText(jp_left.sumI + "");
		}
	};

	/**
	 * updateStatics
	*/
	private void updateStatics() {
		timer.schedule(task, 0, 250);
	}

}// Main Class

