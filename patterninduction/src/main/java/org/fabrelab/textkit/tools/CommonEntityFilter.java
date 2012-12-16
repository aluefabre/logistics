package org.fabrelab.textkit.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textkit.model.TraceText;
import org.fabrelab.textkit.model.TraceTextGroup;
import org.fabrelab.textkit.service.CutService;

public class CommonEntityFilter {
	
	
	public static TraceText filterCommonEntities(String text) {
		CutService breaker = new CutService();
		List<Word> recognizedWords = breaker.cut2ListWithAnnotation(text, false);
		List<Word> recognizedAddresses = new ArrayList<Word>();
		for (Word word : recognizedWords) {
			if("ADDR".equals(word.getPosTags().get(0))){
				recognizedAddresses.add(word);
			}
		}
		
		String result = text.toLowerCase();
		for (Word word : recognizedWords) {
			result = StringUtils.replace(result, word.getWord(), word.getPosTags().get(0).getName());
		}
		result = prcessPerfixAndSuffix(result, recognizedAddresses);
		
		return new TraceText(text, result, recognizedAddresses);
	}

	
	public static String prcessPerfixAndSuffix(String result, List<Word> recognizedAddresses) {
		while(true){
			String mergeAddr = result;
		
			mergeAddr = mergeAddr.replaceAll("\\s\\s", " ");
			mergeAddr = mergeAddr.replaceAll("ADDR\\s*", "ADDR");
			mergeAddr = mergeAddr.replaceAll("\\s*ADDR", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR站", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR中心", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR部", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR办", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR局", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR所", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR区", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR点", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR店", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR外", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR一", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR二", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR三", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR四", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR五", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR六", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR七", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR八", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR九", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR十", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR东", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR南", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR西", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR北", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR路", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR街道", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR街", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR乡", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR镇", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR县", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR大道", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR郊", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR中", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR苑", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR院", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR地区", "ADDR");
			mergeAddr = mergeAddr.replaceAll("ADDR[a-z]", "ADDR");
			mergeAddr = mergeAddr.replaceAll("\\[", " ");
			mergeAddr = mergeAddr.replaceAll("\"", " ");
			mergeAddr = mergeAddr.replaceAll("\\(", " ");
			mergeAddr = mergeAddr.replaceAll("【", " ");
			mergeAddr = mergeAddr.replaceAll("\\]", " ");
			mergeAddr = mergeAddr.replaceAll("\\)", " ");
			mergeAddr = mergeAddr.replaceAll("】", " ");
			mergeAddr = mergeAddr.replaceAll(",", " ");
			mergeAddr = mergeAddr.replaceAll("，", " ");
			mergeAddr = mergeAddr.replaceAll("-", " ");
			mergeAddr = mergeAddr.replaceAll("_", " ");
			mergeAddr = mergeAddr.replaceAll("\\{", " ");
			mergeAddr = mergeAddr.replaceAll("\\}", " ");
			
			mergeAddr = mergeAddr.replaceAll("CO\\s*", "CO");
			mergeAddr = mergeAddr.replaceAll("\\s*CO", "CO");
			mergeAddr = mergeAddr.replaceAll("COCO", "CO");
			
			mergeAddr = mergeAddr.replaceAll("第一CP", "CP");
			mergeAddr = mergeAddr.replaceAll("第二CP", "CP");
			mergeAddr = mergeAddr.replaceAll("第三CP", "CP");
			mergeAddr = mergeAddr.replaceAll("一CP", "CP");
			mergeAddr = mergeAddr.replaceAll("二CP", "CP");
			mergeAddr = mergeAddr.replaceAll("三CP", "CP");
			mergeAddr = mergeAddr.replaceAll("CP\\s*", "CP");
			mergeAddr = mergeAddr.replaceAll("\\s*CP", "CP");
			
			mergeAddr = mergeAddr.replaceAll("ADDRCP", "ADDR");
			mergeAddr = mergeAddr.replaceAll("CPCP", "CP");
			
			mergeAddr = mergeEntities(mergeAddr, recognizedAddresses);
			
			if(mergeAddr.equals(result)){
				result = mergeAddr;
				break;
			}else{
				result = mergeAddr;
			}
		}
		result = result.replaceAll("CO", "");
		result = result.trim();
		return result;
	}

	private static String mergeEntities(String mergeAddr, List<Word> recognizedWords) {
		if(mergeAddr.contains("ADDRADDR")){
			int endIndex = mergeAddr.indexOf("ADDRADDR");
			String prefix = mergeAddr.substring(0, endIndex);
			
			mergeAddr = mergeAddr.replaceFirst("ADDRADDR", "ADDR");
			int wordIndex = StringUtils.countMatches(prefix,"ADDR");
			
			Word word0 = recognizedWords.get(wordIndex);
			Word word1 = recognizedWords.get(wordIndex+1);
			recognizedWords.remove(wordIndex);
			word1.setWord(word0.getWord()+" "+word1.getWord());
		}
		return mergeAddr;
	}


	public static Collection<TraceTextGroup> groupTraceTexts(List<TraceText> lines) {
		Map<String, TraceTextGroup> traceTexts = new HashMap<String, TraceTextGroup>();
		for (TraceText traceText : lines) {
			String line = traceText.getFilteredText();
			if (traceTexts.get(line) == null) {
				TraceTextGroup count = new TraceTextGroup(line);
				traceTexts.put(line, count);
			}
			TraceTextGroup count = traceTexts.get(line);
			count.getTraceTexts().add(traceText);
		}
		return traceTexts.values();
	}


}
