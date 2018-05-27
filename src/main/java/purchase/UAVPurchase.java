package purchase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainReceived.MapData.UAV_price_item;
import domainReceived.NextStep.NextStep;
import domainReceived.NextStep.UAV_we_item;
import domainReceived.NextStep.goods_item;
import domainSend.coordinateOfUAV.purchase_UAV_item;

public class UAVPurchase {
	private List<UAV_price_item> upis ;
	private NextStep nextStep;
	public UAVPurchase(List<UAV_price_item> upis) {
		super();
		this.upis = upis;
	}
	public NextStep getNextStep() {
		return nextStep;
	}
	public void setNextStep(NextStep nextStep) {
		this.nextStep = nextStep;
	}
	public List<purchase_UAV_item> getPurchaseitems() {
		upis.sort(new ComparatorOfUAVPrices());
		Map<String, Integer> uav_map=new HashMap<String, Integer>();
		Map<String, Integer> goods_map=new HashMap<String, Integer>();
		List<purchase_UAV_item> puis=new ArrayList<purchase_UAV_item>();
		for(UAV_price_item upi:upis) {
			//各飞机型号数量
			for(UAV_we_item uwi:nextStep.getUAV_we()) {
				if(uwi.getType().equals(upi.getType())){
					if(!uav_map.containsKey(uwi.getType())) {
						uav_map.put(uwi.getType(), 1);
					}else {
						uav_map.put(uwi.getType(),uav_map.get(uwi.getType())+1);
					}
				}
			}
		}
		if(uav_map.containsKey(upis.get(0).getType()))
			uav_map.put(upis.get(0).getType(), uav_map.get(upis.get(0).getType())/2);
		//货物的重量分布
		for(goods_item gi:nextStep.getGoods()) {
			for(UAV_price_item upi:upis) {
				if(gi.getWeight()<upi.getLoad_weight()) {
					if(!goods_map.containsKey(upi.getType())) {
						goods_map.put(upi.getType(), 1);
						break;
					}else {
						goods_map.put(upi.getType(), goods_map.get(upi.getType())+1);
						break;
					}
				}
			}
		}
		//根据比例选择购买
		int uav_num=nextStep.getUAV_we().size();
		int goods_num=nextStep.getGoods().size();
		int value=nextStep.getWe_value();
		Collections.reverse(upis);
		for(UAV_price_item upi:upis) {
			double value1=0;
			if(uav_map.containsKey(upi.getType())) {
				value1=uav_map.get(upi.getType());
			}
			double value2=0;
			if(goods_map.containsKey(upi.getType())) {
				value2=goods_map.get(upi.getType());
			}
			if(uav_num==0||goods_num==0) {
				return null;
			}
			if(value1<value2&&value1/(uav_num*0.9)<value2/goods_num &&value>=upi.getValue()) {
				puis.add(new purchase_UAV_item(upi.getType()));
				value=value-upi.getValue();
			}
		}
		return puis;
	}
}
