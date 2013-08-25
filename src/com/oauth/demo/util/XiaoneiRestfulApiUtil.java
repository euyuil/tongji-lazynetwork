package com.oauth.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.oauth.demo.model.RenrenSessionKey;
import com.oauth.demo.model.Feed;
import com.oauth.demo.model.Photo;
import com.oauth.demo.model.Comment;
import com.oauth.demo.model.User;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;
import com.renren.api.client.param.impl.SessionKey;

/**
 * 采用开放平台最新的java sdk包来调用restful api接口
 * 
 */
public class XiaoneiRestfulApiUtil {

	/**
	 * 传入access_token 获取session_key
	 */
	public static RenrenSessionKey getXiaonei_SessionKey_info(
			String access_token) {
		RenrenSessionKey sessionkey = new RenrenSessionKey();
		PostMethod method = new PostMethod(
				CommonConst.RENREN_API_SESSIONKEY_URL);
		method.addParameter("oauth_token", access_token);
		HttpClient client = new HttpClient();
		try {
			int statusCode = client.executeMethod(method);
			InputStream result = method.getResponseBodyAsStream();
			StringBuffer sessionkeyline = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					result, "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sessionkeyline.append(line);
			}
			// 判断当前状态码是否是200
			if (statusCode == HttpStatus.SC_OK) {
				sessionkey.setRenrenid(getRenrenUserId(sessionkeyline
						.toString()));
				sessionkey.setSessionkey(getSessionKey(sessionkeyline
						.toString()));
				return sessionkey;
			} else {// 有可能是accesstoken过期或是网络有问题
				return null;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 从json串中获取session_key
	 */
	public static String getSessionKey(String obj) {
		JSONObject renren_token;
		try {
			renren_token = new JSONObject(obj);
			JSONObject sessionkey_obj = new JSONObject(
					renren_token.getString("renren_token"));
			return sessionkey_obj.getString("session_key");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}

	/*
	 * 从json串中获取用户在人人网的id
	 */
	public static int getRenrenUserId(String obj) {
		JSONObject renren_userinfo;
		try {
			renren_userinfo = new JSONObject(obj);
			JSONObject sessionkey_obj = new JSONObject(
					renren_userinfo.getString("user"));
			return Integer.valueOf(sessionkey_obj.getString("id"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}

	/*
	 * 传入sessionkey json串，调用api 获取用户信息接口 把人人网 用户信息封装成本地user
	 */
	public static User getUserInfo(String sessionkey, String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		/*
		 * 调用JAVA SDK 传入参数sessionkey
		 */
		SessionKey auth = new SessionKey(sessionkey);
		RenrenApiClient client = RenrenApiClient.getInstance();
		String fields = "id,name,sex,birthday,tinyurl,headurl,mainurl";
		/*
		 * 传入userid fields 获取用户信息 返回JSONArray 数据
		 */
		JSONArray renren_user = client.getUserService().getInfo(userid, fields,
				auth);
		User user = new User();
		user.setHeadurl(((org.json.simple.JSONObject) renren_user.get(0)).get(
				"headurl").toString());
		user.setName(((org.json.simple.JSONObject) renren_user.get(0)).get(
				"name").toString());
		user.setId(Integer.valueOf(((org.json.simple.JSONObject) renren_user
				.get(0)).get("uid").toString()));

		return user;
	}

	/*
	 * 传入sessionkey，调用api获取好友信息接口
	 */
	public static List<User> getUserFriendInfo(String sessionkey, String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		String fields = "name,headurl";
		int page = 1;
		int count = 3;
		SessionKey auth = new SessionKey(sessionkey);
		List<Integer> friendIds = client.getFriendsService().getFriendIds(page,
				count, auth);
		List<User> userlist = new ArrayList<User>();
		for (int id : friendIds) {
			JSONArray renren_user = client.getUserService().getInfo(
					String.valueOf(id), fields, auth);
			User user = new User();
			user.setHeadurl(((org.json.simple.JSONObject) renren_user.get(0))
					.get("headurl").toString());
			user.setName(((org.json.simple.JSONObject) renren_user.get(0)).get(
					"name").toString());
			userlist.add(user);
		}
		return userlist;
	}

	public static List<Feed> getFeeds(String sessionkey, String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		int page = 1;
		int count = 12;
		JSONArray newFeeds = client.getFeedService().getFeed("10,30", 0, page,
				count, auth);
		List<Feed> feedlist = new ArrayList<Feed>();
		for (int i = 0; i < newFeeds.size(); i++) {
			Feed feed = new Feed();
			feed.setTitle(((org.json.simple.JSONObject) newFeeds.get(i)).get(
					"title").toString());
			feed.setSource_id(((org.json.simple.JSONObject) newFeeds.get(i))
					.get("source_id").toString());
			feed.setActor_id(((org.json.simple.JSONObject) newFeeds.get(i))
					.get("actor_id").toString());
			feed.setName(((org.json.simple.JSONObject) newFeeds.get(i)).get(
					"name").toString());
			feed.setHeadurl(((org.json.simple.JSONObject) newFeeds.get(i)).get(
					"headurl").toString());
			feed.setType(((org.json.simple.JSONObject) newFeeds.get(i)).get(
					"feed_type").toString());
			String time = (((org.json.simple.JSONObject) newFeeds.get(i))
					.get("update_time").toString());
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				feed.setUpdate_time(myFmt.parse(time));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (feed.getType().equals("30")) {
					feed.setPrefix(((org.json.simple.JSONObject) newFeeds
							.get(i)).get("prefix").toString());
					feed.setPhoto(getPhoto(feed.getSource_id(), sessionkey,
							feed.getActor_id()));
					feed.setUrl_main(feed.getPhoto().getUrl_main());
					feed.setComments(getPhotoComments(feed.getPhoto(),
							sessionkey, feed.getActor_id()));
				} else {
					feed.setComments(getComments(feed.getSource_id(),
							sessionkey, feed.getActor_id()));
				}
			} catch (Exception e) {
			}
			feedlist.add(feed);
		}
		return feedlist;
	}

	public static boolean setStatus(String sessionkey, String status) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		client.getStatusService().setStatus(status, auth);
		return true;
	}

	public static List<Comment> getComments(String status_id,
			String sessionkey, String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		int page = 1;
		int count = 12;
		JSONArray comments = client.getStatusService().getComments(
				Long.parseLong(status_id), Long.parseLong(userid), 1, count,
				page, auth);
		List<Comment> commentslist = new ArrayList<Comment>();
		for (int i = 0; i < comments.size(); i++) {
			Comment comment = new Comment();
			comment.setName(((org.json.simple.JSONObject) comments.get(i)).get(
					"name").toString());
			comment.setText(((org.json.simple.JSONObject) comments.get(i)).get(
					"text").toString());
			comment.setTime(((org.json.simple.JSONObject) comments.get(i)).get(
					"time").toString());
			comment.setComment_id(((org.json.simple.JSONObject) comments.get(i))
					.get("comment_id").toString());
			comment.setUid(((org.json.simple.JSONObject) comments.get(i)).get(
					"uid").toString());
			commentslist.add(comment);
		}
		return commentslist;
	}

	public static List<Comment> getPhotoComments(Photo photo,
			String sessionkey, String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		int page = 1;
		int count = 12;
		String uid = photo.getUid();
		String pid = photo.getPid();
		JSONArray comments = client.getPhotoService().getComments(
				Long.parseLong(uid), Long.parseLong(pid), page, count, true,
				auth);
		List<Comment> commentslist = new ArrayList<Comment>();
		for (int i = 0; i < comments.size(); i++) {
			Comment comment = new Comment();
			comment.setName(((org.json.simple.JSONObject) comments.get(i)).get(
					"name").toString());
			comment.setText(((org.json.simple.JSONObject) comments.get(i)).get(
					"text").toString());
			comment.setTime(((org.json.simple.JSONObject) comments.get(i)).get(
					"time").toString());
			comment.setComment_id(((org.json.simple.JSONObject) comments.get(i))
					.get("comment_id").toString());
			comment.setUid(((org.json.simple.JSONObject) comments.get(i)).get(
					"uid").toString());
			commentslist.add(comment);
		}
		return commentslist;
	}

	public static boolean addComment(String sessionkey, Long status_id,
			Long owner_id, String content, String rid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		client.getStatusService().addComment(status_id, owner_id, content,
				Long.parseLong(rid), auth);
		return true;
	}

	public static boolean addPhotoComment(String sessionkey, Long source_id,
			Long owner_id, String content) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		client.getPhotoService().addComment(owner_id, source_id, content, 0, 0,
				true, auth);
		return true;
	}

	public static boolean forward(String sessionkey, Long forward_id,
			Long forward_owner, String _forward) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		org.json.simple.JSONObject j_content = client.getStatusService()
				.getStatus(forward_id, forward_owner, auth);
		String content = j_content.get("message").toString() + _forward;
		client.getStatusService().forwardStatus(forward_id, forward_owner,
				content, auth);
		return true;
	}

	public static Photo getPhoto(String source_id, String sessionkey,
			String userid) {
		RenrenApiConfig.renrenApiKey = LoadProperties
				.readValue("RENREN_API_KEY");
		RenrenApiConfig.renrenApiSecret = LoadProperties
				.readValue("RENREN_API_SECRET");
		RenrenApiClient client = RenrenApiClient.getInstance();
		SessionKey auth = new SessionKey(sessionkey);
		int page = 0;
		int count = 12;
		JSONArray photoes = client.getPhotoService().getPhotos(
				Long.parseLong(userid), Long.parseLong(source_id), page, count,
				"", auth);
		List<?> photolist = new ArrayList<Object>();
		Photo photo = new Photo();
		photo.setAid(((org.json.simple.JSONObject) photoes.get(0)).get("aid")
				.toString());
		photo.setPid(((org.json.simple.JSONObject) photoes.get(0)).get("pid")
				.toString());
		photo.setUid(((org.json.simple.JSONObject) photoes.get(0)).get("uid")
				.toString());
		photo.setCaption(((org.json.simple.JSONObject) photoes.get(0)).get(
				"caption").toString());
		photo.setUrl_main(((org.json.simple.JSONObject) photoes.get(0)).get(
				"url_main").toString());
		photo.setUrl_tiny(((org.json.simple.JSONObject) photoes.get(0)).get(
				"url_tiny").toString());
		return photo;
	}
}
