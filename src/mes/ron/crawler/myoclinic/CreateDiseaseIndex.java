package mes.ron.crawler.myoclinic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import mes.ron.util.CommonFunctionalities;
import mes.ron.util.Stemmer;
import mes.ron.util.StopWords;
import mes.ron.util.ValueComparator;
public class CreateDiseaseIndex 
{
	Stemmer stemmer = null; 
	StopWords stopWords = null; 
	HashSet<String> userRequestedSymptoms = null; 
	public CreateDiseaseIndex() 
	{ 
		stemmer = new Stemmer(); 
		stopWords = new StopWords(stemmer); 
		userRequestedSymptoms = new HashSet<String>(); 
	}
	private boolean isAlreadyAsked(String symptom)
	{ //checks whether it is present in stop words and if not it checks whether we have already asked this similar symptom
		boolean asked = false;
		if(!stopWords.search(symptom))
		{
			String stemmedString = CommonFunctionalities.stemTheWord(stemmer, symptom);
			if(userRequestedSymptoms.contains(stemmedString))
			{
				asked = true;
			}	
		}else{
			asked = true;
		}
		 
		
		return asked;
	}
	
	private  void start(){
		String index = "/index/index";
		String indexsymptom = "/index/index_symptom";
		//		String indexdisease = "/home/kabeer/sandeep/webmining/majorproject/dataset/index/indexdisease";
		//		String reversesymptom = "/home/kabeer/sandeep/webmining/majorproject/dataset/index/index_symptom_reverse";
		String stopwords= "/home/kabeer/sandeep/webmining/majorproject/dataset/index/stopwords";

		TreeMap<String, HashSet<String>> dis = new TreeMap<String, HashSet<String>>();
		TreeMap<String, HashSet<String>> symmap = new TreeMap<String, HashSet<String>>();
		TreeMap<String, HashSet<String>> symmap2 = new TreeMap<String, HashSet<String>>();
		try 
		{
			BufferedReader disease = new BufferedReader(new FileReader(new File(index)));
			BufferedReader symptom = new BufferedReader(new FileReader(new File(indexsymptom)));
			//BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File(indexdisease)));
			//BufferedWriter bw2= new BufferedWriter(new FileWriter(new File(reversesymptom)));
			String line = new String(); 
			while((line=disease.readLine())!=null)
			{
				String[] tokens = line.split(":");
				String diseasename = new String();
				String symptoms = new String();
				if(tokens.length>0)
				{
					diseasename = tokens[0].toLowerCase().trim().replace("_", " "); 
				}
				HashSet<String> sym = new HashSet<String>();
				if(tokens.length>2)
				{
					symptoms = tokens[2];
					String[] tok = symptoms.split("#");
					for(int k=0;k<tok.length;k++)
					{
						String temp = tok[k].toLowerCase().trim();
						if(!temp.isEmpty())
							sym.add(temp);
					}
					if(dis.containsKey(diseasename))
					{
						HashSet s = dis.get(diseasename);
						s.addAll(sym);
						dis.put(diseasename, s);
					}
					else
					{
						dis.put(diseasename, sym);
					}
				}
			}
			while((line=symptom.readLine())!=null)
			{
				String[] tokens = line.split(":");
				String symptomname = new String();
				String diseases = new String();
				if(tokens.length>0)
				{
					symptomname = tokens[0].toLowerCase().trim().replace("_", " "); 
				}
				HashSet<String> disset = new HashSet<String>();
				if(tokens.length>1)
				{
					diseases = tokens[1];
					String[] tok = diseases.split("#");
					for(int k=0;k<tok.length;k++)
					{
						String temp = tok[k].toLowerCase().trim();
						if(!temp.isEmpty())
							disset.add(temp);
					}
					if(symmap2.containsKey(symptomname))
					{
						HashSet s = symmap2.get(symptomname);

						s.addAll(disset);
						symmap2.put(symptomname, s);
					}
					else
					{
						symmap2.put(symptomname, disset);
					}
				}
			}
			Iterator it = dis.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String, HashSet<String>> pairs = (Map.Entry)it.next();
				String dname = pairs.getKey();
				HashSet<String> sym = pairs.getValue();
				Iterator itr = sym.iterator();
				while(itr.hasNext())
				{
					String sname = (String) itr.next();
					if(symmap.containsKey(sname))
					{
						HashSet<String> s = symmap.get(sname);
						s.add(dname);
						symmap.put(sname, s);
					}
					else
					{
						HashSet<String> s = new HashSet<String>();
						s.add(dname);
						symmap.put(sname, s);
					}
				}
			}
			it = dis.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String, HashSet<String>> pairs = (Map.Entry)it.next();
				String dname = pairs.getKey();
				HashSet<String> sym = pairs.getValue();
				Iterator itr = sym.iterator();
				System.out.print(dname+":");
				while(itr.hasNext())
				{
					String sname = (String) itr.next();
					System.out.print(sname+",");
				}
				System.out.println("\n");
			}
			System.out.println("symptomssss\n\n");
			Iterator<Entry<String, HashSet<String>>> itr = symmap.entrySet().iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itr.next();
				String sname = pairs.getKey();
				HashSet<String> sym = pairs.getValue();
				Iterator itr1 = sym.iterator();
				System.out.print(sname+":");
				while(itr1.hasNext())
				{
					String dname = (String) itr1.next();
					System.out.print(dname+",");
				}
				System.out.println("\n");
			}
			itr = symmap2.entrySet().iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itr.next();
				String sname = pairs.getKey();
				HashSet<String> sym = pairs.getValue();
				Iterator itr1 = sym.iterator();
				System.out.print(sname+":");
				while(itr1.hasNext())
				{
					String dname = (String) itr1.next();
					System.out.print(dname+",");
				}
				System.out.println("\n");
			}
			Scanner s = new Scanner(System.in);
			System.out.println("Hi..");
			String symptomtocheck = new String();
			String query = new String();
			query = s.next();
			HashMap <String , HashSet<String>> diseaselocal= new HashMap<String, HashSet<String>>();
			HashMap <String , HashSet<String>> symptomslocal= new HashMap<String, HashSet<String>>();
			diseaselocal.putAll(dis);
			symptomslocal.putAll(symmap);
			symptomslocal.putAll(symmap2);
			boolean first = true;
			boolean answer = false;
			while(!query.equals("exit"))
			{
				if(first)
				{
					System.out.println("Enter Symptom: " );					
					query = s.next().toLowerCase().trim();
					String stemmedquery = CommonFunctionalities.stemTheWord(stemmer, query);					
					if(symptomslocal.containsKey(query))
					{
						if(isAlreadyAsked(query))
						{
							System.out.println("Symptom not matched");
							continue;
						}
						
						HashSet<String> temp = symptomslocal.get(query);
						Iterator itrdiseaselocal = diseaselocal.entrySet().iterator();
						int flag= 0;
						HashMap<String, HashSet<String>> tempdiseaselocal = new HashMap<String, HashSet<String>>();
						while(itrdiseaselocal.hasNext())
						{
							Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itrdiseaselocal.next();
							String dname = pairs.getKey();
							HashSet<String> sym = pairs.getValue();
							if(temp.contains(dname))
							{
								flag=1;
								sym.remove(query);
								tempdiseaselocal.put(dname,sym);

							}
							else
							{

							}	

						}
						diseaselocal.clear();
						diseaselocal.putAll(tempdiseaselocal);
						tempdiseaselocal.clear();
						//			if(diseaselocal.size()<5)
						if(diseaselocal.size()<5||flag==0)
						{
							itrdiseaselocal = diseaselocal.entrySet().iterator();
							while(itrdiseaselocal.hasNext())
							{
								Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itrdiseaselocal.next();
								String dname = pairs.getKey();
								System.out.println("Disease Name: " + dname);
							}
						}
						userRequestedSymptoms.add(stemmedquery);
						symptomtocheck=askSymptoms(diseaselocal);
						while(isAlreadyAsked(symptomtocheck))
						{
							symptomtocheck=askSymptoms(diseaselocal);
							
						}
						stemmedquery=CommonFunctionalities.stemTheWord(stemmer, symptomtocheck);
						userRequestedSymptoms.add(stemmedquery);
						first=false;
						System.out.println("Symptom to check : "+symptomtocheck);
						continue;
					}
					else
					{
						System.out.println("Symptom not matched ");
					}
				}
				else
				{
					System.out.println("Do You have: " + symptomtocheck);
					query = s.next();
					if(query.toLowerCase().trim().equals("yes"))
					{
						System.out.println("In yes");
						answer=true;
					}
					else
					{
						answer=false;
					}
					HashSet<String> temp = symptomslocal.get(symptomtocheck);
					Iterator itrdiseaselocal = diseaselocal.entrySet().iterator();
					HashMap<String, HashSet<String>> tempdiseaselocal = new HashMap<String, HashSet<String>>();
					int flag= 0;
					while(itrdiseaselocal.hasNext())
					{
						Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itrdiseaselocal.next();
						String dname = pairs.getKey();
						HashSet<String> sym = pairs.getValue();
						if(answer)
						{
							if(sym.contains(symptomtocheck))
							{

								flag=1;
								sym.remove(symptomtocheck);
								tempdiseaselocal.put(dname,sym);

							}
						}
						else
						{
							if(!sym.contains(symptomtocheck))
							{
								flag=1;
								sym.remove(symptomtocheck);
								tempdiseaselocal.put(dname,sym);
							}
						}


					}
					diseaselocal.clear();
					diseaselocal.putAll(tempdiseaselocal);
					tempdiseaselocal.clear();
					//			if(diseaselocal.size()<5)
					if(diseaselocal.size()<5||flag==0)
					{
						itrdiseaselocal = diseaselocal.entrySet().iterator();
						while(itrdiseaselocal.hasNext())
						{
							Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itrdiseaselocal.next();
							String dname = pairs.getKey();
							System.out.println("Disease Name: " + dname);
						}
					}
					String stemmedquery = new String();
					symptomtocheck=askSymptoms(diseaselocal);
					while(isAlreadyAsked(symptomtocheck))
					{
						symptomtocheck=askSymptoms(diseaselocal);
						
					}
					stemmedquery=CommonFunctionalities.stemTheWord(stemmer, symptomtocheck);
					userRequestedSymptoms.add(stemmedquery);
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) 
	{
		new CreateDiseaseIndex().start();
	}

	private static String askSymptoms(HashMap<String, HashSet<String>> diseaselocal) 
	{
		int length = diseaselocal.size();
		int threshold = length/2;
		System.out.println("Candidate disease size in intersection: "+length);
		TreeMap<String, Integer> queries= intersection(diseaselocal);
		System.out.println("size of queries: "+queries.size());
		String keyprev = new String();
		int valueprev=0;
		String key = new String();
		int value = 0;
		int i=0;
		Iterator itqueries = queries.entrySet().iterator();
		while(itqueries.hasNext()) 
		{
			i++;
			Map.Entry<String, Integer> pairs = (Map.Entry)itqueries.next();
			key = pairs.getKey();
			value = pairs.getValue();
			if(value>threshold)
				break;
			keyprev=key;
			valueprev=value;
		}
		if(value==0)
		{
			return key;
		}
		else if(i==queries.size())
		{
			return key;
		}
		else
		{
			return (value-threshold>threshold-valueprev)?keyprev:key;
		}
	}

	private static TreeMap<String, Integer> intersection(HashMap<String, HashSet<String>> diseaselocal) 
	{


		TreeMap<String,Integer> queries = new TreeMap<String, Integer>();
		ValueComparator vc  =  new ValueComparator(queries,1);
		TreeMap<String,Integer> sortedmap = new TreeMap<String,Integer>(vc);
		Iterator itrdisease =diseaselocal.entrySet().iterator();
		while(itrdisease.hasNext())
		{
			Map.Entry<String, HashSet<String>> pairs = (Map.Entry)itrdisease.next();
			String dis = pairs.getKey();
			HashSet<String> sym = pairs.getValue();
			Iterator<String> it = sym.iterator();
			while(it.hasNext())
			{
				String k =it.next();
				if(queries.containsKey(k))
				{
					int count = queries.get(k);
					queries.put(k, count+1);

				}
				else 
				{
					queries.put(k, 1);
				}
			}

		}
		sortedmap.putAll(queries);
		return sortedmap;
	}
}
