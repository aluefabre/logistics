package org.fabrelab.textkit.logistics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.util.TextLoader;
import org.fabrelab.textkit.model.ExtractRule;
import org.fabrelab.textkit.model.ExtractRuleGroup;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.model.TraceTextGroup;
import org.fabrelab.textkit.tools.CommonEntityFilter;
import org.fabrelab.textkit.tools.RuleBuilder;
import org.fabrelab.textkit.tools.cluster.TextCluster;

public class LogisticsTextParserTest {
	public static void main(String[] args) throws Exception {

		InputStream is = LogisticsTextParserTest.class.getResourceAsStream("c.txt");
		List<String> originalLines = TextLoader.loadText(is,"utf-8");
		is.close();

		List<TraceText> lines = new ArrayList<TraceText>();
		for (String line : originalLines) {
			TraceText traceText = CommonEntityFilter.filterCommonEntities(line);
			System.out.println(traceText);
			if (StringUtils.isNotBlank(traceText.getFilteredText())) {
				lines.add(traceText);
			}
		}
		checkAddressRecognizeRate(lines);

		Collection<TraceTextGroup> traceTextMap = CommonEntityFilter.groupTraceTexts(lines);

		List<List<TraceTextGroup>> clusters = TextCluster.clusterLines(traceTextMap);

		List<ExtractRule> totalRules = new ArrayList<ExtractRule>();
		
		for (List<TraceTextGroup> cluster : clusters) {
			System.out.println("\n*************CLUSTER************************************************\n");
			for (TraceTextGroup line : cluster) {
				System.out.println(line);
			}
			List<ExtractRule> rules = RuleBuilder.buildRule(cluster);
			totalRules.addAll(rules);
			System.out.println("RULES: " + rules);
		}
		
		System.out.println("\n------------------------RULES--------------------------------------\n");
		System.out.println(totalRules.size());
		for (ExtractRule rule : totalRules) {
			System.out.println(rule);
		}

		List<ExtractRuleGroup> extractRuleGroups = RuleBuilder.groupExtractRules(totalRules);
		System.out.println("\n------------------------RULE GROUPS--------------------------------------\n");
		System.out.println(extractRuleGroups.size());
		for (ExtractRuleGroup ruleGroup : extractRuleGroups) {
			System.out.println(ruleGroup);
		}
		
	}


	private static void checkAddressRecognizeRate(List<TraceText> traceTexts) {
		int j = 0;

		List<String> errorlines = new ArrayList<String>();
		for (TraceText traceText : traceTexts) {
			String line = traceText.getFilteredText();
			if (line.contains("ADDR")) {
				j++;
				continue;
			}
			if (line.trim().equals("正在派件")) {
				j++;
				continue;
			}
			if (line.trim().equals("已取件")) {
				j++;
				continue;
			}
			if (line.trim().equals("派件已签收")) {
				j++;
				continue;
			}
			if (line.trim().contains("派件不成功")) {
				j++;
				continue;
			}
			if (line.trim().contains("派送不成功")) {
				j++;
				continue;
			}
			if (line.trim().contains("预计到达时间")) {
				j++;
				continue;
			}
			if (line.contains("签收")) {
				j++;
				continue;
			}
			if (line.contains("提货通知")) {
				j++;
				continue;
			}
			if (StringUtils.isBlank(line)) {
				j++;
				continue;
			}
			errorlines.add(line);
		}

		System.out.println("-----------------------------------------------------------------");

		for (String line : errorlines) {

			System.out.println(line);
		}

		System.out.println(j + " in " + traceTexts.size() + " " + ((double) j) / traceTexts.size());

	}
}
