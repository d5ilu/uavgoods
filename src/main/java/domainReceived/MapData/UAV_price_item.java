package domainReceived.MapData;

public class UAV_price_item {
    String type;
    int load_weight;
    int value;

    public UAV_price_item(String type, int load_weight, int value) {
        this.type = type;
        this.load_weight = load_weight;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getLoad_weight() {
        return load_weight;
    }

    public int getValue() {
        return value;
    }
    public String toString() {
    	return type+" "+load_weight+" "+value;
    }
}

