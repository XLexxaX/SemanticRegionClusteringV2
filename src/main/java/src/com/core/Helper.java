package src.com.core;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper {

	
	public static HashMap<String, Integer> getAllInstitutionClasses(ArrayList<Instance> instances) {
		
		
		HashMap<String, Integer> allInstitutionClasses = new HashMap<String, Integer>();
		for (Instance instance : instances) {
			for (Category category : instance.getInstitutionClasses()) {
				allInstitutionClasses.put(category.name, 0);
			}
		}
		return allInstitutionClasses;
		
	}
	
	public static void normalizeGeoCoordinates(ArrayList<Instance> instances) {
		
		double minLat = Double.MAX_VALUE;
		double maxLat = 0;
		double minLong = Double.MAX_VALUE;
		double maxLong = 0;
		for (Instance instance : instances) {
			if (instance.getLatitude() < minLat)
				minLat = instance.getLatitude();
			if (instance.getLatitude() > maxLat)
				maxLat = instance.getLatitude();
			if (instance.getLongitude() < minLong)
				minLong = instance.getLongitude();
			if (instance.getLongitude() > maxLong)
				maxLong = instance.getLongitude();
		}
		
		for (Instance instance : instances) {
			   instance.setLatitude((instance.getLatitude() - minLat) / (maxLat - minLat));
			   instance.setLongitude((instance.getLongitude() - minLong) / (maxLong - minLong));
		}
		
		
	}
	
}
