package src.com.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Instance {
	
	private String id;
	private double latitude;
	private double longitude;
	private ArrayList<Category> institutionClasses;
	private Cluster cluster;
	private String name;
	private Location persistentLocation;
	
	public Instance() {
		id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public ArrayList<Category> getInstitutionClasses() {
		return institutionClasses;
	}

	public void setInstitutionClasses(ArrayList<Category> arrayList) {
		this.institutionClasses = arrayList;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getMaxWeight() {
		double maxWeight = 0d;
		for (Category category : institutionClasses) {
			if (category.weight > maxWeight) {
				maxWeight = category.weight;
			}
		}
		return maxWeight;
	}
	
	public double[] oneHotEncode(HashMap<String, Integer> allInstitutionClasses) {
		double[] onehots = new double[allInstitutionClasses.size()];
		for (int i = 0; i < institutionClasses.size(); i++) {
			Iterator<String> iterator = allInstitutionClasses.keySet().iterator();
			int onehotplace = 0;
			for (int j = 0; iterator.hasNext(); j++) {
				String value = iterator.next();
				if (value.equals(institutionClasses.get(i).name))
					onehotplace = j;
			}
			onehots[onehotplace] = 1.0d;
		}
		return onehots;
	}
	
	public double[] oneHotEncodeWeighted(HashMap<String, Integer> allInstitutionClasses) {
		double[] onehots = new double[allInstitutionClasses.size()];
		for (int i = 0; i < institutionClasses.size(); i++) {
			Iterator<String> iterator = allInstitutionClasses.keySet().iterator();
			int onehotplace = 0;
			for (int j = 0; iterator.hasNext(); j++) {
				String value = iterator.next();
				if (value.equals(institutionClasses.get(i).name))
					onehotplace = j;
			}
			onehots[onehotplace] = 1.0d / getMaxWeight();
		}
		return onehots;
	}
	
	@Override
	public String toString() {
		return "" + getName() + " (" + getLatitude() + ", " + getLongitude() + "); " + getCluster().toString() + " @" + getCluster().getCategory().name + " (" + getCluster().getId() + ")" + ";";
	}

	public void persistLocation() {
		this.persistentLocation = new Location(this.latitude, this.longitude);
	}
	public void recoverPersistedLocation() {
		this.latitude = this.persistentLocation.latitude;
		this.longitude = this.persistentLocation.longitude;
	}
	
}
