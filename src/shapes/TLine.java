package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class TLine extends TShape{
	private static final long serialVersionUID = 1L;

	public TLine() {
		this.shape = new Line2D.Double();
	}
	
	public TShape clone() {
		return new TLine();
	}
	
	public void prepareDrawing(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(x,y,x,y);
	}

	public void keepDrawing(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(line.getX1(),line.getY1(),x,y);
	}
	
	public TShape deepCopy() {
		AffineTransform af = new AffineTransform();
		Shape copyshape = af.createTransformedShape(this.shape);
		TLine newshape = new TLine();
		newshape.setShape(copyshape);
		newshape.setAttribute(this);
		return newshape;
	}

}