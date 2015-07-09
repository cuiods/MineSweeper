/*
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.nju.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class RecordDialog {

	/**
	 *  
	 */
	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}

	public boolean show(String[] names, String[] score) {
		File test = new File("save.data");
		String[] temp = new String[3];
		if(test.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader("save.data"));
				for(int i = 0; i < 4; i++){
					temp = br.readLine().split(" ");
					names[i] = temp[0];
					score[i] = temp[1];
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		clear = false;
		this.names = names;
		this.score = score;
		dialog.setVisible(true);
		return clear;
	}
	
	public void show(){
		//dialog.setVisible(true);
//		this.names = new String[]{"UnKnown","UnKnown","UnKnown"};
//		this.score = new int[]{999,999,999};
		show(names,score);
	}

	private void initialization(JFrame parent) {

		dialog = new JDialog(parent, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(100, 145, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		this.names = new String[]{"UnKnown","UnKnown","UnKnown","UnKnown"};
		this.score = new String[]{"0/0","0/0","0/0","0/0"};

		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(192, 145, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear = true;
				int length = names.length;
				File test = new File("save.data");
				if(test.exists()){
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("save.data"));
						bw.write("UnKnown 0/0 Easy"+"\n"+"UnKnown 0/0 Hard"+"\n"+"UnKnown 0/0 Hell"+"\n"+"UnKnown 0/0 Hell"+"\n");
						bw.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				for (int i = 0; i != length; ++i) {
					names[i] = "Unknow Name";
					score[i] = "0/0";
				}
				textPanel.repaint();
			}
		});

		line = new JSeparator();
		line.setBounds(20, 135, 240, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50, 290, 220);

		clear = false;

	}

	private class DescribeTextPanel extends JPanel {

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 130);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			int length = names.length;
			for (int i = 0; i != length; i++) {
				g.drawString(names[i], 20, 30 * (i + 1));
				g.drawString(String.valueOf(score[i]),150, 30 * (i + 1));
				g.drawString(rank[i], 230, 30 * (i + 1));
			}
		}
	}

	private final String[] rank = { "Easy", "Hard", "Hell","Custom"};
  	private JDialog dialog;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	private JSeparator line;

	private String names[];

	private String score[];

	private JPanel textPanel;

	boolean clear;
}