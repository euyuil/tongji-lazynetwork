package sse.provider;

import java.io.Serializable;
import java.util.List;

public interface IHandler extends Serializable {

	public Long getId();

	public void publish(Object content) throws Exception;
	public List<IPostEntry> getFriendsTimeline();
}
