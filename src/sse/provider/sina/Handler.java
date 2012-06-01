package sse.provider.sina;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.support.TransactionTemplate;

import sse.db.HibTransManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.OauthEntity;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthSina;
import sse.provider.IHandler;
import sse.provider.IPostEntry;
import sse.provider.OauthHelper;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.examples.WebOAuth;
import weibo4j.http.AccessToken;

/**
 * @brief 该类代表一个新浪微博账号信息，可从数据库中的实体构造。亦提供静态函数从用
 *        户的登陆行为中构造。该类实现了 IHandler 接口，为统一发布微博提供方便。
 * @author EUYUIL
 * @date 2012-05-08
 */

public class Handler implements IHandler {

	private static final long serialVersionUID = 291224956251589120L;
	private static final String PROVIDER = "sina";

	private TAccount entity;
	private AccessToken accessToken;

	public Handler(TAccount entity) throws Exception {
		if (entity == null || !PROVIDER.equalsIgnoreCase(entity.getProvider()))
			throw new Exception(
					"Only can construct from Sina Weibo account entity.");
		this.entity = entity;
		TOauthSina tokenEntity = AccountUtil.getTOauthSina(entity);
		accessToken = new AccessToken(tokenEntity.getToken(),
				tokenEntity.getTokenSecret());
	}

	@Override
	public List<IPostEntry> getFriendsTimeline() {
		List<IPostEntry> result = new ArrayList<IPostEntry>();
		try {
			Weibo weibo = new Weibo();
			weibo.setOAuthAccessToken(getAccessToken());
			weibo.setOAuthConsumer(Weibo.CONSUMER_KEY, Weibo.CONSUMER_SECRET);
			Paging page = new Paging(1);
			List<Status> statuses = weibo.getFriendsTimeline(page);
			for (Status status : statuses) {
				result.add(new PostEntry(status));
			}
		} catch (WeiboException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void publish(Object content) throws Exception {
		if (content.getClass() == String.class) {
			WebOAuth.update(getAccessToken(), content.toString());
		} else {
			throw new Exception("Can't post content type "
					+ content.getClass().toString() + ".");
		}
	}

	@Override
	public Long getId() {
		return entity == null ? null : entity.getId();
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public static Handler getAccountByLogin(HttpServletRequest request,
			final AccessToken accessToken, final Long externalId) {

		OauthHelper oah = new OauthHelper() {

			@Override
			public OauthEntity newOauthEntity() {
				return new TOauthSina();
			}

			@Override
			public IHandler newAccountFromEntity() throws Exception {
				return new Handler(getCurrentAccount());
			}
		};

		oah.setToken(accessToken.getToken());
		oah.setTokenSecret(accessToken.getTokenSecret());
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
