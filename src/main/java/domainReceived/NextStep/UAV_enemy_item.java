package domainReceived.NextStep;

public class UAV_enemy_item {

    int no;
    String type;
    int x;
    int y;
    int z;
    int goods_no;
    int status;

    public UAV_enemy_item(int no, String type, int x, int y, int z, int goods_no, int status) {
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


}

