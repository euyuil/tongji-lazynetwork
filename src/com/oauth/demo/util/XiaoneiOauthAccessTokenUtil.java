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
 * 获取一个Access_token 对象
 * 
 * @author libo
 * 
 */
public class XiaoneiOauthAccessTokenUtil {
	public static AccessToken getAccessToken(String auth_code,
			String redirect_url) {
		// 此处用的是第三方的包，也可以用java本身的类
		// 设置请求参数，包括：client_id、client_secret、redirect_uri、code、和grant_type
        
		String apikey=LoadProperties.readValue("RENREN_API_KEY");
		String secret=LoadProperties.readValue("RENREN_API_SECRET");
		
		PostMethod method = new PostMethod(
				CommonConst.RENREN_OAUTH_ACCESS_TOKEN_URL);
		method.addParameter("client_id",apikey);
		method.addParameter("client_secret",secret);
		method.addParameter("redirect_uri", redirect_url);
		method.addParameter("grant_type", "authorization_code");
		method.addParameter("code", auth_code);

		HttpClient client = new HttpClient();
		AccessToken access_token =null;
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
				access_token=new AccessToken();
				access_token.setAccess_token(jsonObj.getString("access_token"));
				access_token.setExpires_in(jsonObj.getInt("expires_in"));
				//开发者已经向开放平台申请了生命周期是永久的AccessToken,返回的参数refresh_token不为空
				if(jsonObj.has("refresh_token")){
					access_token.setRefresh_token(jsonObj.getString("refresh_token").toString());
				}
				access_token.setCreate_time(today());
				return access_token;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println("你的应用没有获得永久授权");
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
