package org.fabrelab.textkit.model;

import java.util.ArrayList;
import java.util.List;

public class ExtractRuleGroup {
	String regex;
	List<ExtractRule> extractRules = new ArrayList<ExtractRule>();

	public ExtractRuleGroup(String regex) {
		super();
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public List<ExtractRule> getExtractRules() {
		return extractRules;
	}

	@Override
	public String toString() {
		return "ExtractRuleGroup [regex=" + regex + ", totalCount=" + getTotalCount() + ", extractRules" + extractRules + " ]";
	}

	public int getTotalCount() {
		int count = 0;
		for(ExtractRule rule : extractRules){
			count += rule.getTraceTexts().size();
		}
		return count;
	}



}
