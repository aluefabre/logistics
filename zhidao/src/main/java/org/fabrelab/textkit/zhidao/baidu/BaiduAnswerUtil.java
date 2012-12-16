package org.fabrelab.textkit.zhidao.baidu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.pagekit.baidu.BaiduSession;
import org.fabrelab.pagekit.baiduzhidao.model.BaiduZhidaoQuestion;

public class BaiduAnswerUtil {

	static Pattern urlIdPattern = Pattern.compile("http://zhidao.baidu.com/question/(\\d+).html");
	public static boolean answer(String user, String password, BaiduZhidaoQuestion question) {
		if(question == null){
			return false;
		}
		if(question.isAnswered()){
			return false;
		}
		if(!question.isAnswerable()){
			return false;
		}
		if(StringUtils.isBlank(question.getAnswer())){
			return false;
		}
		try {
			BaiduSession session = new BaiduSession();
			session.login(user, password);
			String url = question.getUrl();
			Matcher matcher = urlIdPattern.matcher(url);
			while(matcher.find()){
				String id = matcher.group(1);
				session.publishAnswer(id, question.getAnswer());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
