package mes.ron.crawler.myoclinic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractSymptomToDisease2
{	
	public static void main(String[] args) 
	{
		System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
		System.getProperties().put("http.proxyPort", "8080");
		String basedir = "/home/kabeer/sandeep/webmining/majorproject/dataset/mayoclinic2";
		for(int i=0;i<26;i++)
		{
			System.out.println("In: "+(char)(i+97));
			String dir =basedir+"/"+(char)(i+97);
			Document doc;
			try 
			{
				doc = Jsoup.parse(new File(dir+"/page"), "UTF-8");
				Elements el = doc.getElementsByAttributeValue("class", "list");
					
				for(Element e : el)
				{
					Elements child = e.getAllElements();
					for(Element ch : child)
					{
						 
						String diseasename = ch.text().replace(" ", "_");
						diseasename = diseasename.replace("/", "_");
						String link = ch.attr("href");
						
					    if(link.isEmpty()||diseasename.isEmpty())
					    	continue;
					    File f2 = new  File(dir+"/"+diseasename);
					    f2.mkdir();
					    File f = new File(dir+"/"+diseasename+"/"+"link");
						BufferedWriter brw = new BufferedWriter(new FileWriter(f));
						//System.out.println("Diseasename: " + diseasename);
						brw.write(link);
						brw.flush();
						brw.close();						
					}
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
	}
}
