package tweej.api.oauth;

import static tweej.TjUtil.encodeBase64;
import static tweej.TjUtil.urlEncode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import tweej.TjException;
import tweej.TjUtil;
import tweej.http.TjHttpCallback;
import tweej.http.TjHttpClient;

/**
 * Twitter OAuth class.
 * 
 * @author tome
 */
public class TjOAuth {
	
	/** request token url. */
	public static final String REQUEST_TOKEN_URL = "http://api.twitter.com/oauth/request_token";

	/** access token url. */
	public static final String ACCESS_TOKEN_URL = "http://twitter.com/oauth/access_token";

	/** Authorize URL. */
	public static final String AUTHORIZE_URL = "http://twitter.com/oauth/authorize";

	public static final String SIGNATURE_METHOD = "HMAC-SHA1";	
	public static final String OAUTH_VERSION = "1.0";

	private final String consumerKey;
	private final String consumerSecret;

	private String oauthToken;
	private String oauthTokenSecret;

	private String oauthTokenOld = null;
	private String oauthTokenSecretOld = null;

	private String oauthVerifier;

	
	/**
	 * Create the new TjOAuth object.
	 * 
	 * @param Consumer Key privided consumerKey Provider
	 * @param Consumer Secret privided consumerSecret Provider
	 */
	public TjOAuth(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		oauthToken = null;
		oauthTokenSecret = null;
	}
	
