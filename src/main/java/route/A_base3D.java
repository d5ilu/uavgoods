package route;

//A*算法基本数据结构
public class A_base3D {
	private int x;
	private int y;
	private int z;
	//总距离=已走距离+估算（启发函数）
	private int dis;
	private int disAlready;
	//0表示未被访问1表示处于开启表中2表示处于关闭表中
	private int state;
	private A_base3D father;

	public A_base3D(int x, int y , int z) {
		super();
		this.x = x;
		this.y = y;
		this.z= z;
	}
	public A_base3D() {
		super();
	}
	public int getDis() {
		return dis;
	}
	public void setDis(int dis) {
		this.dis = dis;
	}
	public A_base3D getFather() {
		return father;
	}
	public void setFather(A_base3D p1) {
		this.father = p1;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getDisAlready() {
		return disAlready;
	}
	public void setDisAlready(int disAlready) {
		this.disAlready = disAlready;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
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
	public boolean equals(A_base3D obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		A_base3D other = (A_base3D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}

	
}
