package Eliza.ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.google.common.collect.HashBiMap;

public class Eliza
{
	private HashBiMap<String, String> conjugations;
	
	LinkedList<Response> responses;
	Response lastResponse;
	LinkedList<String> keywordList;
	
	public String debugOutput ="";
	
	public Eliza()
	{
		conjugations = HashBiMap.create();
		conjugations.put("ARE","AM");
		conjugations.put("WERE","WAS");
		conjugations.put("YOU","ME");
		conjugations.put("YOUR","MY");
		conjugations.put("IVE","YOUVE");
		conjugations.put("IM","YOURE");
		
		
		responses = new LinkedList<Response>();
		keywordList = new LinkedList<String>();
		
		loadDatabase("responses");
		
		
	}
	
	public String response(String message)
	{
		message = " "+message.toUpperCase()+" ";
		LinkedList<String> keywords = getKeywords(message);
		System.out.println("Keyword chosen: "+keywords);
		String subject = message;

		Response response = getResponse(keywords,subject);
		
		for (String keyword: keywords)
		{
			if (response.contains(" "+keyword+" "))
			{
				subject = subject.replace(keyword, "");
			}
		}

		
		subject = replaceConjugates(subject);
	
		return response.getRandomMessage().replace("*", subject).trim();
	}
	
	public LinkedList<String> getKeywords(String message)
	{
		
		LinkedList<String> keywords = new LinkedList<String>();
		for (String keyword: keywordList)
		{
			if (message.contains(" "+keyword+" "))
			{
				keywords.add(keyword);
				System.out.println(keyword);
				//return returnKeyword;
			}
			
		}
		
		if (keywords.size()==0)
		{
			keywords.add("NOKEYFOUND");
		}
		return keywords;
	}
	
	public Response getResponse(LinkedList<String> keywords,String subject)
	{
		Response bestResponse = null;
		int maxSameKeywords=0;
		for (Response response: responses)
		{
			int numSame = response.compareKeywords(keywords);
			if (maxSameKeywords < numSame)
			{
				bestResponse = response;
				maxSameKeywords = numSame;
			}
			
			if (maxSameKeywords == numSame)
			{
				int firstMost = subject.length();
				String bestKeyword="";
				for (String word: keywords)
				{
					if (subject.indexOf(word)<firstMost && subject.indexOf(word)>-1)
					{
						firstMost = subject.indexOf(word);
						bestKeyword = word;
					}
					
					if (subject.indexOf(word) == firstMost)
					{
						if (bestKeyword.length()<word.length())
						{
							bestKeyword = word;
						}
					}
				}
				
				if (response.contains(bestKeyword))
				{
					bestResponse = response;
				}
			}
			
		}
		lastResponse = bestResponse;
		
		
		return bestResponse;
	}
	
	public String returnOppositeConjugate(String conjugate)
	{
		conjugate = conjugate.toUpperCase();
		
		if (!conjugations.containsKey(conjugate))
		{
			return conjugations.inverse().get(conjugate);
		}
		return conjugations.get(conjugate);
	}
			
	public String replaceConjugates(String message)
	{
		
		String messageChunks[] = message.split(" ");
		String finishedMessage ="";
		for (String messageChunk: messageChunks)
		{
			String opposite = returnOppositeConjugate(messageChunk);
			if (opposite!=null)
			{
				messageChunk = opposite;
			}	
			finishedMessage+=" "+messageChunk;
			System.out.println(messageChunk);
		}
		//System.out.println(finishedMessage);
		return finishedMessage;
	}
	
	public void loadDatabase(String path)
	{
		File responsesFile = new File(path);
		try
		{
			Scanner input = new Scanner(responsesFile);
			LinkedList<String> keywords = new LinkedList<String>();
			LinkedList<String> messages = new LinkedList<String>();
			boolean keywordOrMessage=false;
			while (input.hasNext())
			{
				String line = input.nextLine();
				if (line.equals("*END*"))
				{
					responses.add(new Response(keywords,messages));
					keywords.clear();
					messages.clear();
				}
				else if (line.equals("*Keyword*"))
				{
					keywordOrMessage=false;
				}
				else if (line.equals("*Message*"))
				{
					keywordOrMessage=true;
				}
				else if (!line.isEmpty())
				{
					if (keywordOrMessage==false)
					{
						keywords.add(line);
						keywordList.add(line);
					}
					else
					{
						messages.add(line);
					}
				}
			}
		} catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Error loading response data file");
			e.printStackTrace();
		}
	}
	

}
