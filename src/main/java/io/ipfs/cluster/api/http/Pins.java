package io.ipfs.cluster.api.http;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by qs on 2018/5/10.
 */
public class Pins {
    private final String path;
    OkHttpClient okHttpClient;

    public Pins(String path) {
        this.path = path;
    }

    public OkHttpClient getinstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public void get(Callback callback) throws IOException {
        Request build = new Request.Builder()//创建Request 对象。
                .url(path)
                .build();
//        getinstance().newCall(build).execute();
        getinstance().newCall(build).enqueue(callback);

    }

    public void post(Callback callback) {
        Request build = new Request.Builder()//创建Request 对象。
                .url(path)
                .post(new FormBody.Builder().build())
                .build();
        getinstance().newCall(build).enqueue(callback);
    }

    public void delete(Callback callback) {
        Request build = new Request.Builder()//创建Request 对象。
                .url(path)
                .delete()
                .build();
        getinstance().newCall(build).enqueue(callback);
    }
}
