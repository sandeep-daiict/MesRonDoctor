package mes.ron.crawler.myoclinic;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class webCrawler 
{
	public static void main(String[] args) 
	{
	//	if(args[1]!=null)
		//{
			System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
			System.getProperties().put("http.proxyPort", "8080");
		//}
		try 
		{
			File file = new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/datasetnew/default.htm");
			//BufferedWriter brw = new BufferedWriter(new FileWriter(file));
			Document doc = Jsoup.parse(file, "UTF-8");
			//System.out.println("doc:"+doc.text());
			Elements e =doc.getElementsByAttributeValue("id","a-z-alpha");
			Elements el = e.select("a");
			for(Element ele : el)
			{
				 File theDir = new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/commondata/"+ele.text());
				 theDir.mkdir();
				 theDir = new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/common_url/"+ele.text());
				 theDir.mkdir();
				 String Link=ele.attr("href");
				 Link="http://www.webmd.com/"+Link;
				 Connection con = Jsoup.connect(Link);
				 Document docs = con.get();
				// System.out.println("doc"+docs.text());
				 Elements disease=docs.getElementsByAttributeValue("class", "a-to-z list");
				 Elements dis = disease.select("a");
				 for(Element d : dis )
				 {
					 File theDir1 = new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/common_url/"+ele.text().split("/")[0]+"/"+d.text().split("/")[0]+"_urlfinal");
					 //theDir1.mkdir();
					 String Link1=d.attr("href");
					 Link1="http://www.webmd.com/"+Link1;
					 BufferedWriter brw = new BufferedWriter(new FileWriter(theDir1));
					 brw.write(Link1);
					 brw.flush();
					 brw.close();
					 Document docu = null;
					 File theDir2 = new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/commondata/"+ele.text().split("/")[0]+"/"+d.text().split("/")[0]);
					 brw = new BufferedWriter(new FileWriter(theDir2));
					 try
					 {
						 docu=Jsoup.connect(Link1).get();
						 brw.write(docu.html().toString());
						 brw.flush();
						 brw.close();
						 System.out.println("Link1:"+Link1+":alphabet:"+ele.text()+":Text:"+d.text());
					 }
					 catch(IOException e1)
					 {
						 BufferedWriter brwlog = new BufferedWriter(new FileWriter(new File("loghealth")));
						 brwlog.write("Link1:"+Link1+":alphabet:"+ele.text()+":Text:"+d.text());
					 }
					 
				 }
					 
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

}
