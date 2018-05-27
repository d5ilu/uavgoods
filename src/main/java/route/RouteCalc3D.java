package route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteCalc3D {
	public int [][][] map;
	private int h_low;
	private int h_high;
	private A_base3D[][][]A_base3Ds;
	private List<A_base3D> openlist;
	private List<A_base3D> closelist;
	public RouteCalc3D(int[][][] map,int h_high,int h_low) {
		super();
		this.map = map;
		if(map.length!=0&&map[0].length!=0&&map[0][0].length!=0) {
			this.A_base3Ds = new A_base3D[map.length][map[0].length][map[0][0].length];
		}
		for (int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				for(int k=0;k<map[0][0].length;k++)
					this.A_base3Ds[i][j][k]=new A_base3D(i, j, k);
			}
		}
		this.h_high=h_high;
		this.h_low=h_low;
	}

	public List<Position3D> routeCalc(Position3D Position3D1,Position3D Position3D2){
		init();
		List<Position3D> res=new ArrayList<Position3D>();
		int x1=Position3D1.x;
		int y1=Position3D1.y;
		int z1=Position3D1.z;
		int x2=Position3D2.x;
		int y2=Position3D2.y;
		int z2=Position3D2.z;
		if(z1<h_low) z1=h_low;
		if(z2<h_low) z2=h_low;
		A_base3D p1=A_base3Ds[x1][y1][z1];
		A_base3D p2=A_base3Ds[x2][y2][z2];
		if(x1==x2&&y1==y2 &&z1==z2) 
			return res;
		A_base3D currentPoint=null;
		openlist.add(p1);
		closelist.remove(currentPoint);
		while(!openlist.contains(p2)&&!openlist.isEmpty()) {
			int leastvalue=10000;
			for(A_base3D point:openlist) {
				if(leastvalue>point.getDis()) {
					leastvalue=point.getDis();
					currentPoint=point;
				}
			}
			closelist.add(currentPoint);
			openlist.remove(currentPoint);
			currentPoint.setState(2);
			List<A_base3D> list=getNeighbor(currentPoint, p2);
			openlist.addAll(list);
		}
		if(openlist.contains(p2)) {
			currentPoint=p2;
			while(currentPoint!=null)
			{
				res.add(new Position3D(currentPoint.getX(), currentPoint.getY(),currentPoint.getZ()));
				currentPoint=currentPoint.getFather();
			}
			Collections.reverse(res);
			res.remove(0);
			return res;
		}
		else
			return null;
	}

	private List<A_base3D> getNeighbor(A_base3D p1,A_base3D p2) {
		int x=p1.getX();
		int y=p1.getY();
		int z=p1.getZ();
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
		List<A_base3D> list=new ArrayList<A_base3D>();
		for (int i=0;i<xs.length;i++) {
			for (int j=0;j<ys.length;j++) {
				if(map[xs[i]][ys[j]][z]==0) {
					if(A_base3Ds[xs[i]][ys[j]][z].getState()==0) {
						A_base3Ds[xs[i]][ys[j]][z].setState(1);
						A_base3Ds[xs[i]][ys[j]][z].setFather(p1);
						A_base3Ds[xs[i]][ys[j]][z].setDisAlready(value+1);
						A_base3Ds[xs[i]][ys[j]][z].setDis(value+1+dis_enlighten(A_base3Ds[xs[i]][ys[j]][z], p2));
						list.add(A_base3Ds[xs[i]][ys[j]][z]);
					}
					else {
						if(A_base3Ds[xs[i]][ys[j]][z].getState()==1)
							if(value+1<A_base3Ds[xs[i]][ys[j]][z].getDisAlready()) {
								A_base3Ds[xs[i]][ys[j]][z].setFather(p1);
								A_base3Ds[xs[i]][ys[j]][z].setDisAlready(value+1);
								A_base3Ds[xs[i]][ys[j]][z].setDis(value+1+dis_enlighten(A_base3Ds[xs[i]][ys[j]][z], p2));
							}
					}

				}
			}
		}
		if(z>h_low &&map[x][y][z-1]==0) {
			if(A_base3Ds[x][y][z-1].getState()==0) {
			A_base3Ds[x][y][z-1].setState(1);
			A_base3Ds[x][y][z-1].setFather(p1);
			A_base3Ds[x][y][z-1].setDisAlready(value+1);
			A_base3Ds[x][y][z-1].setDis(value+1+dis_enlighten(A_base3Ds[x][y][z-1], p2));
			list.add(A_base3Ds[x][y][z-1]);
			}else {
				if(A_base3Ds[x][y][z-1].getState()==1&&value+1<A_base3Ds[x][y][z].getDisAlready())
				A_base3Ds[x][y][z-1].setFather(p1);
				A_base3Ds[x][y][z-1].setDisAlready(value+1);
				A_base3Ds[x][y][z-1].setDis(value+1+dis_enlighten(A_base3Ds[x][y][z-1], p2));
				list.add(A_base3Ds[x][y][z-1]);
			}
		}
		if(z<h_high &&map[x][y][z+1]==0) {
			if(A_base3Ds[x][y][z+1].getState()==0) {
			A_base3Ds[x][y][z+1].setState(1);
			A_base3Ds[x][y][z+1].setFather(p1);
			A_base3Ds[x][y][z+1].setDisAlready(value+1);
			A_base3Ds[x][y][z+1].setDis(value+1+dis_enlighten(A_base3Ds[x][y][z+1], p2));
			list.add(A_base3Ds[x][y][z+1]);
			}else {
				if(A_base3Ds[x][y][z+1].getState()==1&&value+1<A_base3Ds[x][y][z].getDisAlready())
				A_base3Ds[x][y][z+1].setFather(p1);
				A_base3Ds[x][y][z+1].setDisAlready(value+1);
				A_base3Ds[x][y][z+1].setDis(value+1+dis_enlighten(A_base3Ds[x][y][z+1], p2));
				list.add(A_base3Ds[x][y][z+1]);
			}
		}
		return list;
	}
	//距离的启发值
	private int dis_enlighten(A_base3D p1,A_base3D p2) {
		int x_d=Math.abs(p1.getX()-p2.getX());
		int y_d=Math.abs(p1.getY()-p2.getY());
		int z_d=Math.abs(p1.getZ()-p2.getZ());
		return Math.max(x_d,y_d)+z_d;
	}
	private void init() {
		openlist=new ArrayList<A_base3D>();
		closelist=new ArrayList<A_base3D>();
		for (int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				for(int k=0;k<map[0][0].length;k++) {
					A_base3Ds[i][j][k].setFather(null);
					A_base3Ds[i][j][k].setDis(0);
					A_base3Ds[i][j][k].setDisAlready(0);
					A_base3Ds[i][j][k].setState(0);
				}
			}
		}
	}
}
