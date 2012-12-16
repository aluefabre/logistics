package org.fabrelab.textkit.model;

import java.util.List;

import org.fabrelab.textbreaker.core.model.Word;

public class TraceText {
	String text;
	String filteredText;
	List<Word> recognizedAddresses;
	
	public TraceText(String text, String filteredText, List<Word> recognizedAddresses) {
		super();
		this.text = text;
		this.filteredText = filteredText;
		this.recognizedAddresses = recognizedAddresses;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFilteredText() {
		return filteredText;
	}

	public void setFilteredText(String filteredText) {
		this.filteredText = filteredText;
	}

	@Override
	public String toString() {
		return "TraceText [filteredText=" + filteredText + ", text=" + text + "]";
	}

	public List<Word> getRecognizedAddresses() {
		return recognizedAddresses;
	}

	public void setRecognizedAddresses(List<Word> recognizedAddresses) {
		this.recognizedAddresses = recognizedAddresses;
	}

}
