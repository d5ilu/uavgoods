package Hik;

import com.google.gson.Gson;

import conflictResolve.ConflictResolve;
import discalc.Dis_Cacl;
import domainReceived.Authentication;
import domainReceived.MapData.MapData;
import domainReceived.MapData.building_item;
import domainReceived.MapData.init_UAV_item;
import domainReceived.NextStep.NextStep;
import domainReceived.NextStep.goods_item;
import domainSend.ShowIdentity;
import domainSend.coordinateOfUAV.CoordinateOfUAV;
import domainSend.coordinateOfUAV.UAV_info_item;
import domainSend.coordinateOfUAV.purchase_UAV_item;
import purchase.UAVPurchase;
import route.Position;
import route.RouteCalc;
import taskAlloc.Patrol;
import taskAlloc.TaskAlloc;
import taskAlloc.UAV_task;
import update.UAVTasksUpdate;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

	public static final int SOCKET_HEAD_LEN = 8;
	public static Gson gson = new Gson();
	private static Object ob=new Object();

	private static RouteCalc routeCalc ;
	private static TaskAlloc taskAlloc= new TaskAlloc();;
	private static ConflictResolve conflictResolve;
	private static List<UAV_task> uavtasks=new ArrayList<UAV_task>();
	private static MapData mapData ;
	private static NextStep nextStep;
	private static Patrol patrol=new Patrol();
	private static List<UAV_info_item> UAV_info=new ArrayList<UAV_info_item>();
	private static int[][] distances_we =new int[256][2048];
	private static int [] distances_goods =new int[2048];
	private static Map<goods_item,UAV_task> goodsmap=new HashMap<goods_item,UAV_task>();
	private static UAVPurchase uavpurchase;
	static {
		conflictResolve=new ConflictResolve(uavtasks);
		conflictResolve.goodsmap=goodsmap;
		taskAlloc.uavtasks=uavtasks;
		taskAlloc.goodsmap=goodsmap;
		taskAlloc.routegoods=distances_goods;
		taskAlloc.distances_we=distances_we;
	}

	public static String readFromServer(BufferedReader bufferedReader) throws IOException {
		String info = "";
		char[] buff = new char[1024];
		int length = 0;
		String length_str = null;
		bufferedReader.read(buff, 0, SOCKET_HEAD_LEN);
		length_str = new String(buff, 0, SOCKET_HEAD_LEN);
		if (length_str == null || length_str.trim().length() == 0) {
			System.out.println("读取结束，返回值为空值");
			return null;
		}
		int maxLength = Integer.valueOf(length_str);
		int currentLength = 0;
		while (currentLength < maxLength) {
			length = bufferedReader.read(buff, 0, 1024);
			info += new String(buff, 0, length);
			currentLength += length;
		}
		System.out.println("客户端接收信息：" + length_str + info);
		return info;
	}


	public static String sendData(Object object, PrintWriter printWriter) {
		String data_str = gson.toJson(object);
		String lengthOfJSON = String.format("%08d", data_str.length());
		String sendData = lengthOfJSON + data_str;
		System.out.println("客户端发送消息:" + sendData);
		printWriter.print(sendData);
		printWriter.flush();
		return lengthOfJSON + data_str;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args==null || args.length != 3) {
			System.out.println("Wrong args.");
		} else {
			String serverHost = args[0];
			String serverPort = args[1];
			String authToken = args[2];
			System.out.println(String.format("ServerHost: %s, ServerPort: %s, authToken: %s.", serverHost, serverPort, authToken));
			System.out.println("竞赛开始");
			Socket socket = new Socket(serverHost, Integer.parseInt(serverPort));
			InputStream inputStream = socket.getInputStream();//获取一个输入流，接收服务端的信息
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//包装成字符流，提高效率
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//缓冲区

			//接受服务器消息1         2.1 连接成功后，Judger会返回一条消息：

			readFromServer(bufferedReader);
			String token = authToken;


			//根据输入输出流和服务端连接
			OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
			PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
			System.out.println(socket.isConnected());

			//发送消息1     2.2 选手向裁判服务器表明身份(Player -> Judger)
			ShowIdentity showIdentity = new ShowIdentity(token, "sendtoken");
			sendData(showIdentity, printWriter);


			//接受服务器消息2   2.3 身份验证结果(Judger -> Player)　　
			String temp = readFromServer(bufferedReader);
			Authentication authentication = gson.fromJson(temp, Authentication.class);
			//System.out.println(authentication.getNotice());


			//发送消息2  2.3.1 选手向裁判服务器表明自己已准备就绪(Player -> Judger)
			ShowIdentity showIdentity2 = new ShowIdentity(token, "ready");
			sendData(showIdentity2, printWriter);


			//接受服务器消息3  2.4 对战开始通知(Judger -> Player)　MapData
			String MapData1_str = readFromServer(bufferedReader);
			mapData = gson.fromJson(MapData1_str, MapData.class);
			routeCalc=getRouteCalc(mapData);
			taskAlloc.routeCalc=routeCalc;
			patrol.routeCalc=routeCalc;
			patrol.mapData=mapData;
			taskAlloc.mapData=mapData;
			conflictResolve.mapData=mapData;
			uavpurchase=new UAVPurchase(mapData.getMap().getUAV_price());
			for(init_UAV_item uav:mapData.getMap().getInit_UAV()) {
				uavtasks.add(new UAV_task(uav, mapData));
			}

			//发送消息3           2.5 选手返回下一步无人机坐标(Player -> Judger)

			for(init_UAV_item iui:mapData.getMap().getInit_UAV()) {
				UAV_info.add( new UAV_info_item(iui.getNo(), iui.getX(), iui.getY(), iui.getZ(), iui.getGoods_no()));
			}
			List<purchase_UAV_item> purchase_UAV = new ArrayList<>();

			CoordinateOfUAV coordinateOfUAV = new CoordinateOfUAV(token, "flyPlane");
			coordinateOfUAV.UAV_info=UAV_info;
			coordinateOfUAV.purchase_UAV=purchase_UAV;
			sendData(coordinateOfUAV, printWriter);

			UAV_info.clear();
			//接受服务器消息4  2.6 比赛下一步骤指令(Judger -> Player)
			String nextStep_str = readFromServer(bufferedReader);
			nextStep = gson.fromJson(nextStep_str, NextStep.class);
			int parking_enemy_x=0;
			int parking_enemy_y=0;
			if(nextStep.getUAV_enemy().size()!=0) {
				parking_enemy_x=nextStep.getUAV_enemy().get(0).getX();
				parking_enemy_y=nextStep.getUAV_enemy().get(0).getX();
			}
			Position parking_enemy=new Position(parking_enemy_x, parking_enemy_y);
			taskAlloc.parking_enemy=parking_enemy;
			taskAlloc.init();
			UAVTasksUpdate utu=new UAVTasksUpdate(uavtasks, mapData);
			//计算所有距离的线程
			Dis_Cacl thread=new Dis_Cacl(distances_we,  
					distances_goods, getRouteCalc(mapData) , 
					nextStep,mapData,ob);
			thread.start();
			try {
				//开始不停的收发
				while (true) {
					thread.setNextStep(nextStep);
					uavpurchase.setNextStep(nextStep);
					purchase_UAV =(uavpurchase.getPurchaseitems());
					taskAlloc.goods = new ArrayList<goods_item>(nextStep.getGoods());
					utu.update(nextStep.getUAV_we());
					if(nextStep.getTime()>=mapData.getMap().getH_low())
						taskAlloc.goodsAlloc();
					patrol.patroLine(uavtasks);
					UAV_info=(conflictResolve.getUAV_info_step());
					coordinateOfUAV.UAV_info=UAV_info;
					coordinateOfUAV.purchase_UAV=purchase_UAV;
					// 发送消息4   继续发送指令重复 2.5 选手返回下一步无人机坐标(Player -> Judger)
					sendData(coordinateOfUAV, printWriter);
					UAV_info.clear();
					//接受服务器消息5  接受到空字符串结束比赛
					nextStep_str = readFromServer(bufferedReader);
					if (nextStep_str == "" || nextStep_str == null)
						break;
					nextStep = gson.fromJson(nextStep_str, NextStep.class);

					synchronized (ob) {
						ob.notifyAll();
					}
				}return;
			}catch(Exception e) {
				e.printStackTrace();
				return;
			} finally {
				//关闭输出流输入流
				bufferedReader.close();
				inputStreamReader.close();
				printWriter.close();
				outputStream.close();
				socket.close();
				System.out.println(socket.isClosed());
			}
		}
	}
	public static RouteCalc getRouteCalc(MapData mapData) {
		int x=mapData.getMap().getMap().getX();
		int y=mapData.getMap().getMap().getY();
		int [][] map=new int[x][y];
		for(building_item bi:mapData.getMap().getBuilding()){
			if(bi.getH()>mapData.getMap().getH_low())
				for(int i=bi.getX();i<bi.getX()+bi.getL();i++) {
					for(int j=bi.getY();j<bi.getY()+bi.getW();j++) {
						map[i][j]=1;
					}

				}
		}
		RouteCalc routeCalc =new RouteCalc(map);
		return routeCalc;
	}
}
