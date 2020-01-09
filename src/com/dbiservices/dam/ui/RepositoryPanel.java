package com.dbiservices.dam.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.openide.awt.DropDownButtonFactory;

import com.dbiservices.dam.ctrl.Controller;
import com.dbiservices.dam.engine.DFC;
import com.dbiservices.dam.engine.FavoriteQuery;
import com.dbiservices.dam.engine.RepoSession;
import com.dbiservices.dam.utils.Icons;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;

import fr.triedge.fwk.ui.UI;

public class RepositoryPanel extends JPanel{

	private static final long serialVersionUID = -2476001296289123261L;

	private RepoSession session;
	private Controller controller;

	private JToolBar toolBar;
	private JButton btnExecute, btnAddFav;
	private JTextField queryBar;
	
	private ArrayList<RepoElement> repoElements = new ArrayList<>();
	private ArrayList<String> docbaseTypes = new ArrayList<>();
	private InnerPanel innerPanel;

	public RepositoryPanel(RepoSession session, Controller controller) {
		setSession(session);
		setController(controller);
	}
	
	public void build() {
		// Split button
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemCreateSpringProject = new JMenuItem("Add to Favorites");
		popupMenu.add(menuItemCreateSpringProject);
		JMenuItem menuItemCreateHibernateProject = new JMenuItem("Open Favorites");
		popupMenu.add(menuItemCreateHibernateProject);
		JButton dropDownButton = DropDownButtonFactory.createDropDownButton(Icons.favIcon, popupMenu);

		setLayout(new BorderLayout());
		setToolBar(new JToolBar());
		getToolBar().setFloatable(false);
		setBtnExecute(new JButton(Icons.goIcon));
		setBtnAddFav(dropDownButton);
		getBtnExecute().addActionListener(e -> executeCurrentLine());
		getBtnExecute().setToolTipText("Execute (ENTER)");
		getBtnAddFav().addActionListener(e -> actionSaveFavoriteQuery());
		setQueryBar(new JTextField());
		getQueryBar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executeCurrentLine();
			}
		});
		
		setInnerPanel(new InnerPanel());
		getInnerPanel().setBackground(Color.white);
		
		getToolBar().add(getBtnExecute());
		getToolBar().add(getBtnAddFav());
		getToolBar().add(getQueryBar());
		
		add(getToolBar(), BorderLayout.NORTH);
		add(getInnerPanel(), BorderLayout.CENTER);
		
		listTypes();
	}
	
	private void listTypes() {
		String query = "select type_name from dmi_dd_type_info_sp";
		try {
			IDfCollection col = DFC.executeDQL(getSession().getSession(), query);
			while(col.next()) {
				getDocbaseTypes().add(col.getString("type_name"));
				//FIXME autocomplete
			}
		} catch (DfException e) {
			UI.error("Cannot get repository types", e);
			e.printStackTrace();
		}
	}

	public void executeCurrentLine() {
		String text = getQueryBar().getText();
		executeQuery(text);
	}
	
	public void executeQuery(String query) {
		if (query == null || query.equals("")) {
			UI.error("Query is empty");
			return;
		}
			
		SearchElement el = new SearchElement(this);
		el.setDescription(query);
		try {
			el.runQuery();
		} catch (DfException e) {
			UI.error("Failed to execute query", e);
			e.printStackTrace();
			return;
		}
		SearchResultElement res = new SearchResultElement(this);
		res.setParent(el);
		el.setResults(res);
		getRepoElements().add(res);
		getRepoElements().add(el);
		
		int size = getRepoElements().size();
		int offset = 10;
		int offsetRes = 100;
		int x = size * offset;
		int y = size * offset;
		el.x = x;
		el.y = y;
		res.x = (size * offset )+ offsetRes;
		res.y = y;
		
		repaint();
	}
	
	class InnerPanel extends JPanel implements MouseMotionListener, MouseListener{
		private static final long serialVersionUID = 9164749032789066386L;
		protected RepoElement selectedElement = null;
		
		public InnerPanel() {
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (RepoElement e : getRepoElements()) {
				e.paint((Graphics2D)g);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (selectedElement != null) {
					selectedElement.x = e.getX() - (selectedElement.width/2);
					selectedElement.y = e.getY() - (selectedElement.height/2);
					repaint();
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				for (RepoElement el : getRepoElements()) {
					if (el.contains(e.getX(), e.getY())) {
						el.getPopupMenu().show(this, e.getX(), e.getY());
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				for (RepoElement el : getRepoElements()) {
					if (el.contains(e.getX(), e.getY())) {
						selectedElement = el;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			selectedElement = null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
		
		
	}
	
	public void actionSaveFavoriteQuery() {
		FavoriteQuery favQuery = Dial.showSaveToFav(getQueryBar().getText());
		getController().registerNewFav(favQuery);
	}

	public RepoSession getSession() {
		return session;
	}

	public void setSession(RepoSession session) {
		this.session = session;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JButton getBtnExecute() {
		return btnExecute;
	}

	public void setBtnExecute(JButton btnExecute) {
		this.btnExecute = btnExecute;
	}

	public JButton getBtnAddFav() {
		return btnAddFav;
	}

	public void setBtnAddFav(JButton btnAddFav) {
		this.btnAddFav = btnAddFav;
	}

	public JTextField getQueryBar() {
		return queryBar;
	}

	public void setQueryBar(JTextField queryBar) {
		this.queryBar = queryBar;
	}

	public ArrayList<RepoElement> getRepoElements() {
		return repoElements;
	}

	public void setRepoElements(ArrayList<RepoElement> repoElements) {
		this.repoElements = repoElements;
	}

	public InnerPanel getInnerPanel() {
		return innerPanel;
	}

	public void setInnerPanel(InnerPanel innerPanel) {
		this.innerPanel = innerPanel;
	}

	public ArrayList<String> getDocbaseTypes() {
		return docbaseTypes;
	}

	public void setDocbaseTypes(ArrayList<String> docbaseTypes) {
		this.docbaseTypes = docbaseTypes;
	}
}