	/**
	 * Create the new TjOAuth object.
	 * 
	 * @param Consumer Key privided consumerKey Provider
	 * @param Consumer Secret privided consumerSecret Provider
	 * @param oauthToken
	 * @param oauthTokenSecret
	 * @param oauthVerifier
	 */
	public TjOAuth(String consumerKey, String consumerSecret, String oauthToken, String oauthTokenSecret, String oauthVerifier) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.oauthToken = oauthToken;
		this.oauthTokenSecret = oauthTokenSecret;
		this.oauthVerifier = oauthVerifier;
	}

	/**
	 * Create the new TjOAuth object.
	 * 
	 * @param Consumer Key privided consumerKey Provider
	 * @param Consumer Secret privided consumerSecret Provider
	 * @param oauthToken
	 * @param oauthTokenSecret
	 */
	public TjOAuth(String consumerKey, String consumerSecret, String oauthToken, String oauthTokenSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.oauthToken = oauthToken;
		this.oauthTokenSecret = oauthTokenSecret;
	}

	/**
	 * Return the consumer key.
	 * @return consumerKey
	 */
	public String getConsumerKey() {
		return this.consumerKey;
	}
	
	/**
	 * Return the consumer secret.
	 * @return consumerSecret
	 */
	public String getConsumerSecret() {
		return this.consumerSecret;
	}
	
	/**
	 * Set the request token.
	 * @param oauthToken
	 */
	public void setOAuthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}
	
	/**
	 * Return the request token.
	 * @return oauthToken
	 */
	public String getOAuthToken() {
		return this.oauthToken;
	}

	/**
	 * Return the request token before update.
	 * @return oauthTokenOld
	 */
	public String getOAuthTokenOld() {
		return this.oauthTokenOld;
	}

	/**
	 * Set the request token secret.
	 * @param oauthTokenSecret
	 */
	public void setOAuthTokenSecret(String oauthTokenSecret) {
		this.oauthTokenSecret = oauthTokenSecret;
	}
	
	/**
	 * Return the request token secret.
	 * @return oauthTokenSecret
	 */
	public String getOAuthTokenSecret() {
		return this.oauthTokenSecret;
	}

	/**
	 * Return the request token secret before update.
	 * @return oauthTokenSecretOld
	 */
	public String getOAuthTokenSecretOld() {
		return this.oauthTokenSecretOld;
	}

	/**
	 * リクエストトークンが承認された時にもらえるoauth_verifierの値を設定します.
	 * @param oauthVerifier
	 */
	public void setOAuthVerifier(String oauthVerifier) {
		this.oauthVerifier = oauthVerifier;
	}
	
	/**
	 * リクエストトークンが承認された時にもらえるoauth_verifierの値を返します.
	 * @return oauthVerifier
	 */
	public String getOAuthVerifier() {
		return this.oauthVerifier;
	}
	
	/**
	 * Provider からアクセス・トークンを取得します.
	 * @throws TjException
	 */
	public void getAccessToken() throws TjException {
		try {
			StringBuilder url = new StringBuilder(ACCESS_TOKEN_URL);
			TjHttpClient.httpGet(url.toString(), this, null, new TjHttpCallback() {
				@Override
				public <T> T call(InputStream inputStream) throws TjException {
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder res = new StringBuilder();
				    String line;
				    try {
						while ((line = br.readLine()) != null) {
							res.append(line);
						}
						String[] parameters = res.toString().split("&");
						for (String parameter : parameters) {
							String[] keyAndValue = parameter.split("=");
							String key = keyAndValue[0];
							String value = keyAndValue[1];
							if ("oauth_token".equals(key)) {
								oauthTokenOld = getOAuthToken();
								setOAuthToken(value);
							} else if ("oauth_token_secret".equals(key)) {
								oauthTokenSecretOld = getOAuthTokenSecret();
								setOAuthTokenSecret(value);
							}
						}
						return null;

					} catch (IOException e) {
						throw new TjException("It was failed getting access token.", e);
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								throw new TjException("It was failed getting access token.", e);
							}
						}
					}
				}
			});
			
		} catch (IOException e) {
			throw new TjException("It was failed to getting access token.", e);
		}
	}

	/**
	 * Provider の認証用URLを返します.
	 * 
	 * @param oauthCallback
	 * @return 認証用URL
	 */
	public String getAuthorizeURL(String oauthCallback) {
		if (oauthCallback == null) {
			return AUTHORIZE_URL + "?oauth_token=" + getOAuthToken();
		}
		return AUTHORIZE_URL + "?oauth_token=" + getOAuthToken() + "&oauth_callback=" + oauthCallback;
	}

	/**
	 * Provider の認証用URLを返します.
	 * @return 認証用URL
	 */
	public String getAuthorizeURL() {
		return getAuthorizeURL(null);
	}
	
	/**
	 * Provider からリクエスト・トークンを取得します.
	 * @throws TjException
	 */
	public void getRequestToken() throws TjException {
		try {
			StringBuilder url = new StringBuilder(REQUEST_TOKEN_URL);
			TjHttpClient.httpGet(url.toString(), this, null, new TjHttpCallback() {
			
				@Override
				public <T> T call(InputStream inputStream) throws TjException {
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder res = new StringBuilder();
				    String line;
				    try {
						while ((line = br.readLine()) != null) {
							res.append(line);
						}
						String[] parameters = res.toString().split("&");
						for (String parameter : parameters) {
							String[] keyAndValue = parameter.split("=");
							String key = keyAndValue[0];
							String value = keyAndValue[1];
							if ("oauth_token".equals(key)) {
								setOAuthToken(value);
							} else if ("oauth_token_secret".equals(key)) {
								setOAuthTokenSecret(value);
							}
						}
						return null;

					} catch (IOException e) {
						throw new TjException("It was failed getting request token.", e);
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								throw new TjException("It was failed getting request token.", e);
							}
						}
					}
				}
			});
			
		} catch (IOException e) {
			throw new TjException("It was failed to getting request token.", e);
		}
	}

	/**
	 * ランダムな数字列を返します.
	 * @return ランダムな数字列
	 */
	private String getNonce() {
		return Long.toString(System.nanoTime());
	}

	/**
	 * 現在日時のLong表現を文字列にして返します.
	 * @return 現在日時
	 */
	private String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 署名対象のテキストをそれぞれURLエンコードし、＆で結合して返します.
	 * @param method
	 * @param accessURL
	 * @param requestParameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getSignatureBaseString(String method, String accessURL, String requestParameters)
	throws UnsupportedEncodingException {
		return urlEncode(method) + "&" + urlEncode(accessURL) + "&" + urlEncode(requestParameters);
	}

	/**
	 * 署名キーを返します.
	 * Consumer Secret 及び Token Secret を & でつなげた文字列が署名キーになります.
	 * 
	 * @return 署名キー
	 * @throws UnsupportedEncodingException 
	 */
	private String getKeyString() throws UnsupportedEncodingException{	
		if (getOAuthToken() == null) {
			// Request Token
			return urlEncode(getConsumerSecret()) + "&";
		} else {
			// Access Token
			return urlEncode(getConsumerSecret()) + "&" + urlEncode(getOAuthTokenSecret());
		}
	}

	/**
	 * 元となる文字列をBase64でエンコードして返します.
	 * URLEncode(Base64(HMAC-SHA1(signatureBaseString)))
	 * 
	 * @param signatureBaseString
	 * @param keyString
	 * @return 署名した文字列
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 */
	private String getSignature(String signatureBaseString, String keyString)
	throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		String signature = null;
		String algorithm = "HmacSHA1";
		Mac mac = Mac.getInstance(algorithm);
		Key key= new SecretKeySpec(keyString.getBytes(), algorithm);
		mac.init(key);
		byte[] digest = mac.doFinal(signatureBaseString.getBytes());
		signature = urlEncode(encodeBase64(digest));
		
		return signature;
	}

	/**
	 * OAuth用のAuthorizationヘッダを返します.
	 * @param httpMethod
	 * @param url
	 * @param params
	 * @return
	 * @throws TjException
	 */
	public String getOAuthHeader(String httpMethod, String url, Map<String, String> params)
	throws TjException {
		try {
			// 署名キーの作成
			String _keyString = getKeyString();
			
			// 署名対象のテキストの作成
			String _oauth_nonce = getNonce();
			String _oauth_timestamp = getTimeStamp();
			String _requestParameters = getAuthorizationParameter(params, _oauth_nonce, _oauth_timestamp);
			String _signatureBaseString = getSignatureBaseString(httpMethod, url, _requestParameters);

			// HMAC-SHA1での署名生成
			String _signature = getSignature(_signatureBaseString, _keyString);
			
			// Authorizationヘッダの作成
			StringBuilder header = new StringBuilder("OAuth ");
			header.append(" oauth_consumer_key=\"").append(getConsumerKey()).append("\"");
			header.append(", oauth_nonce=\"").append(_oauth_nonce).append("\"");
			header.append(", oauth_signature=\"").append(_signature).append("\"");
			header.append(", oauth_signature_method=\"").append(SIGNATURE_METHOD).append("\"");
			header.append(", oauth_timestamp=\"").append(_oauth_timestamp).append("\"");
			if (getOAuthToken() != null) {
				header.append(", oauth_token=\"").append(getOAuthToken()).append("\"");
			}
			header.append(", oauth_version=\"").append(OAUTH_VERSION).append("\"");
			if (getOAuthVerifier() != null) {
				header.append(", oauth_verifier=\"").append(getOAuthVerifier()).append("\"");
			}
			
			return header.toString();
			
		} catch (UnsupportedEncodingException e) {
			throw new TjException("It was failed to getting authorization header.", e);
		} catch (InvalidKeyException e) {
			throw new TjException("It was failed to getting authorization header.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new TjException("It was failed to getting authorization header.", e);
		}
	}

	private String getAuthorizationParameter(Map<String, String> params, String oauth_nonce, String oauth_timestamp)
	throws TjException {
		Map<String, String> _params = new HashMap<String, String>();
		_params.put("oauth_consumer_key", getConsumerKey());
		_params.put("oauth_nonce", oauth_nonce);
		_params.put("oauth_signature_method", SIGNATURE_METHOD);
		_params.put("oauth_timestamp", oauth_timestamp);
		if (getOAuthToken() != null) {
			_params.put("oauth_token", getOAuthToken());
		}
		_params.put("oauth_version", OAUTH_VERSION);
		
		if (params != null && params.size() > 0) {
			Set<String> set = params.keySet();
			Iterator<String> itr = set.iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				_params.put(key, params.get(key));
			}
		}
		return TjUtil.canonicalize(new TreeMap<String, String>(_params));
	}

	
	/**
	 * 
	 * @param httpMethod
	 * @param url
	 * @param params
	 * @return
	 * @throws TjException
	 */
	public String getAuthorizationURL(String httpMethod, String url, Map<String, String> params) throws TjException {
		try {
			String requestParameters = getAuthorizationParameter(params);
			String signatureBaseString = getSignatureBaseString(httpMethod, url, requestParameters);
			String keyString = getKeyString();
			String signature = getSignature(signatureBaseString, keyString);
			
			StringBuilder header = new StringBuilder(url);
			header.append("?");
			header.append(requestParameters);
			header.append("&oauth_signature=");
			header.append(signature);

			return header.toString();
			
		} catch (InvalidKeyException e) {
			throw new TjException("It was failed to getting authorization url.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new TjException("It was failed to getting authorization url.", e);
		} catch (UnsupportedEncodingException e) {
			throw new TjException("It was failed to getting authorization url.", e);
		}
	}

	/**
	 * Authorization用のパラメータを作成して返します.
	 * パラメータは以下の通り：
	 * <ul>
	 * <li>oauth_token</li>
	 * <li>oauth_consumer_key</li>
	 * <li>oauth_nonce</li>
	 * <li>oauth_signature_method</li>
	 * <li>oauth_timestamp</li>
	 * <li>oauth_version</li>
	 * </ul>
	 * @param params
	 * @return Authorization用のパラメータ
	 * @throws TjException
	 */
	private String getAuthorizationParameter(Map<String, String> params) throws TjException {
		try {
			Map<String, String> _params = new HashMap<String, String>();
			_params.put("oauth_token", oauthToken);
			_params.put("oauth_consumer_key", consumerKey);
			_params.put("oauth_nonce", getNonce());
			_params.put("oauth_signature_method", SIGNATURE_METHOD);
			_params.put("oauth_timestamp", getTimeStamp());
			_params.put("oauth_version", OAUTH_VERSION);
			
			if (params != null && params.size() > 0) {
				Set<String> set = params.keySet();
				Iterator<String> itr = set.iterator();
				while (itr.hasNext()) {
					String key = itr.next();
					_params.put(key, urlEncode(params.get(key)));
				}
			}
			return TjUtil.canonicalize(new TreeMap<String, String>(_params));
			
		} catch (IOException e) {
			throw new TjException("It was failed to getting authorization header.", e);
		}
	}

}
