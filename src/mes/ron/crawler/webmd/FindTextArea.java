package mes.ron.crawler.webmd;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FindTextArea 
{
	static String text=new String();
	public static String getText(Document doc) 
	{
		text="";
		Elements elem =doc.getElementsByAttributeValue("id", "textArea");
		if(elem==null)
		{
			elem =doc.getElementsByAttributeValue("class", "copyNormal");
		}
		for(Element elems : elem)
		{
			Elements el =elems.getAllElements();
			for(Element e:el)
				text+=e.ownText()+"\n-";
		}
//		System.out.println("--here text ->"+text+"\n---------end--------------\n");
		return text;
	}

}
