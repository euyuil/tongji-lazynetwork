package sse.provider.sina;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import sse.db.pojo.UserUtil;
import weibo4j.examples.WebOAuth;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = -4143976487598758198L;

	public static final String FAILURE = "failure";

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() {

		request.getSession().setMaxInactiveInterval(604800);

		String verifier = request.getParameter("oauth_verifier");

		// 说明是用户主动想要登录微博。
		if (verifier == null || verifier.isEmpty()) {

			System.out.println(request.getProtocol() + request.getHeader("Host") + request.getContextPath());
			RequestToken requestToken = WebOAuth
					.request("http://localhost:8080/LazyNetwork/login/sina");
			request.getSession().setAttribute("request_token", requestToken);
			try {
				response.sendRedirect(requestToken.getAuthorizationURL());
				// 这会把 routine 破坏掉。
			} catch (IOException e) {
				e.printStackTrace();
				return ERROR;
			}

			// 新浪微博的反馈。
		} else {

			RequestToken requestToken = (RequestToken) request.getSession()
					.getAttribute("request_token");
			AccessToken accessToken = WebOAuth.requstAccessToken(requestToken,
					verifier);
			// request.getSession().setAttribute("access_token", accessToken);

			// 获得用户在新浪微博的 ID.
			Long externalId = accessToken.getUserId();
			if (externalId == null || externalId.longValue() == 0)
				return FAILURE;

			Account.getAccountByLogin(request, accessToken, externalId);
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
		this.response = response;
	}

	public Long getUserId() {
		if (UserUtil.instance().getCurrentUser() != null)
			return UserUtil.instance().getCurrentUser().getId();
		return null;
	}
}
