package org.fabrelab.textkit.zhidao.processor;

import org.fabrelab.pagekit.baiduzhidao.QuestionProcessor;
import org.fabrelab.pagekit.baiduzhidao.model.BaiduZhidaoQuestion;
import org.fabrelab.textkit.logistics.question.TextAnswerBuilder;
import org.fabrelab.textkit.logistics.question.LogisticsQuestion;
import org.fabrelab.textkit.logistics.question.QuestionClassifier;
import org.fabrelab.textkit.zhidao.ZhidaoTool;

public class ToolProcessor implements QuestionProcessor {


	QuestionClassifier analysisProcesser = new QuestionClassifier();
	TextAnswerBuilder answerProcesser = new TextAnswerBuilder();
	
	public void process(BaiduZhidaoQuestion question) {
		boolean isGarbage = isGarbage(question);
		if(!isGarbage){
			LogisticsQuestion lquestion = new LogisticsQuestion(question.getTitle(), question.getAnswer());
			
			analysisProcesser.process(lquestion);
			
			answerProcesser.process(lquestion);
			
			question.setType(lquestion.getType());

			question.setAnalysisResult(lquestion.getAnalysisResult());
			question.setExtractedText(lquestion.getExtractedText());
			question.setAnswer(lquestion.getAnswer());
			
			ZhidaoTool.questions.add(question);
			
			ZhidaoTool.updateData();
		}
	}

	private boolean isGarbage(BaiduZhidaoQuestion question) {
		if(isGarbage(question.getTitle())){
			return true;
		}
		if(isGarbage(question.getContent())){
			return true;
		}
		return false;
	}

	private boolean isGarbage(String title) {
		if(title!=null){
			if(title.contains("骗子")){
				return true;
			}
			if(title.contains("诈骗")){
				return true;
			}
			if(title.contains("骗人")){
				return true;
			}
			if(title.contains("虚拟")){
				return true;
			}
			if(title.contains("考试")){
				return true;
			}
			if(title.contains("答案")){
				return true;
			}
			if(title.contains("垃圾")){
				return true;
			}
			if(title.contains("职称")){
				return true;
			}
			if(title.contains("翻译")){
				return true;
			}
			if(title.contains("骗")){
				return true;
			}
			if(title.contains("投诉")){
				return true;
			}
			if(title.contains("申诉")){
				return true;
			}
		}
		return false;
	}

	
}
