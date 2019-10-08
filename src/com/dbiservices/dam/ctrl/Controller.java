package com.dbiservices.dam.ctrl;

import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

import com.dbiservices.dam.engine.DefaultFactory;
import com.dbiservices.dam.engine.Model;
import com.dbiservices.dam.engine.RepoInfo;
import com.dbiservices.dam.engine.Repositories;
import com.dbiservices.dam.ui.MainFrame;
import com.dbiservices.dam.utils.Const;

import fr.triedge.fwk.conf.Config;
import fr.triedge.fwk.ui.UI;
import fr.triedge.fwk.utils.XmlHelper;


public class Controller {
	
	private MainFrame mainFrame;
	private Model model;

	public void init() {
		// Load configuration
		try {
			Config.load();
		} catch (IOException e) {
			UI.error("Cannot load config", e);
			e.printStackTrace();
		}
		
		// Load model
		setModel(new Model());
		try {
			Repositories repos = loadRepositories(Const.REPO_LOCATION);
			getModel().setRepositories(repos);
		} catch (JAXBException e) {
			UI.error("Cannot load repositories", e);
			e.printStackTrace();
		}
	}

	private Repositories loadRepositories(String repoLocation) throws JAXBException {
		File file = new File(repoLocation);
		Repositories repos;
		if (file.exists()) {
			repos = XmlHelper.loadXml(Repositories.class, repoLocation);
		}else {
			repos = DefaultFactory.createDefaultRepoList();
		}
		return repos;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void registerNewRepo(RepoInfo repo) {
		// TODO Auto-generated method stub
		getModel().getRepositories().getRepoInfos().add(repo);
		try {
			XmlHelper.storeXml(getModel().getRepositories(), Const.REPO_LOCATION);
			UI.info("New repository registered");
		} catch (JAXBException e) {
			UI.error("Cannot save new repo", e);
			e.printStackTrace();
		}
	}
	
	public void connectRepo(RepoInfo repo) {
		JPanel pan = new JPanel();
		getMainFrame().getTabbedPane().addTab(repo.getRepoUser()+"@"+repo.getRepoName(), pan);
	}
}
