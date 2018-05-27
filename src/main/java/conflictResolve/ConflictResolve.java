package conflictResolve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domainReceived.MapData.MapData;
import domainReceived.NextStep.goods_item;
import domainSend.coordinateOfUAV.UAV_info_item;
import taskAlloc.UAV_task;

public class ConflictResolve {
	public MapData mapData;
	private List<UAV_task> uavtasks;
	public  Map<goods_item,UAV_task> goodsmap;
	public List<UAV_info_item> getUAV_info_step() throws CloneNotSupportedException{
		List<UAV_info_item> list =new ArrayList<UAV_info_item>();
		int len=uavtasks.size();
		boolean[] state=new boolean [len];
		//冲突消解2：对航线存在互相交换的无人机航线修正
				//第二种方式，上下移动躲避
				for(int i=0;i<len;i++) {
					if(state[i]==true) continue;
					for(int j=0;j<len;j++) {
						if(state[j]==true) continue;
						if(i!=j&&uavtasks.get(i).uavinfos.get(1).equals(uavtasks.get(j).uavinfos.get(0))
								&&uavtasks.get(i).uavinfos.get(0).equals(uavtasks.get(j).uavinfos.get(1))){
							if(uavtasks.get(i).load_weight>uavtasks.get(j).load_weight) {
								uavtasks.get(j).uavinfos.add(0, uavtasks.get(j).uavinfos.get(0).clone());
								uavtasks.get(j).uavinfos.add(0, uavtasks.get(j).uavinfos.get(0).clone());
								uavtasks.get(j).uavinfos.get(1).setZ(uavtasks.get(j).uavinfos.get(0).getZ()+1);
							}else {
								uavtasks.get(i).uavinfos.add(0, uavtasks.get(i).uavinfos.get(0).clone());
								uavtasks.get(i).uavinfos.add(0, uavtasks.get(i).uavinfos.get(0).clone());
								uavtasks.get(i).uavinfos.get(1).setZ(uavtasks.get(i).uavinfos.get(0).getZ()+1);
							}
						}
					}
				}
		UAV_info_item beginningPoint=new UAV_info_item(0, mapData.getMap().getParking().getX(), mapData.getMap().getParking().getY(), 0, -1);
		//冲突消解1：要进入同一个节点的无人机
		//循环两次，根据以第一次信息重新调整
		for(int k=0;k<2;k++) {
			for (int i=0;i<len;i++) {
				int num1=1;
				if(state[i]==true||uavtasks.get(i).state==5) {
					num1=0;
				}
				for(int j=0;j<len;j++) {
					if(i!=j) {
						int num2=1;
						if(state[j]==true||uavtasks.get(i).state==5) num2=0;
						if(uavtasks.get(i).uavinfos.get(num1).equals(uavtasks.get(j).uavinfos.get(num2))
								&&!uavtasks.get(i).uavinfos.get(num1).equals(beginningPoint)){
							if(uavtasks.get(i).load_weight>uavtasks.get(j).load_weight) {
								if(state[j]==false)
									state[j]=true;
								else state[i]=true;
							}
							else {
								if(state[i]==false)
									state[i]=true;
								else state[j]=true;
							}
						}
					}
				}
			}
		}
		
		//		for (int i=0;i<len;i++) {
		//			for(int j=0;j<len;j++) {
		//				if(state[i]==false&&state[j]==false&&i!=j&&uavtasks.get(i).uavinfos.get(0).equals(uavtasks.get(j).uavinfos.get(1))&&
		//						uavtasks.get(j).uavinfos.get(0).equals(uavtasks.get(i).uavinfos.get(1))) {
		//					int x1=uavtasks.get(i).uavinfos.get(0).getX();
		//					int y1=uavtasks.get(i).uavinfos.get(0).getY();
		//					int z1=uavtasks.get(i).uavinfos.get(0).getZ();
		//					int x2=uavtasks.get(j).uavinfos.get(0).getX();
		//					int y2=uavtasks.get(j).uavinfos.get(0).getY();
		//					int z2=uavtasks.get(j).uavinfos.get(0).getZ();
		//					int k=i;
		//					if(uavtasks.get(i).load_weight>uavtasks.get(j).load_weight) {
		//						k=j;
		//					}
		//					uavtasks.get(k).uavinfos.add(1, uavtasks.get(k).uavinfos.get(1));
		//					if(x1==x2){
		//						if(x1>0&&map[x1-1][y1]==0) {
		//							uavtasks.get(k).uavinfos.get(1).setX(x1-1);
		//						}else {
		//							if(x1<x-1)
		//								uavtasks.get(k).uavinfos.get(1).setX(x1+1);
		//						}
		//					}else {
		//						if(y1==y2) {
		//							if(y1>0&&map[x1][y1-1]==0) {
		//								uavtasks.get(k).uavinfos.get(1).setY(y1-1);
		//							}else {
		//								if(y1<y-1)
		//									uavtasks.get(k).uavinfos.get(1).setY(y1+1);
		//							}
		//						}else {
		//							if(map[x1][y1]==0)
		//								uavtasks.get(k).uavinfos.get(1).setX(x1);
		//							else
		//								uavtasks.get(k).uavinfos.get(1).setY(y1);
		//						}
		//					}
		//				}
		//			}
		//		}
		//冲突消解三 对角线交叉方式

		for(int i=0;i<len;i++) {
			UAV_info_item uiii1=uavtasks.get(i).uavinfos.get(0);
			UAV_info_item uiii2=uavtasks.get(i).uavinfos.get(1);
			if(state[i]==true||uiii1.getX()==uiii2.getX()||uiii1.getY()==uiii2.getY()) continue;
			int valueX1=uiii1.getX()+uiii2.getX();
			int valueY1=uiii1.getY()+uiii2.getY();
			for(int j=0;j<len;j++) {
				if(i!=j) {
					UAV_info_item uiij1=uavtasks.get(j).uavinfos.get(0);
					UAV_info_item uiij2=uavtasks.get(j).uavinfos.get(1);
					if(state[j]==true||uiij1.getX()==uiij2.getX()||uiij1.getY()==uiij2.getY()) continue;
					int valueX2=uiij1.getX()+uiij2.getX();
					int valueY2=uiij1.getY()+uiij2.getY();
					if(valueX1==valueX2&&valueY1==valueY2) {
						if(uavtasks.get(i).load_weight>uavtasks.get(j).load_weight) {
							uavtasks.get(j).uavinfos.add(0, uavtasks.get(j).uavinfos.get(0).clone());
							uavtasks.get(j).uavinfos.add(0, uavtasks.get(j).uavinfos.get(0).clone());
							uavtasks.get(j).uavinfos.get(1).setZ(uavtasks.get(j).uavinfos.get(0).getZ()+1);
						}else {
							uavtasks.get(i).uavinfos.add(0, uavtasks.get(i).uavinfos.get(0).clone());
							uavtasks.get(i).uavinfos.add(0, uavtasks.get(i).uavinfos.get(0).clone());
							uavtasks.get(i).uavinfos.get(1).setZ(uavtasks.get(i).uavinfos.get(0).getZ()+1);
						}
					}
				}
			}
		}
		//去掉目标货物已经消失或被捡起的无人机的任务
		for(int i=0;i<len;i++) {
			UAV_info_item ui= uavtasks.get(i).step(state[i]);
			if(ui.getGoods_no()!=-1) {
				goods_item gitem=null;
				for(goods_item gi:goodsmap.keySet()) {
					if(gi.getNo()==ui.getGoods_no()) {
						gitem=gi;
						break;
					}
				}
				goodsmap.remove(gitem);
			}
			list.add(ui);
		}
		return list; 
	}
	public ConflictResolve(List<UAV_task> uavtasks) {
		super();
		this.uavtasks = uavtasks;
	}

}
