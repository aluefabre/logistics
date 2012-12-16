package org.fabrelab.textkit.tools.cluster;

import org.apache.commons.lang.StringUtils;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.NormalizableDistance;
import weka.core.RevisionUtils;
import weka.core.neighboursearch.PerformanceStats;

public class AdjustedEditDistance extends NormalizableDistance {

	{
		m_DontNormalize = true;
	}

	double stringDistance(String stringA, String stringB) {
		Double value = (double) StringUtils.getLevenshteinDistance(stringA, stringB);
		int minLength = Math.min(stringA.length(), stringB.length());
		int maxLength = Math.max(stringA.length(), stringB.length());
		if (stringA.equals(stringB)) {
			return 0;
		}
		if (minLength <= 5) {
			return 1000;
		}

		if (minLength < 10) {
			return value + 2;
		} else if (maxLength < 20) {
			return Math.max(1, value - maxLength / 5D);
		} else if (maxLength < 30) {
			return Math.max(1, value - maxLength / 4D);
		} else {
			return Math.max(1, value - maxLength / 3D);
		}
	}

	public double distance(Instance first, Instance second, double cutOffValue, PerformanceStats stats) {
		double sqDistance = 0;
		int numAttributes = m_Data.numAttributes();

		validate();

		double diff;

		for (int i = 0; i < numAttributes; i++) {
			diff = 0;
			if (m_ActiveIndices[i]) {
				diff = difference(i, first.stringValue(i), second.stringValue(i));
			}
			sqDistance = updateDistance(sqDistance, diff);
			if (sqDistance > (cutOffValue * cutOffValue))
				return Double.POSITIVE_INFINITY;
		}
		double distance = Math.sqrt(sqDistance);
		return distance;
	}

	protected double difference(int index, String string1, String string2) {
		switch (m_Data.attribute(index).type()) {
		case Attribute.STRING:
			double diff = stringDistance(string1, string2);
			if (m_DontNormalize == true) {
				return diff;
			} else {
				if (string1.length() > string2.length()) {
					return diff / ((double) string1.length());
				} else {
					return diff / ((double) string2.length());
				}
			}

		default:
			return 0;
		}
	}

	/**
	 * Returns a string describing this object.
	 * 
	 * @return a description of the evaluator suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String globalInfo() {
		return "Implementing Levenshtein distance function.\n\n"
				+ "One object defines not one distance but the data model in which "
				+ "the distances between objects of that data model can be computed.\n\n"
				+ "Attention: For efficiency reasons the use of consistency checks "
				+ "(like are the data models of the two instances exactly the same), " + "is low.\n\n"
				+ "For more information, see: http://en.wikipedia.org/wiki/Levenshtein_distance\n\n";
	}

	/**
	 * Returns the revision string.
	 * 
	 * @return the revision
	 */
	public String getRevision() {
		return RevisionUtils.extract("$Revision: 1.2 $");
	}

	/**
	 * Updates the current distance calculated so far with the new difference
	 * between two attributes. The difference between the attributes was
	 * calculated with the difference(int,double,double) method.
	 * 
	 * @param currDist
	 *            the current distance calculated so far
	 * @param diff
	 *            the difference between two new attributes
	 * @return the update distance
	 * @see #difference(int, double, double)
	 */
	protected double updateDistance(double currDist, double diff) {
		return (currDist + (diff * diff));
	}

}
