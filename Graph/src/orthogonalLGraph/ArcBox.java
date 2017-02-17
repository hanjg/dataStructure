package orthogonalLGraph;

class ArcBox {//存储弧的信息
	int row;//弧尾下标
	int col;//弧头下标
	int weight;
	ArcBox headLink;//指向弧头相同的弧
	ArcBox tailLink;//指向弧尾相同的弧
	
	public ArcBox(int r,int c,int w){
		row=r;
		col=c;
		weight=w;
	}
	
	public ArcBox(int r,int c,int w,ArcBox h,ArcBox t){
		row=r;
		col=c;
		weight=w;
		headLink=h;
		tailLink=t;
	}

}
