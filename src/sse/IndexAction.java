package sse;

import sse.db.pojo.UserUtil;
import sse.db.pojo.gen.TUser;

import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -7035435968596382756L;

	public String execute() {
		return SUCCESS;
	}

	public TUser getCurrentUser() {
		return UserUtil.instance().getCurrentUser();
	}
}
