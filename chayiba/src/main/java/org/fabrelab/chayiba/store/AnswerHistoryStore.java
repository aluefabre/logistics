package org.fabrelab.chayiba.store;

import java.util.ArrayList;
import java.util.List;

import org.fabrelab.chayiba.adapter.WeiboService;
import org.fabrelab.chayiba.model.Answer;
import org.fabrelab.chayiba.model.Question;

import weibo4j.model.Comment;

public class AnswerHistoryStore {
	
	List<Answer> history = new ArrayList<Answer>();
	
	WeiboService weiboService;

	Long maxId = 0L;

	AccessTokenStore accessTokenStore;
	
	public void init(){
		for(String robotId : AccessTokenStore.ASSISTANTS){
			updateMaxID(robotId);
		}
	}

	private void updateMaxID(String robotId) {
		List<Comment> commentByMe = weiboService.getCommentByMe(robotId);
		for (Comment comment : commentByMe) {
			long statusId = comment.getStatus().getIdstr();
			if(maxId<statusId){
				maxId = statusId;
			}
		}
	}
	
	public void add(Question question, Answer answer){
		String qid = answer.getQuestionId();
		Long lqid = Long.parseLong(qid);
		if(maxId<lqid){
			maxId = lqid;
		}
		//history.add(answer);
	}

	public List<Answer> getHistory() {
		return history;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public void setAccessTokenStore(AccessTokenStore accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}


}
