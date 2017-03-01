package stringTpye;

public class MyStringBuffer {
	private char[] value;
	private int count;
	
	static void arrayCopy(char[] src,int srcPos,char[] dst,int dstPos,int length){
		if (length>src.length-srcPos||length>dst.length-dstPos) {
			throw new StringIndexOutOfBoundsException(length);
		}
		for(int i=0;i<length;i++){
			dst[dstPos++]=src[srcPos++];
		}
	}
	
	public MyStringBuffer() {
		value=new char[0];
		count=0;
	}
	
	public MyStringBuffer(char[] value,int offset,int count){//count为长度
		if (offset<0) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (count<0) {
			throw new StringIndexOutOfBoundsException(count);
		}
		if (count>value.length-offset) {
			throw new StringIndexOutOfBoundsException(offset+count);
		}
		this.count=count;
		this.value=new char[count];
		arrayCopy(value, offset, this.value, 0, count);
		
	}
	
	public MyStringBuffer(char[] value){
		this.count=value.length;
		this.value=new char[count];
		arrayCopy(value, 0, this.value, 0, count);
	}
	
	public MyStringBuffer(String str){
		char[] chararray=str.toCharArray();
		value=chararray;
		count=value.length;
	}
	private void expandCapacity(int newCapacity){
		char newValue[] =new char[newCapacity];
		arrayCopy(value, 0, newValue, 0, count);
		value=newValue;
	}
	
	public int length(){
		return count;
	}
	
	public MyStringBuffer contact(MyStringBuffer str){
		if (str.length()==0) {
			return this;
		}
		expandCapacity(count+str.length());
		arrayCopy(str.toArray(), 0, this.toArray(), this.length(), str.length());
		count=count+str.length();
		return this;
	}
	
	public MyStringBuffer substring(int beginIndex,int endIndex){
		if (beginIndex<0) {
			throw new StringIndexOutOfBoundsException(beginIndex);	
		}
		if (endIndex>count) {//end-begin=sub.length,end<=count
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (beginIndex>endIndex) {
			throw new StringIndexOutOfBoundsException(endIndex-beginIndex);
		}
		return new MyStringBuffer(value, beginIndex, endIndex-beginIndex);//end-begin为长度
	}
	
	public MyStringBuffer substring(int beginIndex){
		return substring(beginIndex, count);
	}
	
	public MyStringBuffer insert(MyStringBuffer str,int pos){
		if (pos<0||pos>count) {
			throw new StringIndexOutOfBoundsException(pos);
		}
			MyStringBuffer stringfront=this.substring(0, pos);
			MyStringBuffer stringback=this.substring(pos,count);
			MyStringBuffer string=stringfront.contact(str);
			MyStringBuffer string2=string.contact(stringback);
			this.value=string2.value;
			this.count=string2.count;
			return this;
		
	}
	
	public MyStringBuffer delete(int beginIndex,int endIndex){
		if (beginIndex<0||endIndex>count||beginIndex>endIndex) {
			throw new StringIndexOutOfBoundsException();
		}
		if (beginIndex==0&&endIndex==count) {
			this.value=new char[0];
			this.count=0;
			return this;
		}
		else {
			MyStringBuffer string1=this.substring(0, beginIndex);
			MyStringBuffer string2=this.substring(endIndex, count);
			MyStringBuffer temp=string1.contact(string2);
			this.value=temp.value;
			this.count=temp.count;
			return this;
		}
	}
	
	public void myPrint(){
		for(int i=0;i<value.length;i++){
			System.out.print(value[i]);
		}
		System.out.println();
	}

	public char[] toArray(){
		return value;
	}
	
	public static void main(String[] args) {
		MyStringBuffer ms1=new MyStringBuffer("lihonglei");
		//MyStringBuffer ms2=new MyStringBuffer("zhangxuhui");
		ms1.delete(2,4);
		ms1.myPrint();
	}
}
