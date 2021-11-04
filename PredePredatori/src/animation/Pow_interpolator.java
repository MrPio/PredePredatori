package animation;

public class Pow_interpolator {
	
	private double pow=1;
	
	public Pow_interpolator(double pow) {
		this.pow=pow;
	}
	public double interpolatorX(double t) {
		return Math.pow(t,pow);
	}
	public double interpolatorY(double t) {
		return Math.pow(t,pow);
	}
}
