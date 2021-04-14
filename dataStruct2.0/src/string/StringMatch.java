package string;

public class StringMatch {
	/**
	 * KMP算法模式匹配
	 * @param s 文本串
	 * @param p	模式串
	 * @return 模式串在文本串中的初始下标
	 */
	public static int index_KMP(String s,String p){
		//当next[j]=k时，模式串在j发生匹配错误，则文本串下标不变，模式串移动到下标k开始匹配。
		//next[0]=-1表示在模式串首个字符匹配错误时，文本串和模式串下标同时+1
		int[] next=getNext(p);
		int i=0,j=0;
		while(i<s.length()&&j<p.length()){
			if (j==-1||s.charAt(i)==p.charAt(j)) {
				i++;j++;
			}
			else {
				j=next[j];
			}
		}
		return j==p.length()?i-j:-1;
	}
	
	/**
	 * 获得模式串的next数组,由next[j]=k递推得到next[j+1],next[0]=-1。
	 * next[j]的值为模式串[0,j)真子串最大前缀和后缀公共元素的长度（小于j）
	 * @param p
	 * @return
	 */
	private static int[] getNext(String p){
		int[] next=new int[p.length()];
		int j=0,k=-1;
		next[j]=k;
		while(j+1<p.length()){
			if (k==-1||p.charAt(k)==p.charAt(j)) {
				next[++j]=++k;
			}
			else {
				k=next[k];
			}
		}
		return next;
	}
	
	public static int index_BF(String s,String p){
		int i=0,j=0;
		while(i<s.length()&&j<p.length()){
			if (s.charAt(i)==p.charAt(j)) {
				i++;j++;
			}
			else {
				i=i-j+1;j=0;
			}
		}
		return j==p.length()?i-j:-1;
	}
	public static void main(String[] args) {
		String s="sf33dskfsdx3fdfsdsggg";
		String p="sdsgg";
		System.out.println(StringMatch.index_KMP(s, p));
		System.out.println(StringMatch.index_BF(s, p));
	}
}
