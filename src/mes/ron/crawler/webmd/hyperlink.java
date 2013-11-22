package mes.ron.crawler.webmd;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class hyperlink 
{
	static String symptoms=new String();
	public static String getsymptom(Document doc) throws IOException 
	{
		//String text= FindTextArea.getText(doc);
		symptoms="";
		Elements e= doc.getElementsByAttributeValue("class", "chapterTitle_fmt");
		for (Element e1 :e)
		{
			//System.out.println("->"+e1.text()+"  "+e1.attr("a"));
			if(e1.text().toLowerCase().contains("symptom"))
			{
				Elements link = e1.getElementsByAttribute("onclick");
				for(Element e2 :link )
				{
					//System.out.println("--->"+e2.attr("href"));
					Connection con = Jsoup.connect(e2.attr("href"));
					Document newdoc = con.get();
					symptoms+=FindTextArea.getText(newdoc)+"\n";
					symptoms+=ManyPages.getsymptoms(e2.attr("href"))+"\n";
					
				}
			
			}
		}
		
		//System.out.println("----------------end------------------------");
		return symptoms;
	}

}
