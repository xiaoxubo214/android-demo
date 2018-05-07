package com.by.wind.component.net;


import com.by.wind.util.logger.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by wind ic_on 17/2/23.
 */
public class LoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        long t1 = System.nanoTime();
        Logger.init();
        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                contentType.charset(UTF8);
            }
            Logger.i(String.format("Sending request %s on %s%n%s %s %n%s",
                    request.url(), chain.connection(), request.headers(), requestPath(request.url()), URLDecoder.decode(buffer.readString(charset), "utf-8")));
        } else {
            Logger.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        long t2 = System.nanoTime();
        if (responseBody.contentLength() != 0) {

            Logger.i(String.format("Received response for %s in %.1fms%n%s %s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), buffer.clone().readString(charset)));
            if (response.code() == 401) {
                response = response.newBuilder().code(200).build();
            }
        } else {

            Logger.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        }
        return response;
    }

    private static String requestPath(HttpUrl url) {
        String path = url.encodedPath();
        String query = url.encodedQuery();
        return query != null ? (path + '?' + query) : path;
    }

}
