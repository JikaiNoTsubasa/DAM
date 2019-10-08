package com.dbiservices.dam.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.dbiservices.dam.ctrl.Controller;
import com.dbiservices.dam.engine.RepoInfo;

import fr.triedge.fwk.utils.SBIEncrypter;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = -2398011287944159974L;
	private Controller controller;
	
	private JMenuBar bar;
	private JMenu menuFile, menuSession;
	private JMenuItem itemQuit, itemNewSession, itemOpenSession;
	
	private JTabbedPane tabbedPane;
	

	public void build() {
		setTitle("Documentum Amadeus Manager");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setBar(new JMenuBar());
		setMenuFile(new JMenu("File"));
		setMenuSession(new JMenu("Session"));
		setItemQuit(new JMenuItem("Exit"));
		setItemNewSession(new JMenuItem("Register Session"));
		setItemOpenSession(new JMenuItem("Open Session"));
		
		getItemQuit().addActionListener(e -> actionQuit());
		getItemNewSession().addActionListener(e -> actionNewSession());
		getItemOpenSession().addActionListener(e -> actionOpenSession());
		
		getMenuFile().add(getItemQuit());
		getMenuSession().add(getItemNewSession());
		getMenuSession().add(getItemOpenSession());
		getBar().add(getMenuFile());
		getBar().add(getMenuSession());
		setJMenuBar(getBar());
		
		setTabbedPane(new JTabbedPane());
		setContentPane(getTabbedPane());
		
		setVisible(true);
	}
	
	private void actionNewSession() {
		RepoInfo repo = Dial.showNewRepo();
		if (repo == null)
			return;
		repo.setRepoPassword(SBIEncrypter.encrypt(repo.getRepoPassword()));
		getController().registerNewRepo(repo);
	}
	
	private void actionOpenSession() {
		RepoInfo repo = Dial.showOpenSession(getController().getModel().getRepositories());
		getController().connectRepo(repo);
	}
	
	private void actionQuit() {
		this.dispose();
	}

	public JMenuBar getBar() {
		return bar;
	}

	public void setBar(JMenuBar bar) {
		this.bar = bar;
	}

	public JMenu getMenuFile() {
		return menuFile;
	}

	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}

	public JMenuItem getItemQuit() {
		return itemQuit;
	}

	public void setItemQuit(JMenuItem itemQuit) {
		this.itemQuit = itemQuit;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JMenu getMenuSession() {
		return menuSession;
	}

	public void setMenuSession(JMenu menuSession) {
		this.menuSession = menuSession;
	}

	public JMenuItem getItemNewSession() {
		return itemNewSession;
	}

	public void setItemNewSession(JMenuItem itemNewSession) {
		this.itemNewSession = itemNewSession;
	}

	public JMenuItem getItemOpenSession() {
		return itemOpenSession;
	}

	public void setItemOpenSession(JMenuItem itemOpenSession) {
		this.itemOpenSession = itemOpenSession;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
