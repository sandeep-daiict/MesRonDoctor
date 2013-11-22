package mes.ron.crawler.webmd;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DropDown 
{
	static String symptoms=new String();
	public static String getSymptoms (Document doc) 
	{
		symptoms="";
		Elements dropdown = doc.getElementsByAttributeValue("class", "listBox_fmt");
		if(dropdown==null)
		{
			dropdown =doc.getElementsByAttributeValue("name", "guideNavDropdown");			
		}
		//System.out.println("dropdown text:"+dropdown.text());
		for(Element drop : dropdown)
		{
			
			Elements op = drop.getElementsByAttribute("value");
			//`int flag=0;
			for(Element o : op)
			{
				String text = o.text();
				//System.out.println("href text:"+text);
				if(text.toLowerCase().contains("symptom"))
				{
						System.out.println("Links:"+o.val());
						Connection con = Jsoup.connect(o.val());
						Document newdoc;
						try 
						{
							newdoc = con.get();
							symptoms+=FindTextArea.getText(newdoc)+"\n";
							symptoms+=hyperlink.getsymptom(newdoc)+"\n";
							symptoms+=ManyPages.getsymptoms(o.val())+"\n";
							return symptoms;
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}						
				}
			}
		}
		return symptoms;
	}

}
