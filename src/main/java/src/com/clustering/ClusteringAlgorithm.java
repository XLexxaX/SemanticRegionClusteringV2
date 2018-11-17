package src.com.clustering;

import java.util.ArrayList;

import src.com.core.Cluster;
import src.com.core.Instance;

public abstract class ClusteringAlgorithm implements IClusteringAlgorithm {

	private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	private ArrayList<Instance> instances = new ArrayList<Instance>();
	
	public ArrayList<Instance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<Instance> instances) {
		this.instances = instances;
	}
	

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<Cluster> clusters) {
		this.clusters = clusters;
	}
	
	
	public void buildCluster(ArrayList<Instance> instances) {
		
	}

}
