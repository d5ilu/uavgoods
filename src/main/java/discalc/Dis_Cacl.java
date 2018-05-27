package discalc;

import domainReceived.MapData.MapData;
import domainReceived.NextStep.NextStep;
import domainReceived.NextStep.UAV_enemy_item;
import domainReceived.NextStep.UAV_we_item;
import domainReceived.NextStep.goods_item;
import route.Position;
import route.RouteCalc;

public class Dis_Cacl extends Thread {
	private int[][] dis_we;
	private int [] dis_goods;
	private RouteCalc routeCalc;
	private  NextStep nextStep;
	private MapData mapData;
	private Object ob;


	public Dis_Cacl(int[][] dis_we,  int[] dis_goods, RouteCalc routeCalc,
			NextStep nextStep,MapData mapData,Object ob) {
		super();
		this.dis_we = dis_we;
		this.dis_goods = dis_goods;
		this.routeCalc = routeCalc;
		this.nextStep=nextStep;
		this.mapData=mapData;
		this.ob=ob;
	}
	@Override
	public void run() {
		while(true) {
			synchronized (ob) {
			calcdis_goods();
			calcdis_we();
			//calcdis_enemy();
			try {
					ob.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void calcdis_we() {
		int uav_no=-1;
		int goods_no=-1;
		for(UAV_we_item uav:nextStep.getUAV_we()) {
			uav_no=uav.getNo();
			for(goods_item good:nextStep.getGoods()) {
				goods_no=good.getNo();
				dis_we[uav_no][goods_no]=routeCalc.routeCalc(new Position(uav.getX(), uav.getY()),new Position(good.getStart_x(), good.getStart_y())).size()+
						2*mapData.getMap().getH_low()-uav.getZ();
			}
		}
	}
	public void calcdis_enemy() {
		int uav_no=-1;
		int goods_no=-1;
		for(UAV_enemy_item uav:nextStep.getUAV_enemy()) {
			uav_no=uav.getNo();
			for(goods_item good:nextStep.getGoods()) {
				goods_no=good.getNo();
			//	dis_enemy[uav_no][goods_no]=routeCalc.routeCalc(new Position(uav.getX(), uav.getY()),new Position(good.getStart_x(), good.getStart_y())).size()+
			//			2*mapData.getMap().getH_low()-uav.getZ();
			}
		}
	}
	public void calcdis_goods() {
		for(goods_item good:nextStep.getGoods()) {
			dis_goods[good.getNo()]=routeCalc.routeCalc(new Position(good.getStart_x(), good.getStart_y()),new Position(good.getEnd_x(), good.getEnd_y())).size()
					+2*mapData.getMap().getH_low();
		}
	}
	public void setNextStep(NextStep nextStep) {
		this.nextStep = nextStep;
	}
}
