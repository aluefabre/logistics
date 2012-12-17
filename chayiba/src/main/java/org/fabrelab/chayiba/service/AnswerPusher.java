package org.fabrelab.chayiba.service;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.fabrelab.chayiba.adapter.WeiboService;
import org.fabrelab.chayiba.model.Answer;
import org.fabrelab.chayiba.store.AccessTokenStore;

public class AnswerPusher extends Thread {

	private Logger log = Logger.getLogger(AnswerPusher.class);
	
	BlockingQueue<Answer> answerQueue;

	WeiboService weiboService;
	
	AccessTokenStore accessTokenStore;
	
	public void run(){
		this.setName("AnswerPusher");
		while(true){
			try {
				Answer answer = answerQueue.take();
				String userId = accessTokenStore.getRandomUserId();
				weiboService.postComment(userId, answer);				
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}


	public void setAnswerQueue(BlockingQueue<Answer> answerQueue) {
		this.answerQueue = answerQueue;
	}


	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public void setAccessTokenStore(AccessTokenStore accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}
	
}
