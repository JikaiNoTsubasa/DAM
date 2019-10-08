package com.dbiservices.dam.engine;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Repositories")
public class Repositories {

	private ArrayList<RepoInfo> repoInfos = new ArrayList<>();

	public ArrayList<RepoInfo> getRepoInfos() {
		return repoInfos;
	}

	@XmlElementWrapper(name="RepoList")
	@XmlElement(name="RepoInfo")
	public void setRepoInfos(ArrayList<RepoInfo> repoInfos) {
		this.repoInfos = repoInfos;
	}
}
