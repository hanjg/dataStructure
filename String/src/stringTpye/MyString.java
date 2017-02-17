package stringTpye;

public class MyString {
	private char[] value;
	private int count;
	
	private static void arrayCopy(char[] src,int srcPos,char[] dst,int dstPos,int length){
		if (length>src.length-srcPos||length>dst.length-dstPos) {
			throw new StringIndexOutOfBoundsException(length);
		}
		for(int i=0;i<length;i++){
			dst[dstPos++]=src[srcPos++];
		}
	}
	
	public MyString() {
		value=new char[0];
		count=0;
	}
	
	public MyString(char[] value,int offset,int count){//offset为数组起始下标count:[0,value.length-offset]
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
	
	public MyString(char[] value){
		this.count=value.length;
		this.value=new char[count];
		arrayCopy(value, 0, this.value, 0, count);
	}
	
	public MyString(String str){
		char[] chararray=str.toCharArray();
		value=chararray;
		count=value.length;
	}
	
	public char[] toArray(){//返回一个新的char数组来存储value的值
		char[] buf=new char[count];
		arrayCopy(value, 0, buf, 0, count);
		return buf;
	}

	public char charAt(int index){
		if (index<0||index>=count) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return value[index];
	}
	
	public int length(){
		return count;
	}
	
	public void print(){
		for(int i=0;i<value.length;i++){
			System.out.print(value[i]);
		}
		System.out.println();
	}

	public int compareTo(MyString myStringB){//返回串值的差或者串长度的差
		int lim=Math.min(this.count, myStringB.count);
		for(int i=0;i<lim;i++){
			char c1=this.value[i];
			char c2=myStringB.value[i];
			if (c1!=c2) {
				return c1-c2;
			}
		}
		return this.count-myStringB.count;
	}
	
	public MyString substring(int beginIndex,int endIndex){//取子串[beginIndex,endIndex)
															//长度为endIndex-beginIndex
		if (beginIndex<0) {
			throw new StringIndexOutOfBoundsException(beginIndex);	
		}
		if (endIndex>count) {//end-begin=sub.length,end<=count
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (beginIndex>endIndex) {
			throw new StringIndexOutOfBoundsException(endIndex-beginIndex);
		}
		return ((beginIndex==0)&&(endIndex==count)?
				this:new MyString(value, beginIndex, endIndex-beginIndex));
		//this pointer：如果子串的下标是主串的开始和结尾，则返回主串的地址，
		//如果是主串的一部分，则返回一个新的字符串
	}
	
	public MyString substring(int beginIndex){
		return substring(beginIndex, count);
	}
	
	public MyString contact(MyString stringB){
		if (stringB.count==0) {
			return this;
			//this pointer:如果stringB长度为0，则返回第一个字符串的地址，否则生成一个连接两个字符串的新子串
		}
		char buf[]=new char[count+stringB.count];
		arrayCopy(value, 0, buf, 0, count);
		arrayCopy(stringB.value, 0, buf, count, stringB.count);
		return new MyString(buf);
	}
	
	public MyString insert(MyString str,int pos){
		if (pos<0||pos>count) {
			throw new StringIndexOutOfBoundsException(pos);
		}
		if (pos!=0) {
			MyString stringfront=this.substring(0, pos);
			MyString stringback=this.substring(pos,count);
			/*MyString string=stringfront.contact(str);
			MyString string2=string.contact(stringback);*/
			MyString string2=stringfront.contact(str).contact(stringback);
			return string2;
		}
		else {
			return str.contact(this);
		}
	}
	
	public MyString delete(int beginIndex,int endIndex){
		if (beginIndex<0||endIndex>count||beginIndex>endIndex) {
			throw new StringIndexOutOfBoundsException();
		}
		if (beginIndex==0&&endIndex==count) {
			return new MyString();
		}
		else {
			MyString string1=this.substring(0, beginIndex);
			MyString string2=this.substring(endIndex, count);
			return string1.contact(string2);
		}
	}
	
	public int indexOf_BF(MyString subStr,int start){
		int i=start;
		int j=0;
		int v;//模式串的第一个字符在主串中的下标
		while(i<this.length()&&j<subStr.length()){
			if (this.charAt(i)==subStr.charAt(j)) {
				i++;j++;
			}
			else {
				i=i-j+1;
				j=0;
			}
		}
		if (j==subStr.length()) {
			v=i-j;
		}
		else {
			v=-1;
		}
		return v;
	}
	
	public int indexOf_KMP(MyString subStr,int start){
		int[] next=getNext(subStr);
		int i=start;
		int j=0;
		int v;
		while(i<this.length()&&j<subStr.length()){
			if (this.charAt(i)==subStr.charAt(j)) {
				i++;j++;
			}
			else if (j==0) {
				i++;j++;
			}
			else {
				j=next[j];
			}
		}
		if (j==subStr.length()) {
			v=i-j;
		}
		else {
			v=-1;
		}
		return v;
	}
			
	private int[] getNext(MyString str){//KMP算法中得到next数组
		//next[j]=k表示子串[0，j-1]中相互重叠的最大真子串的长度为k
		int[] next=new int[str.length()];
		next[0]=-1;//next[j]=-1时，令主串和模式串的下标同事增加1
		next[1]=0;
		int j=1,k=0;
		while(j<str.length()-1){
			if (str.charAt(j)==str.charAt(k)) {
				next[j+1]=k+1;
				j++;
				k++;
			}
			else if (k==0) {//当k==0时，不会存在相互重叠的子串
				next[j+1]=0;
				j++;
			}
			else {
				k=next[k];//k!=0且str.charAt(j)!=str.charAt(k)
			}
		}
		return next;
	}

	public static void main(String[] args) {
		MyString string=new MyString("abcabcaaa");
		MyString string2=new MyString("jjj");
		string.substring(1, 3).print();
		string.contact(string2).print();
		string.insert(string2, 2).print();
		string.delete(1, 3).print();
		System.out.println(string.compareTo(string2));
		MyString subStr=new MyString("bcaa");
		System.out.println(string.indexOf_BF(subStr, 0));
		System.out.println(string.indexOf_KMP(subStr, 0));
	}

}
