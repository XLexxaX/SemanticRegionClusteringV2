package src.com.clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import src.com.core.Helper;
import src.com.core.Instance;
import weka.core.Instances;

public class WekaPreprocessor {

	private File tmpFile;

	public WekaPreprocessor() {
		tmpFile = null;
	}

	public void postprocess() {
		this.tmpFile.delete();
	}

	public Instances preprocess(ArrayList<Instance> instances) {

		Helper.normalizeGeoCoordinates(instances);

		File tmp = saveToArff(instances);

		Instances data = MyUtilsForWekaInstanceHelper.getInstanceFromFile(tmp.getAbsolutePath());
		tmpFile = tmp;

		return data;

	}

	@SuppressWarnings("resource")
	private File saveToArff(ArrayList<Instance> instances) {
		String tmpFileName = "" + UUID.randomUUID() + ".arff";// "src/main/java/tmpFiles/"+UUID.randomUUID()+".arff";

		File tmp = null;
		try {

			tmp = new File(tmpFileName);
			tmp.createNewFile();

			PrintWriter pw = new PrintWriter(tmp);
			
			HashMap<String, Integer> allInstitutionClasses = Helper.getAllInstitutionClasses(instances);

			pw.print("\n" + "@RELATION iris\n" + "\n" + "@ATTRIBUTE lat	REAL\n" + "@ATTRIBUTE long 	REAL\n");
			for (String institution : allInstitutionClasses.keySet()) {
				pw.print("@ATTRIBUTE " + institution + " 	REAL\n");
			}

			pw.print("\n" + "@DATA\n");

			for (Instance instance : instances) {
				pw.print(instance.getLatitude() + "," + instance.getLongitude());
				for (double d : instance.oneHotEncodeWeighted(allInstitutionClasses)) {
					pw.print("," + d);
				}
				pw.print("\n");
			}
			
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmp;
	}

	public static class MyUtilsForWekaInstanceHelper {

		public static Instances getInstanceFromFile(String pFileName) {
			Instances data = null;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(pFileName));
				data = new Instances(reader);
				reader.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return data;

		}
	}

}
