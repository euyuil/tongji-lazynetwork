package sse.provider.sina;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

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

	@Override
	public Date getPublishDate() {
		return status.getCreatedAt();
	}

	@Override
	public String getThumbnail() {
		return status.getOriginal_pic();
	}

	@Override
	public String getThumbnailSmall() {
		return status.getBmiddle_pic();
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
		return status.getUser().getProfileImageURL().toString();
	}
}
