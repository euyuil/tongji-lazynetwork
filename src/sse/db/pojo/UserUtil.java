package sse.db.pojo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import sse.Spring;
import sse.db.HibTransManager;
import sse.db.pojo.gen.TUser;
import sse.db.pojo.gen.TUserDAO;
import sse.user.HomeAction;

public class UserUtil extends TUserDAO {

	public static String USER_ID = "user_id";

	private static UserUtil springInstance = null;

	public static UserUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (UserUtil) Spring.getBean("UserUtil");
	}

	private static final Logger log = LoggerFactory.getLogger(HomeAction.class);

	private TUser currentUser = null;
	private boolean currentUserInitialized = false;
	private Long potentialUserId = null;

	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public TUser getCurrentUser() {
		if (getRequest() != null) {
			if (!currentUserInitialized) {
				potentialUserId = (Long) getRequest().getSession().getAttribute(USER_ID);
				if (potentialUserId != null) {
					currentUser = new TransactionTemplate(HibTransManager.instance())
							.execute(new GetCurrentUserEntityTransaction());
				}
				currentUserInitialized = true;
			}
		}
		log.debug("Current user: " + currentUser);
		return currentUser;
	}

	public void setCurrentUser(TUser user) {
		if (getRequest() != null) {
			if ((currentUser = user) == null) {
				getRequest().getSession().removeAttribute(USER_ID);
			} else {
				getRequest().getSession().setAttribute(USER_ID, currentUser.getId());
			}
		}
	}

	private class GetCurrentUserEntityTransaction implements TransactionCallback<TUser> {

		@Override
		public TUser doInTransaction(TransactionStatus status) {
			return UserUtil.this.findById(UserUtil.this.potentialUserId);
		}
	}
}
