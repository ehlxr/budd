/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrv@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.util;

import com.google.common.collect.Maps;
import io.github.ehlxr.enums.FormType;
import io.github.ehlxr.enums.HttpContentType;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author ehlxr
 * @since 2020/4/20.
 */
public class HttpUtil {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build();

    public static void trustAllClient() {
        Try.of(() -> {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());

            client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();
        }).trap(Throwable::printStackTrace).run();
    }

    public static String get(String url, Map<String, String> headers) {
        String resp;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                resp = response.body() != null ? response.body().string() : "";
            } else {
                throw new RuntimeException("send get http request failed. Unexpected code: " + response);
            }
        } catch (Exception e) {
            throw new RuntimeException("send get http request failed.", e);
        }

        return resp;
    }

    public static String get(String url) {
        return get(url, Maps.newHashMap());
    }

    public static String post(String url, Map<String, String> headers, String body) {
        String resp;
        try {
            MediaType mediaType = MediaType.parse(headers.getOrDefault(HttpContentType.KEY.value(), HttpContentType.JSON.value()));
            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .post(RequestBody.create(mediaType, body))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                resp = response.body() != null ? response.body().string() : "";
            } else {
                throw new RuntimeException("send post http request failed. Unexpected code: " + response);
            }
        } catch (Exception e) {
            throw new RuntimeException("send post http request failed.", e);
        }

        return resp;
    }

    public static String post(String url, String body) {
        return post(url, Maps.newHashMap(), body);
    }

    public static String formPost(String url, Map<String, Map<FormType, Object>> params) {
        return formPost(url, params, Maps.newHashMap());
    }

    public static String formPost(String url, Map<String, Map<FormType, Object>> params, Map<String, String> header) {
        String resp;
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            params.forEach((key, value) -> value.forEach((k, v) -> {
                if (k.equals(FormType.FILE)) {
                    File file = (File) v;
                    builder.addFormDataPart(
                            key,
                            file.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"), file));
                } else if (k.equals(FormType.STRING)) {
                    builder.addFormDataPart(key, String.valueOf(v));
                }
            }));

            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .headers(Headers.of(header))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                resp = response.body() != null ? response.body().string() : "";
            } else {
                throw new RuntimeException("send multipart post http request failed. Unexpected code: " + response);
            }
        } catch (Exception e) {
            throw new RuntimeException("send multipart post http request failed.", e);
        }

        return resp;
    }

    public static String appendParam(Map<String, Object> param) {
        StringBuilder sb = new StringBuilder();
        param.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));

        return sb.substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {
        HttpUtil.trustAllClient();
    }
}

