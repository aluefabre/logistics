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
			answer = "�˵����޸��ټ�¼����˶�������˾���ƺ��˵������Ƿ���ȷ��";
		}
		
		answer = answer + " http://56.1688.com/order/logistics_trace.htm";
		
     	answer = answer.replaceAll("ǩ��", "ǩ��");//Work Around error: contain advertising! error_code:20020/2/comments/create.json
		answer = answer.replaceAll("��Ӫ", "");
		answer = answer.replaceAll("�ռ�", "����");
		answer = answer.replaceAll("��˾", "");
		
		return new Answer(question.getQuestionId(), answer, question.getUid());
	}
	
}
