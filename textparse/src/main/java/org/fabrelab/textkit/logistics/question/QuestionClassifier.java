package org.fabrelab.textkit.logistics.question;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fabrelab.textbreaker.core.TextAnalyser;
import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textbreaker.core.model.Phrase;
import org.fabrelab.textbreaker.core.model.PhraseTree;
import org.fabrelab.textbreaker.core.model.TextAnalysisContext;
import org.fabrelab.textkit.logistics.question.QuestionDictionaryLoader;
import org.fabrelab.textkit.logistics.question.QuestionGrammerLoader;

public class QuestionClassifier  {

	Dictionary dic = QuestionDictionaryLoader.loadDictionary();
	Grammer grammer = QuestionGrammerLoader.loadGrammer();

	TextAnalyser analyser = new TextAnalyser(dic, grammer);

	public void process(LogisticsQuestion question) {
		if(question.getTitle()!=null){
			question.setTitle(question.getTitle().toUpperCase());
		}
		if(question.getContent()!=null){
			question.setContent(question.getContent().toUpperCase());
		}
		List<TextAnalysisContext> titleContexts = analyser.analysis(question.getTitle());
		List<TextAnalysisContext> contentContexts = analyser.analysis(question.getContent());
		String abstractedText = "";

		List<Phrase> phrases = new ArrayList<Phrase>();

		for (TextAnalysisContext context : titleContexts) {
			PhraseTree phraseTree = context.getSentenceParserResult().getPhraseTree();
			abstractedText = abstractedText + phraseTree.getAbstractedText() + "\n" ;
			phrases.addAll(phraseTree.getPhrases());
		}
		for (TextAnalysisContext context : contentContexts) {
			PhraseTree phraseTree = context.getSentenceParserResult().getPhraseTree();
			abstractedText = abstractedText + phraseTree.getAbstractedText() + "\n" ;
			phrases.addAll(phraseTree.getPhrases());
		}
		process(question, abstractedText, phrases);

	}

	Pattern mailNoPattern = Pattern.compile("[A-Za-z\\d]+");
	private boolean process(LogisticsQuestion question, String abstractedText, List<Phrase> phrases) {
		question.setAnalysisResult(abstractedText);
		if (abstractedText.contains("@GBG")) {
			question.setType("GARBAGE");
			for (Phrase phrase : phrases) {
				if ("@GBG".equals(phrase.getPosTag().getName())) {
					question.setExtractedText(phrase.getFromText());
					break;
				}
			}
			return true;
		} 

		if (abstractedText.contains("@WORK")) {
			question.setType("WORK");
			for (Phrase phrase : phrases) {
				if ("@WORK".equals(phrase.getPosTag().getName())) {
					question.setExtractedText(phrase.getFromText());
					break;
				}
			}
			return true;
		} 

		if ((abstractedText.contains("@TOID") && abstractedText.contains("@TCPBRAND")) || abstractedText.contains("@TRACE")) {
			question.setType("TRACE");
			String extractedText = "";
			for (Phrase phrase : phrases) {
				if ("@TCPBRAND".equals(phrase.getPosTag().getName())) {
					extractedText = phrase.getFromText();
				}
			}
			Matcher matcher = mailNoPattern.matcher(question.getTitle()+ " " + question.getContent());
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
			question.setExtractedText(extractedText);
			return true;
		} 
		
		if (abstractedText.contains("@TCPBRAND")) {
			String extractedText = "";
			for (Phrase phrase : phrases) {
				if ("@TCPBRAND".equals(phrase.getPosTag().getName())) {
					extractedText = phrase.getFromText();
				}
			}
			Matcher matcher = mailNoPattern.matcher(question.getTitle()+ " " + question.getContent());
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
			if(foundMailNos.size()>0){
				question.setType("TRACE");
				question.setExtractedText(extractedText);
				return true;
			}
		} 
		
		if (abstractedText.contains("@TCPBRAND") && abstractedText.contains("@ADDR") && abstractedText.contains("@CONTACT")) {
			question.setType("CONTACT");
			String extractedText = "";
			for (Phrase phrase : phrases) {
				if ("@TCPBRAND".equals(phrase.getPosTag().getName())) {
					extractedText = phrase.getFromText();
				}
			}
			for (Phrase phrase : phrases) {
				if ("@ADDR".equals(phrase.getPosTag().getName())) {
					extractedText = extractedText +" "+phrase.getFromText();
				}
			}
			question.setExtractedText(extractedText);
			return true;
		} 

		if (abstractedText.contains("@ROUTE")) {
			question.setType("ROUTE");
			String extractedText = "";
			for (Phrase phrase : phrases) {
				if ("@TCPBRAND".equals(phrase.getPosTag().getName())) {
					extractedText = phrase.getFromText();
				}
			}
			for (Phrase phrase : phrases) {
				if ("@ROUTE".equals(phrase.getPosTag().getName())) {
					for(Phrase child :phrase.getChildren()){
						if ("@ADDR".equals(child.getPosTag().getName())) {
							extractedText = extractedText+child.getFromText()+" ";
						}
					}
				}
			}
			question.setExtractedText(extractedText);
			return true;
		} 

		if (abstractedText.contains("@TRANS") && abstractedText.contains("@ADDR")) {
			question.setType("NETWORK");
			String extractedText = "";
			for (Phrase phrase : phrases) {
				if ("@TCPBRAND".equals(phrase.getPosTag().getName())) {
					extractedText = phrase.getFromText();
				}
			}
			for (Phrase phrase : phrases) {
				if ("@ADDR".equals(phrase.getPosTag().getName())) {
					extractedText = extractedText+phrase.getFromText()+" ";
				}
			}
			question.setExtractedText(extractedText);
			return true;
		} 

		question.setType("UNKNOWN");
		return false;
	}

}
