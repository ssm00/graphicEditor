package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;

import global.Constants;
import global.Constants.ETools;
import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;


public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;
	
	public ToolBar() {
		
		ButtonGroup buttonGroup = new ButtonGroup();
		MyActionListener actionListener = new MyActionListener();
		
		for(ETools eTool : ETools.values()) {
			JRadioButton drawingTool = new JRadioButton(eTool.getLabel());
			drawingTool.setActionCommand(eTool.name());
			drawingTool.addActionListener(actionListener);
			drawingTool.setIcon(new ImageIcon(eTool.getIcon())); //icon추가
			drawingTool.setSelectedIcon(new ImageIcon(eTool.getSIcon())); //selected icon추가
			drawingTool.setToolTipText(eTool.getToolTips());
			this.add(drawingTool);
			buttonGroup.add(drawingTool);
		}
	}
	

	public void asociation(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton defaultbutton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultbutton.doClick();
	}
	
	public void initialize() {	
	}
	
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}
		
	}

}
