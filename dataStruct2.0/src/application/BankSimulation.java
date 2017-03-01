package application;

import java.util.Random;

import heap.MinHeap;
import list.List;
import list.SeqList;
import queue.LinQueue;
import queue.Queue;

/**
 * 模拟拥有4个窗口的银行办理业务的过程，统计顾客平均消耗的时间
 * @author hjg
 *
 */
public class BankSimulation {
	private MinHeap<Event> eventQueue;
	private List<Queue<Guest>> windows;
	private int curMoment;
	private int totalTime;
	private int numbers;
	
	private Random random; 
	
	private static final int END_MOMENT=480;
	private static final int DEFAULT_EVENT_QUEUE_SIZE=50;
	/**
	 * 最大顾客到达的时间间隔
	 */
	private static final int MAX_ARRIVE_GAP=10;
	/**
	 * 最大顾客办理业务的时间
	 */
	private static final int MAX_SERVICE_TIME=30;
	
	public BankSimulation() {
		eventQueue=new MinHeap<>(DEFAULT_EVENT_QUEUE_SIZE);
		windows=new SeqList<>(4);
		for (int i=0;i<4;i++) {
			windows.add(new LinQueue<>());
		}
		curMoment=0;
		totalTime=0;
		numbers=0;
		random=new Random();
		eventQueue.add(new Event(curMoment+random.nextInt(MAX_ARRIVE_GAP), Event.ARRIVE));
	}
	public void start(){
		while (curMoment<END_MOMENT&&!eventQueue.isEmpty()) {
			Event event=eventQueue.poll();
			curMoment=event.occurrence;
			if (event.type==Event.ARRIVE) {
				arrive();
			}else {
				leave(event);
			}
		}
	}
	/**
	 * 处理顾客离开事件
	 * @param event
	 */
	private void leave(Event event) {
		curMoment=event.occurrence;
		int i=event.type;
		Guest guest=windows.get(i).poll();
		totalTime+=curMoment-guest.arriveMonent;
		numbers++;
		if (!windows.get(i).isEmpty()) {
			guest=windows.get(i).peek();
			eventQueue.add(new Event(curMoment+guest.serviceTime, i));
		}
	}
	/**
	 * 处理顾客到达事件
	 */
	private void arrive() {
		eventQueue.add(new Event(curMoment+random.nextInt(MAX_ARRIVE_GAP), Event.ARRIVE));
		Guest guest=new Guest(curMoment, random.nextInt(MAX_SERVICE_TIME));
		//寻找队伍最短的窗口加入
		int min=0;
		int minSize=windows.get(min).size();
		for (int i=1;i<windows.size();i++) {
			if (windows.get(i).size()<minSize) {
				min=i;
				minSize=windows.get(min).size();
			}
		}
		//若直接开始办理业务时增加一个离开事件
		if (windows.get(min).isEmpty()) {
			eventQueue.add(new Event(curMoment+guest.serviceTime, min));
		}
		windows.get(min).add(guest);
	}
	
	/**
	 * 统计顾客平均消耗的时间
	 * @return
	 */
	public double averageTime(){
		return totalTime/numbers;
	}
	
	private class Guest{
		private int arriveMonent;
		private int serviceTime;
		public Guest(int arriveMonent, int serviceTime) {
			this.arriveMonent=arriveMonent;
			this.serviceTime=serviceTime;
		}
	}
	/**
	 * 顾客事件
	 * @author hjg
	 *
	 */
	private class Event implements Comparable<Event>{
		private int occurrence;
		/**
		 * 顾客到达
		 */
		private static final int ARRIVE=-1;
		/**
		 * 0-3:离开事件；ARRIVE:到达事件
		 */
		private int type;
		public Event(int occurrence, int type) {
			this.occurrence=occurrence;
			this.type=type;
		}
		@Override
		public int compareTo(Event o) {
			return this.occurrence-o.occurrence;
		}
		
	}
}
