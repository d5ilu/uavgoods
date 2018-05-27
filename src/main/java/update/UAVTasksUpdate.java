package update;

import java.util.ArrayList;
import java.util.List;

import domainReceived.MapData.MapData;
import domainReceived.NextStep.UAV_we_item;
import taskAlloc.UAV_task;

public class UAVTasksUpdate {
	private List<UAV_task> uavtasks;
	private MapData mapData;
	
	public UAVTasksUpdate(List<UAV_task> uavtasks, MapData mapData) {
		super();
		this.uavtasks = uavtasks;
		this.mapData = mapData;
	}

	public void update(List<UAV_we_item> uwis) {
		//加入新的无人机
		int len=uwis.size();
		int [] state=new int [len];
		for(int i=0;i<len;i++) {
			UAV_we_item uwi=uwis.get(i);
			for(UAV_task ut:uavtasks) {
				if(ut.uav.equals(uwi)) {
					state[i]=1;
					break;
				}
			}
		}
		for(int i=0;i<len;i++) {
			if(state[i]==0) {
				uavtasks.add(new UAV_task(uwis.get(i), mapData));
			}
		}
		//去掉损坏的无人机
		for(UAV_we_item uwi:uwis) {
			List<UAV_task> list=new ArrayList<UAV_task>();
			if(uwi.getStatus()==1) {
				for (UAV_task ut:uavtasks) {
					if (ut.uav.getNo()==uwi.getNo())
						list.add(ut);
				}
			}
			uavtasks.removeAll(list);
		}
		
	}
}
