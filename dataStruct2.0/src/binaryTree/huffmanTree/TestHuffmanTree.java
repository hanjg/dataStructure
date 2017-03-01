package binaryTree.huffmanTree;

import binaryTree.huffmanTree.HuffmanTree.Code;

public class TestHuffmanTree {
	public static void main(String[] args) {
		int[] weight={5,29,7,8,14,23,3,11};
		HuffmanTree huffmanTree=new HuffmanTree(weight.length);
		huffmanTree.init(weight);
		Code[] codes=huffmanTree.getCodes();
		for(int i=0;i<weight.length;i++){
			System.out.println(i+": weight="+weight[i]+"\t"+codes[i].getCode());
		}
	}
}
