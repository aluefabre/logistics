package org.fabrelab.textkit.logistics.question;

import java.util.Map;
import java.util.Set;

import org.fabrelab.textbreaker.core.dic.Dictionary;

public class QuestionDictionary extends Dictionary {

	private static final long serialVersionUID = -8106478077897336346L;

	public QuestionDictionary(Map<String, String> wordsMap, Set<Character> stopWords) {
		super(wordsMap, stopWords);
	}
	
	public String getWordAnnotation(String word){
		String annotation = wordsMap.get(word);
		if(annotation!=null){
			return annotation;
		}
		if(word.matches("[1-2]\\d\\d\\d.[0-1]\\d.[0-3]\\d")){
			return "Date";
		}
		if(word.matches("[0-2]\\d.[0-6]\\d.[0-6]\\d")){
			return "Time";
		}
		if(word.matches("0-2\\d.0-6\\d.0-6\\d.\\d\\d\\d")){
			return "Time";
		}
		if(word.matches("[/\\.\\-:\\da-zA-Z]+")){
			if(word.length()>1){
				return "CO";
			} 
			if(word.matches("\\d")){
				return "D";
			}else{
				return word;
			}
		}
		return "";
	}
}
