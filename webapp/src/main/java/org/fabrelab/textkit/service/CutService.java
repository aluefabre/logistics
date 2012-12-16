package org.fabrelab.textkit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.fabrelab.textbreaker.core.model.PosTag;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textkit.logistics.trace.TraceTextCutter;

public class CutService {
	private static final Logger log = Logger.getLogger(CutService.class.getName());
	
	public Map<String, Integer> cut2CountMap(String text, boolean greedy){
		log.info(text);
		log.info("greedy: " + greedy+"");
		List<String> result =  cut2List(text, greedy);
		Map<String, Integer> hashmap = new HashMap<String, Integer>();
		for(String term : result){
			if(hashmap.containsKey(term)){
				Integer count = hashmap.get(term);
				hashmap.put(term, count+1);
			}else{
				hashmap.put(term, 1);
			}
		}
		return hashmap;
	}
	
	public List<Word> cut2ListWithAnnotation(String text, boolean greedy){
		TraceTextCutter breaker = new TraceTextCutter();
		List<String> words =  breaker.cut(text, greedy);
		List<Word> result = new ArrayList<Word>();
		for(String word : words){
			String wordAnnotation = TraceTextCutter.getDic().getWordAnnotation(word);
			Word wordWithAnnotation = new Word(word, new PosTag(wordAnnotation));
			result.add(wordWithAnnotation);
		}
		return result;
	}
	
	public List<String> cut2List(String text, boolean greedy){
		TraceTextCutter breaker = new TraceTextCutter();
		List<String> result =  breaker.cut(text, greedy);
		return result;
	}
}
