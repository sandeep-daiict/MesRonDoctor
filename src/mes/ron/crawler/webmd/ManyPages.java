package mes.ron.crawler.webmd;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class ManyPages 
{	
	static String symptoms=new String();
	static String preSymptoms=new String();
	static String currSymptoms=new String();
	public static String  getsymptoms(String url) throws IOException 
	{
		Connection con = Jsoup.connect(url);
		Document doc = con.get();
		Elements page = doc.getElementsByAttributeValue("class", "right_fmt");
		preSymptoms="";
		if(page!=null&&page.text().length()!=0)
		{
			int k=2;			
			while(true)
			{
				String link = url;
				try
				{
					link=link+"?page="+k;
				//	System.out.println("MANYPAGE link:"+link);
					k++;
					Connection con1 = Jsoup.connect(link);
					Document newdoc = con1.get();
					currSymptoms=FindTextArea.getText(newdoc);
					if(!preSymptoms.equals(currSymptoms))
					{
					//	System.out.println("---here symptoms ->"+currSymptoms);
						symptoms+=currSymptoms+"\n";
						preSymptoms=currSymptoms;
						symptoms+=hyperlink.getsymptom(newdoc)+"\n";
					}
					else
					{
						break;
					}
					if (k==20)
						break;
				}
				catch( Exception ex)
				{
					ex.printStackTrace();
					break;
				}
			}
			
		}
		else
		{
			symptoms="";
		}
		return symptoms;
	}

}
