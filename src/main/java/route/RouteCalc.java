package route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteCalc {
	public int [][] map;
	private A_base[][]a_bases;
	private List<A_base> openlist;
	private List<A_base> closelist;
	public RouteCalc(int[][] map) {
		super();
		this.map = map;
		if(map.length!=0&&map[0].length!=0) {
			this.a_bases = new A_base[map.length][map[0].length];
		}
		for (int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				this.a_bases[i][j]=new A_base(i, j);
			}
		}

	}

	public List<Position> routeCalc(Position position1,Position position2){
		init();
		List<Position> res=new ArrayList<Position>();
		int x1=position1.x;
		int y1=position1.y;
		int x2=position2.x;
		int y2=position2.y;
		A_base p1=a_bases[x1][y1];
		A_base p2=a_bases[x2][y2];
		if(x1==x2&&y1==y2 ) 
			return res;
		A_base currentPoint=null;
		openlist.add(p1);
		closelist.remove(currentPoint);
		while(!openlist.contains(p2)&&!openlist.isEmpty()) {
			int leastvalue=10000;
			for(A_base point:openlist) {
				if(leastvalue>point.getDis()) {
					leastvalue=point.getDis();
					currentPoint=point;
				}
			}
			closelist.add(currentPoint);
			openlist.remove(currentPoint);
			currentPoint.setState(2);
			List<A_base> list=getNeighbor(currentPoint, p2);
			list.addAll(openlist);
			openlist=list;
		}
		if(openlist.contains(p2)) {
			currentPoint=p2;
			while(currentPoint!=null)
			{
				res.add(new Position(currentPoint.getX(), currentPoint.getY()));
				currentPoint=currentPoint.getFather();
			}
			Collections.reverse(res);
			res.remove(0);
			return res;
		}
		else
			return null;
	}

	private List<A_base> getNeighbor(A_base p1,A_base p2) {
		int x=p1.getX();
		int y=p1.getY();
		int value=p1.getDisAlready();
		int [] xs,ys;
		if(x==0) {
			xs= new int[] {x,x+1};
		}
		else {
			if(x==map.length-1) {
				xs= new int[] {x-1,x};
			}
			else {
				xs= new int[] {x-1,x,x+1};
			}
		}
		if(y==0) {
			ys= new int[]{y,y+1};
		}
		else {
			if(y==map[0].length-1) {
				ys= new int[]{y-1,y};
			}
			else {
				ys= new int[]{y-1,y,y+1};
			}
		}
		List<A_base> list=new ArrayList<A_base>();
		for (int i=0;i<xs.length;i++) {
			for (int j=0;j<ys.length;j++) {
				if(map[xs[i]][ys[j]]==0) {
					if(a_bases[xs[i]][ys[j]].getState()==0) {
						a_bases[xs[i]][ys[j]].setState(1);
						a_bases[xs[i]][ys[j]].setFather(p1);
						a_bases[xs[i]][ys[j]].setDisAlready(value+1);
						a_bases[xs[i]][ys[j]].setDis(value+1+dis_enlighten(a_bases[xs[i]][ys[j]], p2));
						list.add(a_bases[xs[i]][ys[j]]);
					}
					else {
						if(a_bases[xs[i]][ys[j]].getState()==1)
							if(value+1<a_bases[xs[i]][ys[j]].getDisAlready()) {
								a_bases[xs[i]][ys[j]].setFather(p1);
								a_bases[xs[i]][ys[j]].setDisAlready(value+1);
								a_bases[xs[i]][ys[j]].setDis(value+1+dis_enlighten(a_bases[xs[i]][ys[j]], p2));
							}
					}

				}
			}
		}
		return list;
	}
	//距离的启发值
	private int dis_enlighten(A_base p1,A_base p2) {
		int x_d=Math.abs(p1.getX()-p2.getX());
		int y_d=Math.abs(p1.getY()-p2.getY());
		return Math.max(x_d,y_d);
	}
	private void init() {
		openlist=new ArrayList<A_base>();
		closelist=new ArrayList<A_base>();
		for (int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				a_bases[i][j].setFather(null);
				a_bases[i][j].setDis(0);
				a_bases[i][j].setDisAlready(0);
				a_bases[i][j].setState(0);
			}
		}
	}
}
