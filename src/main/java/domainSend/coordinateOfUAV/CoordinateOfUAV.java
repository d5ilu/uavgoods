package domainSend.coordinateOfUAV;

import java.util.List;

public class CoordinateOfUAV {
    String token;
    String action;
    public List<UAV_info_item> UAV_info;
    public List<purchase_UAV_item> purchase_UAV;

    public CoordinateOfUAV(String token, String action, List<UAV_info_item> UAV_info, List<purchase_UAV_item> purchase_UAV) {
        this.token = token;
        this.action = action;
        this.UAV_info = UAV_info;
        this.purchase_UAV = purchase_UAV;
    }

    public CoordinateOfUAV(String token, String action) {
		super();
		this.token = token;
		this.action = action;
	}

	public String getToken() {
        return token;
    }

    public String getAction() {
        return action;
    }

    public List<UAV_info_item> getUAV_info() {
        return UAV_info;
    }

    public List<purchase_UAV_item> getPurchase_UAV() {
        return purchase_UAV;
    }
}

