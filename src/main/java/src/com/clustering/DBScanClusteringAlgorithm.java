package src.com.clustering;

import java.util.ArrayList;
import java.util.HashSet;

import src.com.core.Cluster;
import src.com.core.Instance;
import weka.core.Instances;

public class DBScanClusteringAlgorithm extends ClusteringAlgorithm {

	private double epsilon = 1;
	private int minPoints = 1;
	
	public DBScanClusteringAlgorithm() {
		
		
		
	}
	
	@Override
	public void buildCluster(ArrayList<Instance> instances) {
		
		WekaPreprocessor wp = new WekaPreprocessor();
		for (Instance instance : instances) {
			instance.persistLocation();
		}
		Instances wInstances = wp.preprocess(instances);
		
		weka.clusterers.DBScan dbscan = new weka.clusterers.DBScan();
		dbscan.setEpsilon(this.epsilon);
		dbscan.setMinPoints(this.minPoints);
		try {
			dbscan.buildClusterer(wInstances);
			HashSet<Integer> clustersInt = new HashSet<Integer>();
			int[] assignments = new int[instances.size()];
			for (int i = 0; i < instances.size(); i++) {
				int assignment = dbscan.clusterInstance(wInstances.instance(i));
				assignments[i] = assignment;
				clustersInt.add(assignment);
			}
			ArrayList<Cluster> clusters = new ArrayList<Cluster>();
			for (int cluster : clustersInt) {
				Cluster c = new Cluster();
				ArrayList<Instance> clustered_instances = new ArrayList<Instance>();
				for (int i = 0; i < assignments.length; i++) {
					if (assignments[i] == cluster) {
						clustered_instances.add(instances.get(i));
						instances.get(i).setCluster(c);
					}
				}
				c.setInstances(clustered_instances);
				clusters.add(c);
			}
			
			for (Instance instance : instances) {
					instance.recoverPersistedLocation();
			}
			super.setClusters(clusters);
			super.setInstances(instances);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wp.postprocess();
		}
		
		
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
	
}
