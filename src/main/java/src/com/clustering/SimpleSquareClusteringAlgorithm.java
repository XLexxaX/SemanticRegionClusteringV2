package src.com.clustering;

import java.util.ArrayList;

import src.com.core.Cluster;
import src.com.core.Instance;
import src.com.core.Location;

public class SimpleSquareClusteringAlgorithm extends ClusteringAlgorithm {

	private double clustersize = 0.0005d;

	public void buildCluster(ArrayList<Instance> instances) {
		if (instances.size() == 0)
			return;

		Instance referencePoint = instances.get(0);

		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		Cluster c = new Cluster();
		c.addEdge(new Location(referencePoint.getLatitude() - (clustersize / 2.0d),
				referencePoint.getLongitude() - (clustersize / 2.0d)));
		c.addEdge(new Location(referencePoint.getLatitude() - (clustersize / 2.0d),
				referencePoint.getLongitude() + (clustersize / 2.0d)));
		c.addEdge(new Location(referencePoint.getLatitude() + (clustersize / 2.0d),
				referencePoint.getLongitude() - (clustersize / 2.0d)));
		c.addEdge(new Location(referencePoint.getLatitude() + (clustersize / 2.0d),
				referencePoint.getLongitude() + (clustersize / 2.0d)));
		c.orderEdges();
		clusters.add(c);

		for (Instance instance : instances) {
			

			// Is the current instance located within any existing cluster.
			// If so, register it to the cluster's instance-list
			// If not, create a new cluster and add the instance to it.
			// (assume ordered edges for clusters)
			Cluster pivotC = null;

			// round to lower number
			double tmp = (instance.getLatitude() - referencePoint.getLatitude()) / clustersize;
			tmp = Math.round(tmp);
			double centroidLat = referencePoint.getLatitude() + tmp * clustersize * 1.0d;
			tmp = (((instance.getLongitude() - referencePoint.getLongitude())) / clustersize);
			tmp = Math.round(tmp);
			double centroidLong = referencePoint.getLongitude() + tmp * clustersize * 1.0d;

			c = new Cluster();
			c.addEdge(new Location(centroidLat - (clustersize / 2.0d), centroidLong - (clustersize / 2.0d)));
			c.addEdge(new Location(centroidLat - (clustersize / 2.0d), centroidLong + (clustersize / 2.0d)));
			c.addEdge(new Location(centroidLat + (clustersize / 2.0d), centroidLong - (clustersize / 2.0d)));
			c.addEdge(new Location(centroidLat + (clustersize / 2.0d), centroidLong + (clustersize / 2.0d)));
			c.orderEdges();
			boolean found = false;
			for (Cluster cluster : clusters) {
				if (cluster.equals(c)) {
					found = true;
					cluster.getInstances().add(instance);
					instance.setCluster(cluster);
				}
			}
			if (!found) {
				clusters.add(c);
				c.getInstances().add(instance);
				instance.setCluster(c);
			}
		}

		super.setClusters(clusters);
		super.setInstances(instances);
	}

	public double getClustersize() {
		return clustersize;
	}

	public void setClustersize(double clustersize) {
		this.clustersize = clustersize;
	}

}
