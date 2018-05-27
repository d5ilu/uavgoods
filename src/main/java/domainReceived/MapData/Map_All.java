package domainReceived.MapData;


import java.util.List;

public class Map_All {
    map_item map;
    parking_item parking;
    int h_low;
    int h_high;
    List<building_item> building;
    List<fog_item> god ;
    List<init_UAV_item> init_UAV;
    List<UAV_price_item> UAV_price;

    public map_item getMap() {
        return map;
    }

    public parking_item getParking() {
        return parking;
    }

    public int getH_low() {
        return h_low;
    }

    public int getH_high() {
        return h_high;
    }

    public List<building_item> getBuilding() {
        return building;
    }

    public List<fog_item> getGod() {
        return god;
    }

    public List<init_UAV_item> getInit_UAV() {
        return init_UAV;
    }

    public List<UAV_price_item> getUAV_price() {
        return UAV_price;
    }

    public Map_All(map_item map, parking_item parking, int h_low, int h_high, List<building_item> building, List<fog_item> god, List<init_UAV_item> init_UAV, List<UAV_price_item> UAV_price) {
        this.map = map;
        this.parking = parking;
        this.h_low = h_low;
        this.h_high = h_high;
        this.building = building;
        this.god = god;
        this.init_UAV = init_UAV;
        this.UAV_price = UAV_price;
    }
    public int[][] get2_Dmatrix(){
    	int x=this.getMap().getX();
		int y=this.getMap().getY();
		int[][] res=new int[x][y];
		for(building_item build_i:this.getBuilding()) {
			for(int i=build_i.getX();i<build_i.getX()+build_i.getL();i++) {
				for(int j=build_i.getY();j<build_i.getY()+build_i.getW();j++) {
					res[i][j]=1;
				}
			}
		}
		return res;
    }
}

