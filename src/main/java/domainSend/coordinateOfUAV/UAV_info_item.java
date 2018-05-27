package domainSend.coordinateOfUAV;

public class UAV_info_item implements Cloneable{
    int no;
    int x;
    int y;
    int z;
    int goods_no;

    public UAV_info_item(int no, int x, int y, int z, int goods_no) {
        this.no = no;
        this.x = x;
        this.y = y;
        this.z = z;
        this.goods_no = goods_no;
    }

    public int getNo() {
        return no;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getGoods_no() {
        return goods_no;
    }

	public void setGoods_no(int goods_no) {
		this.goods_no = goods_no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public String toString() {
		return "["+no+" "+x+" "+y+" "+z+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
	public UAV_info_item clone() throws CloneNotSupportedException {
		return (UAV_info_item) super.clone();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UAV_info_item other = (UAV_info_item) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	
}

