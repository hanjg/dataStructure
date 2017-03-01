package indexList;

import java.io.*;

public class KeyIndexList {//将书目文件转化为关键词索引表
	private SeqList listItems;//索引表用顺序表结构存储,存储的元素类型为ListItem
	private BufferedReader in;
	private String line;
	
	public KeyIndexList(){
		listItems=new SeqList();
	}
	
	public void establishList(String fileName)throws Exception{
		in=new BufferedReader(new FileReader(fileName));
		Book book=getBook();
		while(book.id!=null){
			insertBook(book);
			book=getBook();
		}
	}
	
	private Book getBook() throws Exception{
		line=in.readLine();
		//System.out.println(line);
		while(line!=null){
			return new Book(line.substring(4).toLowerCase(), line.substring(0, 3));
		}
		return new Book(null,null);
	}
	
	public void insertBook(Book book) throws Exception{
		int flag;//flag==0表示book的信息还未插入索引表，flag==1表示已经插入
		for(int i=0;i<book.keys.getSize();i++){
			flag=0;
			String bookKey=(String)book.keys.getData(i);//book中下标为i的关键词
			for(int j=0;flag==0&&j<listItems.getSize();j++){
				LinList ids=((ListItem)listItems.getData(j)).ids;//索引表中下标为j的索引的书号链表
				String key=((ListItem)listItems.getData(j)).key;//索引表中下标为j的索引的关键词
				if (bookKey.equalsIgnoreCase(key)) {
					ids.insert(ids.getSize(), book.id);
					flag=1;
				}
			}
			if (flag==0) {
				LinList linList=new LinList();
				linList.insert(0, book.id);
				ListItem item=new ListItem(bookKey, linList);
				listItems.KeyOrderInsert(item);
			}
		}
	}
	
	public void printIndexList(String fileName) throws Exception{
		BufferedWriter out=new BufferedWriter(new FileWriter(fileName));
		for(int i=0;i<listItems.getSize();i++){
			ListItem item=(ListItem)listItems.getData(i);
			out.write(item.key+":"+'\t');
			for(int j=0;j<item.ids.getSize();j++){
				out.write((String)item.ids.getData(j)+" ");
			}
			out.newLine();
		}
		out.close();
	}
	
	public static void main(String[] args) {
		try {
			KeyIndexList keyIndexList=new KeyIndexList();
			keyIndexList.establishList("bookList.txt");
			keyIndexList.printIndexList("keyIndexList.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class ListItem{//单行索引表的元素
	String key;//存储关键词
	LinList ids;//存储关键词对应的所有书号
	
	public ListItem(String key,LinList ids){
		this.key=key;
		this.ids=ids;
	}
}

class Book{
	String name;//存放书名
	String id;//书号
	SeqList keys;//存放书名中的关键词
	
	public Book(String name,String id) throws Exception{
		this.name=name;
		this.id=id;
		if (id!=null) {
			keys=new SeqList();
			String[] temp=this.name.split(" ");
			for(int i=0;i<temp.length;i++){
				if (!(temp[i].equalsIgnoreCase("the")||temp[i].equalsIgnoreCase("and")
						||temp[i].equalsIgnoreCase("of")||temp[i].equalsIgnoreCase("to"))) {
					keys.insert(keys.getSize(), temp[i]);
				}
			}
		}
	}
	
}
