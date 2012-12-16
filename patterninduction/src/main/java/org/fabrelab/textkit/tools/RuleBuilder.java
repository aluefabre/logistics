package org.fabrelab.textkit.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textkit.model.ExtractRule;
import org.fabrelab.textkit.model.ExtractRuleGroup;
import org.fabrelab.textkit.model.TraceTextGroup;
import org.fabrelab.textkit.sequence.NeedlemanWunsch;

public class RuleBuilder {
	public static List<ExtractRule> buildRules(List<List<TraceTextGroup>> clusters) {
		List<ExtractRule> rules = new ArrayList<ExtractRule>();
		for (List<TraceTextGroup> cluster : clusters) {
			List<ExtractRule> rule = buildRule(cluster);
			rules.addAll(rule);
		}
		return rules;
	}

	public static List<ExtractRule> buildRule(List<TraceTextGroup> traceTextGroups) {

		int nClusters = traceTextGroups.size();
		double[][] fClusterDistance = initDistanceMatrix(traceTextGroups, nClusters);

		while (true) {
			int iMin1 = -1;
			int iMin2 = -1;
			// TODO Auto-generated method stub
			/* simple but inefficient implementation */
			double fMinDistance = Double.MAX_VALUE;
			for (int i = 0; i < nClusters; i++) {
				for (int j = i + 1; j < nClusters; j++) {
					double fDist = fClusterDistance[i][j];
					if (fDist < fMinDistance) {
						fMinDistance = fDist;
						iMin1 = i;
						iMin2 = j;
					}
				}
			}

			if (iMin1 >= 0 && iMin2 >= 0) {
				merge(traceTextGroups, fClusterDistance, iMin1, iMin2);
			} else {
				break;
			}
		}

		List<ExtractRule> rules = new ArrayList<ExtractRule>();
		for (TraceTextGroup group : traceTextGroups) {
			if (StringUtils.isNotBlank(group.getText())) {
				rules.add(new ExtractRule(group));
			}
		}
		return rules;
	}

	private static double[][] initDistanceMatrix(List<TraceTextGroup> traceTextGroups, int nClusters) {
		double[][] fClusterDistance = new double[nClusters][nClusters];
		for (int i = 0; i < nClusters; i++) {
			fClusterDistance[i][i] = Double.MAX_VALUE;
			for (int j = i + 1; j < nClusters; j++) {
				fClusterDistance[i][j] = StringUtils.getLevenshteinDistance(traceTextGroups.get(i).getText(),
						traceTextGroups.get(j).getText());
				fClusterDistance[j][i] = fClusterDistance[i][j];
			}
		}
		return fClusterDistance;
	}

	private static boolean merge(List<TraceTextGroup> traceTextGroups, double[][] fClusterDistance,
			int iMin1, int iMin2) {
		TraceTextGroup traceTextGroup1 = traceTextGroups.get(iMin1);
		String str1 = traceTextGroup1.getText();
		TraceTextGroup traceTextGroup2 = traceTextGroups.get(iMin2);
		String str2 = traceTextGroup2.getText();

		NeedlemanWunsch nw = new NeedlemanWunsch(str1, str2);

		String mergedStr = nw.getMergedSequence();

		if (!mergeOK(str1, str2, mergedStr)) {
			fClusterDistance[iMin2][iMin1] = Double.MAX_VALUE;
			fClusterDistance[iMin1][iMin2] = Double.MAX_VALUE;
			return false;
		} else {
			traceTextGroup1.getTraceTexts().addAll(traceTextGroup2.getTraceTexts());
			traceTextGroup1.setText(mergedStr);
			traceTextGroup2.setText("");
			traceTextGroup2.getTraceTexts().clear();

			for (int i = 0; i < traceTextGroups.size(); i++) {
				if (StringUtils.isNotBlank(traceTextGroups.get(i).getText())) {
					fClusterDistance[iMin1][i] = StringUtils.getLevenshteinDistance(
							traceTextGroup1.getText(), traceTextGroups.get(i).getText());
					;
					fClusterDistance[i][iMin1] = fClusterDistance[iMin1][i];
				} else {
					fClusterDistance[iMin1][i] = Double.MAX_VALUE;
					fClusterDistance[i][iMin1] = Double.MAX_VALUE;
				}
			}

			for (int i = 0; i < traceTextGroups.size(); i++) {
				fClusterDistance[iMin2][i] = Double.MAX_VALUE;
				fClusterDistance[i][iMin2] = Double.MAX_VALUE;
			}

			return true;
		}
	}

	private static boolean mergeOK(String str1, String str2, String mergedStr) {
		if(StringUtils.countMatches(str1, "ADDR") != StringUtils.countMatches(mergedStr, "ADDR")){
			return false;
		}
		if(StringUtils.countMatches(str2, "ADDR") != StringUtils.countMatches(mergedStr, "ADDR")){
			return false;
		}
		
		int count = 0;
		for (int i = 0; i < mergedStr.length(); i++) {
			String ch = mergedStr.charAt(i) + "";
			if (ch.matches("[\\u4E00-\\u9FA5]+")) {
				count++;
			}
		}
		if (count >= 2) {
			return true;
		}
		return false;
	}

	public static List<ExtractRuleGroup> groupExtractRules(List<ExtractRule> rules) {
		Map<String, ExtractRuleGroup> ruleGroups = new HashMap<String, ExtractRuleGroup>();
		for (ExtractRule rule : rules) {
			String line = rule.getRegex();
			if (ruleGroups.get(line) == null) {
				ExtractRuleGroup count = new ExtractRuleGroup(line);
				ruleGroups.put(line, count);
			}
			ExtractRuleGroup count = ruleGroups.get(line);
			count.getExtractRules().add(rule);
		}
		List<ExtractRuleGroup> result = new ArrayList<ExtractRuleGroup>();
		while(!ruleGroups.isEmpty()){
			ExtractRuleGroup ruleGroup = popMaxRuleGroup(ruleGroups);
			result.add(ruleGroup);
		}
		return result;
	}

	private static ExtractRuleGroup popMaxRuleGroup(Map<String, ExtractRuleGroup> ruleGroups) {
		int maxCount = -1;
		ExtractRuleGroup maxGroup = null;
		String maxKey = "";
		for(Entry<String, ExtractRuleGroup> entry : ruleGroups.entrySet()){
			if(entry.getValue().getTotalCount()>maxCount){
				maxCount = entry.getValue().getTotalCount();
				maxKey = entry.getKey();
				maxGroup = entry.getValue();
			}
		}
		ruleGroups.remove(maxKey);
		return maxGroup;
	}

}
