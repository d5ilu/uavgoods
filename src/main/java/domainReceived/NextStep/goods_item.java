package domainReceived.NextStep;

public class goods_item implements Comparable<goods_item>{
    int no;
    int start_x;
    int start_y;
    int end_x;
    int end_y;
    int weight;
    int value;
    int start_time;
    int remain_time;
    int left_time;
    int status;

    public goods_item(int no, int start_x, int start_y, int end_x, int end_y, int weight, int value, int start_time, int remain_time,int left_time, int status) {
        this.no = no;
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
        this.weight = weight;
        this.value = value;
        this.start_time = start_time;
        this.remain_time = remain_time;
        this.left_time=left_time;
        this.status = status;
    }


    public int getNo() {
        return no;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getEnd_x() {
        return end_x;
    }

    public int getEnd_y() {
        return end_y;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getLefttime() {
		return left_time;
	}


	public void setLefttime(int left_time) {
		this.left_time = left_time;
	}


	public int getRemain_time() {
        return remain_time;
    }

    public int getStatus() {
        return status;
    }


	@Override
	public int compareTo(goods_item arg0) {
		
		if(value>arg0.value)
			return 1;
		else {
			if(value==arg0.value)
				return 0;
			else return -1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end_x;
		result = prime * result + end_y;
		result = prime * result + no;
		result = prime * result + start_time;
		result = prime * result + start_x;
		result = prime * result + start_y;
		result = prime * result + value;
		result = prime * result + weight;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		goods_item other = (goods_item) obj;
		if (end_x != other.end_x)
			return false;
		if (end_y != other.end_y)
			return false;
		if (no != other.no)
			return false;
		if (start_time != other.start_time)
			return false;
		if (start_x != other.start_x)
			return false;
		if (start_y != other.start_y)
			return false;
		if (value != other.value)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
	public String toString() {
		return "["+no+" "+value +" "+weight+"]";
	}
    
}

