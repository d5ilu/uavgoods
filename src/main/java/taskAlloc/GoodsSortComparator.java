package taskAlloc;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import domainReceived.NextStep.NextStep;
import domainReceived.NextStep.UAV_we_item;
import domainReceived.NextStep.goods_item;
import route.Position;
import route.RouteCalc;

//货物的排序规则（value，weight，货物运输距离,）
public class GoodsSortComparator implements Comparator<goods_item>{

	private int[] routegoods;
	private int[][] distance_we;
	private List<UAV_task> uavtasks;
	private RouteCalc routecalc;

	public GoodsSortComparator(int[] routegoods, int[][] distance_we,  List<UAV_task> uavtasks,RouteCalc routecalc) {
		super();
		this.routegoods = routegoods;
		this.distance_we = distance_we;
		this.uavtasks = uavtasks;
		this.routecalc=routecalc;
	}
	@Override
	public int compare(goods_item arg0, goods_item arg1) {
		if(valueCalc(arg0)==valueCalc(arg1))
			return 0;
		else {
			if(valueCalc(arg0)>valueCalc(arg1))
				return 1;
			else return -1;
		}
	}
	private double valueCalc(goods_item gi) {
		int res=0;
		int no=gi.getNo();
		int value=100;
		for(UAV_task ut:uavtasks) {
			if(ut.load_weight>=gi.getWeight()){
				if(distance_we[ut.uav.getNo()][no]==0) {
					distance_we[ut.uav.getNo()][no]=routecalc.routeCalc(new Position(ut.uav.getX(), ut.uav.getY()),new Position(gi.getStart_x(), gi.getStart_y())).size(); 
				}
				value=Math.min(value, distance_we[ut.uav.getNo()][no]);
			}	
		}
		if(routegoods[gi.getNo()]!=0) {
			res= 100*gi.getValue()/(routegoods[gi.getNo()]+value);
		}else {
			int dis=routecalc.routeCalc(new Position(gi.getStart_x() ,gi.getStart_y()),new Position(gi.getEnd_x(), gi.getEnd_y())).size();
			res= 100*gi.getValue()/(dis+value);
		}
		return res;
	}

}
