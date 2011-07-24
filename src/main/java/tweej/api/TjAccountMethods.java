package tweej.api;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * Account Methods のラッパー・インターフェイス. 
 * @author tome
 *
 */
public interface TjAccountMethods {

	/**
	 * 自分の「API 制限状況」(この1時間以内にあと何回APIを実行できるか)を取得します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T rateLimitStatus(TjFormat format, TjOAuth oauth,
			TjHttpCallback callback)
	throws TjException;
	
}
