package Eliza.ai;

import java.util.LinkedList;

public class WordSwapper
{
	private LinkedList<Pair> wordSet;
	
	public WordSwapper()
	{
		wordSet = new LinkedList<Pair>();
	}
	
	public String swap(String word)
	{
		System.out.println("TEST");
		for (Pair pair : wordSet)
		{
			String swappedWord = pair.swap(word);
			if (swappedWord!=null)
				return swappedWord;
		}
		
		return null;
	}
	
	public void add(String word1, String word2)
	{
		
		wordSet.add(new Pair(word1, word2));
	}
	

}
