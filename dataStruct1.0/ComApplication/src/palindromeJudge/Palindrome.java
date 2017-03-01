package palindromeJudge;



public class Palindrome {
	public static boolean isPalindrome(String str){
		SeqQueue queue=new SeqQueue();
		SeqStack stack=new SeqStack();
		for(int i=0;i<str.length();i++){
			try {
				queue.append(str.substring(i, i+1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			stack.push(str.substring(i,i+1));
		}
		while(queue.notEmpty()&&stack.notEmpty()){
			try {
				if (!queue.delete().equals(stack.pop())) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	public static void main(String[] args) {
		String string="sDfDs";
		System.out.println(Palindrome.isPalindrome(string));
	}

}
