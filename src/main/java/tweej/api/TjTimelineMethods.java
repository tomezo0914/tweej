package tweej.api;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * Timeline Methods のラッパー・インターフェイス. 
 * @author tome
 *
 */
public interface TjTimelineMethods {

	/**
	 * パブリックな（世界中の）つぶやきを最大20件取得します.
	 * @param <T>
	 * @param format(xml, json, rss, atom)
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getPublicTimeline(TjFormat format, TjHttpCallback callback)
	throws TjException;
	
	/**
	 * 自分と自分の friend の過去24時間以内に update されたステータス(retweetを含む)から
	 * 最大20件(count引数使用時は最大200件)を取得します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param sinceId
	 * @param maxId
	 * @param count
	 * @param page
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getHomeTimeline(TjFormat format, TjOAuth oauth,
			String sinceId, String maxId, String count, String page,
			TjHttpCallback callback)
	throws TjException;
	
	/**
	 * 特定のユーザのつぶやきを最大20件取得します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @param sinceId
	 * @param maxId
	 * @param count
	 * @param page
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getUserTimeline(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName, String sinceId,
			String maxId, String count, String page, TjHttpCallback callback)
	throws TjException;

	/**
	 * 直近２０件の mentation（@username を含むつぶやき）を取得します.
	 * @param <T>
	 * @param format(xml, json, rss, atom)
	 * @param oauth
	 * @param sinceId
	 * @param maxId
	 * @param count
	 * @param page
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T getMentions(TjFormat format, TjOAuth oauth,
			String sinceId, String maxId, String count, String page,
			TjHttpCallback callback)
	throws TjException; 
}
