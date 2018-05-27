package domainReceived.NextStep;

public class UAV_we_item {
    int no;
    String type;
    int x;
    int y;
    int z;
    int goods_no;
    int status;

    public UAV_we_item(int no, String type, int x, int y, int z, int goods_no, int status) {
        this.no = no;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.goods_no = goods_no;
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public String getType() {
        return type;
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

    public int getStatus() {
        return status;
    }

	public void setNo(int no) {
		this.no = no;
	}

	public void setType(String type) {
		this.type = type;
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

	public void setGoods_no(int goods_no) {
		this.goods_no = goods_no;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UAV_we_item other = (UAV_we_item) obj;
		if (no != other.no)
			return false;
		return true;
	}
    
}

