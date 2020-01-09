package com.dbiservices.dam.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apache.commons.lang.WordUtils;

public class SearchResultElement extends RepoElement{
	
	private SearchElement parent;
	
	public SearchResultElement(RepositoryPanel repoPanel) {
		super(repoPanel);
		setId("SearchResult");
		this.x = 200;
		this.y = 50;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		if (getParent() != null) {
			int dx1 = (this.x + (this.width/2));
			int dx2 = (getParent().x + (getParent().width/2));
			int dy1 = (this.y + (this.height/2));
			int dy2 = (getParent().y + (getParent().height/2));
			g.drawLine(dx1, dy1, dx2, dy2);
		}
		g.setColor(Color.gray);
		g.fillOval(this.x, this.y, this.width, this.height);
		g.drawString(getId(), x, y + this.height+15);
		if (getDescription() != null) {
			String str = WordUtils.wrap(getDescription(), 50);
			int offset = y + this.height+30 - g.getFontMetrics().getHeight();
			if (str.contains("\n")) {
				for (String line : str.split("\n")) {
					int off = offset += g.getFontMetrics().getHeight();
					g.drawString(line, x, off);
				}
			}else {
				g.drawString(getDescription(), x, y + this.height+30);
			}
		}
	}

	public SearchElement getParent() {
		return parent;
	}

	public void setParent(SearchElement parent) {
		this.parent = parent;
	}

}
