package sse.provider;

import sse.db.pojo.gen.TAccount;

public class HandlerHelper {

	public static IHandler buildHandlerFromAccount(TAccount accountEntity) {
		IHandler handler = null;
		try {
			if ("sina".equalsIgnoreCase(accountEntity.getProvider())) {
				handler = new sse.provider.sina.Handler(accountEntity);
			} else if ("qq".equalsIgnoreCase(accountEntity.getProvider())) {
				handler = new sse.provider.qq.Handler(accountEntity);
			}
		} catch (Exception e) {
			return null;
		}
		return handler;
	}
}
