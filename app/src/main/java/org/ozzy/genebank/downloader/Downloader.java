package org.ozzy.genebank.downloader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * 传入要访问的URL，返回获取到的数据*/
class Downloader {
    private String url;
    private String responseData;
    Thread thread;
    Downloader(String paraUrl) throws IOException, InterruptedException {
        url = paraUrl;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = okHttpClient.newCall(request).execute();
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getData() throws InterruptedException {
        thread.start();//启动thread
        thread.join();//等待thread结束
        return responseData;
    }
}

