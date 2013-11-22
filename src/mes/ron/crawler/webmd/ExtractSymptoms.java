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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractSymptoms 
{
	
	static String Symptoms = new String();
	public static void main(String[] args) 
	{
		
			System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
			System.getProperties().put("http.proxyPort", "8080");
		
		try 
		{
			for(int i=0;i<26;i++)
			{
				String dir ="/media/Programs/IIIT-H/web mining/projectdata/majorproject/healthdata/"+(char)(i+65);
				System.out.println(dir);
			    File file = new File(dir);
			    file.mkdir();
			    dir ="/media/Programs/IIIT-H/web mining/projectdata/majorproject/health_details/"+(char)(i+65);
			    File f2 = new  File(dir);
			    f2.mkdir();
				//File[] files = file.listFiles();
				//System.out.println(files[0]);
				//for(int j=0;j<files.length;j++)
			  
			    for(File files : file.listFiles())
				{
					System.out.println("file : "+files.getName());
					try 
					{
					//	if(!files.getName().contains("url"))
						//{
							Document doc = Jsoup.parse(new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/healthdata/"+(char)(i+65)+"/"+files.getName()), "UTF-8");
				//			System.out.println("DOC:"+doc.text());
							Symptoms+=FindTextArea.getText(doc)+"\n";
							Symptoms+=DropDown.getSymptoms(doc)+"\n";										
							BufferedReader brpage = new BufferedReader(new FileReader(new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/health_url/"+(char)(i+65)+"/"+files.getName()+"_urlfinal")));
							String link= brpage.readLine();
							Symptoms+= ManyPages.getsymptoms(link)+"\n";
					//		System.out.println("Symptoms i got : "+ Symptoms);
							BufferedWriter brw = new BufferedWriter(new FileWriter(new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/health_details/"+(char)(i+65)+"/"+files.getName()+"_details")));
							brw.write(Symptoms);
							brw.close();
						//}
					} 
					catch (IOException e) 
					{
						
						try 
						{
							BufferedWriter brwlog = new BufferedWriter(new FileWriter(new File("logsymptomcommon")));
							//brwlog.write("file:"+files[j].getName());
						} 
						catch (IOException e1) 
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
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
