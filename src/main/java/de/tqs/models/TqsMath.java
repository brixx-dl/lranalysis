package de.tqs.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class TqsMath {
	
	public static void getMean(TreeMap resultTM)
	{
		
		SortedMap<Integer, String> sortedMap = resultTM.subMap(2700,5400);
		System.out.println(sortedMap.values());
		
		Collection<String> values = sortedMap.values();
		ArrayList<String> val = new ArrayList<String>(values);
		
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
					
		for (int h = 0;h<val.size();h++){
		
			String test = val.get(h);
			double value = Double.parseDouble(test);
			stats.addValue(value);
			
		}
		System.out.println(stats.getMean());	
		
	}
	
}
