package com.dbiservices.dam.ui;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.dbiservices.dam.engine.FavoriteQuery;
import com.dbiservices.dam.engine.RepoInfo;
import com.dbiservices.dam.engine.Repositories;
import com.dbiservices.dam.utils.SpringUtilities;


public class Dial {

	public static RepoInfo showNewRepo() {
		JTextField textName = new JTextField("");
		JTextField textHost = new JTextField("localhost");
		JTextField textPort = new JTextField("1489");
		JTextField textUser = new JTextField("");
		JPasswordField textPassword = new JPasswordField("");

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Repo Name:"));
		panel.add(textName);
		panel.add(new JLabel("Repo Host:"));
		panel.add(textHost);
		panel.add(new JLabel("Repo Port:"));
		panel.add(textPort);
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		panel.add(new JLabel("Repo User:"));
		panel.add(textUser);
		panel.add(new JLabel("Password:"));
		panel.add(textPassword);

		int result = JOptionPane.showConfirmDialog(null, panel, "Register Repository",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			RepoInfo info = new RepoInfo();
			info.setRepoName(textName.getText());
			info.setRepoHost(textHost.getText());
			info.setRepoUser(textUser.getText());
			info.setRepoPort(Integer.parseInt(textPort.getText()));
			info.setRepoPassword(new String(textPassword.getPassword()));
			return info;
		}
		return null;
	}
	
	public static RepoInfo showOpenSession(Repositories repos) {
		JComboBox<RepoInfo> comboRepo = new JComboBox<RepoInfo>();
		DefaultComboBoxModel<RepoInfo> mod = (DefaultComboBoxModel<RepoInfo>)comboRepo.getModel();
		for (RepoInfo i : repos.getRepoInfos()) {
			mod.addElement(i);
		}
		
		JPanel panel = new JPanel(new GridLayout(2, 0));
		panel.add(new JLabel("Repo Name:"));
		panel.add(comboRepo);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Open Repository",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			return (RepoInfo)comboRepo.getSelectedItem();
		}
		return null;
	}
	
	public static FavoriteQuery showSaveToFav(String query) {
		JTextField textName = new JTextField();
		JTextArea areaQuery = new JTextArea(query);
		areaQuery.setLineWrap(true);
		areaQuery.setColumns(30);
		areaQuery.setWrapStyleWord(true);
		areaQuery.setSize(areaQuery.getPreferredSize().width, 1);
		JScrollPane scroll = new JScrollPane(areaQuery);
		
		JLabel labName = new JLabel("Favorite Name:");
		JLabel labQuery = new JLabel("Query:");
		
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		panel.add(labName);
		panel.add(textName);
		panel.add(labQuery);
		panel.add(scroll);
		
		//Lay out the panel.
		SpringUtilities.makeCompactGrid(panel,
		                                2, 2, //rows, cols
		                                6, 6,        //initX, initY
		                                6, 6);       //xPad, yPad
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Save Favorite Query",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			FavoriteQuery fq = new FavoriteQuery();
			fq.setName(textName.getText());
			fq.setQuery(areaQuery.getText());
			return fq;
		}
		return null;
	}
}
