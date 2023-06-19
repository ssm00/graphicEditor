package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class TOval extends TShape{
	private static final long serialVersionUID = 1L;

	public TOval() {
		this.shape = new Ellipse2D.Double();
	}
	
	public TShape clone() {
		return new TOval();
	}
	
	public void prepareDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
		
	}

	public void keepDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(ellipse.getX(),ellipse.getY(), x-ellipse.getX(), y-ellipse.getY());
	}
	
	public TShape deepCopy() {
		AffineTransform af = new AffineTransform();
		Shape copyshape = af.createTransformedShape(this.shape);
		TOval newshape = new TOval();
		newshape.setShape(copyshape);
		newshape.setAttribute(this);
		return newshape;
	}

}
