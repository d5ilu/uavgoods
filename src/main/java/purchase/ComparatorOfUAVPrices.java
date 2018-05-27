package purchase;

import java.util.Comparator;

import domainReceived.MapData.UAV_price_item;

public class ComparatorOfUAVPrices implements Comparator<UAV_price_item> {

	@Override
	public int compare(UAV_price_item arg0, UAV_price_item arg1) {
		if(arg0.getLoad_weight()<arg1.getLoad_weight()) {
			return -1;
		}else {
			if(arg0.getLoad_weight()>arg1.getLoad_weight()) {
				return 1;
			}else {
				return 0;
			}
		}

	}
}
