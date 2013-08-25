package sse.provider.renren;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.support.TransactionTemplate;

import sse.db.HibTransManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.OauthEntity;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthRenren;
import sse.provider.IHandler;
import sse.provider.IPostEntry;
import sse.provider.OauthHelper;
import sse.provider.renren.PostEntry;

import com.oauth.demo.model.AccessToken;
import com.oauth.demo.model.Feed;
import com.oauth.demo.model.RenrenSessionKey;
import com.oauth.demo.util.XiaoneiRestfulApiUtil;

/**
 * @brief 该类代表一个人人微博账号信息，可从数据库中的实体构造。亦提供静态函数从用
 *        户的登陆行为中构造。该类实现了 IHandler 接口，为统一发布微博提供方便。
 * @author MENGYI
 * @date 2012-05-08
 */

public class Handler implements IHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4543314092765509978L;
	private static final String PROVIDER = "renren";
	private RenrenSessionKey sk;
	private TAccount entity;
	private AccessToken accessToken = new AccessToken();
	
	public Handler(TAccount entity) throws Exception {
		if (entity == null || !PROVIDER.equalsIgnoreCase(entity.getProvider()))
			throw new Exception(
					"Only can construct from Renren account entity.");
		TOauthRenren tokenEntity = AccountUtil.getTOauthRenren(entity);
		accessToken.setAccess_token(tokenEntity.getToken());
		sk = XiaoneiRestfulApiUtil.getXiaonei_SessionKey_info(accessToken.getAccess_token());
	}
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return entity == null ? null : entity.getId();
	}

	@Override
	public void publish(Object content) throws Exception {
		// TODO Auto-generated method stub
		if (content.getClass() == String.class) {
			XiaoneiRestfulApiUtil.setStatus(sk.getSessionkey(), content.toString());
		} else {
			throw new Exception("Can't post content type "
					+ content.getClass().toString() + ".");
		}
	}

	@Override
	public List<IPostEntry> getFriendsTimeline() {
		// TODO Auto-generated method stub
		List<IPostEntry> result = new ArrayList<IPostEntry>();
		List<Feed> feeds = XiaoneiRestfulApiUtil.getFeeds(sk.getSessionkey(), String.valueOf(sk.getRenrenid()));
		try{
			for (Feed feed : feeds){
			result.add(new PostEntry(feed));
			}
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return result;
	}
	
	public AccessToken getAccessToken() {
		return accessToken;
	}

	public static Handler getAccountByLogin(HttpServletRequest request,
			final AccessToken accessToken, final Long externalId) {
		
		OauthHelper oah = new OauthHelper() {

			@Override
			public OauthEntity newOauthEntity() {
				return new TOauthRenren();
			}

			@Override
			public IHandler newAccountFromEntity() throws Exception {
				return new Handler(getCurrentAccount());
			}
		};

		oah.setToken(accessToken.getAccess_token());
		oah.setTokenSecret("HI9ac");
		oah.setRefreshToken(accessToken.getRefresh_token());
		oah.setProvider(PROVIDER);
		oah.setExternalId(externalId.toString());

		TAccount account = new TransactionTemplate(HibTransManager.instance())
				.execute(oah.getAccountEntityByLogin());
		oah.setCurrentAccount(account);

		new TransactionTemplate(HibTransManager.instance())
				.execute(oah.saveCurrentUser());

		return (Handler) new TransactionTemplate(
				HibTransManager.instance()).execute(oah.getAccountFromEntity());
	}
}