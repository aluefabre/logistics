package org.fabrelab.textkit.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fabrelab.textbreaker.core.model.Word;


public class RuleExtractor {
	String regex;
	String status;
	int addressWordIndex;
	int addressGroupIndex;
	
	public RuleExtractor(String regex, String status, int addressWordIndex, int addressGroupIndex) {
		super();
		this.regex = regex;
		this.status = status;
		this.addressWordIndex = addressWordIndex;
		this.addressGroupIndex = addressGroupIndex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAddressGroupIndex() {
		return addressGroupIndex;
	}

	public void setAddressGroupIndex(int addressGroupIndex) {
		this.addressGroupIndex = addressGroupIndex;
	}

	public int getAddressWordIndex() {
		return addressWordIndex;
	}

	public void setAddressWordIndex(int addressWordIndex) {
		this.addressWordIndex = addressWordIndex;
	}
	
	public ExtractResult extract(TraceText traceText) {
		ExtractResult result = new ExtractResult("",status,true);
		Pattern p = Pattern.compile(getRegex());
		Matcher m = p.matcher(traceText.getFilteredText());
		if(m.matches()){
			if(getAddressWordIndex()>=0){
				result.setAddress(traceText.getRecognizedAddresses().get(getAddressWordIndex()).getWord());
				return result;
			}else if(getAddressWordIndex()==-2){
				String resultAddress = traceText.getRecognizedAddresses().get(0).getWord() + " "+
							traceText.getRecognizedAddresses().get(1).getWord();
				result.setAddress(resultAddress);
				return result;
			}else if(getAddressWordIndex()==-1){
				if(getAddressGroupIndex()>0){
					result.setAddress(m.group(getAddressGroupIndex()));
					return result;
				}else if(getAddressGroupIndex()==-1){
					return result;
				}
			}
		}
		result.setSuccess(false);
		return result;
	}
}
