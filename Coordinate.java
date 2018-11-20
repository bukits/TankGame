package JHW;

public class Coordinate{
	private double x;
	private double y;
	
	public Coordinate(double d, double e) {
		this.x = d;
		this.y = e;
	}
	
	public double getx() {
		return x;
	}
	
	public double gety() {
		return y;
	}
	
	public void setx(int x) {
		this.x = x;
	}
	
	public void sety(int y) {
		this.y = y;
	}
	
	public double getdistance(Coordinate c1, Coordinate c2) {
		double result = Math.sqrt((c1.getx() - c2.getx()) * (c1.getx() - c2.getx()) + 
				(c1.gety() - c2.gety()) * (c1.gety() - c2.gety()));
		return result;
	}
}
