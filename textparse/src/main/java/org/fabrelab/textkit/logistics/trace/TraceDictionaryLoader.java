package org.fabrelab.textkit.logistics.trace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.fabrelab.textbreaker.core.util.TextLoaderUtil;


public class TraceDictionaryLoader {

	static String addressFileName = "/trace/address.dic";
	static String companyFileName = "/trace/company.dic";
	
	static String suffixFileName = "/trace/suffix.dic";
	
	static private Map<String, String> loadWords() {
		Map<String, String> words = new HashMap<String, String>();
		TextLoaderUtil.loadWords(TraceDictionaryLoader.class, addressFileName, words,  "ADDR", "utf-8");
		TextLoaderUtil.loadWords(TraceDictionaryLoader.class, companyFileName, words,  "CP", "utf-8");
		return words;
	}
	
	
	static public TraceDictionary loadDictionary() {
		Map<String, String> words = loadWords();
		Set<Character> stopWords =  TextLoaderUtil.loadStopWords(TraceDictionaryLoader.class, suffixFileName, "utf-8");
		TraceDictionary dic = new TraceDictionary(words, stopWords);
		dic.buildTrie();
		return dic;
	}

}
