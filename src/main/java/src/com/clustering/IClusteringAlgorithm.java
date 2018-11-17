package src.com.clustering;

import java.util.ArrayList;

import src.com.core.Cluster;
import src.com.core.Instance;

public interface IClusteringAlgorithm {

	public void buildCluster(ArrayList<src.com.core.Instance> instances);
	
	public ArrayList<Instance> getInstances();
	
	public ArrayList<Cluster> getClusters();
}
