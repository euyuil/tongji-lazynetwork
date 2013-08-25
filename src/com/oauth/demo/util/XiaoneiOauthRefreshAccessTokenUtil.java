package com.oauth.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;

import com.oauth.demo.model.AccessToken;

/**
 * 根据refresh token获取一个access_token对象
 * 
 * @author Administrator
 * 
 */
public class XiaoneiOauthRefreshAccessTokenUtil {
	/**
	 * 使用Refresh Token作为Access Grant来获取Access Token，需要发送请求（推荐用POST）
	 * “https://graph.renren.com/oauth/token”，并传递以下参数： grant_type：使用Refresh
	 * Token 作为Access Grant时，此值为“refresh_token”。 refresh_token：Refresh Token；
	 * client_id：应用的API Key； client_secret：应用的Secret Key。
	 * 
	 * @param AccessToken
	 * @return
	 */

	public static AccessToken getAccessToken(String refresh_token) {
		
		String apikey=LoadProperties.readValue("RENREN_API_KEY");
		String secret=LoadProperties.readValue("RENREN_API_SECRET");
 
		PostMethod method = new PostMethod(
				CommonConst.RENREN_OAUTH_ACCESS_TOKEN_URL);
		method.addParameter("client_id", apikey);
		method.addParameter("client_secret",secret);
		method.addParameter("refresh_token", refresh_token);
		method.addParameter("grant_type", "authorization_code");
		HttpClient client = new HttpClient();
		AccessToken access_token = null;
		try {
			client.executeMethod(method);
			InputStream result = method.getResponseBodyAsStream();
			JSONObject jsonObj;
			try {
				StringBuffer accessline = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						result, "UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					accessline.append(line);
				}
				jsonObj = new JSONObject(accessline.toString());
				access_token = new AccessToken();
				access_token.setAccess_token(jsonObj.getString("access_token"));
				access_token.setExpires_in(jsonObj.getInt("expires_in"));
				
				access_token.setRefresh_token(jsonObj
						.getString("refresh_token"));
				
				access_token.setCreate_time(today());
 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return access_token;
	}

	private static String today() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 当前时间
		Date now = new Date();
		return dateformat.format(now);
	}

}
