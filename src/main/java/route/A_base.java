package route;

//A*算法基本数据结构
public class A_base {
	private int x;
	private int y;
	//总距离=已走距离+估算（启发函数）
	private int dis;
	private int disAlready;
	//0表示未被访问1表示处于开启表中2表示处于关闭表中
	private int state;
	private A_base father;

	public A_base(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public A_base() {
		super();
	}
	public int getDis() {
		return dis;
	}
	public void setDis(int dis) {
		this.dis = dis;
	}
	public A_base getFather() {
		return father;
	}
	public void setFather(A_base p1) {
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
	public boolean equals(A_base obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		A_base other = (A_base) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}

	
}
