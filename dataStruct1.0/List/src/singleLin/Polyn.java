package singleLin;

import java.util.Scanner;



class PolynItem{
	double coaf;
	int expn;
	
	PolynItem(double c,int e){
		coaf=c;
		expn=e;
	}
}

public class Polyn {
	private LinList poLinList;

	private Scanner input;
	
	public Polyn(){
		poLinList=new LinList();
	}
	
	public void createPolyn(int m){
		for(int i=0;i<m;i++){
			input = new Scanner(System.in);//多形式需要按照指数从小到大排序
			PolynItem PolynItem=new PolynItem(input.nextDouble(), input.nextInt());
			poLinList.insert(poLinList.getSize(), PolynItem);
		}
		
	}
	
	public void createPolyn(double [] a){
		for(int i=0;i<a.length;i+=2){
			PolynItem pItem=new PolynItem(a[i], (int)a[i+1]);
			poLinList.insert(poLinList.getSize(), pItem);
		}
	}
	
	private int compare(double a,double b){
		if (a<b) {
			return -1;
		}
		else if (a==b) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public void addPolyn(Polyn polynB){
		Node pA=this.poLinList.head.getNext();
		Node pB=polynB.poLinList.head.getNext();
		Node pC=this.poLinList.head;
		while(pA!=null&&pB!=null){
			PolynItem itemA=(PolynItem)pA.getElement();
			PolynItem itemB=(PolynItem)pB.getElement();
			int key=compare(itemA.expn,itemB.expn);
			switch (key) {
			case -1:{
				pC.setNext(pA);
				pC=pC.getNext();
				pA=pA.getNext();
				break;
			}
			case 1:{
				pC.setNext(pB);
				pC=pC.getNext();
				pB=pB.getNext();
				break;
			}
			case 0:{
				if ((itemA.coaf+itemB.coaf)!=0) {
					itemA.coaf=itemA.coaf+itemB.coaf;
					pC.setNext(pA);
					pC=pC.getNext();
					pA=pA.getNext();
					pB=pB.getNext();
				}
				else {
					pA=pA.getNext();
					pB=pB.getNext();
				}
				break;
			}
			}
		}
		
		pC.setNext(pA==null?pB:pA);
	}

	public void outPutPolyn(){//指针访问链表，isEmpty,print均须通过size访问，但是在多项式相加时只进行了指针操作
								//并未调用insert,delete函数，所以size并未变化
		if (this.poLinList.isEmpty2()) {
			System.out.println(0);
			return;
		}
		Node node=this.poLinList.head.getNext();
		while(node.getNext()!=null){
			System.out.print(((PolynItem)node.getElement()).coaf+"x^"+
					((PolynItem)node.getElement()).expn+"+");
			node=node.getNext();
		}
		System.out.println(((PolynItem)node.getElement()).coaf+"x^"+
				((PolynItem)node.getElement()).expn);
	}

	public static void main(String[] args) {
		double[] a={4,5,6,7};
		double[] b={-4,5,-6,7};
		Polyn polyn1=new Polyn();
		polyn1.createPolyn(a);
		polyn1.outPutPolyn();
		Polyn polyn2=new Polyn();
		polyn2.createPolyn(b);
		polyn2.outPutPolyn();
		polyn1.addPolyn(polyn2);
		polyn1.outPutPolyn();
		
	}

}
