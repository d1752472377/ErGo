
package com.example.ergo.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author luoqifeng
 */
@Slf4j
@Component
public class OkHttpClientUtil {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private static OkHttpClient okHttpClient;

    // 为使用静态调用异步注入
    @Autowired
    private OkHttpClient getOkHttpClient;
    @PostConstruct
    public void init() {
        okHttpClient = getOkHttpClient;
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @return string
     * */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static byte[] doGetByte(String url) {
        return doGetByte(url, null, null);
    }

    public static String doPost(String url) {
        return doPost(url, null, null);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @return string
     * */
    public static String doGetToParams(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public static String doGetToHeaders(String url, Map<String, String> headers) {
        return doGet(url, null, headers);
    }
    /**
     * get 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String header:headers.keySet()){
                builder.addHeader(header, headers.get(header));
            }
        }
        Request request = builder.url(sb.toString()).build();
        log.info("do get request and url[{}]", sb.toString());
        return executeBody(request);
    }

    public static byte[] doGetByte(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String header:headers.keySet()){
                builder.addHeader(header, headers.get(header));
            }
        }
        Request request = builder.url(sb.toString()).build();
        log.info("do get request and url[{}]", sb.toString());
        return executeByte(request);
    }
    /**
     * post 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public static String doPostForm(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);
        return execute(request);
    }

    /**
     * post 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @param headers 请求头字段 {k1:v1, k2: v2, ...}
     * @return string
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String header:headers.keySet()){
                requestBuilder.addHeader(header, headers.get(header));
            }
        }
        Request request = requestBuilder.url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);
        return execute(request);
    }
    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url  请求url地址
     * @param json  请求数据, json 字符串
     * @return string
     */
    public static String doPostJson(String url, String json) {
        log.info("do post request and url[{}]", url);
        return executePost(url, json, JSON);
    }
    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url  请求url地址
     * @param json  请求数据, json 字符串
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    public static String doPostJson(String url, String json, Map<String, String> headers) {
        log.info("do post request and url[{}]", url);

        RequestBody requestBody = RequestBody.create(json, JSON);
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String header:headers.keySet()){
                builder.addHeader(header, headers.get(header));
            }
        }
        Request request = builder.url(url).post(requestBody).build();
        return execute(request);
    }
    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url  请求url地址
     * @param xml  请求数据, xml 字符串
     * @return string
     */
    public static String doPostXml(String url, String xml) {
        log.info("do post request and url[{}]", url);
        return executePost(url, xml, XML);
    }
    private static String executePost(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(data ,contentType);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return execute(request);
    }
    private static String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    private static String executeBody(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    private static byte[] executeByte(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


}

