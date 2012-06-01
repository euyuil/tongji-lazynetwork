package sse.user;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import sse.db.HibSessManager;
import sse.db.HibTransManager;
import sse.db.pojo.UserUtil;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TUser;
import sse.provider.HandlerHelper;
import sse.provider.IHandler;

import com.opensymphony.xwork2.ActionSupport;

public class PublishAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 3990538061629827827L;
	private static final Logger log = LoggerFactory.getLogger(PublishAction.class);

	@SuppressWarnings("unused")
	private HttpServletResponse response;
	private HttpServletRequest request;

	public String execute() {
		String content = request.getParameter("content");
		if (content == null || content.isEmpty()) {
			log.debug("Cannot publish: no content specified.");
			return ERROR; // TODO more detailed error types for NO CONTENT SPECIFIED.
		}
		// NOTICE this user object is detached. we should attach it again.
		HibSessManager.current().update(UserUtil.instance().getCurrentUser());
		Set<TAccount> accounts = new TransactionTemplate(HibTransManager.instance())
				.execute(new GetUserAccountsTransaction());
		if (accounts == null) {
			log.debug("Cannot publish: user did not login.");
			return ERROR; // TODO more detailed error types for USER NOT LOGIN.
		} else if (accounts.isEmpty()) {
			log.debug("Cannot publish: user has no account.");
			return ERROR; // TODO user didn't add any account.
		}
		for (TAccount account : accounts) {
			IHandler acc = HandlerHelper.buildHandlerFromAccount(account);
			try {
				acc.publish(content);
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		return SUCCESS;
	}

	private class GetUserAccountsTransaction implements TransactionCallback<Set<TAccount>> {
		@Override
		public Set<TAccount> doInTransaction(TransactionStatus status) {
			TUser user = UserUtil.instance().getCurrentUser();
			if (user == null)
				return null;
			return user.getTAccounts();
		}
	}

	public Long getUserId() {
		if (UserUtil.instance().getCurrentUser() != null)
			return UserUtil.instance().getCurrentUser().getId();
		return null;
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
