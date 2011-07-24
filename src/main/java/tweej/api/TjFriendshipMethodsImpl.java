package tweej.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;
import tweej.http.TjHttpClient;

/**
 * Friendship Methods のラッパー実装クラス. 
 * @author tome
 *
 */
public class TjFriendshipMethodsImpl implements TjFriendshipMethods {

	public static final String FRIENDSHIP_CREATE = "http://api.twitter.com/1/friendships/create";
	public static final String FRIENDSHIP_DESTROY = "http://api.twitter.com/1/friendships/destroy";

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjFriendshipMethods#create(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T create(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String follow,
			TjHttpCallback callback) throws TjException {
		
		StringBuilder url = new StringBuilder(FRIENDSHIP_CREATE);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		if (userId != null) {
			params.put("user_id", userId);
		}
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		if (follow != null) {
			params.put("follow", follow);
		}
		
		try {
			return TjHttpClient.httpPost(url.toString(), oauth, params, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of friendship/create.", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjFriendshipMethods#destroy(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T destroy(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName,
			TjHttpCallback callback) throws TjException {
		
		StringBuilder url = new StringBuilder(FRIENDSHIP_DESTROY);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		if (userId != null) {
			params.put("user_id", userId);
		}
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		
		try {
			return TjHttpClient.httpPost(url.toString(), oauth, params, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of friendship/destroy.", e);
		}
	}

}
