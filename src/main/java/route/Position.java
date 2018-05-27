package route;

public class Position {
	int x;
	int y;
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Position() {
		super();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String toString() {
		return "["+x+" "+y+"]";
	}
}
