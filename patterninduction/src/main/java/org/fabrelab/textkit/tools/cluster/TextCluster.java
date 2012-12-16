package org.fabrelab.textkit.tools.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fabrelab.textkit.model.TraceTextGroup;

import weka.clusterers.ClusterEvaluation;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class TextCluster {

	public static List<List<TraceTextGroup>> clusterLines(Collection<TraceTextGroup> traceTextMap) throws Exception {
		FastVector attribInfo = new FastVector();
		attribInfo.addElement(new Attribute("TEXT", (FastVector) null));

		Instances instances = new Instances("Lines", attribInfo, 0);
		for (TraceTextGroup group : traceTextMap) {
			Instance instance = new Instance(1);
			instance.setDataset(instances);
			instance.setValue(0, group.getText());
			instances.add(instance);
		}

		HierarchicalClustererWithThreshold cluster = new HierarchicalClustererWithThreshold();
		cluster.setOptions(new String[] { "-L", "COMPLETE" });
		cluster.setMaxLinkDistance(6);
		DistanceFunction df = new AdjustedEditDistance();
		cluster.setDistanceFunction(df);

		cluster.buildClusterer(instances);

		List<List<TraceTextGroup>> components = new ArrayList<List<TraceTextGroup>>();

		for (int i = 0; i < cluster.numberOfClusters(); i++) {
			List<TraceTextGroup> component = new ArrayList<TraceTextGroup>();
			components.add(component);
		}

		for (TraceTextGroup group : traceTextMap) {
			Instance instance = new Instance(1);
			instance.setDataset(instances);
			instance.setValue(0, group.getText());
			int assignment = cluster.clusterInstance(instance);
			List<TraceTextGroup> component = components.get(assignment);
			component.add(group);
		}

		evaluateCluster(instances, cluster);

		return components;
	}

	private static void evaluateCluster(Instances instances, HierarchicalClustererWithThreshold cluster)
			throws Exception {
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(cluster);
		eval.evaluateClusterer(instances);

		System.out.println("Cluster Evaluation:" + eval.clusterResultsToString());
	}

}
