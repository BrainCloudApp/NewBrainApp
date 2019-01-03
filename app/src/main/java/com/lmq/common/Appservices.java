package com.lmq.common;

import com.google.gson.JsonObject;
import com.r.http.cn.RHttp;
import com.r.http.cn.callback.HttpCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONObject;

import java.util.TreeMap;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class Appservices {
    private final String LOGIN = "/app/login";
    private final String INITCONTENT="/app/news";


    public void login(String username, String password, LifecycleProvider lifecycle, HttpCallback callback) {
        /**
         * 构建请求参数
         */
      //  TreeMap<String, Object> request = new TreeMap<>();
        try {
            JSONObject request = new JSONObject();
            request.put("username", username);
            request.put("password", password);


            /**
             * 发送请求
             */
            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(LOGIN)
                    .setBodyString(String.valueOf(request), true)
                    //  .addParameter(request)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }

    }
    public void getinitContent(LifecycleProvider lifecycle, HttpCallback callback){
        RHttp http = new RHttp.Builder()
                .get()
                .baseUrl(AppContact.getBaseUrl)
                .apiUrl(INITCONTENT)

                .lifecycle(lifecycle)
                .build();

        http.request(callback);

    }
}
