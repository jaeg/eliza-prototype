package Eliza.ai;

public class Pair
{
	private String words[];
	
	public Pair(String word1, String word2)
	{
		words = new String[2];
		words[0] = word1;
		words[1] = word2;
	}
	
	public String swap(String word)
	{
		if (words[0].equals(word))
		{
			return words[1];
		}
		return null;
	}
}
