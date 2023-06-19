package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import frames.DrawingPanel;
import global.Constants.EFileMenu;
import shapes.TShape;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	//----필요시 클래스 이름 수정하고 생성.
	
	private File file;
	
	private DrawingPanel drawingPanel;
	public FileMenu(String title) {
		super(title);
		
		this.file = null;
		
		ActionHandler actionHandler = new ActionHandler();

		for(EFileMenu eMenuItem : EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}
	
	public void association(DrawingPanel drawingPanel) { //Menubar
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {
		
	}
	
	private void load(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.anchorclose();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void store(File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
			
			this.drawingPanel.anchorclose();
			this.drawingPanel.setUpdated(false);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void quit() {//
		int val = this.updateCheck(file);
		if(val != 2) {
			System.exit(0);			
		}
	}
	
	private void open() {//
		this.updateCheck(this.file);
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.load(this.file);
		}
	}

	private void save() {//
		if(this.file == null) {
			this.saveAs();
		}else if(drawingPanel.isUpdated()){
			this.store(this.file);
		}
	}
	
	private void saveAs() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.store(this.file);
		}
	}

	private void newPanel() {//
		int val = updateCheck(file); // 파일 저장 체크
		if(val != 2) {
			this.drawingPanel.clearShapes(); // vector shapes지우기
			this.drawingPanel.repaint();
			this.drawingPanel.setUpdated(false);
		}else if(val == 2) {
			return;	
		}
	}
	
	private int updateCheck(File file) { //변경사항있는지 체크
		int retval = -1;
		if(drawingPanel.isUpdated()) {
			JOptionPane opt = new JOptionPane();
			int val = opt.showConfirmDialog(this.drawingPanel, "변경사항이 있습니다. 파일을 저장할까요?");
			if(val == JOptionPane.OK_OPTION) {
				retval = 0;
				this.save();
			}else if(val == JOptionPane.NO_OPTION) {
				retval = 1;
			}else if(val == JOptionPane.CANCEL_OPTION) {
				retval = 2;
			}
		}
		return retval;
	}
	
	class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			}else if(e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			}else if(e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			}else if(e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			}else if(e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			}
		}
	}


	
}
