package search.dynamicTree;

import binaryTree.Tree;

/**
 * 多重链表表示的键树，存储单词信息
 * @author hjg
 *
 */
public class TrieTree implements Tree<Character>{
	private Node root;
	public TrieTree(){
		root=new Node(' ');
	}
	public boolean contain(String word){
		return contain(root, 0, word);
	}
	/**
	 * 从cur开始查找word[i]
	 * @param cur
	 * @param i
	 * @param word
	 * @return
	 */
	private boolean contain(Node cur, int i, String word){
		if (cur==null) {
			return false;
		}
		if (i==word.length()) {
			return cur.isEnd;
		}
		int index=word.charAt(i)-'a';
		return contain(cur.children[index], i+1, word);
	}
	public void insert(String word){
		insert(root, 0, word);
	}
	private void insert(Node cur, int i, String word){
		if (i==word.length()) {
			cur.isEnd=true;
			return;
		}
		int index=word.charAt(i)-'a';
		if (cur.children[index]==null) {
			cur.children[index]=new Node(word.charAt(i));
		}
		insert(cur.children[index], i+1, word);
	}
	public void delete(String word){
		delete(root, 0, word);
	}
	private void delete(Node cur, int i, String word){
		if (i==word.length()) {
			cur.isEnd=false;//懒惰删除
			return;
		}
		int index=word.charAt(i)-'a';
		if (cur.children[index]==null) {
			return;
		}else {
			delete(cur.children[index], i+1, word);
		}
	}
	public class Node{
		private boolean isEnd;
		private char val;
		private Node[] children;
		public Node(char val){
			this.val=val;
			children=new Node[26];
		}
		public void setVal(char val) {
			this.val = val;
		}
		public void setEnd(boolean isEnd) {
			this.isEnd = isEnd;
		}
		public void setChildren(Node[] children) {
			this.children = children;
		}
		public char getVal() {
			return val;
		}
		public Node[] getChildren() {
			return children;
		}
		public boolean isEnd(){
			return isEnd;
		}
	}
}
