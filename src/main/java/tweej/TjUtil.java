package tweej;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public final class TjUtil {
	
	public static final String UTF8_CHARSET = "UTF-8";
	public static final boolean isDebug = true;

	public final static String dumpResponseHeader(HttpURLConnection http_connection) {
		StringBuilder _header = new StringBuilder();
		if (isDebug) {
			Map<String, List<String>> header = http_connection.getHeaderFields();
			for ( String key : header.keySet() ) {
				if ( key != null) {
					_header.append(key).append(":").append(header.get(key)).append("\n");
				}
			}
		}
		return _header.toString();
	}

	/**
	 * 指定の文字列を「UTF-8」でURLエンコードした文字列を返します.
	 * @param str
	 * @return URLエンコードされた文字列
	 * @throws UnsupportedEncodingException
	 */
	public final static String urlEncode(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, UTF8_CHARSET);
	}

	/**
	 * 指定のマップのキーと値を「=」で結び、「&」で連結した文字列を返します.
	 * @param sortedParamMap
	 * @return マップで保持しているキーと値を連結した文字列
	 */
	public final static String canonicalize(SortedMap<String, String> sortedParamMap) {
	    if (sortedParamMap.isEmpty()) {
		    return "";
		}
		Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();
		StringBuilder url = new StringBuilder();
		   
		while (iter.hasNext()) {
		    Map.Entry<String, String> kvpair = iter.next();
		    url.append(percentEncodeRfc3986(kvpair.getKey()));
		    url.append("=");
		    url.append(percentEncodeRfc3986(kvpair.getValue()));
		    if (iter.hasNext()) {
		    	url.append("&");
		    }
		}
	    return url.toString();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public final static String percentEncodeRfc3986(String s) {
		String out;
		try {
		    out = URLEncoder.encode(s, UTF8_CHARSET)
		    	.replace("+", "%20")
		        .replace("*", "%2A")
		        .replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
		    out = s;
		}
		return out;
	}

	/**
	 * 指定のバイト配列を Base64 でエンコードした文字列で返します.
	 * @param data
	 * @return Base64 でエンコードされた文字列
	 */
	public static String encodeBase64(final byte[] data) {
		if (data == null) {
			throw new NullPointerException("The str is null.");
		}

        int len = data.length;
        if (len <= 0) {
            throw new IllegalArgumentException();
        }

        int off = 0;
        char out[] = new char[(len / 3) * 4 + 4];
        int rindex = off;
        int windex = 0;
        int rest;

        for (rest = len - off; rest >= 3; rest -= 3) {
            int i = ((data[rindex] & 255) << 16) + ((data[rindex + 1] & 255) << 8) + (data[rindex + 2] & 255);
            out[windex++] = S_BASE64CHAR[i >> 18];
            out[windex++] = S_BASE64CHAR[i >> 12 & 63];
            out[windex++] = S_BASE64CHAR[i >> 6 & 63];
            out[windex++] = S_BASE64CHAR[i & 63];
            rindex += 3;
        }

        if (rest == 1) {
            int i = data[rindex] & 255;
            out[windex++] = S_BASE64CHAR[i >> 2];
            out[windex++] = S_BASE64CHAR[i << 4 & 63];
            out[windex++] = '=';
            out[windex++] = '=';
        
        } else if(rest == 2) {
            int i = ((data[rindex] & 255) << 8) + (data[rindex + 1] & 255);
            out[windex++] = S_BASE64CHAR[i >> 10];
            out[windex++] = S_BASE64CHAR[i >> 4 & 63];
            out[windex++] = S_BASE64CHAR[i << 2 & 63];
            out[windex++] = '=';
        }
        return new String(out, 0, windex);
	}

	private static final char S_BASE64CHAR[] = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
		'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
		'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', '+', '/'
	};

}
