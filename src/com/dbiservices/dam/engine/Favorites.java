package com.dbiservices.dam.engine;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Favorites")
public class Favorites {

	private ArrayList<FavoriteQuery> favoriteQueries = new ArrayList<>();

	public ArrayList<FavoriteQuery> getFavoriteQueries() {
		return favoriteQueries;
	}

	@XmlElementWrapper(name="FavList")
	@XmlElement(name="FavQuery")
	public void setFavoriteQueries(ArrayList<FavoriteQuery> favoriteQueries) {
		this.favoriteQueries = favoriteQueries;
	}
}
