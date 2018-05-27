package taskAlloc;

import java.util.List;

import domainReceived.MapData.MapData;
import route.Position;
import route.RouteCalc;

public class Patrol {
	public MapData mapData;
	public RouteCalc routeCalc;
	private static int num=1;
	public void patroLine( List<UAV_task> uavs) {
		for(UAV_task ut:uavs) {
			if(ut.state==0) {
				ut.movetarget=getPosition();
				num++;
				ut.routetostart=routeCalc.routeCalc(new Position(ut.uav.getX(),ut.uav.getY()),ut.movetarget );
				ut.uavinfosClac(false);
				ut.state=2;
			}
		}
	}
	//地图的几个点位置，代表大致几个方向
	private Position getPosition() {
		int numtmp=num;
		while(numtmp>=16) {
			numtmp=numtmp-16;
		}
		int block_x=numtmp%4;
		int block_y=numtmp/4;
		int map_x=mapData.getMap().getMap().getX();
		int map_y=mapData.getMap().getMap().getY();
		int x=(block_x+1)*map_x/6;
		int y=(block_y+1)*map_y/6;
		while(routeCalc.map[x][y]==1&&x>0) {
			x--;
		}
		while(routeCalc.map[x][y]==1&&y>0) {
			y--;
		}
		while(routeCalc.map[x][y]==1&&x<mapData.getMap().getMap().getX()-1) {
			x++;
		}
		while(routeCalc.map[x][y]==1&&y<mapData.getMap().getMap().getY()-1) {
			y++;
		}
		Position po=new Position(x, y);
		return po;
	}
}

