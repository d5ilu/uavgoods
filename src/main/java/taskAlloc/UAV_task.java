package taskAlloc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domainReceived.MapData.MapData;
import domainReceived.MapData.UAV_price_item;
import domainReceived.MapData.init_UAV_item;
import domainReceived.NextStep.UAV_we_item;
import domainReceived.NextStep.goods_item;
import domainSend.coordinateOfUAV.UAV_info_item;
import route.A_base;
import route.Position;

public class UAV_task {
	public UAV_we_item  uav;
	//目标货物
	public goods_item target;
	public int load_weight;
	//巡逻目标点
	public Position movetarget;
	//到货物上方的规划路线和货物从开始到结束点的路线
	public List<Position> routetostart;
	public List<Position> routetoend;
	public int h_low;
	private int h_high;
	//无人机当前航线，由taskalloc产生
	public List<UAV_info_item> uavinfos=new ArrayList<UAV_info_item>();
	//状态 1运送中 0空闲2巡逻3封锁货物4封锁停机坪
	public int state;
	public UAV_task(init_UAV_item uav,MapData map) {
		super();
		this.uav= new UAV_we_item(uav.getNo(), uav.getType(), uav.getX(), uav.getY(), uav.getZ(), -1, 0);
		h_low=map.getMap().getH_low();
		h_high=map.getMap().getH_high();
		load_weight=uav.getLoad_weight();
		uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), uav.getZ(), -1));
	}

	public UAV_task(UAV_we_item uav,MapData map) {
		super();
		this.uav = uav;
		h_low=map.getMap().getH_low();
		h_high=map.getMap().getH_high();
		String type=uav.getType();
		for(UAV_price_item upi:map.getMap().getUAV_price()) {
			if(type.equals(upi.getType()))
				load_weight=upi.getLoad_weight();
		}
		uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), uav.getZ(), -1));
	}

	public void uavinfosClac(boolean flag){
		uavinfos.clear();
		uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), uav.getZ(), -1));
		CalcUavinfoStep1();
		if(state==3||state==4) {
			int len=uavinfos.size();
			UAV_info_item uii=new UAV_info_item(uav.getNo(), uavinfos.get(len-1).getX(),
					uavinfos.get(len-1).getY(), uavinfos.get(len-1).getZ()-1, -1);
			uavinfos.add(uii);
		}
		if(flag==true) {
			CalcUavinfoStep2();
			if(state==1) {
				CalcUavinfoStep3();
			}
		}
	}
	public UAV_info_item step(boolean status) {
		UAV_info_item uii=null;
		if(status==true) {
			uii=uavinfos.get(0);
		}
		else {
			if(uavinfos.size()>1) {
				uii=uavinfos.get(1);
				uavinfos.remove(0);
				uav.setX(uii.getX());
				uav.setY(uii.getY());
				uav.setZ(uii.getZ());
				uav.setGoods_no(uii.getGoods_no());
			}else {
				uii=uavinfos.get(0);
			}
			if(uavinfos.size()==1) {
				if(state==1||state==3) {
					this.state=0;
					target=null;
					uav.setGoods_no(-1);
				}else {
					uavinfos.add(uavinfos.get(0));
					if(state==2)
					state=5;
				}

			}
			if(uavinfos.size()==0) {
				uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), uav.getZ(), -1));
				uavinfos.add(uavinfos.get(0));
				state=5;
			}
		}
		return uii;

	}
	public void CalcUavinfoStep1() {
		int tmp=uav.getZ();
		if(target==null||uav.getX()!=target.getStart_x()||uav.getY()!=target.getStart_y()) {
			while(tmp<h_low) {
				tmp++;
				uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), tmp, -1));
			}
			if(state==2) {
				tmp++;
				uavinfos.add(new UAV_info_item(uav.getNo(), uav.getX(), uav.getY(), tmp, -1));
			}
		}
		for(Position ab:routetostart) {
			uavinfos.add(new UAV_info_item(uav.getNo(), ab.getX(), ab.getY(), tmp, -1));
		}
	}
	public void CalcUavinfoStep2() {
		int x=uavinfos.get(uavinfos.size()-1).getX();
		int y=uavinfos.get(uavinfos.size()-1).getY();
		int tmp=uavinfos.get(uavinfos.size()-1).getZ();
		while(tmp>0) {
			tmp--;
			uavinfos.add(new UAV_info_item(uav.getNo(), x,y , tmp, -1));
		}

	}
	public void CalcUavinfoStep3() {
		uavinfos.get(uavinfos.size()-1).setGoods_no(target.getNo());
		int x=uavinfos.get(uavinfos.size()-1).getX();
		int y=uavinfos.get(uavinfos.size()-1).getY();
		int tmp=uavinfos.get(uavinfos.size()-1).getZ();
		while(tmp<h_low) {
			tmp++;
			uavinfos.add(new UAV_info_item(uav.getNo(), x,y, tmp, target.getNo()));
		}
		for(Position ab:routetoend) {
			uavinfos.add(new UAV_info_item(uav.getNo(), ab.getX(), ab.getY(), tmp,target.getNo()));
		}
		tmp=uavinfos.get(uavinfos.size()-1).getZ();
		x=uavinfos.get(uavinfos.size()-1).getX();
		y=uavinfos.get(uavinfos.size()-1).getY();
		while(tmp>0) {
			tmp--;
			uavinfos.add(new UAV_info_item(uav.getNo(), x,y , tmp, target.getNo()));

		}
	}
	public String toString() {
		return ""+uav.getNo()+" "+load_weight;
	}

}
