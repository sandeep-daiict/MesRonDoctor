package mes.ron.metamap;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;


public class Tagger 
{
	
	public static void symptomsChecker(String text,MetaMapApi api) 
	{
		List<Result> resultList = api.processCitationsFromString(text);
		for(int i=0;i<resultList.size();i++)
		{
			Result result = resultList.get(i);			
			try {
				for (Utterance utterance: result.getUtteranceList()) 
				{			
					for (PCM pcm: utterance.getPCMList()) 
					{
						System.out.println("Phrase:");
						System.out.println(" text: " + pcm.getPhrase().getPhraseText());
						System.out.println("Candidates:");
				          for (Ev ev: pcm.getCandidateList()) 
				          {
				            System.out.println("  Candidate:");
				            System.out.println("  Score: " + ev.getScore());
				            System.out.println("  Concept Name: " + ev.getConceptName());
				            System.out.println("  Preferred Name: " + ev.getPreferredName());
				            System.out.println("  Matched Words: " + ev.getMatchedWords());
				            System.out.println("  Semantic Types: " + ev.getSemanticTypes());
				            System.out.println("  Sources: " + ev.getSources());
				            System.out.println("  Positional Info: " + ev.getPositionalInfo());
				          }
					}
				}
			} 
			catch (Exception e) 
			{
				
				e.printStackTrace();
			}
		}
		
		

	}

}
