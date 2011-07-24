package tweej.http;

import static tweej.TjUtil.dumpResponseHeader;
import static tweej.TjUtil.canonicalize;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import tweej.TjException;
import tweej.api.oauth.TjOAuth;

/**
 * HTTP通信のクライアント・クラス.
 * @author tome
 *
 */
public class TjHttpClient {

	public static final Logger logger = Logger.getLogger(TjHttpClient.class.getName());

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
	public static final String CONTENT_TYPE_XML = "application/atom+xml;charset=UTF-8";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	/**
	 * URLで指定されたコンテンツをGETで取得します（認証なし）.
	 * @param <T>
	 * @param url
	 * @param callback
	 * @return callbackの返却値. callbackが未設定の場合 null を返却します
	 * @throws IOException
	 * @throws TjException
	 */
	public static <T> T httpGet(String url, TjHttpCallback callback)
	throws IOException, TjException {
		return httpGet(url, null, callback);
	}

	/**
	 * URLで指定されたコンテンツをGETで取得します（認証なし）.
	 * @param <T>
	 * @param url
	 * @param params
	 * @param callback
	 * @return callbackの返却値. callbackが未設定の場合 null を返却します
	 * @throws IOException
	 * @throws TjException
	 */
	public static <T> T httpGet(String url, Map<String, String> params, TjHttpCallback callback)
	throws IOException, TjException {
		HttpURLConnection http_connection = null;
		InputStream inputStream = null;
		URL _url = null;
		try {
			_url = new URL(getURLWithParameter(url, params));
			http_connection = (HttpURLConnection) _url.openConnection();
			http_connection.setRequestMethod(METHOD_GET);
			http_connection.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_XML);
			inputStream = http_connection.getInputStream();
			if (callback == null) {
				return null;
			}
			return callback.call(inputStream);

		} finally {
			if (http_connection != null) {
				http_connection.disconnect();
			}
			
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	/**
	 * URLで指定されたコンテンツをGETで取得します（認証あり）.
	 * @param <T>
	 * @param url
	 * @param oauth
	 * @param params
	 * @param callback
	 * @return callbackの返却値. callbackが未設定の場合 null を返却します
	 * @throws IOException
	 * @throws TjException
	 */
	public static <T> T httpGet(String url, TjOAuth oauth, Map<String, String> params, TjHttpCallback callback)
	throws IOException, TjException {
		HttpURLConnection http_connection = null;
		InputStream inputStream = null;
		URL _url = null;
		String _header = null;
		try {
			_header = oauth.getOAuthHeader(METHOD_GET, url, params);
			_url = new URL(getURLWithParameter(url, params));
			
			logger.info(_header);// HTTPリクエスト・ヘッダ
			logger.info(_url.toString());// HTTPリクエストURL
			
			http_connection = (HttpURLConnection) _url.openConnection();
			http_connection.setRequestMethod(METHOD_GET);
			http_connection.addRequestProperty(HEADER_AUTHORIZATION, _header);
			http_connection.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_XML);
			
			// HTTPステータスチェック
			isResponseStatusOK(http_connection);
			
			inputStream = http_connection.getInputStream();
			if (callback == null) {
				return null;
			}
			return callback.call(inputStream);

		} finally {
			if (http_connection != null) {
				http_connection.disconnect();
			}
			
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

	/**
	 * URLで指定されたコンテンツをPOSTで取得します（認証あり）.
	 * @param <T>
	 * @param url
	 * @param oauth
	 * @param params
	 * @param callback
	 * @return callbackの返却値. callbackが未設定の場合 null を返却します
	 * @throws IOException
	 * @throws TjException
	 */
	public static <T> T httpPost(String url, TjOAuth oauth, Map<String, String> params, TjHttpCallback callback)
	throws IOException, TjException {
		HttpURLConnection http_connection = null;
		InputStream inputStream = null;
		String _header = null;
		URL _url = null;
		try {
			_header = oauth.getOAuthHeader(METHOD_POST, url, params);
			_url = new URL(getURLWithParameter(url, params));
			
			logger.info(_header);// HTTPリクエスト・ヘッダ
			logger.info(_url.toString());// HTTPリクエストURL

			http_connection = (HttpURLConnection) _url.openConnection();
			http_connection.setDoOutput(true);
			http_connection.setRequestMethod(METHOD_POST);
			http_connection.addRequestProperty(HEADER_AUTHORIZATION, _header);
			http_connection.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_XML);

			// HTTPステータスチェック
			isResponseStatusOK(http_connection);
			
			inputStream = http_connection.getInputStream();
			
			if (callback == null) {
				return null;
			}
			return callback.call(inputStream);

		} finally {
			if (http_connection != null) {
				http_connection.disconnect();
			}
			
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

	/**
	 * 指定のURLにパラメータを繋げて返します.
	 * @param url
	 * @param params
	 * @return url + parameters
	 * @throws UnsupportedEncodingException 
	 */
	private static String getURLWithParameter(String url, Map<String, String> params) throws UnsupportedEncodingException {
		if (params == null || params.size() <= 0) {
			return url;
		}
		return url + "?" + canonicalize(new TreeMap<String, String>(params));
	}
	
	/**
	 * HTTPステータスが200かどうかをチェックします.
	 * 
	 * @param http_connection
	 * @return レスポンスステータスが 200 の場合true.それ以外は例外を投げます
	 * @throws TjException
	 */
	private static boolean isResponseStatusOK(HttpURLConnection http_connection) throws TjException {
		Map<String, List<String>> header = http_connection.getHeaderFields();
		if (header == null) {
			throw new TjException("Header is null.");
		}
		
		for (String key : header.keySet()) {
			if (key != null) {
				if ("STATUS".equals(key.toUpperCase())) {
					List<String> values = header.get(key);
					for (String v : values) {
						if (v.contains("200")) {
							logger.info(dumpResponseHeader(http_connection));
							return true;
						}
					}
					logger.info(dumpResponseHeader(http_connection));
					throw new TjException("Status is unexpected.");
				}
			}
		}
		return true;
	}
}
