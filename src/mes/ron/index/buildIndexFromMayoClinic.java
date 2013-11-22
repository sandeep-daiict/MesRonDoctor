package mes.ron.index;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import mes.ron.metamap.Tagger;

public class buildIndexFromMayoClinic 
{
	
	public static void main(String[] args) 
	{
		MetaMapApi api = new MetaMapApiImpl();
		ArrayList<String> theOptions = new ArrayList<String>();
	    theOptions.add("-y"); 
	    if (theOptions.size() > 0) 
	    {
	      api.setOptions(theOptions);
	    }
		String indexdir = "/home/kabeer/sandeep/webmining/majorproject/index/mayoclinic";
		String basedir = "/home/kabeer/sandeep/webmining/majorproject/dataset/mayoclinic";
		for(int i=22;i<26;i++)
		{
			String dir = basedir+"/"+(char)(i+97);
			File f = new File(dir);
			
			for(File x : f.listFiles())
			{
				String diseasename = x.getName();
				String diseasedir = dir+"/"+x.getName()+"/symptoms";				
				try
				{
					BufferedReader br = new BufferedReader(new FileReader(new File(diseasedir)));
					String text = new String();
					String Line = new String();
					while((Line=br.readLine())!=null)
					{
						text += Line+"\n";
					}
					Tagger.symptomsChecker(text,api);
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

}
