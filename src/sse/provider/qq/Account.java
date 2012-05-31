package sse.provider.qq;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.support.TransactionTemplate;

import com.tencent.weibo.api.Statuses_API;
import com.tencent.weibo.api.T_API;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.WeiBoConst;

import sse.db.HibTransManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.OauthEntity;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthQq;
import sse.provider.IAccount;
import sse.provider.IPostEntry;
import sse.provider.OauthHelper;

/**
 * @brief 该类代表一个 QQ 微博账号信息，可从数据库中的实体构造。亦提供静态函数从用
 *        户的登陆行为中构造。该类实现了 IAccount 接口，为统一发布微博提供方便。
 * @author EUYUIL
 * @date 2012-05-31
 */

public class Account implements IAccount {

	private static final long serialVersionUID = 291224956251589120L;
	private static final String PROVIDER = "qq";

	private TAccount entity;
	private OAuth accessToken;

	public Account(TAccount entity) throws Exception {
		if (entity == null || !PROVIDER.equalsIgnoreCase(entity.getProvider()))
			throw new Exception(
					"Only can construct from QQ Weibo account entity.");
		this.entity = entity;
		TOauthQq tokenEntity = AccountUtil.getTOauthQq(entity);
		accessToken = new OAuth();
		accessToken.setOauth_token(tokenEntity.getToken());
		accessToken.setOauth_token_secret(tokenEntity.getTokenSecret());
	}

	@Override
	public List<IPostEntry> getFriendsTimeline() {
		List<IPostEntry> result = new ArrayList<IPostEntry>();
		try {
			Statuses_API sapi = new Statuses_API();
			String json = sapi.home_timeline(accessToken,
					WeiBoConst.ResultType.ResultType_Json, "0", "0", "20");
			result.addAll(PostEntry.fromJson(json));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public void publish(Object content) throws Exception {
		if (content.getClass() == String.class) {
			T_API tapi = new T_API();
			tapi.add(accessToken, WeiBoConst.ResultType.ResultType_Json,
					(String) content, "127.0.0.1"); // TODO Change this IP.
		} else {
			throw new Exception("Can't post content type "
					+ content.getClass().toString() + ".");
		}
	}

	@Override
	public Long getId() {
		return entity == null ? null : entity.getId();
	}

	public OAuth getAccessToken() {
		return accessToken;
	}

	public static Account getAccountByLogin(HttpServletRequest request,
			OAuth accessToken, String externalId) {

		OauthHelper oah = new OauthHelper() {

			@Override
			public OauthEntity newOauthEntity() {
				return new TOauthQq();
			}

			@Override
			public IAccount newAccountFromEntity() throws Exception {
				return new Account(getCurrentAccount());
			}
		};

		oah.setToken(accessToken.getOauth_token());
		oah.setTokenSecret(accessToken.getOauth_token_secret());
		oah.setProvider(PROVIDER);
		oah.setExternalId(externalId);

		TAccount account = new TransactionTemplate(HibTransManager.instance())
				.execute(oah.getAccountEntityByLogin());
		oah.setCurrentAccount(account);

		new TransactionTemplate(HibTransManager.instance())
				.execute(oah.saveCurrentUser());

		return (Account) new TransactionTemplate(
				HibTransManager.instance()).execute(oah.getAccountFromEntity());
	}
}
