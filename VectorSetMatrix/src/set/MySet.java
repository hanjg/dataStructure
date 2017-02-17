package set;

import vector.MyVector;

public class MySet {//用向量实现集合，集合的数据元素无序且不重复
	private MyVector values=new MyVector();
	
	public void add(Object obj){
		if (obj==null) {
			return;
		}
		if (values.indexOf(obj)<0) {
			values.add(obj);
		}
	}
	
	public void remove(Object obj){
		values.remove(obj);
	}
	
	public boolean contain(Object object){
		return values.contain(object);
	}
	
	public boolean include(Object object){//判断this集合是否包含集合object
		if (object instanceof MySet) {
			MySet set=(MySet) object;
			for(int i=0;i<set.getSize();i++){
				Object temp=set.values.get(i);
				if (!this.contain(temp)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean equals(Object object){
		if (object instanceof MySet) {
			MySet set=(MySet)object;
			if (this.include(set)&&set.include(this)) {
				return true;
			}
		}
		return false;
	}
	
	public int getSize(){
		return values.getSize();
	}
	
	public boolean isEmpty(){
		return values.getSize()>0;
	}
	
	public void print(){
		System.out.print("{");
		for(int i=0;i<this.getSize();i++){
			System.out.print(values.get(i)+" ");
		}
		System.out.println("}");
	}
	
	public static void main(String[] args) {
		MySet set=new MySet();MySet set2=new MySet();MySet set3=new MySet();
		set.add(new Integer(0));
		set.add(new Integer(2));
		set.add(new Integer(4));
		
		set2.add(new Integer(0));
		set2.add(new Integer(5));
		set2.add(new Integer(2));
		set2.add(new Integer(5));
		
		set3.add(new Integer(7));
		set3.remove(new Integer(4));
		set3.remove(new Integer(7));
		
		set.print();
		set2.print();
		set3.print();
		
		System.out.println(set.include(set2));
		System.out.println(set2.include(set));
		System.out.println(set.equals(set2));
	}

}
