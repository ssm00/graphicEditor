package frames;
import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.*;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;

	public MainFrame() {
		this.setSize(600,600);
		
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar,layoutManager.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, layoutManager.CENTER);
		
		this.toolBar.asociation(this.drawingPanel);
		this.menuBar.asociation(this.drawingPanel);
	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}

}
