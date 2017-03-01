package search.dynamicTree;

public class TestTrieTree {
	public static void main(String[] args) {
		TrieTree tree=new TrieTree();
		String[] words={"oath","pea","eat","rain"};
		for(String word: words){
			tree.insert(word);
		}
		System.out.println("contain pea:"+tree.contain("pea"));
		System.out.println("contain eta:"+tree.contain("eta"));
		tree.delete("pea");
		System.out.println("contain pea:"+tree.contain("pea"));
	}
}
