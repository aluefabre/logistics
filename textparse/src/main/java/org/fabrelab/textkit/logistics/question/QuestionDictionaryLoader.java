package org.fabrelab.textkit.logistics.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.fabrelab.textbreaker.core.util.TextLoaderUtil;


public class QuestionDictionaryLoader {

	static String addressFileName = "/question/address.dic";
	static String goodFileName = "/question/adjadv.dic";
	static String transportFileName = "/question/transport.dic";
	static String particleFileName = "/question/particle.dic";
	static String numquFileName = "/question/numqu.dic";
	static String otherFileName = "/question/other.dic";
	static String suffixFileName = "/question/suffix.dic";
	
	private static Map<String, String> loadWords() {
		Map<String, String> words = new HashMap<String, String>();
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, addressFileName, words,  "@ADDR", "utf-8");
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, goodFileName, words, "utf-8");
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, transportFileName, words, "utf-8");
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, particleFileName, words, "utf-8");
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, numquFileName, words, "utf-8");
		TextLoaderUtil.loadWords(QuestionDictionaryLoader.class, otherFileName, words, "utf-8");
		return words;
	}
	
	
	public static QuestionDictionary loadDictionary() {
		Map<String, String> words = loadWords();
		Set<Character> stopWords =  TextLoaderUtil.loadStopWords(QuestionDictionaryLoader.class, suffixFileName, "utf-8");
		QuestionDictionary dic = new QuestionDictionary(words, stopWords);
		dic.buildTrie();
		return dic;
	}

}
