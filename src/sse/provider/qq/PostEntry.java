package sse.provider.qq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sse.provider.IPostEntry;

public class PostEntry implements IPostEntry {

	private static final int AVATAR_SIZE = 50;
	private static final String AVATAR_DEFAULT_URL = "http://mat1.gtimg.com/www/mb/images/head_50.jpg";

	private String authorName;
	private String content;
	private Date publishDate;
	private String authorAvatar;

	public static List<PostEntry> fromJson(String json) {
		List<PostEntry> result = new ArrayList<PostEntry>();
		try {
			JSONObject root = new JSONObject(json);
			JSONObject data = root.getJSONObject("data");
			JSONArray arr = data.getJSONArray("info");
			int arrlen = arr.length();
			for (int i = 0; i < arrlen; ++i)
				result.add(new PostEntry(arr.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public PostEntry(JSONObject json) {
		setAttributes(json);
	}

	private void setAttributes(JSONObject json) {
		// TODO: 谢代锦，请根据所给 URL 所述文档设置本类的属性。
		// http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E4%B8%BB%E9%A1%B5%E6%97%B6%E9%97%B4%E7%BA%BF
		try {
			authorName = json.getString("nick");
			content = json.getString("text");
			publishDate = new Date(json.getLong("timestamp") * 1000l);
			authorAvatar = json.getString("head");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getAuthorName() {
		return authorName;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public Date getPublishDate() {
		return publishDate;
	}

	@Override
	public String getThumbnail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThumbnailSmall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThumbnailSmallAlt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthorAvatar() {
		if (authorAvatar == null || authorAvatar.isEmpty())
			return AVATAR_DEFAULT_URL;
		return authorAvatar + "/" + AVATAR_SIZE;
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
				/*StringEscapeUtils.escapeHtml(*/getContent()/*)*/);
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
}
