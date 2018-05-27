package domainReceived.MapData;

public class fog_item {
    int x;
    int y;
    int l;
    int w;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getL() {
        return l;
    }

    public int getW() {
        return w;
    }

    public int getB() {
        return b;
    }

    public int getT() {
        return t;
    }

    public fog_item(int x, int y, int l, int w, int b, int t) {
        this.x = x;
        this.y = y;
        this.l = l;
        this.w = w;
        this.b = b;
        this.t = t;
    }

    int b;
    int t;
}

