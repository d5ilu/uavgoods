package domainReceived.MapData;

public class init_UAV_item {
    int no;
    int x;
    int y;
    int z;
    int load_weight;
    String type;
    int status;
    int goods_no;

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

    public int getLoad_weight() {
        return load_weight;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getGoods_no() {
        return goods_no;
    }

    public init_UAV_item(int no, int x, int y, int z, int load_weight, String type, int status, int goods_no) {
        this.no = no;
        this.x = x;
        this.y = y;
        this.z = z;
        this.load_weight = load_weight;
        this.type = type;
        this.status = status;
        this.goods_no = goods_no;
    }
}

