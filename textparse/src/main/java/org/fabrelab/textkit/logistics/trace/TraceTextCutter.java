package org.fabrelab.textkit.logistics.trace;

import java.util.ArrayList;
import java.util.List;

import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textbreaker.core.util.DicLookupUtil;

public class TraceTextCutter {

	private static Dictionary dic;

	public List<String> cut(String text) {
		return this.cut(text, false);
	}

	public List<String> cut(String text, boolean greedy) {
		if (dic == null) {
			dic = TraceDictionaryLoader.loadDictionary();
		}

		List<String> result = new ArrayList<String>();
		List<Word> lookupResults = DicLookupUtil.invertedMaxCut(dic, text, greedy);
		for (Word lookupResult : lookupResults) {
			result.add(lookupResult.getWord());
		}
		return result;

	}

	public static Dictionary getDic() {
		if (dic == null) {
			dic = TraceDictionaryLoader.loadDictionary();
		}
		return dic;
	}

}
