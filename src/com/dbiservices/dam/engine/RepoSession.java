package com.dbiservices.dam.engine;

import com.documentum.fc.client.IDfSession;

public class RepoSession {

	private IDfSession session;
	private RepoInfo repoInfo;
	
	public RepoSession(IDfSession session, RepoInfo repoInfo) {
		super();
		this.session = session;
		this.repoInfo = repoInfo;
	}

	public IDfSession getSession() {
		return session;
	}

	public void setSession(IDfSession session) {
		this.session = session;
	}

	public RepoInfo getRepoInfo() {
		return repoInfo;
	}

	public void setRepoInfo(RepoInfo repoInfo) {
		this.repoInfo = repoInfo;
	}
	
}
