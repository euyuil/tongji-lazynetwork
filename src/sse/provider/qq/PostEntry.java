package sse.provider.qq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sse.provider.IPostEntry;

public class PostEntry implements IPostEntry {

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
		// TODO: 谢代锦。。
	}

	@Override
	public String getAuthorName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getPublishDate() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHtmlExcerpt() {
		// TODO Auto-generated method stub
		return null;
	}

}
