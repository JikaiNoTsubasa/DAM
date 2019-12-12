package com.dbiservices.dam.engine;


public class Model {

	private Repositories repositories = new Repositories();
	private Favorites favorites = new Favorites();

	public Repositories getRepositories() {
		return repositories;
	}

	public void setRepositories(Repositories repositories) {
		this.repositories = repositories;
	}

	public Favorites getFavorites() {
		return favorites;
	}

	public void setFavorites(Favorites favorites) {
		this.favorites = favorites;
	}

}
