package system;
import java.awt.image.BufferedImage;

public class Food {
	private double energy=1.0d;
	public double X=0,Y=0;
	boolean eaten=false;
	public static BufferedImage food_png = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
	
	public Food(double X,double Y){
		this.X=X;
		this.Y=Y;
	};
	public Food(double X,double Y,double energy) {
		this.X=X;
		this.Y=Y;
		this.energy=energy;
	}
	
	public double gotEaten() {
		eaten=true;
		return energy;
	}
	public boolean isAvaible() {
		return !eaten;
	}
}
