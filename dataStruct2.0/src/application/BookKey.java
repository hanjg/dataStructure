package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 将书目文件转化为关键词索引表(使用hashMap)
 * @author hjg
 *
 */
public class BookKey {
	/**
	 * 存放k=关键字,v=书目序号的列表
	 */
	private Map<String, List<Integer>> map;
	private String bookList="src/application/bookList.txt";
	private String indexTable="src/application/indexTable.txt";
	private Scanner in;
	private PrintWriter out;
	public BookKey() {
		map=new HashMap<>();
		try {
			in=new Scanner(new File(bookList));
			out=new PrintWriter(new File(indexTable));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void tranform(){
		while (in.hasNextLine()) {
			String line=in.nextLine();
			String[] tokens=line.split(" ");
			int index=Integer.parseInt(tokens[0]);
			for (int i=1;i<tokens.length;i++) {
				String key=tokens[i].toLowerCase();
				if (key.equals("the")||key.equals("and")||key.equals("to")||key.equals("for")
						||key.equals("of")) {
					continue;
				}
				if (map.containsKey(key)) {
					map.get(key).add(index);
				}else {
					List<Integer> temp=new ArrayList<>();
					temp.add(index);
					map.put(key, temp);
				}
			}
		}
		for (String key:map.keySet()) {
			List<Integer> list=map.get(key);
			out.println(key+":"+list.toString());
			System.out.println(key+":"+list.toString());
		}
		out.flush();
	}
	public static void main(String[] args) {
		BookKey bookKey=new BookKey();
		bookKey.tranform();
	}
}
