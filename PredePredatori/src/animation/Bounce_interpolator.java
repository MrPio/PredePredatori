package animation;

public class Bounce_interpolator {
	private double amplitude = 1;
    private double frequency = 10;
	
    public Bounce_interpolator() {};
	public Bounce_interpolator(double frequency, double amplitude) {
		this.frequency=frequency;
		this.amplitude=amplitude;
	}
	public double interpolatorX(double t) {
		return  (-1 * Math.pow(Math.E, -t/ amplitude) *
                Math.cos(frequency * t) + 1);
	}
	public double interpolatorY(double t) {
		return t;
	}
	
}
