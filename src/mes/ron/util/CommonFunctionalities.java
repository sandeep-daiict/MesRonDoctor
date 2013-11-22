package mes.ron.util;

public class CommonFunctionalities 
{

	public static String stemTheWord(Stemmer stemmer, String word)
	{
		if(word != null)
		{
			stemmer.add(word.toCharArray(),word.length());
			stemmer.stem();
			word = stemmer.toString();	
		}
		return word;
	}

}
