package applyment;
import java.io.BufferedReader;
import java.io.FileReader;
//import java.util.StringTokenizer;

class InterSection{
	int left;
	int forward;
	int right;
	
	public InterSection(){
		
	}
	
	public InterSection(int left,int forward,int right){
		this.left=left;
		this.forward=forward;
		this.right=right;
	}
}

public class Maze {//迷宫求解
	private int maxSize;//除了出口之外的路口数，包括入口
	private InterSection[] interSections;
	private int exit;
	
	public Maze(String fileName) {//fileName为名字的文件存储了数字化的迷宫
		String line;
		try {
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			line=in.readLine();
			maxSize=Integer.parseInt(line.trim());
			interSections=new InterSection[maxSize+1];//index0不存储有效数据,[1,maxSize]存储
			interSections[0]=new InterSection(0, 0, 0);//interSections[0]表示死路
			for(int i=1;i<=maxSize;i++){
				line=in.readLine();
				InterSection interSection=new InterSection();
				/*StringTokenizer tokenizer=new StringTokenizer(line);
				interSection.left=Integer.parseInt(tokenizer.nextToken());
				interSection.forward=Integer.parseInt(tokenizer.nextToken());
				interSection.right=Integer.parseInt(tokenizer.nextToken());*/
				String[] temp=line.split(" ");
				interSection.left=Integer.parseInt(temp[0]);
				interSection.forward=Integer.parseInt(temp[1]);
				interSection.right=Integer.parseInt(temp[2]);
				interSections[i]=interSection;
			}
			exit=Integer.parseInt(in.readLine());
			in.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean traceInMave(int interSectionValue){
		if (interSectionValue==exit) {
				System.out.print(interSectionValue+"<-");
				return true;
			}
		//System.out.print(interSectionValue+":left-"+interSections[interSectionValue].left);
		//System.out.print(" forward-"+interSections[interSectionValue].forward);
		//System.out.println(" right"+interSections[interSectionValue].right);
		if (interSectionValue>0) {
			if (interSections[interSectionValue].left!=0&&
					traceInMave(interSections[interSectionValue].left)) {
				System.out.print(interSectionValue+"<-");
				return true;
			}
			else if (interSections[interSectionValue].forward!=0&&
					traceInMave(interSections[interSectionValue].forward)) {
				System.out.print(interSectionValue+"<-");
				return true;
			}
			else if (interSections[interSectionValue].right!=0&&
					traceInMave(interSections[interSectionValue].right)) {
				System.out.print(interSectionValue+"<-");
				return true;
			}
		}
		//System.out.println(interSectionValue+":false");
		return false;
		
	}
	public static void main(String[] args) {
		String fileName="Maze.txt";
		Maze maze=new Maze(fileName);
		int start=1;
		if (maze.traceInMave(start)) {
			System.out.println("trace is above");
		}
		else {
			System.out.println("no trace");
		}
	}

}
