package org.fabrelab.chayiba.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.fabrelab.chayiba.service.QuestionProcessor;
import org.fabrelab.chayiba.store.AccessTokenStore;
import org.fabrelab.pagekit.MappingUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;


public class GatewayController implements Controller {


	private Logger log = Logger.getLogger(QuestionProcessor.class);
	
	AccessTokenStore accessTokenStore;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Map model = new HashMap();
        try {

			Oauth oauth = new Oauth();
			
			String authorizeUrl = oauth.authorize("code");
			model.put("authorizeUrl", authorizeUrl);
			
            String code = MappingUtil.parseString(request, "code");
			if(StringUtils.isNotBlank(code)){
				AccessToken token = oauth.getAccessTokenByCode(code);
				accessTokenStore.add(token.getUid(), token);
				Weibo weibo = new Weibo();
				weibo.setToken(token.getAccessToken());
				
				Account am = new Account();
				String uid = (String)am.getUid().get("uid");
				
				model.put("uid", token.getUid());
				Users um = new Users();
				User user = um.showUserById(uid);
				model.put("user", user);
			}
			
		} catch (WeiboException e) {
			log.error(e);
		} catch (JSONException e) {
			log.error(e);
		}
        return new ModelAndView("gateway", "model", model);
    }

	public void setAccessTokenStore(AccessTokenStore accessTokenStore) {
		this.accessTokenStore = accessTokenStore;
	}

}