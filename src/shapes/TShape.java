package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attribute 특정한 값, 관점에서 특징을 나타내는 값
	private static final long serialVersionUID = 1L;
	// graphics attributes
	private boolean bSelected;
	
	protected Color lineColor, fillColor;
	// component 부품
	protected Shape shape;
	private AffineTransform affineTransform;
	private TAnchors anchors;
	// working
	private int px, py;
	private double cx, cy;
	private double xScale, yScale;

	// setter and getter
	public boolean isSelected() {
		return this.bSelected;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	public AffineTransform getAffineTransform() {
		return this.affineTransform;
	}
	
	public void setTAnchor(TAnchors anchors) {
		this.anchors = anchors;
	}
	
	public TAnchors getTAnchor() {
		return this.anchors;
	}

	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public Shape getShape() {
		return this.shape;
	}

	public Rectangle getBound() {
		return this.shape.getBounds();
	}
	public Color getLineColor() {return lineColor;}
	public void setLineColor(Color lineColor) {this.lineColor = lineColor;}
	public Color getFillColor() {return fillColor;}
	public void setFillColor(Color fillColor) {this.fillColor = fillColor;}

	// association 없음
	// constructor
	public TShape() {
		this.lineColor = Color.black;
		this.fillColor = Color.lightGray;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();

		this.anchors = new TAnchors();
		this.bSelected = false;
	}

	public abstract TShape clone();

	public void initialize() {
	}

	// methods
	public boolean contains(int x, int y) {
		if (isSelected()) {
			if (this.anchors.contains(x, y)) {
				return true;
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		if (transformedShape.contains(x, y)) { // ?
			this.anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}

	public void draw(Graphics2D graphics2d) {
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		graphics2d.setColor(this.fillColor);
		graphics2d.fill(transformedShape);
		graphics2d.setColor(this.lineColor);
		graphics2d.draw(transformedShape);
		if (isSelected()) {
			this.anchors.draw(graphics2d, transformedShape.getBounds());
		}
	}
	
	public void setAttribute(TShape shape) {
		setFillColor(shape.getFillColor());
		setLineColor(shape.getLineColor());
		setTAnchor(shape.getTAnchor());
		setSelected(shape.isSelected());
	}
	

	public abstract void prepareDrawing(int x, int y);
	abstract public void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {
	}

	public abstract TShape deepCopy();

}