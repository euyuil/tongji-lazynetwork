package sse.provider.renren;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import sse.db.pojo.UserUtil;
import sse.provider.renren.Handler;

import com.oauth.demo.model.AccessToken;
import com.oauth.demo.model.RenrenSessionKey;
import com.oauth.demo.util.CommonConst;
import com.oauth.demo.util.LoadProperties;
import com.oauth.demo.util.XiaoneiOauthAccessTokenUtil;
import com.oauth.demo.util.XiaoneiRestfulApiUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4979900443703539553L;
	private HttpServletRequest request = ServletActionContext.getRequest();  
	private HttpServletResponse response = ServletActionContext.getResponse();
	public static final String FAILURE = "failure";

	public String execute() {
		//verifier是什么？
		String verifier = request.getParameter("code");

		// 说明是用户主动想要登录微博。
		if (verifier == null || verifier.isEmpty()) {
			String apikey = LoadProperties.readValue("RENREN_API_KEY");
			String redirect_url = "http://localhost:8080/LazyNetwork/login/renren";
			String authorizeurl = "";
			authorizeurl += CommonConst.RENREN_OAUTH_AUTHORIZE_URL;
			authorizeurl += "?client_id=" + apikey;
			authorizeurl += "&redirect_uri=" + redirect_url;
			authorizeurl += "&response_type=code";
			authorizeurl += "&scope=status_update+read_user_feed+read_user_status+publish_comment+read_user_photo+read_user_comment";
			try {
				response.sendRedirect(authorizeurl);
			} catch (IOException e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			String redirect_url = "http://localhost:8080/LazyNetwork/login/renren";
			String authorization_code = verifier;
			AccessToken accesstoken = new AccessToken();
			accesstoken = XiaoneiOauthAccessTokenUtil.getAccessToken(
					authorization_code, redirect_url);
			RenrenSessionKey sk = XiaoneiRestfulApiUtil
			.getXiaonei_SessionKey_info(accesstoken
					.getAccess_token());
			accesstoken.setRenren_id(sk.getRenrenid());
			Long externalId = (long) sk.getRenrenid();
			if (externalId == null || externalId.longValue() == 0)
				return FAILURE;
			Handler.getAccountByLogin(request, accesstoken, externalId);
			if (UserUtil.instance().getCurrentUser() == null)
				return ERROR;
		}
		return SUCCESS;
	}

	public String failure() {
		return FAILURE;
	}

	public String error() {
		return ERROR;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
	}

	public Long getUserId() {
		if (UserUtil.instance().getCurrentUser() != null)
			return UserUtil.instance().getCurrentUser().getId();
		return null;
	}
}
