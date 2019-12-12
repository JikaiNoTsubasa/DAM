package com.dbiservices.dam.ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.dbiservices.dam.utils.Const;

public class UIPainter {

	public static void paint(JButton el) {
		el.setBorderPainted(false);
		el.setBackground(Const.COLOR_BACKGROUND);
		el.setForeground(Const.COLOR_FORGROUND);
		el.setFocusPainted(false);
		el.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
	
	public static void paint(JMenuBar el) {
		el.setBorderPainted(false);
		el.setBackground(Const.COLOR_BACKGROUND);
		el.setForeground(Const.COLOR_FORGROUND);
		el.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
	
	public static void paint(JMenu el) {
		el.setBorderPainted(false);
		el.setBackground(Const.COLOR_BACKGROUND);
		el.setForeground(Const.COLOR_FORGROUND);
		el.setFocusPainted(false);
		el.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
	
	public static void paint(JMenuItem el) {
		el.setBorderPainted(false);
		el.setBackground(Const.COLOR_BACKGROUND);
		el.setForeground(Const.COLOR_FORGROUND);
		el.setFocusPainted(false);
	}
}
