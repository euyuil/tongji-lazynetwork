package sse.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sse.db.pojo.UserUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 这个 Action 可以把其它账号附加到现在登陆的用户名下。
 * @author Yue
 * @date 2012-05-31
 */

public class LogoutAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 7536570177654728461L;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LogoutAction.class);

	@SuppressWarnings("unused")
	private HttpServletResponse response;
	@SuppressWarnings("unused")
	private HttpServletRequest request;

	public String execute() {
		UserUtil.instance().setCurrentUser(null);
		return SUCCESS;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
