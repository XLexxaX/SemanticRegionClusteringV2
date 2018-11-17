package src.com.core;

import java.util.ArrayList;
import java.util.Arrays;

import src.com.clustering.ClassOverlap;
import src.com.clustering.DBScanClusteringAlgorithm;

public class Processor {

	public Processor() {
		// TODO Auto-generated constructor stub
	}
	
	public void execute() {
		
		ArrayList<Instance> instances = createDummyInstances();
		
		DBScanClusteringAlgorithm clusterAlg = new DBScanClusteringAlgorithm();
		clusterAlg.setEpsilon(0.01);
		clusterAlg.setMinPoints(1);
		
		/*SimpleSquareClusteringAlgorithm clusterAlg = new SimpleSquareClusteringAlgorithm();
		clusterAlg.setClustersize(1.0);*/
		
		clusterAlg.buildCluster(instances);
		instances = clusterAlg.getInstances();
		
		ArrayList<Cluster> cluster = clusterAlg.getClusters();
		ClassOverlap co = new ClassOverlap();
		co.setClusterClasses(cluster);

		System.out.println("name (location instance); (location cluster center)");
		for (Instance instance : instances) {
			System.out.println(instance.toString());
		}
	}
	
	private ArrayList<Instance> createDummyInstances() {
		ArrayList<Instance> instances = new ArrayList<Instance>(); 

		Instance i = new Instance();
		i.setInstitutionClasses(new ArrayList<Category>(Arrays.asList(new Category[]{new Category("Thing", 1.0), new Category("bar", 2.0)})));
		i.setName("a");
		i.setLatitude(50);
		i.setLongitude(50);
		instances.add(i);
		
		i = new Instance();
		i.setInstitutionClasses(new ArrayList<Category>(Arrays.asList(new Category[]{new Category("Thing", 1.0), new Category("bar", 2.0)})));
		i.setName("b");
		i.setLatitude(50.0001);
		i.setLongitude(50.0001);
		instances.add(i);
		
		i = new Instance();
		i.setInstitutionClasses(new ArrayList<Category>(Arrays.asList(new Category[]{new Category("Thing", 1.0), new Category("museum", 2.0)})));
		i.setName("c");
		i.setLatitude(40.002);
		i.setLongitude(40.002);
		instances.add(i); 
		
		return instances;
	}
	
	/*public static void main(String[] args) {
		new Processor().execute();
	}*/
	
	
}
