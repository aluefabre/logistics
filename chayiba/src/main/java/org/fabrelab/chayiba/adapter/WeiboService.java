package org.fabrelab.chayiba.adapter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.fabrelab.chayiba.model.Answer;
import org.fabrelab.chayiba.service.AnswerPusher;
import org.fabrelab.chayiba.service.QuestionProcessor;
import org.fabrelab.chayiba.service.QuestionPuller;
import org.fabrelab.chayiba.store.AccessTokenStore;
import org.fabrelab.chayiba.store.AnswerHistoryStore;

import weibo4j.Comments;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

public class WeiboService {

	private Logger log = Logger.getLogger(WeiboService.class);
	
	// error:expired_token error_code:21327/2/statuses/mentions.json
	private static final int EXPIRED_TOKEN_ERROR_CODE = 21327;
	
	AccessTokenStore accessTokenStore;
	AnswerHistoryStore answerHistoryStore;
	QuestionPuller questionPuller;
	QuestionProcessor questionProcessor;
	AnswerPusher answerPusher;

	public void init() throws WeiboException {
		refreshToken(accessTokenStore.getMainRobotId());
		for(String assistantId : AccessTokenStore.ASSISTANTS){
			refreshToken(assistantId);
		}
		answerHistoryStore.init();
		questionPuller.start();
		questionProcessor.start();
		answerPusher.start();
	}

	public List<Comment> getCommentByMe(String userId) {
		try {
			return getCommentByMeInner(userId);
		} catch (WeiboException e) {
			try {
				if (e.getErrorCode() == EXPIRED_TOKEN_ERROR_CODE) {
					refreshToken(userId);
					return getCommentByMeInner(userId);
				}
			} catch (WeiboException we) {
				log.error(we);
			}
			log.error(e);
		}
		return null;
	}

	private void refreshToken(String userId) throws WeiboException {
		Oauth oauth = new Oauth();
		String accountName = accessTokenStore.getAccount(userId);
		String password = accessTokenStore.getPassword(userId);
		AccessToken token = oauth.refreshToken(accountName, password);
		accessTokenStore.add(userId, token);
	}

	private List<Comment> getCommentByMeInner(String userId) throws WeiboException {
		AccessToken token = accessTokenStore.get(userId);
		if(token==null){
			refreshToken(userId);
		}
		Weibo weibo = new Weibo();
		weibo.setToken(token.getAccessToken());
		Comments cm = new Comments();

		CommentWapper comment = cm.getCommentByMe();
		return comment.getComments();
	}

	public List<Status> getMentions(String userId, String sinceId) {

		try {
			return getMentionsInner(userId, sinceId);
		} catch (WeiboException e) {
			try {
				if (e.getErrorCode() == EXPIRED_TOKEN_ERROR_CODE) {
					refreshToken(userId);
					return getMentions(userId, sinceId);
				}
			} catch (WeiboException we) {
				log.error(we);
			}
			log.error(e);
		}
		return null;
	}

	private List<Status> getMentionsInner(String userId, String sinceId) throws WeiboException {
		Weibo weibo = new Weibo();
		AccessToken token = accessTokenStore.get(userId);
		if(token==null){
			refreshToken(userId);
		}
		weibo.setToken(token.getAccessToken());
		Timeline tm = new Timeline();
		if (StringUtils.isBlank(sinceId)) {
			StatusWapper status = tm.getMentions();
			return status.getStatuses();
		} else {
			StatusWapper status = tm.getMentions(sinceId);
			return status.getStatuses();
		}
	}

	public void postComment(String userId, Answer comment) {
		log.info("Answer Generated: " + comment.getText());
		try {
			postCommentInner(userId, comment);
		} catch (WeiboException e) {
			try {
				if (e.getErrorCode() == EXPIRED_TOKEN_ERROR_CODE) {
					refreshToken(userId);
					postCommentInner(userId, comment);
				}
			} catch (WeiboException we) {
				log.error(we);
			}
			log.error(e);
		}
	}

	private void postCommentInner(String userId, Answer comment) throws WeiboException {
		AccessToken token = accessTokenStore.get(userId);
		if(token==null){
			refreshToken(userId);
		}
		Weibo weibo = new Weibo();
		weibo.setToken(token.getAccessToken());
		Comments cm = new Comments();
		cm.createComment(comment.getText(), comment.getQuestionId());
	}

	public void setAccessTokenStore(AccessTokenStore accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}

	public void setAnswerHistoryStore(AnswerHistoryStore answerHistoryStore) {
		this.answerHistoryStore = answerHistoryStore;
	}

	public void setQuestionPuller(QuestionPuller questionPuller) {
		this.questionPuller = questionPuller;
	}

	public void setQuestionProcessor(QuestionProcessor questionProcessor) {
		this.questionProcessor = questionProcessor;
	}

	public void setAnswerPusher(AnswerPusher answerPusher) {
		this.answerPusher = answerPusher;
	}

}
