package route;

public class Position3D {
	int x;
	int y;
	int z;
	public Position3D(int x, int y ,int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Position3D() {
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
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public String toString() {
		return "["+x+" "+y+" "+z+"]";
	}
	
}
