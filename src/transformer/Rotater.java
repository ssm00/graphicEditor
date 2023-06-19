package transformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import shapes.TShape;

public class Rotater extends Transformer{

	private Rectangle bound;
	private double theta;
	
	public Rotater(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		bound = this.shape.getBound();
		theta = Math.atan2(bound.getCenterY() - y, x - bound.getCenterX()); 
	}

	@Override
	public void keepTransforming(int x, int y) {
		bound = this.shape.getBound();
		double theta2 = theta - Math.atan2(bound.getCenterY() - y, x - bound.getCenterX());
		
		this.affineTransform.translate(bound.getCenterX(), bound.getCenterY());
		this.affineTransform.rotate(theta2);
		this.affineTransform.translate(-bound.getCenterX(), -bound.getCenterY());
		theta = Math.atan2(bound.getCenterY() - y, x - bound.getCenterX()); 
	}

	@Override
	public void finalize(int x, int y) {
		this.shape.setShape(this.affineTransform.createTransformedShape(this.shape.getShape()));
		this.affineTransform.setToIdentity();
	}

}
