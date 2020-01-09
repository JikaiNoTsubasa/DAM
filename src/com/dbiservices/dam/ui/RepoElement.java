package com.dbiservices.dam.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPopupMenu;

public abstract class RepoElement {

	private String id;
	private String description;
	public int x, y, width=50, height=50;
	private JPopupMenu popupMenu = new JPopupMenu();
	private RepositoryPanel repoPanel;
	
	public RepoElement(RepositoryPanel repoPanel) {
		setRepoPanel(repoPanel);
	}
	public abstract void paint(Graphics2D g);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean contains(int x, int y) {
		Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
		return rect.contains(x, y);
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public RepositoryPanel getRepoPanel() {
		return repoPanel;
	}

	public void setRepoPanel(RepositoryPanel repoPanel) {
		this.repoPanel = repoPanel;
	}
}
