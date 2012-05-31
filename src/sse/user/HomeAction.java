package sse.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

import sse.db.HibTransManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.UserUtil;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TUser;
import sse.provider.IAccount;
import sse.provider.IPostEntry;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 3990538061629827827L;
	private static final Logger log = LoggerFactory.getLogger(HomeAction.class);

	@SuppressWarnings("unused")
	private HttpServletResponse response;
	private HttpServletRequest request;
	private ArrayList<IPostEntry> entries;

	public String execute() {
		log.debug("Home owner ID: " + request.getParameter(UserUtil.USER_ID) + ".");
		TUser user = UserUtil.instance().getCurrentUser();
		if (user != null && user.getId().toString().equals(request.getParameter(UserUtil.USER_ID))) {
			// 说明当前登陆的用户是这个首页的主人。
			entries = new TransactionTemplate(
					HibTransManager.instance()).execute(new GetPostEntriesTransaction());
			for (int i = 0; i < entries.size(); ++i) {
				if (entries.get(i) == null)
					entries.remove(i--);
			}
			Collections.sort(entries, new EntryDateComparator());
		}
		return SUCCESS;
	}

	private class EntryDateComparator implements Comparator<IPostEntry> {
		@Override
		public int compare(IPostEntry a, IPostEntry b) {
			long atime = 0, btime = 0;
			try {
				atime = a.getPublishDate().getTime();
				btime = b.getPublishDate().getTime();
			} catch (Exception e) { }
			if (atime > btime) return -1;
			else if (atime < btime) return 1;
			return 0;
		}
	}

	private class GetPostEntriesTransaction implements TransactionCallback<ArrayList<IPostEntry>> {
		@Override
		public ArrayList<IPostEntry> doInTransaction(TransactionStatus status) {
			TUser user = UserUtil.instance().getCurrentUser();
			if (user == null) return null;
			user = UserUtil.instance().findById(user.getId());
			if (user == null) return null;
			Set<TAccount> accounts = user.getTAccounts();
			ArrayList<IPostEntry> entries = new ArrayList<IPostEntry>();
			if (accounts != null) {
				for (TAccount account : accounts) {
					IAccount acc = AccountUtil.buildAccountFromEntity(account);
					List<IPostEntry> ents = acc.getFriendsTimeline();
					if (ents != null)
						entries.addAll(ents);
				}
			}
			return entries;
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setEntries(ArrayList<IPostEntry> entries) {
		this.entries = entries;
	}

	public List<IPostEntry> getEntries() {
		return entries;
	}
}
