package tweej;

import java.util.List;

import tweej.api.TjAccountMethods;
import tweej.api.TjAccountMethodsImpl;
import tweej.api.TjFriendshipMethods;
import tweej.api.TjFriendshipMethodsImpl;
import tweej.api.TjStatusMethods;
import tweej.api.TjStatusMethodsImpl;
import tweej.api.TjTimelineMethods;
import tweej.api.TjTimelineMethodsImpl;
import tweej.api.TjUserMethods;
import tweej.api.TjUserMethodsImpl;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjHttpCallback;

/**
 * Twiiter API をコールするクラス.
 * @author tome
 *
 */
public class TjTwitterAPIExecutor
implements TjTimelineMethods, TjUserMethods, TjStatusMethods, TjFriendshipMethods, TjAccountMethods {
	
	//--------------------------------------
	// Timeline Methods

	private TjTimelineMethods timelineMethods = null;
	private TjTimelineMethods getTimelineMethods() {
		if (timelineMethods == null) {
			timelineMethods = new TjTimelineMethodsImpl();
		}
		return timelineMethods;
	}

	@Override
	public <T> T getPublicTimeline(TjFormat format, TjHttpCallback callback) throws TjException {
		return getTimelineMethods().getPublicTimeline(format, callback);
	}

	@Override
	public <T> T getHomeTimeline(TjFormat format, TjOAuth oauth,
			String sinceId, String maxId, String count, String page,
			TjHttpCallback callback) throws TjException {
		return getTimelineMethods().getHomeTimeline(format, oauth, sinceId, maxId, count, page, callback);
	}

	@Override
	public <T> T getUserTimeline(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String sinceId, String maxId,
			String count, String page, TjHttpCallback callback)
			throws TjException {
		return getTimelineMethods().getUserTimeline(format, oauth, id, userId, screenName, sinceId, maxId, count, page, callback);
	}
	
	@Override
	public <T> T getMentions(TjFormat format, TjOAuth oauth, String sinceId,
			String maxId, String count, String page, TjHttpCallback callback)
			throws TjException {
		return getTimelineMethods().getMentions(format, oauth, sinceId, maxId, count, page, callback);
	}
	
	//--------------------------------------
	// Status Methods

	private TjStatusMethods statusMethods = null;
	private TjStatusMethods getStatusMethods() {
		if (statusMethods == null) {
			statusMethods = new TjStatusMethodsImpl();
		}
		return statusMethods;
	}

	@Override
	public <T> T update(TjFormat format, TjOAuth oauth, String status,
			String inReplyToStatusId, String lat, String longitude,
			String placeId, String displayCoordinates, TjHttpCallback callback)
			throws TjException {
		return getStatusMethods().update(format, oauth, status, inReplyToStatusId, lat, longitude, placeId, displayCoordinates, callback);
	}

	
	//--------------------------------------
	// User Methods

	private TjUserMethods userMethods = null;
	private TjUserMethods getUserMethods() {
		if (userMethods == null) {
			userMethods = new TjUserMethodsImpl();
		}
		return userMethods;
	}
	
	@Override
	public <T> T getFollowers(TjFormat format, String id, String userId,
			String screenName, String cursor, TjHttpCallback callback)
			throws TjException {
		return getUserMethods().getFollowers(format, id, userId, screenName, cursor, callback);
	}

	@Override
	public <T> T getFollowers(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String cursor,
			TjHttpCallback callback) throws TjException {
		return getUserMethods().getFollowers(format, oauth, id, userId, screenName, cursor, callback);
	}

	@Override
	public List<String> getAllFollowers(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName) throws TjException {
		return  getUserMethods().getAllFollowers(format, oauth, id, userId, screenName);
	}

	@Override
	public <T> T getFriends(TjFormat format, String id, String userId,
			String screenName, String cursor, TjHttpCallback callback)
			throws TjException {
		return getUserMethods().getFriends(format, id, userId, screenName, cursor, callback);
	}

	@Override
	public <T> T getFriends(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String cursor,
			TjHttpCallback callback) throws TjException {
		return getUserMethods().getFriends(format, oauth, id, userId, screenName, cursor, callback);
	}

	@Override
	public List<String> getAllFriends(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName) throws TjException {
		return  getUserMethods().getAllFriends(format, oauth, id, userId, screenName);
	}
	
	//--------------------------------------
	// Friendship Methods

	private TjFriendshipMethods friendshipMethods = null;
	private TjFriendshipMethods getFriendshipMethods() {
		if (friendshipMethods == null) {
			friendshipMethods = new TjFriendshipMethodsImpl();
		}
		return friendshipMethods;
	}
	
	@Override
	public <T> T create(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, String follow,
			TjHttpCallback callback) throws TjException {
		return getFriendshipMethods().create(format, oauth, id, userId, screenName, follow, callback);
	}

	@Override
	public <T> T destroy(TjFormat format, TjOAuth oauth, String id,
			String userId, String screenName, TjHttpCallback callback)
			throws TjException {
		return getFriendshipMethods().destroy(format, oauth, id, userId, screenName, callback);
	}

	//--------------------------------------
	// Account Methods

	private TjAccountMethods accountMethods = null;
	private TjAccountMethods getAccountMethods() {
		if (accountMethods == null) {
			accountMethods = new TjAccountMethodsImpl();
		}
		return accountMethods;
	}

	@Override
	public <T> T rateLimitStatus(TjFormat format, TjOAuth oauth,
			TjHttpCallback callback) throws TjException {
		return getAccountMethods().rateLimitStatus(format, oauth, callback);
	}

}
