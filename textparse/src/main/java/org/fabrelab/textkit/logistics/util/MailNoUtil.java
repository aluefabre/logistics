package org.fabrelab.textkit.logistics.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailNoUtil {
	static Pattern mailNoPattern = Pattern.compile("[A-Za-z\\d]+");
	public static List<String> parseMailNos(String extractedText) {
		Matcher matcher = mailNoPattern.matcher(extractedText);
		List<String> foundMailNos = new ArrayList<String>();
		while(matcher.find()){
			String group = matcher.group(0);
			if(group.length()>5){
				if(!foundMailNos.contains(group)){
					foundMailNos.add(group);
					extractedText = extractedText + " " + group;
				}
			}
		}
		return foundMailNos;
	}
}
