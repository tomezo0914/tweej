package tweej.api;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * Status Methods のラッパー・インターフェイス. 
 * @author tome
 *
 */
public interface TjStatusMethods {

	/**
	 * 状態を更新（つぶやき）します.
	 * @param <T>
	 * @param format
	 * @param oauth
	 * @param status
	 * @param inReplyToStatusId
	 * @param lat
	 * @param longitude
	 * @param placeId
	 * @param displayCoordinates
	 * @param callback
	 * @return callbackの返却値
	 * @throws TjException
	 */
	public <T> T update(TjFormat format, TjOAuth oauth,
			String status, String inReplyToStatusId, String lat,
			String longitude, String placeId, String displayCoordinates,
			TjHttpCallback callback)
	throws TjException;
	
}
