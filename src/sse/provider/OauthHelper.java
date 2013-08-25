package sse.provider;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import sse.db.HibSessManager;
import sse.db.pojo.AccountUtil;
import sse.db.pojo.OauthEntity;
import sse.db.pojo.OauthEntityUtil;
import sse.db.pojo.UserUtil;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TUser;

public abstract class OauthHelper {

	private String token;
	private String tokenSecret;
	private String refreshToken;
	private String provider;
	private String externalId;
	private TAccount currentAccount;

	public abstract OauthEntity newOauthEntity();
	public abstract IHandler newAccountFromEntity() throws Exception;

	/**
	 * 该类是一�Transaction 回调类，能创造和更新数据库中相应 Token �
	 * Token Secret, 并获得账号对象�
	 * @author EUYUIL
	 * @date 2012-05-31
	 */

	public GetAccountEntityByLogin getAccountEntityByLogin() {
		return new GetAccountEntityByLogin();
	}

	public class GetAccountEntityByLogin implements
			TransactionCallback<TAccount> {

		@Override
		public TAccount doInTransaction(TransactionStatus status) {

			Session session = HibSessManager.current();

			// 看看这个账号以前是否登录过�
			TAccount accountEntity = AccountUtil.instance().findByProviderExternalId(
					getProvider(), getExternalId());
			if (accountEntity == null) { // 说明这个账号不存在�
				accountEntity = new TAccount();
				// 将账号的主人设置成当前已经登录的用户，如果当前没有登录，�
				// 数据库的触发器会帮助创建一个用户的�
				accountEntity.setTUser(UserUtil.instance().getCurrentUser());
				accountEntity.setProvider(getProvider());
				accountEntity.setExternalId(getExternalId());
				AccountUtil.instance().save(accountEntity);
			}

			OauthEntity oauthEntity = newOauthEntity();

			// 如果原来�token, 则删除原有的�
			Query deleteTokens = session.createQuery("delete "
					+ oauthEntity.getClass().getName()
					+ " where TAccount = :account " + " or token = :token");
			deleteTokens.setEntity("account", accountEntity);
			deleteTokens.setString("token", getToken());
			deleteTokens.executeUpdate();

			// �token 写入数据库�
			oauthEntity.setTAccount(accountEntity);
			oauthEntity.setToken(getToken());
			oauthEntity.setTokenSecret(getTokenSecret());
			oauthEntity.setRefreshToken(getRefreshToken());
			OauthEntityUtil.save(oauthEntity);

			return accountEntity;
		}
	}

	/**
	 * 该类是一�Transaction 回调类，能够将当前的用户保存�Spring Bean 中�
	 * @author EUYUIL
	 * @date 2012-05-31
	 */

	public SaveCurrentUser saveCurrentUser() {
		return new SaveCurrentUser();
	}

	public class SaveCurrentUser extends TransactionCallbackWithoutResult {

		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
			// 设置当前用户�
			TUser userEntity = getCurrentAccount().getTUser();
			UserUtil.instance().setCurrentUser(userEntity);
		}
	}

	/**
	 * 该类是一�Transaction 回调类，能够从数据库实体中获得账号对象�
	 * @author EUYUIL
	 * @date 2012-05-31
	 */

	public GetAccountFromEntity getAccountFromEntity() {
		return new GetAccountFromEntity();
	}

	public class GetAccountFromEntity implements TransactionCallback<IHandler> {

		@Override
		public IHandler doInTransaction(TransactionStatus status) {
			IHandler handler = null;
			try {
				handler = newAccountFromEntity();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return handler;
		}
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public TAccount getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(TAccount currentAccount) {
		this.currentAccount = currentAccount;
	}
}
