package src.com.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Cluster {

	private List<Location> edges = new LinkedList<Location>();
	private Category category;
	private ArrayList<Instance> instances = new ArrayList<Instance>();
	private String id = "";
	private Color color = null;
	private boolean ordered = false;
	
	
	public Cluster() {
		id = UUID.randomUUID().toString();
		Random r = new Random();
		color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Color getColor() {
		return color;
	}
	
	public String getHexColor() {
		return "#"+Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public ArrayList<Instance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<Instance> instances) {
		this.instances = instances;
	}
	
	public List<Location> getEdges() {
		return edges;
	}

	public void addEdge(Location loc) {
		this.edges.add(loc);
		ordered = false;
	}
	
	@Override
	public String toString() {
		//Get cluster center
		return "(" + this.getEdges().get(0).latitude + "-" + this.getEdges().get(0).longitude +")";
	}
	
	
	public double getCenterLat() {
		this.orderEdges();
		double halfClusterSize = (edges.get(0).latitude - edges.get(1).latitude) / 2.0d;
		double centerLat = (edges.get(0).latitude + halfClusterSize);
		return centerLat;
	}
	public double getCenterLong() {
		this.orderEdges();
		double halfClusterSize = (edges.get(0).latitude - edges.get(1).latitude) / 2.0d;
		double centerLong = (edges.get(0).longitude + halfClusterSize);
		return centerLong;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		
		if (!obj.getClass().toString().equals("class src.com.core.Cluster")) {
			return super.equals(obj);
		}
		
		Cluster cObj = (Cluster) obj; 
		/* Former implementation
		 * double halfClusterSize = Math.abs((getEdge1()[0] - getEdge4()[0]) / 2.0d);

		double tmp = (getCenterLat() - cObj.getCenterLat()) ;
		if ( Math.abs(getCenterLat() - cObj.getCenterLat()) < halfClusterSize/2.0d) {
			if ( (getCenterLong() - cObj.getCenterLong()) < halfClusterSize/2.0d) {
				return true;
			}
		}		 */
		
		boolean found = false;
		LinkedList<Location> oEdges = (LinkedList<Location>) cObj.getEdges();
		for (Location loc : oEdges) {
			for (Location loc2 : this.getEdges()) {
				if (loc.latitude == loc2.latitude && loc.longitude == loc2.longitude){
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
			found = false;
		}
		
		return true;
	}
	
	public void orderEdges() {
		if (edges.size() == 0) 
			return;
		if (ordered)
			return;
		
		LinkedList<Location> newEdges = new LinkedList<>();
		int pivotIndex = 0;
		//Search for the lowest and most left point.
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).latitude < edges.get(pivotIndex).latitude || edges.get(i).longitude < edges.get(pivotIndex).longitude ) {
				pivotIndex = i;
			}
		}
		newEdges.add(edges.get(pivotIndex));
		edges.remove(pivotIndex);
		//Link the list by minimizing the distances between Locations; should work in most use-cases.
		while (edges.size() > 0) {
			pivotIndex = 0;
			for (int i = 0; i < edges.size(); i++) {
				if (edges.get(i).distance(newEdges.get(newEdges.size()-1)) < edges.get(pivotIndex).distance(newEdges.get(newEdges.size()-1))) {
					pivotIndex = i;
				}
			}
			newEdges.add(edges.get(pivotIndex));
			edges.remove(pivotIndex);
		}
		
		edges = newEdges;
		ordered = true;
	}

	
}
