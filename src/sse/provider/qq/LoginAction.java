package sse.provider.qq;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import sse.db.pojo.UserUtil;
import sse.provider.qq.Handler;

import com.opensymphony.xwork2.ActionSupport;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.beans.QParameter;
import com.tencent.weibo.utils.OAuthClient;

public class LoginAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = -4143976487598758198L;
	private static final String REQUEST_TOKEN = "request_token_qq";

	public static final String FAILURE = "failure";

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() {

		request.getSession().setMaxInactiveInterval(604800);

		String verifier = request.getParameter("oauth_verifier");

		com.tencent.weibo.beans.OAuth oauth =
			new com.tencent.weibo.beans.OAuth();
		OAuthClient auth = new OAuthClient();

		// 说明是用户主动想要登录微博。
		if (verifier == null || verifier.isEmpty()) {

			oauth.setOauth_callback("http://localhost:8080/LazyNetwork/login/qq");
			try {
				oauth = auth.requestToken(oauth);
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}

			if (oauth.getStatus() == 1) {
				System.out.println("Get Request Token failed!");
				return ERROR;
			} else {
				String oauth_token = oauth.getOauth_token();
				String url = "http://open.t.qq.com/cgi-bin/authorize?oauth_token="
						+ oauth_token;
				request.getSession().setAttribute(REQUEST_TOKEN, oauth);
				try {
					response.sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
					return ERROR;
				}
			}

			// QQ 微博的反馈。
		} else {

			String verify = verifier;
			oauth = (OAuth) request.getSession().getAttribute(REQUEST_TOKEN);
			oauth.setOauth_verifier(verify);
			try {
				oauth = auth.accessToken(oauth);
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			if (oauth.getStatus() == 2) {
				System.out.println("Get Access Token failed!");
				return ERROR;
			} else { // 说明成功了。
				for (QParameter param : oauth.getParams()) {
					System.out.println(param.getName() + " -> " + param.getValue());
				}
				String externalName = oauth.getAccount().getName();
				System.out.println("External Name: " + externalName + ".");
				// 获得用户在新浪微博的 ID.

				if (externalName == null || externalName.isEmpty())
					return FAILURE;

				Handler.getAccountByLogin(request, oauth, externalName);
				if (UserUtil.instance().getCurrentUser() == null)
					return ERROR;
			}
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
		this.response = response;
	}

	public Long getUserId() {
		if (UserUtil.instance().getCurrentUser() != null)
			return UserUtil.instance().getCurrentUser().getId();
		return null;
	}
}
