package mes.ron.crawler.myoclinic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class CreateSymptomIndex 
{
	public static void main(String[] args) 
	{
		String basedir = "/home/kabeer/sandeep/webmining/majorproject/dataset/mayoclinic2";
		String indexdir = "/home/kabeer/sandeep/webmining/majorproject/dataset/index/index_symptom";
		BufferedWriter bwindex;
		try {
			bwindex = new BufferedWriter(new FileWriter(indexdir));
			for(int i=0;i<26;i++)
			{
				String dir = basedir+"/"+(char)(i+97);
				File f = new File(dir);
				
					System.out.println("char: "+(char)(i+97));
					for(File x : f.listFiles())
					{
						
							String symptomdir = dir+"/"+x.getName()+"/causes";
							File symptom = new File(symptomdir);
							System.out.println("diseasename: "+x.getName());
							String symptomname = x.getName().trim().toLowerCase();
							try
							{
								BufferedReader br = new BufferedReader(new FileReader(symptom));
								String line = new String();
								HashSet<String> sym = new HashSet<String>();
								while((line=br.readLine())!=null)
								{
									
									if(!line.isEmpty())
									{
										//System.out.println("Symptom="+line);
										sym.add(line.toLowerCase().trim());
									}
								}
								if(sym.size()==0)
									continue;
								Iterator<String> it = sym.iterator();
								bwindex.write(symptomname+":");
								
								System.out.print(symptomname+":");
								while(it.hasNext())
								{
									String symp = it.next();
									bwindex.write(symp+"#");
									System.out.print(symp+"#");
								}
								bwindex.write("\n");
								System.out.print("\n");
								bwindex.flush();
							}
							catch (FileNotFoundException e1)
							{
								
							}
							catch(IOException e)
							{
								
							}
							
					}
				}
				 	
				
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		
	}

}
