package sse.provider;

import java.util.Date;

public interface IPostEntry {

	public String getAuthorName();
	public String getContent();
	public Date getPublishDate();
	public String getThumbnail();
	public String getThumbnailSmall();
	public String getThumbnailSmallAlt();
	public String getAuthorAvatar();

	public String getHtmlExcerpt();
}
