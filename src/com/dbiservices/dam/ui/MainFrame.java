package com.dbiservices.dam.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dbiservices.dam.ctrl.Controller;
import com.dbiservices.dam.engine.DFC;
import com.dbiservices.dam.engine.RepoInfo;
import com.dbiservices.dam.utils.Const;
import com.dbiservices.dam.utils.Icons;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

import fr.triedge.fwk.conf.Config;
import fr.triedge.fwk.ui.UI;
import fr.triedge.fwk.utils.SBIEncrypter;

public class MainFrame extends JFrame implements WindowListener{

	private static final long serialVersionUID = -2398011287944159974L;
	private Controller controller;
	
	private JMenuBar bar;
	private JMenu menuFile, menuSession;
	private JMenuItem itemQuit, itemNewSession, itemOpenSession;
	
	private JTabbedPane tabbedPane;
	private JPanel frameBar;
	

	public void build() {
		setTitle("Documentum Amadeus Manager");
		setIconImage(Icons.logoIcon.getImage());
		setSize(
				Integer.parseInt(Config.params.getProperty(Const.CONFIG_FRAME_WIDTH, "800")), 
				Integer.parseInt(Config.params.getProperty(Const.CONFIG_FRAME_HEIGHT, "600")));
		setExtendedState(Integer.parseInt(Config.params.getProperty(Const.CONFIG_FRAME_FULLSCREEN, "0")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setFrameBar(new JPanel());
		getFrameBar().setSize(getWidth(), 20);
		getFrameBar().setBackground(Color.CYAN);
		
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
		
		addWindowListener(this);
		
		setVisible(true);
	}
	
	public void addTab(JPanel tab, String title) {
		// tab.build();
		getTabbedPane().addTab(title, tab);
		getTabbedPane().setSelectedComponent(tab);

		int index = getTabbedPane().indexOfTab(title);
		JPanel pnlTab = new JPanel(new GridBagLayout());
		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel(title);
		JButton btnClose = new JButton(Icons.closeIcon);
		btnClose.setPreferredSize(new Dimension(12, 12));
		btnClose.setBorderPainted(false); 
		btnClose.setContentAreaFilled(false); 
		btnClose.setFocusPainted(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		pnlTab.add(lblTitle, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		pnlTab.add(btnClose, gbc);

		getTabbedPane().setTabComponentAt(index, pnlTab);

		btnClose.addActionListener(e -> actionCloseTab(tab));
	}
	
	public void actionCloseTab(JPanel tab) {
		if (tab != null) {
			getTabbedPane().remove(tab);
			if (tab instanceof RepositoryPanel) {
				IDfSession ses = ((RepositoryPanel) tab).getSession().getSession();
				try {
					DFC.closeSession(ses);
				} catch (DfException e) {
					UI.error("Error closing session",e);
					e.printStackTrace();
				}
			}
		}
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

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		getController().actionCloseWindow();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	public JPanel getFrameBar() {
		return frameBar;
	}

	public void setFrameBar(JPanel frameBar) {
		this.frameBar = frameBar;
	}
}
