package org.fabrelab.chayiba.service;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.chayiba.model.Answer;
import org.fabrelab.chayiba.model.Question;
import org.fabrelab.textkit.logistics.question.LogisticsQuestion;
import org.fabrelab.textkit.logistics.question.QuestionClassifier;
import org.fabrelab.textkit.logistics.question.WeiboAnswerBuilder;

public class ChayibaService {


	QuestionClassifier analysisProcesser = new QuestionClassifier();

	WeiboAnswerBuilder answerProcesser = new WeiboAnswerBuilder();
	
	public Answer process(Question question) {
				
		String text = question.getText();
		
		text = StringUtils.replace(text, "@", "%");
		
		LogisticsQuestion lquestion = new LogisticsQuestion(text, "");
		
		analysisProcesser.process(lquestion);
		
		answerProcesser.process(lquestion);
		
		String answer = lquestion.getAnswer();
		if(answer.length()>110){
			answer = answer.substring(0, 110);
		}
		if(StringUtils.isBlank(answer)){
			answer = "此单号无跟踪记录，请核对物流公司名称和运单号码是否正确！";
		}
		
		answer = answer + " http://56.1688.com/order/logistics_trace.htm";
		
     	answer = answer.replaceAll("签收", "签収");//Work Around error: contain advertising! error_code:20020/2/comments/create.json
		answer = answer.replaceAll("经营", "");
		answer = answer.replaceAll("收件", "収件");
		answer = answer.replaceAll("公司", "");
		
		return new Answer(question.getQuestionId(), answer, question.getUid());
	}
	
}
