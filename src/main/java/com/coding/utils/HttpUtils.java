package com.coding.utils;

import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    public static final MediaType APPLICATION_JSON = MediaType.parse("application/json");
    public static final MediaType MULTIPART_FORMDATA = MediaType.parse("multipart/form-data");

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build();

    public static ResponseBody getFileBody(String fileName, File file, URL url) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        return getResult2(url, requestBody);
    }

    public static ResponseBody getFileBody(String fileName, byte[] content, URL url) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), content))
                .build();

        return getResult2(url, requestBody);
    }

    private static ResponseBody getResult2(URL url, RequestBody requestBody) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body();
    }

    public static ResponseBody compressImage(MultipartFile file,String host) throws IOException {
        return getFileBody(file.getOriginalFilename(), file.getBytes(), new URL(host + "/stream/img2img"));
    }
}
