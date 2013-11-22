package mes.ron.crawler.myoclinic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractRelevantData 
{
	public static void main(String[] args) 
	{
		System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
		System.getProperties().put("http.proxyPort", "8080");
		String basedir = "/home/kabeer/sandeep/webmining/majorproject/dataset/mayoclinic";
		String array[] = new String[9];
		array[0]="description";
		array[1]="symptoms";
		array[2]="causes";
		array[3]="risk-factor";
		array[4]="complications";
		array[5]="tests-and-diagnosis";
		array[6]="treatments-and-drugs";
		array[7]="lifestyle-and-home-remedies";
		array[8]="prevention";
		for(int i=22;i<26;i++)
		{
			String dir = basedir+"/"+(char)(i+97);
			File f = new File(dir);
			
			for(File x : f.listFiles())
			{
				String diseasedir = dir+"/"+x.getName()+"/link";
				try
				{
					
					BufferedReader br = new BufferedReader(new FileReader( new File(diseasedir)));
					String link = "http://www.mayoclinic.com"+br.readLine()+"/";
					br.close();
					Document  doc = null;
					System.out.println("name: "+x.getName());
					BufferedWriter brlog = new BufferedWriter(new FileWriter(new File("log")));;
					for(int j=0;j<array.length;j++)
					{
						if(j==0)
						{
							try 
							{
								doc = Jsoup.connect(link).get();
								String text = extractdata(doc);
								File disease = new File(dir+"/"+x.getName()+"/"+array[j]);
								BufferedWriter bw = new BufferedWriter(new FileWriter(disease));
								System.out.println("type: "+array[j]);
								bw.write(text);
								bw.flush();
								bw.close();
							} 
							catch (IOException e) 
							{
								try 
								{
									brlog.write("Disease Name: " + x.getName());
									brlog.write("Type: "+array[j]);
									System.out.println("Erorrrrrrrrrrrrrr");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
							
							
						}
						else
						{
							try 
							{
								doc = Jsoup.connect(link+"DSECTION="+array[j]).get();
								String text = extractdata(doc);
								File disease = new File(dir+"/"+x.getName()+"/"+array[j]);
								BufferedWriter bw = new BufferedWriter(new FileWriter(disease));
								System.out.println("type: "+array[j]);
								//System.out.println("Text : " + text);
								bw.write(text);
								bw.flush();
								bw.close();
							} 
							catch (IOException e) 
							{
								try 
								{
									
									brlog.write("Disease Name: " + x.getName());
									brlog.write("Type: "+array[j]);
									System.out.println("Erorrrrrrrrrrrrrr");
								} 
								catch (IOException e1) 
								{
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
						}
						
					}
					
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

	private static String extractdata(Document doc)
	{
		String text = new String();
		Elements elem = doc.getElementsByAttributeValue("id", "b");
		for(Element el : elem)
		{
			Elements child = el.getAllElements();
			for(Element ch : child)
			{
				if(ch.className().equals("pagenav"))
					break;
				text+=ch.ownText()+"\n";
				
			}
		}
			
		return text;
	}

}
