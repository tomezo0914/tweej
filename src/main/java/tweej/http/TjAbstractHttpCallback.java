package tweej.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import tweej.TjException;

/**
 * API呼び出し結果を解析するクラスの抽象基底クラス.
 * 
 * @author tome
 *
 */
public abstract class TjAbstractHttpCallback implements TjHttpCallback {

	public static final Logger logger = Logger.getLogger(TjAbstractHttpCallback.class.getName());

	/**
	 * HTTP リクエストのインプットストリーム読み込みをフックします.
	 * エラーが返ってきていないかをチェックし、
	 * エラーでなければ抽象メソッド callback を呼びます.
	 * 
	 * @param <T>
	 * @param inputStream
	 * @return 実装クラスで定義された返却値
	 * @throws TjException
	 */
	public <T> T call(InputStream inputStream) throws TjException {
		
	    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true);
	    DocumentBuilder builder = null;
	    Document doc = null; 
	    try {
			builder = domFactory.newDocumentBuilder();
			doc = builder.parse(inputStream);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			
			String error = xpath.evaluate("/hash/error/text()", doc);
			if (error != null && !"".equals(error)) {
				// api error happend.
				logger.info(error);
				throw new TjException(error);
				
			} else {
				return callback(doc, xpath);
			}

		} catch (ParserConfigurationException e) {
			throw new TjException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new TjException(e.getMessage(), e);
		} catch (IOException e) {
			throw new TjException(e.getMessage(), e);
		} catch (XPathExpressionException e) {
			throw new TjException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param <T>
	 * @param doc
	 * @param xpath
	 * @return
	 * @throws TjException
	 */
	public abstract <T> T callback(Document doc, XPath xpath) throws TjException;

}
