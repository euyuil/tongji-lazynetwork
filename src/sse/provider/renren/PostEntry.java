package sse.provider.renren;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import sse.provider.IPostEntry;
import com.oauth.demo.model.Feed;

public class PostEntry implements IPostEntry {

	private Feed feed;

	public PostEntry(Feed feed) {
		this.feed = feed;
	}
	
	@Override
	public String getAuthorName() {
		if (feed == null) return null;
		return feed.getActor_id();
	}

	@Override
	public String getContent() {
		if (feed == null) return null;
		return feed.getTitle();
	}

	@Override
	public Date getPublishDate() {
		return feed.getUpdate_time();
	}

	@Override
	public String getThumbnail() {
		return null;
	}

	@Override
	public String getThumbnailSmall() {
		return feed.getUrl_main();
	}

	@Override
	public String getThumbnailSmallAlt() {
		return "单击查看大图";
	}

	@Override
	public String getHtmlExcerpt() {

		StringBuilder sb = new StringBuilder();

		String authorAvatar = String.format(
				"<span class=\"author-avatar\"><img src=\"%s\" alt=\"%s\" /></span>",
				StringEscapeUtils.escapeHtml(getAuthorAvatar()),
				StringEscapeUtils.escapeHtml(getAuthorName()));
		sb.append(authorAvatar);

		String authorName = String.format(
				"<span class=\"author-name\">%s</span>",
				StringEscapeUtils.escapeHtml(getAuthorName()));
		sb.append(authorName);

		String entryContent = String.format(
				"<span class=\"entry-content\">%s</span>",
				StringEscapeUtils.escapeHtml(getContent()));
		sb.append(entryContent);

		if (getThumbnailSmall() != null && !getThumbnailSmall().isEmpty()) {
			String thumbnailSmall = String.format(
					"<span class=\"thumbnail-small\"><img src=\"%s\" alt=\"%s\" /></span>",
					StringEscapeUtils.escapeHtml(getThumbnailSmall()),
					StringEscapeUtils.escapeHtml(getThumbnailSmallAlt()));
			sb.append(thumbnailSmall);
		}

		sb.append(getPublishDate().toString());

		return sb.toString();
	}

	@Override
	public String getAuthorAvatar() {
		return feed.getHeadurl();
	}
}
