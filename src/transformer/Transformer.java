package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformer {
	
	protected TShape shape;
	
	protected AffineTransform affineTransform;
	protected TAnchors anchors;
	//working
	protected int px,py;
	
	public Transformer(TShape shape) {
		this.affineTransform = shape.getAffineTransform();
		this.anchors = shape.getTAnchor();
		this.shape = shape;
	}
	
	public abstract void prepare(int x, int y) ;
	
	public abstract void keepTransforming(int x, int y) ;	

	public abstract void finalize(int x, int y);
	
}
