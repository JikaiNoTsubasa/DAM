package com.dbiservices.dam.engine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RepoInfo")
public class RepoInfo {

	private String repoName, repoHost, repoUser, RepoPassword;
	private int repoPort;
	
	public String getRepoName() {
		return repoName;
	}
	@XmlElement(name="RepoName")
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getRepoHost() {
		return repoHost;
	}
	@XmlElement(name="RepoHost")
	public void setRepoHost(String repoHost) {
		this.repoHost = repoHost;
	}
	public String getRepoUser() {
		return repoUser;
	}
	@XmlElement(name="RepoUser")
	public void setRepoUser(String repoUser) {
		this.repoUser = repoUser;
	}
	public String getRepoPassword() {
		return RepoPassword;
	}
	public void setRepoPassword(String repoPassword) {
		RepoPassword = repoPassword;
	}
	public int getRepoPort() {
		return repoPort;
	}
	@XmlElement(name="RepoPort")
	public void setRepoPort(int repoPort) {
		this.repoPort = repoPort;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(repoUser).append("@").append(repoName).append(" [").append(repoHost).append(":").append(repoPort).append("]");
		return builder.toString();
	}
	
	
}
