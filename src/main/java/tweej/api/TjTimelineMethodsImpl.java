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
 * Timeline Methods のラッパー実装クラス. 
 * @author tome
 *
 */
public class TjTimelineMethodsImpl implements TjTimelineMethods {

	public static final String STATUSES_PUBLIC_TIMELINE = "http://api.twitter.com/1/statuses/public_timeline";
	public static final String STATUSES_HOME_TIMELINE = "http://api.twitter.com/1/statuses/home_timeline";
	public static final String STATUSES_USER_TIMELINE = "http://api.twitter.com/1/statuses/user_timeline";
	public static final String STATUSES_MENTIONS = "http://api.twitter.com/1/statuses/mentions";

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjTimelineMethods#getPublicTimeline(tweej.TjFormat)
	 */
	@Override
	public <T> T getPublicTimeline(TjFormat format, TjHttpCallback callback) throws TjException {
		StringBuilder url = new StringBuilder(STATUSES_PUBLIC_TIMELINE);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json, TjFormat.rss, TjFormat.atom);
		url.append(".").append(_format);
		
		try {
			return TjHttpClient.httpGet(url.toString(), callback);
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/public_timeline.", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjTimelineMethods#getHomeTimeline(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getHomeTimeline(TjFormat format, TjOAuth oauth,
			String sinceId, String maxId, String count, String page,
			TjHttpCallback callback) throws TjException {
		
		StringBuilder url = new StringBuilder(STATUSES_HOME_TIMELINE);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json, TjFormat.atom);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		if (sinceId != null) {
			params.put("since_id", sinceId);
		}
		if (maxId != null) {
			params.put("max_id", maxId);
		}
		if (count != null) {
			params.put("count", count);
		}
		if (page != null) {
			params.put("page", page);
		}
		
		try {
			return TjHttpClient.httpGet(url.toString(), oauth, params, callback);
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/home_timeline.", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjTimelineMethods#getUserTimeline(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getUserTimeline(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String sinceId, String maxId,
			String count, String page, TjHttpCallback callback) throws TjException {
		
		StringBuilder url = new StringBuilder(STATUSES_USER_TIMELINE);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json, TjFormat.rss, TjFormat.atom);
		url.append(".").append(_format);

		Map<String, String> params = new HashMap<String, String>();
		if (userId != null) {
			params.put("user_id", userId);
		}
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		if (sinceId != null) {
			params.put("since_id", sinceId);
		}
		if (maxId != null) {
			params.put("max_id", maxId);
		}
		if (count != null) {
			params.put("count", count);
		}
		if (page != null) {
			params.put("page", page);
		}

		try {
			if (oauth == null) {
				return TjHttpClient.httpGet(url.toString(), params, callback);
			}
			return TjHttpClient.httpGet(url.toString(), oauth, params, callback);
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/user_timeline.", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjTimelineMethods#getMentions(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getMentions(TjFormat format, TjOAuth oauth, String sinceId,
			String maxId, String count, String page, TjHttpCallback callback)
			throws TjException {
		
		StringBuilder url = new StringBuilder(STATUSES_MENTIONS);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json, TjFormat.rss, TjFormat.atom);
		url.append(".").append(_format);

		Map<String, String> params = new HashMap<String, String>();
		if (sinceId != null) {
			params.put("since_id", sinceId);
		}
		if (maxId != null) {
			params.put("max_id", maxId);
		}
		if (count != null) {
			params.put("count", count);
		}
		if (page != null) {
			params.put("page", page);
		}

		try {
			return TjHttpClient.httpGet(url.toString(), oauth, params, callback);
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/mentions.", e);
		}
	}

}
