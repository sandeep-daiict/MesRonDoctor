package mes.ron.crawler.myoclinic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractSymptoms 
{
	
	static String Symptoms = new String();
	public static void main(String[] args) 
	{
		
			System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
			System.getProperties().put("http.proxyPort", "8080");
			String basedir = "/home/kabeer/sandeep/webmining/majorproject/dataset/mayoclinic";
		
			for(int i=0;i<26;i++)
			
			{
				try 
				{
			    String url ="http://www.mayoclinic.com/health/DiseasesIndex/DiseasesIndex/METHOD=displayAlphaList&LISTTYPE=mcDisease&LETTER="+(char)(i+97);
			    File f2 = new  File(basedir+"/"+(char)(i+97));
			    f2.mkdir();
			    System.out.println((char)(i+97));
				Document doc = Jsoup.connect(url).get();
				File f = new File(basedir+"/"+(char)(i+97)+"/"+"page");
				BufferedWriter brw = new BufferedWriter(new FileWriter(f));
				
				brw.write(doc.html());
				brw.flush();
				brw.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		
	}
}
