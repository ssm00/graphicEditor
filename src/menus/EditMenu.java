package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.DrawingPanel;
import global.Constants.EEditMenu;
import global.Constants.EFileMenu;


public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	DrawingPanel drawingPanel;
	
	public EditMenu(String title) {
		
		super(title);
		
		ActionHandler actionHandler = new ActionHandler();
		
		for(EEditMenu eMenuItem : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}

	}

	public void initialize() {
		
	}
	
	public void association(DrawingPanel drawinPanel) {
		this.drawingPanel = drawinPanel;
	}
	
	
	class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				drawingPanel.undo();
			}else if(e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				drawingPanel.redo();
			}else if(e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				drawingPanel.copy();
			}else if(e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				drawingPanel.paste();
			}else if(e.getActionCommand().equals(EEditMenu.eCut.name())) {
				drawingPanel.cut();
			}else if(e.getActionCommand().equals(EEditMenu.eDelete.name())) {
				drawingPanel.delete();
			}
		}
	}

}
