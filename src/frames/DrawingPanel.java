package frames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants;
import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TAnchors.EAnchors;
import shapes.TLine;
import shapes.TPolygon;
import shapes.TShape;
import shapes.TOval;
import transformer.*;
public class DrawingPanel extends JPanel {
	
	//attributes
	private static final long serialVersionUID = 1L;

	private boolean bUpdated;
	//components
	public File saveFile;
	private Vector<TShape> shapes;
	private Stack<TShape> stack;
	private Vector<TShape> copy;
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;
	private Color lineColor, fillColor;
	
	//associated attribute
	private TShape selectedShape;
	private ETools selectedTool;
	private TShape currentShape;
	private Transformer transformer;
	
	//working variables
	private enum EDrawingState{
		eIdle,
		e2PointTransformation,
		eNPointTransformation,
	}
	//---------------------------------
	
	EDrawingState eDrawingState;
	
	
	public DrawingPanel() {
		//this.setBackground(Color.white);
		this.eDrawingState = EDrawingState.eIdle;
		
		this.lineColor = this.getForeground();
		this.fillColor = this.getBackground();
		this.stack = new Stack<TShape>();
		this.copy = new Vector<TShape>();
		this.shapes = new Vector<TShape>();
		this.bUpdated = false;
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
	}
	
	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
	}
	
	public boolean isUpdated() {
		return this.bUpdated;
	}
	
	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}
	
	public void setLineColor(Color c) {
		this.lineColor = c;
	}
	
	public void setFillColor(Color c) {
		this.fillColor = c;
	}
	
	//file open/save
	public Object getShapes() {
		return this.shapes;
	}
	
	public TShape getSelectedShape() {
		return this.selectedShape;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>)shapes;
		this.drawAll();
		this.repaint();
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}
	
	// 오버라이딩
	public void paint(Graphics graphics) {
		super.paint(graphics); // 부모가 해야 할 일을 하고 부른다
		graphics.drawImage(this.bufferedImage,0,0,this);
	}
	
	public void clearShapes() {
		this.shapes.clear();
		this.graphics2DBufferedImage.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	public void drawAll() {
		this.graphics2DBufferedImage.clearRect(0, 0, this.getWidth(), this.getHeight());
		for(TShape shape: this.shapes) {
			shape.draw(this.graphics2DBufferedImage);
		}
		this.graphics2DBufferedImage.drawImage(this.bufferedImage,0,0,this);
	}
	

	public void anchorclose() {
		this.selectedShape.setSelected(false);
	}

	private void prepareTransformation(int x, int y) {
		if(selectedTool == ETools.eSelection) {
			currentShape = onShape(x, y);
			if(currentShape != null) {
				if(currentShape.getSelectedAnchor() == EAnchors.eMove) {
					this.transformer = new Mover(currentShape);
				}else if(currentShape.getSelectedAnchor() == EAnchors.eRR) { //rotate
					this.transformer = new Rotater(currentShape);
				}else { 	//resize
					this.transformer = new Resizer(currentShape);
				}
			}else {	//선택할때 마우스 당기기
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(currentShape);
			}
		} else {	//new shape
			this.currentShape = this.selectedTool.newShape();
			this.currentShape.setFillColor(fillColor);
			this.currentShape.setLineColor(lineColor);
			this.transformer = new Drawer(currentShape);
		}
		
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
		this.transformer.prepare(x, y);
		
	}
	

	private void keepTransformation(int x, int y) {
		// erase
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
		this.currentShape.draw(this.graphics2DBufferedImage);

		// draw
		this.transformer.keepTransforming(x, y);
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.repaint();
	}

	private void continueTransformation(int x, int y) {
		this.currentShape.addPoint(x,y);
	}
	

	private void finishTransformation(int x, int y) {
		this.graphics2DBufferedImage.setPaintMode();
		this.transformer.finalize(x,y);
		
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);			
		}
		
		if(!(this.currentShape instanceof TSelection)) {
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);	
		}else {
			for(TShape shape : shapes) {
				if(shape.getShape().intersects(currentShape.getShape().getBounds())) {
					shape.setSelected(true); 
				}
			}
		}
		repaint();
		drawAll();
		
		this.setUpdated(true);
	}
	
	
	private TShape onShape(int x, int y) {
		for(TShape shape : this.shapes) {
			if(shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if(this.selectedTool != ETools.eSelection) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}

		this.currentShape = onShape(x, y);
		if(currentShape != null) {
			cursor = new Cursor(Cursor.MOVE_CURSOR);
			if(this.currentShape.isSelected()) {
				EAnchors eAnchor = this.currentShape.getSelectedAnchor();
				switch (eAnchor) {
				case eRR: cursor = new Cursor(Cursor.HAND_CURSOR); break;
				case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
				case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
				case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
				case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
				case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
				case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
				case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
				case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
				default: break;
				}
			}
		}
		this.setCursor(cursor);
	}
	
	private void changeSelection(int x, int y) {
	
		//erase
		for(TShape shape : shapes) {
			if(shape.isSelected()) {
				shape.setSelected(false);
			}
		}
		//draw
		this.selectedShape = this.onShape(x, y);
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(true);			
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
		drawAll();
		repaint();
	}
	
	public void redo() {
		int size = this.stack.size();
		if(size == 0) {
			System.out.println("다시실행 불가능");
		}else {
			this.shapes.add(stack.pop());
			this.drawAll();
			repaint();
		}
	}
	
	public void undo() {
		int size = this.shapes.size();
		if(size == 0) {
			System.out.println("되돌리기 불가능");
		}else {
			stack.push(shapes.get(size-1));
			shapes.remove(size-1);
			this.drawAll();
			repaint();
		}
	}
	
	public void copy() {
		this.copy.clear();
		if(this.selectedShape != null) {
			this.copy.add(selectedShape);
		}
	}
	
	public void paste() {
		if(!copy.isEmpty()) {
			TShape temp = copy.get(0).deepCopy();
			stack.push(temp);
			this.shapes.add(temp);
		}
		
		drawAll();
		repaint();
	}

	public void cut() {
		copy();
		if(this.selectedShape != null) {
			stack.push(selectedShape);
			delete();
		}
	}

	public void delete() {
		for(int i=0; i<shapes.size(); i++) {
			if(shapes.get(i).isSelected()) {
				stack.push(shapes.get(i));
				shapes.remove(i);
			}
		}
		drawAll();
		repaint();
	}

	public void group() {
		
	}

	public void ungroup() {
		
	}
	
	
	
	
	private class MouseHandler implements MouseInputListener, MouseWheelListener {

		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(e.getClickCount() == 1) {
					this.lButtonClicked(e);
				}else if(e.getClickCount() ==2) {
					this.lbuttonDoubleClicked(e);
				}
			}
		}

		private void lButtonClicked(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(),e.getY());
				if(selectedTool.getETransformationStyle() == ETransformationStyle.eNpoint) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransformation;
				}
			}else if(eDrawingState == EDrawingState.eNPointTransformation) {
				continueTransformation(e.getX(), e.getY());
			}
		}
		
		private void lbuttonDoubleClicked(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointTransformation) {
				finishTransformation(e.getX(), e.getY());	
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointTransformation) {
				keepTransformation(e.getX(), e.getY());
			}else if(eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}
		
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle) {
				if(selectedTool.getETransformationStyle() == ETransformationStyle.e2Point) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransformation;	
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2PointTransformation) {
				keepTransformation(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2PointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

		}

	}





}
