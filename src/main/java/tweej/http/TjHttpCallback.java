package tweej.http;

import java.io.InputStream;

import tweej.TjException;

/**
 * HTTP リクエストのインプットストリーム読み込みをフックするインターフェイス.
 * @author tome
 *
 */
public interface TjHttpCallback {
	
	/**
	 * HTTP リクエストのインプットストリーム読み込みをフックします.
	 * @param <T>
	 * @param inputStream
	 * @return 実装クラスで定義された返却値
	 * @throws TjException
	 */
	abstract <T> T call(InputStream inputStream) throws TjException; 

}
