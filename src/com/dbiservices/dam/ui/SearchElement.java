package com.dbiservices.dam.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JMenuItem;

import org.apache.commons.lang.WordUtils;

import com.dbiservices.dam.engine.DFC;
import com.documentum.fc.common.DfException;

import fr.triedge.fwk.ui.UI;

public class SearchElement extends RepoElement{
	
	private RepoElement results;
	
	public SearchElement(RepositoryPanel repoPanel) {
		super(repoPanel);
		setId("Search");
		this.x = 50;
		this.y = 50;
		buildPopup();
	}
	
	public void buildPopup() {
		JMenuItem itemQuery = new JMenuItem("Re-Run Query");
		itemQuery.addActionListener(e -> {
			try {
				runQuery();
			} catch (DfException e1) {
				UI.error("Failed to execute query",e1);
				e1.printStackTrace();
			}
		});
		getPopupMenu().add(itemQuery);
	}

	public void runQuery() throws DfException {
		if (getDescription() == null || getDescription().equals("")) {
			UI.error("Query is empty");
		}
		DFC.executeDQL(getRepoPanel().getSession().getSession(), getDescription());
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.darkGray);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.drawString(getId(), x, y + this.height+15);
		String str = WordUtils.wrap(getDescription(), 50);
		int offset = y + this.height+30 - g.getFontMetrics().getHeight();
		for (String line : str.split("\n")) {
			int off = offset += g.getFontMetrics().getHeight();
			g.drawString(line, x, off);
		}
	}

	@Override
	public String toString() {
		return getId()+" - "+getDescription();
	}

	public RepoElement getResults() {
		return results;
	}

	public void setResults(RepoElement results) {
		this.results = results;
	}

	
}
