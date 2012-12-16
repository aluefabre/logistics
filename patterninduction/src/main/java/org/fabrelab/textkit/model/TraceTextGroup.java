package org.fabrelab.textkit.model;

import java.util.ArrayList;
import java.util.List;

public class TraceTextGroup {
	String text;
	List<TraceText> traceTexts = new ArrayList<TraceText>();

	public TraceTextGroup(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TraceText> getTraceTexts() {
		return traceTexts;
	}

	public void setTraceTexts(List<TraceText> traceTexts) {
		this.traceTexts = traceTexts;
	}

	@Override
	public String toString() {
		return "TraceTextGroup [text=" + text + ", traceTexts=" + traceTexts.size() + "]";
	}

}
