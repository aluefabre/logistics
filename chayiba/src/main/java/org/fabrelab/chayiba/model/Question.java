package org.fabrelab.chayiba.model;

public class Question {
	String questionId;
	String text;
	String uid;
	
	public Question(String questionId, String text, String uid) {
		super();
		this.questionId = questionId;
		this.text = text;
		this.uid = uid;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
