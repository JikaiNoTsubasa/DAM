package com.dbiservices.dam.ctrl;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.xml.bind.JAXBException;

import com.dbiservices.dam.engine.DFC;
import com.dbiservices.dam.engine.DefaultFactory;
import com.dbiservices.dam.engine.FavoriteQuery;
import com.dbiservices.dam.engine.Favorites;
import com.dbiservices.dam.engine.Model;
import com.dbiservices.dam.engine.RepoInfo;
import com.dbiservices.dam.engine.RepoSession;
import com.dbiservices.dam.engine.Repositories;
import com.dbiservices.dam.ui.MainFrame;
import com.dbiservices.dam.ui.RepositoryPanel;
import com.dbiservices.dam.utils.Const;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

import fr.triedge.fwk.conf.Config;
import fr.triedge.fwk.ui.UI;
import fr.triedge.fwk.utils.SBIEncrypter;
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
			Repositories repos = loadRepositories(Config.params.getProperty(Const.CONFIG_REPO_LOCATION, Const.DEFAULT_REPO_LOCATION));
			getModel().setRepositories(repos);
			Favorites favs = loadFavorites(Config.params.getProperty(Const.CONFIG_FAV_LOCATION, Const.DEFAULT_FAV_LOCATION));
			getModel().setFavorites(favs);
		} catch (JAXBException e) {
			UI.error("Cannot load repositories", e);
			e.printStackTrace();
		}
		
		// Look and feel customs
		// https://thebadprogrammer.com/swing-uimanager-keys/
		/*
		UIManager.put("MenuItem.background", Const.COLOR_BACKGROUND);
		UIManager.put("MenuItem.foreground", Const.COLOR_FORGROUND);
		UIManager.put("Menu.background", Const.COLOR_BACKGROUND);
		UIManager.put("Menu.foreground", Const.COLOR_FORGROUND);
		UIManager.put("Menu.borderPainted", false);
		UIManager.put("MenuBar.background", Const.COLOR_BACKGROUND);
		UIManager.put("MenuBar.foreground", Const.COLOR_FORGROUND);
		UIManager.put("Button.background", Const.COLOR_BACKGROUND);
		UIManager.put("Button.foreground", Const.COLOR_FORGROUND);
		UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
		*/
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
	
	private Favorites loadFavorites(String favLocation) throws JAXBException {
		File file = new File(favLocation);
		Favorites favs;
		if (file.exists()) {
			favs = XmlHelper.loadXml(Favorites.class, favLocation);
		}else {
			favs = DefaultFactory.createDefaultFavList();
		}
		return favs;
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
		if (repo == null)
			return;
		getModel().getRepositories().getRepoInfos().add(repo);
		try {
			XmlHelper.storeXml(getModel().getRepositories(), Const.DEFAULT_REPO_LOCATION);
			UI.info("New repository registered");
		} catch (JAXBException e) {
			UI.error("Cannot save new repo", e);
			e.printStackTrace();
		}
	}
	
	public void registerNewFav(FavoriteQuery fav) {
		if (fav == null)
			return;
		getModel().getFavorites().getFavoriteQueries().add(fav);
		try {
			XmlHelper.storeXml(getModel().getFavorites(), Const.DEFAULT_FAV_LOCATION);
			UI.info("New Favorite registered");
		} catch (JAXBException e) {
			UI.error("Cannot save new favorite", e);
			e.printStackTrace();
		}
	}
	
	public void connectRepo(RepoInfo repo) {
		if (repo == null)
			return;
		String password = SBIEncrypter.decode(repo.getRepoPassword());
		try {
			IDfSession session = DFC.getSession(repo.getRepoName(), repo.getRepoHost(), repo.getRepoPort(), repo.getRepoUser(), password);
			RepoSession ses = new RepoSession(session, repo);
			//RepoSession ses = new RepoSession(null, repo);
			RepositoryPanel pan = new RepositoryPanel(ses, this);
			pan.build();
			getMainFrame().getTabbedPane().addTab(repo.getRepoUser()+"@"+repo.getRepoName(), pan);
		} catch (DfException | GeneralSecurityException | IOException e) {
			UI.error("Cannot connect to repository: "+repo.toString(), e);
			e.printStackTrace();
		}
	}

	public void actionCloseWindow() {
		// Save config
		Config.params.setProperty(Const.CONFIG_FRAME_WIDTH, String.valueOf(getMainFrame().getWidth()));
		Config.params.setProperty(Const.CONFIG_FRAME_HEIGHT, String.valueOf(getMainFrame().getHeight()));
		Config.params.setProperty(Const.CONFIG_FRAME_FULLSCREEN, String.valueOf(getMainFrame().getExtendedState()));
		if (!Config.params.containsKey(Const.CONFIG_REPO_LOCATION))
			Config.params.setProperty(Const.CONFIG_REPO_LOCATION, Const.DEFAULT_REPO_LOCATION);
		if (!Config.params.containsKey(Const.CONFIG_FAV_LOCATION))
			Config.params.setProperty(Const.CONFIG_FAV_LOCATION, Const.DEFAULT_FAV_LOCATION);
		try {
			Config.save();
		} catch (IOException e) {
			UI.error("Cannot save config", e);
			e.printStackTrace();
		}
	}
}
