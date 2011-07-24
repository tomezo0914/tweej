package tweej.api;

import java.util.List;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * User Methods のラッパー・インターフェイス. 
 * @author tome
 *
 */
public interface TjUserMethods {

	/**
	 * 自分の friends の一覧を(各 friends の最新ステータス付きで)取得します(認証無し).
	 * @param <T>
	 * @param format
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param cursor
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getFriends(TjFormat format,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException;

	/**
	 * 自分の friend の一覧を(各 friend の最新ステータス付きで)取得します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param cursor
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getFriends(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException;
	
	/**
	 * 指定ユーザの全フレンドのscreenNameを返します.
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @return 全フレンドのscreenNameの配列
	 * @throws TjException
	 */
	public List<String> getAllFriends(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName)
	throws TjException;

	
	/**
	 * 自分の follower の一覧を(各 follower の最新ステータス付きで)取得します(認証無し).
	 * @param <T>
	 * @param format
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param cursor
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getFollowers(TjFormat format,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException;

	/**
	 * 自分の follower の一覧を(各 follower の最新ステータス付きで)取得します(認証有り).
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param cursor
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getFollowers(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException;

	/**
	 * 指定ユーザの全フォロワーのscreenNameを返します.
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @return 全フォロワーのscreenNameの配列
	 * @throws TjException
	 */
	public List<String> getAllFollowers(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName)
	throws TjException;

}
