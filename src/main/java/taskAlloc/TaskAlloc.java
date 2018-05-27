package taskAlloc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainReceived.MapData.MapData;
import domainReceived.MapData.UAV_price_item;
import domainReceived.NextStep.goods_item;
import domainSend.coordinateOfUAV.UAV_info_item;
import route.Position;
import route.RouteCalc;

/*
 * 预定义几种任务状态：运送货物1 ，破坏运送3，巡逻搜索 2，封锁敌方4
 * 
 */
public class TaskAlloc {
	public Position parking_enemy;
	public MapData mapData;
	private String leastvaluetype;
	public List<goods_item> goods;
	public RouteCalc routeCalc;
	public Map<goods_item,UAV_task> goodsmap;
	public int[][] distances_we;
	public List<UAV_task> uavtasks;
	private Map<goods_item,UAV_task> lockingmap;
	//货物的运送路线
	public int[] routegoods ;
	private GoodsSortComparator comparator;
	public void init() {
		int leastvalue=1000;
		UAV_price_item upitmp=null;
		if(mapData!=null) {
			List<UAV_price_item> upis= mapData.getMap().getUAV_price();
			for(UAV_price_item upi:upis) {
				if(leastvalue>upi.getValue()) {
					leastvalue=upi.getValue();
					upitmp=upi;
				}
			}
		}
		if(upitmp!=null) {
			leastvaluetype=upitmp.getType();
		}
		lockingmap=new HashMap<goods_item,UAV_task>();
	}
	//对于任务货物已被捡走的，调节任务状态;对于封锁货物状态的，若货物被我方捡到，取消封锁
	public void goodsStateDetec() {
		for (goods_item gi: goodsmap.keySet()) {
			if(!goods.contains(gi)||goods.get(goods.indexOf(gi)).getStatus()!=0) {
				UAV_task uts=goodsmap.get(gi);
				if(uts.uav.getX()==gi.getStart_x()
						&&uts.uav.getY()==gi.getStart_y()) {
					uts.state=3;
					uts.routetostart=routeCalc.routeCalc(new Position(uts.uav.getX(), uts.uav.getY()), new Position(gi.getStart_x(),gi.getStart_y()));
					uts.uavinfosClac(false);
					lockingmap.put(gi,uts);
					for(UAV_info_item uii:uts.uavinfos) {
						uii.setGoods_no(-1);
					}
				}
			}
		}
		for(UAV_task ut:uavtasks) {
			if(ut.state==4&&ut.target!=null) {
				if(goods.contains(ut.target)) {
					ut.state=0;
					break;
				}
				for(UAV_task uts:uavtasks) {
					if(uts.state==1&&uts.uavinfos.get(0).getGoods_no()==ut.target.getNo()) {
						ut.state=0;
					}
				}
			}
		}
		return;
	}
	public void lockParking_enemy() {
		UAV_task utmp=null;
		int num=0;
		for(UAV_task ut:uavtasks) {
			if(ut.state==4)
				num++;
		}
		if(num==0) {
			for(UAV_task ut:uavtasks) {
				if((ut.state==0||ut.state==2)&&ut.uav.getType().equals(leastvaluetype))
					utmp=ut;
			}
			if(utmp!=null) {
				utmp.state=4;
				utmp.target=null;
				utmp.movetarget=parking_enemy;
				utmp.routetostart=routeCalc.routeCalc(new Position(utmp.uav.getX(), utmp.uav.getY()), 
						parking_enemy);
				utmp.uavinfosClac(false);
			}
		}
	}
	/*
	 * 根据信息获得货物优先级,依次分配
	 */
	public void goodsAlloc() {
		goodsStateDetec();
		lockParking_enemy();
		sortGoods(uavtasks);	
		for(goods_item gi:goods ) {
			if(gi.getStatus()==0 &&goodsmap.containsKey(gi)==false) {
				int tmpdis=10000;
				UAV_task ut=null;
				for(UAV_task uav:uavtasks) {
					if( (uav.state==0||uav.state==2||uav.state==5)&&uav.load_weight >=gi.getWeight()) {
						int dis=distances_we[uav.uav.getNo()][gi.getNo()];
						if(dis!=0&&dis< gi.getLefttime() &&tmpdis>dis ) {
							tmpdis=dis;
							ut=uav;
						}
					}	
				}
				if(ut!=null) {
					ut.routetostart=routeCalc.routeCalc(new Position(ut.uav.getX(),ut.uav.getY()),
							new Position(gi.getStart_x(), gi.getStart_y()));
					ut.state=1;
					ut.routetoend=routeCalc.routeCalc(new Position(gi.getStart_x(),gi.getStart_y()),
							new Position(gi.getEnd_x(), gi.getEnd_y()));
					ut.target=gi;
					goodsmap.put(gi, ut);
					ut.uavinfosClac(true);
				}else {
					if(!lockingmap.containsKey(gi)) {
						UAV_task utmp=null;
						for(UAV_task uts:uavtasks) {
							if( (uts.state==0||uts.state==2||uts.state==5)&&uts.uav.getType().equals(leastvaluetype)) {
								utmp=uts;
							}
						}
						if(utmp!=null) {
							utmp.state=4;
							utmp.routetostart=routeCalc.routeCalc(new Position(utmp.uav.getX(),utmp.uav.getY()),
									new Position(gi.getEnd_x(), gi.getEnd_y()));
							utmp.target=gi;
							lockingmap.put(gi, utmp);
							utmp.uavinfosClac(false);
						}
					}
				}
			}
		}
		return;
	}
	private void sortGoods(List<UAV_task> uavs) {
		comparator =new GoodsSortComparator(routegoods,distances_we,uavs,routeCalc);
		Collections.sort(goods,comparator);
		Collections.reverse(goods);
	}
}
