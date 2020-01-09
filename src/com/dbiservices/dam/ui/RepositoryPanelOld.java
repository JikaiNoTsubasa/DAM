package com.dbiservices.dam.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

import org.openide.awt.DropDownButtonFactory;

import com.dbiservices.dam.ctrl.Controller;
import com.dbiservices.dam.engine.DFC;
import com.dbiservices.dam.engine.DocRow;
import com.dbiservices.dam.engine.DocTable;
import com.dbiservices.dam.engine.FavoriteQuery;
import com.dbiservices.dam.engine.RepoSession;
import com.dbiservices.dam.utils.Icons;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfValue;

import fr.triedge.fwk.ui.UI;

public class RepositoryPanelOld extends JPanel implements KeyListener{

	private static final long serialVersionUID = -2476001296289123261L;

	private RepoSession session;
	private Controller controller;

	private JSplitPane splitPane;
	private JTextPane textPane;
	private JToolBar toolBar;
	private JButton btnExecute, btnAddFav;
	private JTable resultTable;

	public RepositoryPanelOld(RepoSession session, Controller controller) {
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
		setTextPane(new JTextPane());
		setBtnExecute(new JButton(Icons.goIcon));
		setBtnAddFav(dropDownButton);
		getBtnExecute().addActionListener(e -> executeCurrentLine());
		getBtnExecute().setToolTipText("Execute (CTRL+SPACE)");
		getBtnAddFav().addActionListener(e -> actionSaveFavoriteQuery());
		
		setResultTable(new JTable());

		getToolBar().add(getBtnExecute());
		getToolBar().add(getBtnAddFav());
		setSplitPane(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(getTextPane()),new JScrollPane(getResultTable())));
		getSplitPane().setOneTouchExpandable(true);
		getSplitPane().setDividerLocation(200);
		
		getTextPane().addKeyListener(this);

		add(getToolBar(), BorderLayout.NORTH);
		add(getSplitPane(), BorderLayout.CENTER);
		
	}
	
	public void actionSaveFavoriteQuery() {
		FavoriteQuery favQuery = Dial.showSaveToFav(getCurrentLine(getTextPane()));
		getController().registerNewFav(favQuery);
	}

	public String getCurrentLine(JTextPane textTx)
	{
		// Get section element
		Element section = textTx.getDocument().getDefaultRootElement();

		// Get number of paragraphs.
		// In a text pane, a span of characters terminated by single
		// newline is typically called a paragraph.
		int paraCount = section.getElementCount();

		int position = textTx.getCaret().getDot();

		// Get index ranges for each paragraph
		for (int i = 0; i < paraCount; i++)
		{
			Element e1 = section.getElement(i);

			int rangeStart = e1.getStartOffset();
			int rangeEnd = e1.getEndOffset();

			try 
			{
				String para = textTx.getText(rangeStart, rangeEnd-rangeStart);

				if (position >= rangeStart && position <= rangeEnd)
					return para;
			}
			catch (BadLocationException ex) 
			{
				System.err.println("Get current line from editor error: " + ex.getMessage());
			}
		}
		return null;
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

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
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

	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	public JButton getBtnAddFav() {
		return btnAddFav;
	}

	public void setBtnAddFav(JButton btnAddFav) {
		this.btnAddFav = btnAddFav;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            executeCurrentLine();
        }
	}

	private void executeCurrentLine() {
		String line = getCurrentLine(getTextPane()).trim();
		IDfSession ses = getSession().getSession();
		DocTable table = new DocTable();
		try {
			IDfCollection col = DFC.executeDQL(ses, line);
			while(col.enumAttrs().hasMoreElements())
				table.getHeaders().add(col.enumAttrs().nextElement().toString());
			while (col.next()) {
				DocRow row = new DocRow();
				row.setId(col.getObjectId());
				int attrCount = col.getAttrCount();
				for (int i = 0; i < attrCount; ++i) {
					col.getAttr(i);
				}
				
				for (int i=0; i<col.getAttrCount(); ++i) { 
					IDfAttr attr = col.getAttr(i); 
					// Extract the IDfValue for the attribute. 
					// This is convenient to get the data out of the collection, 
					// but we still need to know how to interpret the value 
					// (ie., as int, string, id, ...). 
					IDfValue attrValue = col.getValue(attr.getName());
					}
				}
			populateResultTable(table);
			col.close();
		} catch (DfException e) {
			UI.error("Failed to execute DQL: "+line, e);
			e.printStackTrace();
		}
	}
	
	private void populateResultTable(DocTable table) {
		/*
		DefaultTableModel model = new DefaultTableModel(table.getHeaders().toArray(), 0);
		for (DocRow row : table.getRows()) {
			model.add
		}
		model.addRow(rowData);
		jTable1.setModel(model);
		*/
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public JTable getResultTable() {
		return resultTable;
	}

	public void setResultTable(JTable resultTable) {
		this.resultTable = resultTable;
	}
}
