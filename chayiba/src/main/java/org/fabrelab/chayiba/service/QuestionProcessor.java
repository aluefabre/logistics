package org.fabrelab.chayiba.service;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.fabrelab.chayiba.model.Answer;
import org.fabrelab.chayiba.model.Question;
import org.fabrelab.chayiba.store.AnswerHistoryStore;


public class QuestionProcessor extends Thread {
	

	private Logger log = Logger.getLogger(QuestionProcessor.class);
	
	BlockingQueue<Question> questionQueue;
	
	BlockingQueue<Answer> answerQueue;
	
	ChayibaService chayibaService;
	
	AnswerHistoryStore answerHistoryStore;
	 
	public void run(){
		this.setName("QuestionProcessor");
		while(true){
			try {
				Question question = questionQueue.take();
				Answer answer = chayibaService.process(question);
				answerQueue.put(answer);
				answerHistoryStore.add(question, answer);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	public void setQuestionQueue(BlockingQueue<Question> questionQueue) {
		this.questionQueue = questionQueue;
	}

	public void setAnswerQueue(BlockingQueue<Answer> commentQueue) {
		this.answerQueue = commentQueue;
	}

	public void setChayibaService(ChayibaService chayibaService) {
		this.chayibaService = chayibaService;
	}

	public void setAnswerHistoryStore(AnswerHistoryStore answerHistoryStore) {
		this.answerHistoryStore = answerHistoryStore;
	}

}
