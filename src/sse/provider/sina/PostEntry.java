package sse.provider.sina;

import sse.provider.IPostEntry;
import weibo4j.Status;

public class PostEntry implements IPostEntry {

	private Status status;

	public PostEntry(Status status) {
		this.status = status;
	}

	@Override
	public String getAuthorName() {
		if (status == null) return null;
		return status.getUser().getName();
	}

	@Override
	public String getContent() {
		if (status == null) return null;
		return status.getText();
	}
}
