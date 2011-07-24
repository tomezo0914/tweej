package tweej.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tweej.TjException;

/**
 * HTTP リクエストのインプットストリームを文字列として返すデバッグ用クラス.
 * @author tome
 *
 */
public class TjHttpCallbackDebug implements TjHttpCallback {

	@SuppressWarnings("unchecked")
	@Override
	public String call(InputStream inputStream) throws TjException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder ret = new StringBuilder();
	    String line;
	    try {
			while ((line = in.readLine()) != null) {
				ret.append(line).append(System.getProperty("line.separator"));
			}
		    in.close();
		    
		    return ret.toString();
		    
		} catch (IOException e) {
			throw new TjException("It was failed to read inputstream.", e);
		}
	}

}
