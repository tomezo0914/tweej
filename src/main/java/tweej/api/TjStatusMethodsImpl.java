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
 * Status Methods のラッパー実装クラス. 
 * @author tome
 *
 */
public class TjStatusMethodsImpl implements TjStatusMethods {

	public static final String STATUSES_UPDATE = "http://api.twitter.com/1/statuses/update";

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjStatusMethods#update(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T update(TjFormat format, TjOAuth oauth, String status,
			String inReplyToStatusId, String lat, String longitude, String placeId,
			String displayCoordinates, TjHttpCallback callback)
			throws TjException {
		
		StringBuilder url = new StringBuilder(STATUSES_UPDATE);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", status);
		if (inReplyToStatusId != null) {
			params.put("in_reply_to_status_id", inReplyToStatusId);
		}
		if (lat != null) {
			params.put("lat", lat);
		}
		if (longitude != null) {
			params.put("long", longitude);
		}
		if (placeId != null) {
			params.put("place_id", placeId);
		}
		if (displayCoordinates != null) {
			params.put("display_coordinates", displayCoordinates);
		}
		
		try {
			return TjHttpClient.httpPost(url.toString(), oauth, params, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/update.", e);
		}
	}

}
