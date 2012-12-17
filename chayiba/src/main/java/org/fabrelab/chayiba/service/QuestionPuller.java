package org.fabrelab.chayiba.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.fabrelab.chayiba.adapter.WeiboService;
import org.fabrelab.chayiba.model.Question;
import org.fabrelab.chayiba.store.AccessTokenStore;
import org.fabrelab.chayiba.store.AnswerHistoryStore;

import weibo4j.model.Status;

public class QuestionPuller extends Thread {
	

	private Logger log = Logger.getLogger(QuestionPuller.class);
	
	BlockingQueue<Question> questionQueue;
	
	WeiboService weiboService;
		
	AnswerHistoryStore answerHistoryStore;

	AccessTokenStore accessTokenStore;
	
	public void run(){
		this.setName("QuestionPuller");
		while(true){
			log.info("QuestionPuller is pulling");
			try {
				List<Status> metions = weiboService.getMentions(accessTokenStore.getMainRobotId(), answerHistoryStore.getMaxId()+"");
				if(metions!=null && metions.size()>0){
					for(Status status : metions){
						Question question = buildQuestion(status);
						log.info("Question Found: " + question.getText());
						questionQueue.put(question);
					}
				}else{
					log.info("No Question Found");
				}
			} catch (InterruptedException e) {
				log.error(e);
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	private Question buildQuestion(Status status) {
		return new Question(status.getId(), status.getText(), status.getUser().getId());
	}

	public void setQuestionQueue(BlockingQueue<Question> questionQueue) {
		this.questionQueue = questionQueue;
	}

	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public void setAnswerHistoryStore(AnswerHistoryStore answerHistoryStore) {
		this.answerHistoryStore = answerHistoryStore;
	}

	public void setAccessTokenStore(AccessTokenStore accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}
	
}
