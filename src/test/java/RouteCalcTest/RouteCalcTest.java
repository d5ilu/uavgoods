package RouteCalcTest;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import route.Position;
import route.RouteCalc;

public class RouteCalcTest {
	@Test
	public void testRouteCalc() {
		int [][] map=new int[100][100];
		for(int i=0;i<80;i++){
			map[i][30]=1;
		}
		for (int i=0;i<9;i++) {
			map[100-i-1][70]=1;
		}
		RouteCalc rouc= new RouteCalc(map);
		Date date1=new Date();
		List<Position> res=rouc.routeCalc(new Position(20,2),new Position(80, 90));
		Date date2=new Date();
		System.out.println(res);
		System.out.println(res.size());
		System.out.println(date2.getTime()-date1.getTime());
		
	}
	
}
