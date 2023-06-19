package frames;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;

import global.Constants.ETools;
import menus.ColorMenu;
import menus.EditMenu;
import menus.FileMenu;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private ColorMenu colorMenu;
	private DrawingPanel drawingPanel;
	
	public MenuBar() {
		this.fileMenu = new FileMenu("File");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("Edit");
		this.add(this.editMenu);
		
		this.colorMenu = new ColorMenu("Color");
		this.add(this.colorMenu);
	}
	
	public void asociation(DrawingPanel drawingPanel) {	//mainFrame
		this.drawingPanel = drawingPanel;
		this.fileMenu.association(this.drawingPanel);
		this.editMenu.association(this.drawingPanel);
		this.colorMenu.association(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();
	}
}
