package osc.git.eh3.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	private static final int timeOut = 30000;// timeOut(Millisecond)
	private static final int BUFFERSIZE = 2048;

	private static Registry<CookieSpecProvider> getRegistry() {
		return RegistryBuilder.<CookieSpecProvider> create().register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).register("easy", getCookieProvider()).build();
	}

	private static CookieSpecProvider getCookieProvider() {
		return new CookieSpecProvider() {
			public CookieSpec create(HttpContext arg0) {
				// TODO Auto-generated method stub
				return new BrowserCompatSpec() {
					@Override
					public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
						// Oh, I am easy
					}
				};
			}
		};
	}

	public static String sendPostParamDefaultCookie(String url, Map<String, String> postParam) throws Exception {
		String result = null;
		StringBuffer out = null;
		CloseableHttpClient httpclient = null;
		HttpPost postmethod = null;
		RequestConfig requestConfig = null;
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		BasicCookieStore cookieStore = null;
		Registry<CookieSpecProvider> r = null;
		List<NameValuePair> nvps = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream inputStream = null;
		try {
			out = new StringBuffer();// 返回数据
			cookieStore = new BasicCookieStore();
			r = getRegistry();
			postmethod = new HttpPost(url);
			requestConfig = RequestConfig.custom().setCookieSpec("easy").setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut)
					.setSocketTimeout(timeOut).build();
			httpclient = HttpClients.custom().setDefaultCookieSpecRegistry(r).setDefaultRequestConfig(requestConfig)
					.setDefaultCookieStore(cookieStore).build();
			nvps = new ArrayList<NameValuePair>();
			if (postParam != null && postParam.size() != 0) {
				for (String param : postParam.keySet()) {
					nvps.add(new BasicNameValuePair(param, postParam.get(param)));
				}
			}
			postmethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = httpclient.execute(postmethod);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				entity = response.getEntity();
				inputStream = entity.getContent();
				isr = new InputStreamReader(inputStream, "UTF-8");
				br = new BufferedReader(isr);
				int count = 0;
				char[] b = new char[BUFFERSIZE];
				while ((count = br.read(b, 0, BUFFERSIZE)) != -1) {
					out.append(new String(b, 0, count));
				}
			} else {
				out.append(SystemConstants.SYSTEM_ERROR);
			}
		} catch (Exception e) {
			out.append(SystemConstants.SYSTEM_ERROR);
		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (response != null) {
				response.close();
			}
			if (postmethod != null) {
				postmethod.releaseConnection();
			}
			if (httpclient != null) {
				httpclient.close();
			}
			result = out.toString();
			out.setLength(0);
			out = null;
		}
		return result;
	}

	public static String sendPostParam(String url, Map<String, String> postParam) throws Exception {
		log.debug("请求地址：" + url);
		String result = null;
		StringBuffer out = null;
		CloseableHttpClient httpclient = null;
		HttpPost postmethod = null;
		RequestConfig requestConfig = null;
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		List<NameValuePair> nvps = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream inputStream = null;
		try {
			out = new StringBuffer();// 返回数据
			httpclient = HttpClients.createDefault();
			postmethod = new HttpPost(url);
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut).setSocketTimeout(timeOut)
					.build();
			postmethod.setConfig(requestConfig);
			nvps = new ArrayList<NameValuePair>();
			if (postParam != null && postParam.size() != 0) {
				for (String param : postParam.keySet()) {
					nvps.add(new BasicNameValuePair(param, postParam.get(param)));
				}
			}
			postmethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = httpclient.execute(postmethod);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				entity = response.getEntity();
				inputStream = entity.getContent();
				isr = new InputStreamReader(inputStream, "UTF-8");
				br = new BufferedReader(isr);
				int count = 0;
				char[] b = new char[BUFFERSIZE];
				while ((count = br.read(b, 0, BUFFERSIZE)) != -1) {
					out.append(new String(b, 0, count));
				}
			} else {
				out.append(SystemConstants.SYSTEM_ERROR);
			}
		} catch (Exception e) {
			out.append(SystemConstants.SYSTEM_ERROR);
		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (response != null) {
				response.close();
			}
			if (postmethod != null) {
				postmethod.releaseConnection();
			}
			if (httpclient != null) {
				httpclient.close();
			}
			result = out.toString();
			out.setLength(0);
			out = null;
		}
		return result;
	}

	public static String sendPostJSONData(String url, String json) throws Exception {
		log.debug("请求地址：" + url);
		String result = null;
		StringBuffer out = null;
		CloseableHttpClient httpclient = null;
		HttpPost postmethod = null;
		RequestConfig requestConfig = null;
		HttpEntity entity = null;
		StringEntity strentity = null;
		CloseableHttpResponse response = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream inputStream = null;
		try {
			out = new StringBuffer();// 返回数据
			httpclient = HttpClients.createDefault();
			postmethod = new HttpPost(url);
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut).setSocketTimeout(timeOut)
					.build();
			postmethod.setConfig(requestConfig);
			strentity = new StringEntity(URLEncoder.encode(json.toString(), "UTF-8"));
			postmethod.addHeader("content-type", "application/json");
			postmethod.setEntity(strentity);
			response = httpclient.execute(postmethod);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				entity = response.getEntity();
				inputStream = entity.getContent();
				isr = new InputStreamReader(inputStream, "UTF-8");
				br = new BufferedReader(isr);
				int count = 0;
				char[] b = new char[BUFFERSIZE];
				while ((count = br.read(b, 0, BUFFERSIZE)) != -1) {
					out.append(new String(b, 0, count));
				}
			} else {
				out.append(SystemConstants.SYSTEM_ERROR);
			}
		} catch (Exception e) {
			out.append(SystemConstants.SYSTEM_ERROR);
		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (response != null) {
				response.close();
			}
			if (postmethod != null) {
				postmethod.releaseConnection();
			}
			if (httpclient != null) {
				httpclient.close();
			}
			result = out.toString();
			out.setLength(0);
			out = null;
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
