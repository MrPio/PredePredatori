package animation;
public class Sine_interpolator {
	
	private int frequency=2;
	
	public Sine_interpolator(double frequency) {
		this.frequency=(int)frequency;
	}
	public double interpolatorX(double t) {
		return Math.sin(t*((Math.PI/2)+(2*frequency*Math.PI)));
	}
	public double interpolatorY(double t) {
		return t;
	}
}
