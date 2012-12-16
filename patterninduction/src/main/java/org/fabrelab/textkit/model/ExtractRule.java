package org.fabrelab.textkit.model;

import java.util.List;

public class ExtractRule {
	String regex;
	String mergeText;
	List<TraceText> traceTexts = null;

	public ExtractRule(TraceTextGroup group) {
		super();
		this.mergeText = group.text;
		buildRegex();
		this.traceTexts = group.traceTexts;
	}

	private void buildRegex() {
		regex = mergeText;
		String old = "";
		while(!old.equals(regex)){
			old = regex;
			regex = regex.replaceAll("X", "(.*)");
			regex = regex.replaceAll("CP", "(.*)");
			regex = regex.replaceAll("\\(\\.\\*\\)X", "(.*)");
			regex = regex.replaceAll("\\(\\.\\*\\)CP",  "(.*)");
			regex = regex.replaceAll("X\\(\\.\\*\\)",  "(.*)");
			regex = regex.replaceAll("CP\\(\\.\\*\\)", "(.*)");
			regex = regex.replaceAll("\\(\\.\\*\\)\\s", "(.*)");
			regex = regex.replaceAll("\\s\\(\\.\\*\\)", "(.*)");
			regex = regex.replaceAll("\\(\\.\\*\\)\\(\\.\\*\\)", "(.*)");
		}
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getMergeText() {
		return mergeText;
	}

	public void setMergeText(String mergeText) {
		this.mergeText = mergeText;
	}

	public List<TraceText> getTraceTexts() {
		return traceTexts;
	}

	@Override
	public String toString() {
		return "ExtractRule [regex=" + regex + ", mergeText=" + mergeText + ", count=" + traceTexts.size()
				+ "]";
	}

}
