package org.fabrelab.textkit.logistics.question;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textbreaker.core.grammer.rule.PhraseRule;
import org.fabrelab.textbreaker.core.grammer.rule.SentenceRule;
import org.fabrelab.textbreaker.core.model.Phrase;
import org.fabrelab.textbreaker.core.model.PhraseTree;
import org.fabrelab.textbreaker.core.model.PosTag;
import org.fabrelab.textbreaker.core.util.TextLoaderUtil;
import org.fabrelab.textbreaker.core.util.TextLoaderUtil.LineVisitor;

public class QuestionGrammerLoader {

	static String phraseRuleName = "/question/phrase.rule";
	
	public static Grammer loadGrammer() {		
		final List<PhraseRule> phraseRules = new ArrayList<PhraseRule>();
		LineVisitor visitor = new LineVisitor(){
			@Override
			public void visit(String line) {
				final String[] parts = StringUtils.split(line);
				phraseRules.add(new PhraseRule() {
					Pattern fromPattern  = Pattern.compile(parts[0].trim());
					PosTag toType = new PosTag(parts[1].trim());
					Integer groupIndex = Integer.parseInt(parts[2].trim());
					
					@Override
					public boolean match(PhraseTree phraseTree) {
						String abstractedText = phraseTree.getAbstractedText();
						Matcher matcher = fromPattern.matcher(abstractedText);
						if(matcher.find()){
							return true;
						}
						return false;
					}
					
					@Override
					public void apply(PhraseTree phraseTree) {
						String abstractedText = phraseTree.getAbstractedText();
						Matcher matcher = fromPattern.matcher(abstractedText);
						if(matcher.find()){
							String fromText = matcher.group(groupIndex);
							Phrase phrase = new Phrase(fromText, toType);
							phrase.setStartPoint(matcher.start());
							phraseTree.merge(phrase);
						}
					}
				});
			}
		};
		TextLoaderUtil.traverse(QuestionGrammerLoader.class, phraseRuleName, "utf-8", visitor);
		
		
		List<SentenceRule> sentenceRules = new ArrayList<SentenceRule>();
		return new Grammer(phraseRules, sentenceRules);
	}

}
