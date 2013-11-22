package mes.ron.util;
import java.util.Comparator;
import java.util.Map;

	public class ValueComparator implements Comparator<String> 
	{
		// 1 for ascending
		// -1 for descending
	    Map<String, Integer> base;
	    int flag=0;
	    public ValueComparator(Map<String, Integer> base, int i) 
	    {
	        this.base = base;
	        this.flag=i;
	    }

	    public int compare(String a, String b) 
	    {
	        if (base.get(a) >= base.get(b)) 
	        {
	            return this.flag*1;
	        } else {
	            return -1*this.flag;
	        } // returning 0 would merge keys
	    }
	}