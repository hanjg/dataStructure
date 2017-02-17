package digitalSearchTree;


class TrieNode {
    // Initialize your data structure here.
    char val;
    boolean isWord;
    TrieNode[] children=new TrieNode[26];
    
    public TrieNode(char val){
    	this.val=val;
    } 
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode current=root;
        for(int i=0;i<word.length();i++){
        	char c=word.charAt(i);
        	if (current.children[c-'a']==null) {
				current.children[c-'a']=new TrieNode(c);
			}
        	current=current.children[c-'a'];
        }
        current.isWord=true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode current=root;
        for(int i=0;i<word.length();i++){
        	char c=word.charAt(i);
        	if (current.children[c-'a']==null) {
				return false;
			}
        	current=current.children[c-'a'];
        }
        return current.isWord;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode current=root;
        for(int i=0;i<prefix.length();i++){
        	char c=prefix.charAt(i);
        	if (current.children[c-'a']==null) {
				return false;
			}
        	current=current.children[c-'a'];
        }
        return true;
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");
