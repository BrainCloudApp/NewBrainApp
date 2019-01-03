package com.lmq.common;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.r.http.cn.RHttp;
import com.r.http.cn.callback.HttpCallback;
import com.r.http.cn.callback.UploadCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class Appservices {
    private final String LOGIN = "/app/login";
    private final String INITCONTENT="/app/news";
    private final String SHAREEXPLIST="/app/shareexperience_list";//获取所有用户心得列表
    private final String PATIENT_SHAREEXPLIST="/app/patient_shareexperience_list";//获取某一个患者心得列表
    private final String SHAREEXP_ZAN="/app/shareexperience_support";//心得点赞
    private final String SHAREEXP_COMMENT="/app/shareexperience_comment";//心得评论
    private final String SEARCH_PATIENT="/app/search_patient";//搜索患者
    private final String SEARCH_DOCTOR="/app/ssearch_doctor";//搜索医生
    private final String DETAIL_PATIENT="/app/detail_patient";//患者信息详情
    private final String DETAIL_DOCTOR   ="/app/detail_doctor";//医生信息详情
    private final String ADVICE= "/app/advice";//软件意见建议


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

    /**
     * 获取类似患者分享心得列表
     * @param keyword 关键字
     * @param lifecycle
     * @param callback
     */
    public void getexpriencelist(String keyword,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("keyword", keyword);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(SHAREEXPLIST)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 获取某个患者的心得列表
     * @param id_pat 患者id
     * @param lifecycle
     * @param callback
     */
    public void getPatientexpriencelist(String id_pat,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("id_pat", id_pat);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(PATIENT_SHAREEXPLIST)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
    /**
     * 心得点赞
     * @param id_ex 心得id
     * @param lifecycle
     * @param callback
     */

    public void supportExp(String id_ex,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("id_ex", id_ex);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(SHAREEXP_ZAN)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 心得评论
     * @param id_ex 心得id
     * @param content 评论内容
     * @param fileMap 上传文件
     * @param lifecycle
     * @param callback
     */
    public void comment(String id_ex,String content, Map<String, File> fileMap,LifecycleProvider lifecycle, UploadCallback callback) {
        try {
            JSONObject request = new JSONObject();
            request.put("id_ex", id_ex);
            request.put("cotent", content);
            /**
             * 发送请求
             */
            new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(SHAREEXP_COMMENT)

                    .setBodyString(String.valueOf(request), true)

                    .file(fileMap)
                    .lifecycle(lifecycle)
                    .build()
                    .upload(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 搜索患者
     * @param keyword 患者关键字
     * @param lifecycle
     * @param callback
     */
    public void searchPatient(String keyword,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("keyword", keyword);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(SEARCH_PATIENT)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 搜索医生
     * @param keyword
     * @param lifecycle
     * @param callback
     */
    public void searchDoctor(String keyword,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("keyword", keyword);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(SEARCH_DOCTOR)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 获取患者详情
     * @param id_pat 患者id
     * @param lifecycle
     * @param callback
     */
    public void getPatientInfo(String id_pat,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("id_pat", id_pat);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(DETAIL_PATIENT)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 获取医生详情
     * @param id_doc 医生id
     * @param lifecycle
     * @param callback
     */
    public void getDoctorInfo(String id_doc,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject request = new JSONObject();
            request.put("id_doc", id_doc);

            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(DETAIL_DOCTOR)
                    .setBodyString(String.valueOf(request), true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 软件意见建议
     * @param content 建议内容
     * @param fileMap 图片
     * @param lifecycle
     * @param callback
     */
    public void advice(String content, Map<String, File> fileMap,LifecycleProvider lifecycle, UploadCallback callback) {
        try {
            JSONObject request = new JSONObject();
            request.put("cotent", content);
            /**
             * 发送请求
             */
            new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(ADVICE)

                    .setBodyString(String.valueOf(request), true)

                    .file(fileMap)
                    .lifecycle(lifecycle)
                    .build()
                    .upload(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
}
