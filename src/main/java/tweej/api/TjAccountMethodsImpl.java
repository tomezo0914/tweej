package tweej.api;

import java.io.IOException;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;
import tweej.http.TjHttpClient;

/**
 * Account Methods のラッパー実装クラス. 
 * @author tome
 *
 */
public class TjAccountMethodsImpl implements TjAccountMethods {

	public static final String RATE_LIMIT_STATUS = "http://api.twitter.com/1/account/rate_limit_status";

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
	throws TjException {

		StringBuilder url = new StringBuilder(RATE_LIMIT_STATUS);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);

		try {
			return TjHttpClient.httpGet(url.toString(), oauth, null, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of account/rate_limit_status.", e);
		}
	}
	
}
