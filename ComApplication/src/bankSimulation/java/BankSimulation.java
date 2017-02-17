package bankSimulation.java;
//模拟拥有4个窗口的银行办理业务的过程，统计顾客平均消耗的时间
public class BankSimulation {
	private EventList eventList;
	private Event event;
	private LinQueue[] windows;//4个窗口队列
	private int totalTime;//所有顾客的总时间
	private int custmerNum;
	private static final int COLSETIME = 480;
	
	public void openForDay(){
		totalTime=0;
		custmerNum=0;
		eventList=new EventList();
		windows=new LinQueue[5];for(int i=1;i<5;i++){
			windows[i]=new LinQueue();
		}
		
		//产生第一个事件，即顾客到达事件
		event=new Event();
		event.occurTime=0;
		event.nType=0;
		eventList.orderInsert(event);
		
	}
	
	public void customerArrived(){
		custmerNum++;
		int durtime=(int)(Math.random()*30);//这一个顾客的办理业务所需时间
		int intertime=(int)(Math.random()*5);//下一个顾客到达的时间间隔
		int t=event.occurTime+intertime;//下一个客户到达事件
		if (t<COLSETIME) {
			event=new Event(t, 0);
			eventList.orderInsert(event);
		}
		//寻找人数最少的队列，并插入
		int j=1;
		for(int i=1;i<5;i++){
			if (windows[i].getSize()<windows[j].getSize()) {
				j=i;
			}
		}
		try {
			windows[j].append(new QueueElemType(event.occurTime,durtime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (windows[j].getSize()==1) {
			eventList.orderInsert(new Event(event.occurTime+durtime,j));
		}
	}
	
	public void customerDeparture(){
		try {
			int eventType=event.nType;//客户离开的队列
			QueueElemType customer=(QueueElemType)windows[eventType].delete();
			totalTime+=event.occurTime-customer.arrivalTime;
			if (windows[eventType].notEmpty()) {
				customer=(QueueElemType) windows[eventType].getFront();
				eventList.orderInsert(new Event(event.occurTime+customer.duration, eventType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeForDay(){
		System.out.println("顾客平均时间："+totalTime/custmerNum);
	}
	
	public void simulation(){
		openForDay();
		try {
			while(!eventList.isEmpty()){
				event=eventList.delete(0);
				if (event.nType==0) {
					customerArrived();
				}
				else {
					customerDeparture();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeForDay();
	}
	public static void main(String[] args) {
		BankSimulation b=new BankSimulation();
		for(int i=0;i<10;i++){
			b.simulation();
		}
	}

}
