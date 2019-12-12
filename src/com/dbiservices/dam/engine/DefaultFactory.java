package com.dbiservices.dam.engine;

public class DefaultFactory {

	public static Repositories createDefaultRepoList() {
		Repositories repos = new Repositories();
		RepoInfo info = new RepoInfo();
		info.setRepoName("DummyRepo");
		info.setRepoHost("localhost");
		info.setRepoPort(1489);
		info.setRepoUser("dummy");
		repos.getRepoInfos().add(info);
		return repos;
	}
	
	public static Favorites createDefaultFavList() {
		Favorites favs = new Favorites();
		return favs;
	}

}
