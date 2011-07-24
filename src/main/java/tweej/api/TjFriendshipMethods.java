package tweej.api;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * Friendship Methods のラッパー・インターフェイス. 
 * @author tome
 *
 */
public interface TjFriendshipMethods {

	/**
	 * 特定のユーザをフォローします.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param follow
	 * @param callback
	 * @param callback返却値
	 * @throws TjException
	 */
	public <T> T create(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			String follow, TjHttpCallback callback)
	throws TjException;

	/**
	 * 特定のユーザのフォローを解除します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param callback
	 * @param callback返却値
	 * @throws TjException
	 */
	public <T> T destroy(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			TjHttpCallback callback)
	throws TjException;

}
