package search.hashTable;

public class TestHashTable {
	public static void main(String[] args) {
		String[] data={"oath","pea","eat","rain"};
		HashTable table=new HashTable2(7);
		for(String string:data){
			table.insert(string);
		}
		System.out.println("contain pea:"+table.contain("pea"));
		System.out.println("contain tea:"+table.contain("tea"));
		System.out.println("size:"+table.size());
		
		System.out.println("insert abc:");
		table.insert("abc");
		System.out.println("size:"+table.size());
		
		System.out.println("insert sdf:");
		table.insert("sdf");
		System.out.println("size:"+table.size());

		System.out.println("contain pea:"+table.contain("pea"));
		System.out.println("contain tea:"+table.contain("tea"));
		System.out.println("size:"+table.size());
		
		System.out.println("delete pea:");
		table.delete("pea");
		System.out.println("contain pea:"+table.contain("pea"));
		System.out.println("size:"+table.size());
	}
}
