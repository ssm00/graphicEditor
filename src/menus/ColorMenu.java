package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.DrawingPanel;
import global.Constants.EColorMenu;

public class ColorMenu extends JMenu{
	private static final long serialVersionUID = 1L;
	
	DrawingPanel drawingPanel;
	
	public ColorMenu(String title) {
		super(title);
		ActionHandler actionHandler = new ActionHandler();
		
		for(EColorMenu eMenuItem : EColorMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}
	
	
	public void association(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void setLineColor() {
		Color color = JColorChooser.showDialog(null, EColorMenu.eLine.name(), null);
		if(color != null) {
			drawingPanel.setLineColor(color);
		}
	}
	
	public void setFillColor() {
		Color color = JColorChooser.showDialog(null, EColorMenu.eFill.name(), null);
		if(color != null) {
			drawingPanel.setFillColor(color);
		}
	}
	
	public void setNowLine() {
		Color color = JColorChooser.showDialog(null, EColorMenu.eLinenow.name(), null);
		if(color != null) {
			drawingPanel.getSelectedShape().setLineColor(color);
		}
		drawingPanel.drawAll();
		drawingPanel.repaint();
	}
	
	public void setNowFill() {
		Color color = JColorChooser.showDialog(null, EColorMenu.eFillnow.name(), null);
		if(color != null) {
			drawingPanel.getSelectedShape().setFillColor(color);
		}
		drawingPanel.drawAll();
		drawingPanel.repaint();
	}
	
	class ActionHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EColorMenu.eLine.name())) {
				setLineColor();
			}else if(e.getActionCommand().equals(EColorMenu.eFill.name())) {
				setFillColor();
			}else if(e.getActionCommand().equals(EColorMenu.eLinenow.name())) {
				setNowLine();
			}else if(e.getActionCommand().equals(EColorMenu.eFillnow.name())) {
				setNowFill();
			}
		}
	}
}
