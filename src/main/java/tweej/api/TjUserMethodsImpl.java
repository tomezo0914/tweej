package tweej.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import tweej.TjException;
import tweej.TjFormat;
import tweej.api.oauth.TjOAuth;
import tweej.http.TjAbstractHttpCallback;
import tweej.http.TjHttpCallback;
import tweej.http.TjHttpClient;

/**
 * User Methods のラッパー実装クラス. 
 * @author tome
 *
 */
public class TjUserMethodsImpl implements TjUserMethods {

	public static final String STATUSES_FRIENDS = "http://api.twitter.com/1/statuses/friends";
	public static final String STATUSES_FOLLOWERS = "http://api.twitter.com/1/statuses/followers";

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjUserMethods#getFriends(tweej.TjFormat, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getFriends(TjFormat format, String id, String userId,
			String screenName, String cursor, TjHttpCallback callback)
			throws TjException {
		return getFriends(format, null, id, userId, screenName, cursor, callback);
	}
	
	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjUserMethods#getFriends(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getFriends(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException {
		
		StringBuilder url = new StringBuilder(STATUSES_FRIENDS);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		if (userId != null) {
			params.put("user_id", userId);
		}
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		if (cursor != null) {
			params.put("cursor", cursor);
		}
		
		try {
			if (oauth == null) {
				return TjHttpClient.httpGet(url.toString(), params, callback);
			}
			return TjHttpClient.httpGet(url.toString(), oauth, params, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/friends.", e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjUserMethods#getFollowers(tweej.TjFormat, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getFollowers(TjFormat format,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException {
		return getFollowers(format, null, id, userId, screenName, cursor, callback);
	}

	/*
	 * (非 Javadoc)
	 * @see tweej.api.TjUserMethods#getFollowers(tweej.TjFormat, tweej.api.oauth.TjOAuth, java.lang.String, java.lang.String, java.lang.String, java.lang.String, tweej.http.TjHttpCallback)
	 */
	@Override
	public <T> T getFollowers(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName,
			String cursor, TjHttpCallback callback)
	throws TjException {
		StringBuilder url = new StringBuilder(STATUSES_FOLLOWERS);
		String _format = TjFormat.formatToString(format, TjFormat.xml, TjFormat.json);
		url.append(".").append(_format);
		
		Map<String, String> params = new HashMap<String, String>();
		if (userId != null) {
			params.put("user_id", userId);
		}
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		if (cursor != null) {
			params.put("cursor", cursor);
		}
		
		try {
			if (oauth == null) {
				return TjHttpClient.httpGet(url.toString(), params, callback);
			}
			return TjHttpClient.httpGet(url.toString(), oauth, params, callback);
			
		} catch (IOException e) {
			throw new TjException("It failed to call the api of statuses/followers.", e);
		}

	}

	/**
	 * 指定ユーザの全フレンドのscreenNameを返します.
	 * 
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @return フォロアーの screenName のリスト
	 * @throws TjException
	 */
	public List<String> getAllFriends(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName)
	throws TjException {
		
		List<String> userList = new ArrayList<String>();
		String cursor = "-1";
		while (cursor != null && !"".equals(cursor) && !"0".equals(cursor)) {
			UserListWithCursor _userList = null;
			if (oauth == null) {
				_userList = getFriends(format, id, userId, screenName, cursor, new TjHttpAllFriendMethodsCallback());
			} else {
				_userList = getFriends(format, oauth, id, userId, screenName, cursor, new TjHttpAllFriendMethodsCallback());
			}
			cursor = _userList.getNextCursor();
			for (int i = 0, total = _userList.getUserList().size(); i < total; i++) {
				userList.add(_userList.getUserList().get(i));
			}
		}
		return userList;
	}

	/**
	 * 指定ユーザの全フォロアーのscreenNameを返します.
	 * 
	 * @param format
	 * @param oauth
	 * @param id
	 * @param userId
	 * @param screenName
	 * @return フォロアーの screenName のリスト
	 * @throws TjException
	 */
	public List<String> getAllFollowers(TjFormat format, TjOAuth oauth,
			String id, String userId, String screenName)
	throws TjException {
		
		List<String> userList = new ArrayList<String>();
		String cursor = "-1";
		while (cursor != null && !"".equals(cursor) && !"0".equals(cursor)) {
			UserListWithCursor _userList = null;
			if (oauth == null) {
				_userList = getFollowers(format, id, userId, screenName, cursor, new TjHttpAllFriendMethodsCallback());
			} else {
				_userList = getFollowers(format, oauth, id, userId, screenName, cursor, new TjHttpAllFriendMethodsCallback());
			}
			cursor = _userList.getNextCursor();
			for (int i = 0, total = _userList.getUserList().size(); i < total; i++) {
				userList.add(_userList.getUserList().get(i));
			}
		}
		return userList;
	}

	/**
	 * Twitter API friendship/create, friendship/destroy のレスポンスxmlから
	 * screen_nameを抽出するコールバック.
	 * @author tome
	 *
	 */
	public class TjHttpAllFriendMethodsCallback extends TjAbstractHttpCallback {

		@SuppressWarnings("unchecked")
		@Override
		public UserListWithCursor callback(Document doc, XPath xpath) throws TjException {
			UserListWithCursor userList = new UserListWithCursor();
		    try {
				// cursor
				String nextCursor = xpath.evaluate("/users_list/next_cursor/text()", doc);
				userList.setNextCursor(nextCursor);
				
				// user
				XPathExpression expr = xpath.compile("/users_list/users/user/screen_name/text()");
			    Object result = expr.evaluate(doc, XPathConstants.NODESET);
			    NodeList nodes = (NodeList) result;
			    for (int i = 0; i < nodes.getLength(); i++) {
			    	userList.add(nodes.item(i).getNodeValue());
			    }
			    return userList;

			} catch (XPathExpressionException e) {
				throw new TjException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * 
	 * @author tome
	 *
	 */
	public class UserListWithCursor {
		private String nextCursor;
		private List<String> userList;
		
		public UserListWithCursor() {
			nextCursor = null;
			userList = new ArrayList<String>();
		}
		
		public void setNextCursor(String nextCursor) {
			this.nextCursor = nextCursor;
		}
		
		public String getNextCursor() {
			return this.nextCursor;
		}
		
		public int size() {
			return userList.size();
		}
		
		public void add(String screenName) {
			this.userList.add(screenName);
		}
		
		public List<String> getUserList() {
			return userList;
		}
		
	}

}
