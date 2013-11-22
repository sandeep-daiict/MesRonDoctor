package mes.ron.index;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import mes.ron.metamap.metamap;


public class buildIndexFromWebmd 
{

	static HashMap< String, ArrayList<String>> diseaseToSymptom = new HashMap<String ,ArrayList<String>>();
	static HashMap< String, ArrayList<String>> symptomToDisease = new HashMap<String ,ArrayList<String>>();
	public static void main(String[] args) 
	{
		for(int i=0;i<26;i++)
		{
			String dir ="/media/Programs/IIIT-H/web mining/projectdata/majorproject/health_details/"+(char)(i+65);
			System.out.println(dir);
		    File file = new File(dir);
		    file.mkdir();	
		    for(File files : file.listFiles())
			{
				System.out.println("file : "+files.getName());
				if(metamap.isDisease(files.getName().split("_")[0]))
				{
					String disease = files.getName();
					ArrayList<String> symptoms =metamap.getSymptoms(files);
					
					dtos(disease,symptoms);
					stod(disease,symptoms);
				}		
			}
		}
		writeToFile();
	}
	static void dtos(String disease ,ArrayList symptoms)
	{
		diseaseToSymptom.put(disease, symptoms);
	}
	static void stod(String disease ,ArrayList symptoms)
	{
		Iterator<String> it = symptoms.iterator();
		while(it.hasNext())
		{
			if(symptomToDisease.containsKey(it))
			{
				ArrayList<String> temp = symptomToDisease.get(it);
				temp.add(disease);
				symptomToDisease.put(it.toString(), temp);
			}
			else
			{
				ArrayList<String> temp  = new ArrayList<String>();
				temp.add(disease);
				symptomToDisease.put(it.toString(), temp);
			}
		}
	}
	static void writeToFile()
	{
		StringBuilder outString = new StringBuilder();
		BufferedWriter bw;
		try 
		{
			bw = new  BufferedWriter(new FileWriter(new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/sympton_disease")));
			Iterator it =symptomToDisease.entrySet().iterator();
			
			while(it.hasNext())
			{
				Entry<String, ArrayList<String>> en = (Entry<String,  ArrayList<String>>) it.next();
				outString.append(en.getKey()+":");
				ArrayList<String > vals = en.getValue();
				Iterator it2= vals.iterator();
				while (it2.hasNext()) 
				{
					
					outString.append(it2.next()+",");	
				}
				outString.append("\n");
			}
			bw.write(outString.toString());
			bw = new  BufferedWriter(new FileWriter(new File("/media/Programs/IIIT-H/web mining/projectdata/majorproject/disease_symptom")));
			it =diseaseToSymptom.entrySet().iterator();
			outString.delete(0, outString.length());
			System.out.println("out String :"+outString);
			while(it.hasNext())
			{
				Entry<String, ArrayList<String>> en = (Entry<String,  ArrayList<String>>) it.next();
				outString.append(en.getKey()+":");
				ArrayList<String > vals = en.getValue();
				Iterator it2= vals.iterator();
				while (it2.hasNext()) 
				{
					
					outString.append(it2.next()+",");	
				}
				outString.append("\n");
			}
			bw.write(outString.toString());
		} 
		catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
}
