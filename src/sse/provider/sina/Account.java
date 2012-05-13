package sse.provider.sina;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import sse.db.HibSessManager;
import sse.db.HibTransManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.OauthSinaUtil;
import sse.db.pojo.UserUtil;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthSina;
import sse.db.pojo.gen.TUser;
import sse.provider.IAccount;
import sse.provider.IPostEntry;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.examples.WebOAuth;
import weibo4j.http.AccessToken;

/**
 * @brief 该类代表一个新浪微博账号信息，可从数据库中的实体构造。亦提供静态函数从用
 *        户的登陆行为中构造。该类实现了 IAccount 接口，为统一发布微博提供方便。
 * @author EUYUIL
 * @date 2012-05-08
 */

public class Account implements IAccount {

	private static final long serialVersionUID = 291224956251589120L;

	private TAccount entity;
	private AccessToken accessToken;

	public Account(TAccount entity) throws Exception {
		if (entity == null || !"sina".equalsIgnoreCase(entity.getProvider()))
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
			weibo.setToken(getAccessToken());
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

	public static Account getAccountByLogin(HttpServletRequest request,
			AccessToken accessToken, Long externalId) {

		TAccount accountEntity =
			new TransactionTemplate(HibTransManager.instance())
				.execute(new GetAccountEntityByLoginTransaction(accessToken,
					externalId));
		new TransactionTemplate(HibTransManager.instance())
				.execute(new SaveCurrentUserTransaction(accountEntity));
		return new TransactionTemplate(HibTransManager.instance())
				.execute(new GetAccountFromEntityTransaction(accountEntity));
	}

	/**
	 * 该类是一个 Transaction 回调类，提供新浪微博的 AccessToken 和其唯一 ID, 构造
	 * 该类。该类能创造和更新数据库中相应 Token 和 Token Secret, 并获得账号对象。
	 * @author EUYUIL
	 * @date 2012-05-08
	 */

	private static class GetAccountEntityByLoginTransaction implements
			TransactionCallback<TAccount> {

		private AccessToken accessToken;
		private Long externalId;

		public GetAccountEntityByLoginTransaction(
				AccessToken accessToken, Long externalId) {
			this.accessToken = accessToken;
			this.externalId = externalId;
		}

		@Override
		public TAccount doInTransaction(TransactionStatus status) {

			Session session = HibSessManager.current();

			// 看看这个账号以前是否登录过。
			TAccount accountEntity = AccountUtil.instance().findByProviderExternalId(
					"sina", externalId);
			if (accountEntity == null) { // 说明他是新来的。
				accountEntity = new TAccount();
				accountEntity.setProvider("sina");
				accountEntity.setExternalId(externalId);
				AccountUtil.instance().save(accountEntity);
			}

			// 如果原来有 token, 则删除原有的。
			Query deleteTokens = session.createQuery("delete TOauthSina "
					+ "where TAccount = :account " + "   or    token = :token");
			deleteTokens.setEntity("account", accountEntity);
			deleteTokens.setString("token", accessToken.getToken());
			deleteTokens.executeUpdate();

			// 将 token 写入数据库。
			TOauthSina oauthSina = new TOauthSina();
			oauthSina.setTAccount(accountEntity);
			oauthSina.setToken(accessToken.getToken());
			oauthSina.setTokenSecret(accessToken.getTokenSecret());
			OauthSinaUtil.instance().save(oauthSina);

			return accountEntity;
		}
	}

	/**
	 * 该类是一个 Transaction 回调类，能够将当前的用户保存到 Spring Bean 中。
	 * @author EUYUIL
	 * @date 2012-05-08
	 */

	private static class SaveCurrentUserTransaction extends TransactionCallbackWithoutResult {

		private TAccount accountEntity;

		public SaveCurrentUserTransaction(TAccount accountEntity) {
			this.accountEntity = accountEntity;
		}

		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
			// 设置当前用户。
			TUser userEntity = accountEntity.getTUser();
			UserUtil.instance().setCurrentUser(userEntity);
		}
	}

	/**
	 * 该类是一个 Transaction 回调类，能够从数据库实体中获得账号对象。
	 * @author EUYUIL
	 * @date 2012-05-08
	 */

	private static class GetAccountFromEntityTransaction implements TransactionCallback<Account> {

		private TAccount accountEntity;

		public GetAccountFromEntityTransaction(TAccount accountEntity) {
			this.accountEntity = accountEntity;
		}

		@Override
		public Account doInTransaction(TransactionStatus status) {
			Account account = null;
			try {
				account = new Account(accountEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return account;
		}
	}
}
