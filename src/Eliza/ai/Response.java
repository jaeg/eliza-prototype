package Eliza.ai;

import java.util.LinkedList;
import java.util.Random;


public class Response
{
	LinkedList<String> keywords;
	LinkedList<String> messages;
	
	public Response(LinkedList<String> keywords, LinkedList<String> messages)
	{
		this.keywords = (LinkedList<String>)keywords.clone();
		this.messages = (LinkedList<String>)messages.clone();
		
		for (int i=0;i<this.keywords.size();i++)
		{
			System.out.println("Keyword: "+this.keywords.get(i));
		}
		for (int i=0;i<this.messages.size();i++)
		{
			System.out.println("Message: "+this.messages.get(i));
		}
		System.out.println("\n");
		
	}
	
	public int compareKeywords(LinkedList<String> otherKeywords)
	{
		int numberTheSame = 0;
		for(String currentKeyword: otherKeywords)
		{
			for (String keyword: keywords)
			{
				if (keyword.equals(currentKeyword))
				{
					numberTheSame++;
					break;
				}
			}
		}
		
		return numberTheSame;
	}
	
	public String getRandomMessage()
	{
		Random random = new Random();
		System.out.println(messages.size());

		return messages.get(random.nextInt(messages.size()));
	}
	
	public boolean contains(String keyword)
	{
		for (String word: keywords)
		{
			if (word.equals(keyword))
				return true;
		}
		return false;
	}

}
