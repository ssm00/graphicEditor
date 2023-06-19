package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class TRectangle extends TShape {
	private static final long serialVersionUID = 1L;

	public TRectangle() {
		this.shape = new Rectangle();
	}
	
	public TShape clone() {
		return new TRectangle();
	}
	
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(x,y,0,0);
	}
	
	@Override
	public void keepDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
	}
	
	public TShape deepCopy() {
		AffineTransform af = new AffineTransform();
		af.translate(20, 20);
		Shape copyshape = af.createTransformedShape(this.shape);
		TRectangle newshape = new TRectangle();
		newshape.setShape(copyshape);
		newshape.setAttribute(this);
		newshape.setSelected(false);
		return newshape;
	}

	
}