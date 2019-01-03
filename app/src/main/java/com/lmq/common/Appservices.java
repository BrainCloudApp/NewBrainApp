package com.lmq.common;


import com.r.http.cn.RHttp;
import com.r.http.cn.callback.HttpCallback;
import com.r.http.cn.callback.UploadCallback;
import com.trello.rxlifecycle2.LifecycleProvider;


import org.json.JSONObject;

import java.io.File;
import java.util.Map;

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

    private final String MESSAGE= "/app/message";//用户消息列表
    private final String HEALTHINFO= "/app/healthinfo";//用户健康档案

    /**
     * 接口请求
     * @param api 请求方法
     * @param param 参数
     * @param lifecycle
     * @param callback
     */
    public void request(String api,JSONObject param,LifecycleProvider lifecycle, HttpCallback callback){
        try {


            String bodystring=param==null?"":String.valueOf(param);
            RHttp http = new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(api)
                    .setBodyString(bodystring, true)
                    .lifecycle(lifecycle)
                    .build();

            http.request(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 带文件上传的接口请求
     * @param api 请求方法
     * @param param json参数
     * @param fileMap 文件
     * @param lifecycle
     * @param callback
     */
    public void upload(String api,JSONObject param,Map<String, File> fileMap,LifecycleProvider lifecycle, UploadCallback callback){
        try {

            String bodystring=param==null?"":String.valueOf(param);
            /**
             * 发送请求
             */
            new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(api)

                    .setBodyString(bodystring, true)

                    .file(fileMap)
                    .lifecycle(lifecycle)
                    .build()
                    .upload(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
    public void login(String username, String password, LifecycleProvider lifecycle, HttpCallback callback) {

      //  TreeMap<String, Object> request = new TreeMap<>();
        try {
            JSONObject param = new JSONObject();
            param.put("username", username);
            param.put("password", password);
            request(LOGIN,param,lifecycle,callback);


        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }

    }
    public void getinitContent(LifecycleProvider lifecycle, HttpCallback callback){
        request(INITCONTENT,null,lifecycle,callback);



    }

    /**
     * 获取类似患者分享心得列表
     * @param keyword 关键字
     * @param lifecycle
     * @param callback
     */
    public void getexpriencelist(String keyword,LifecycleProvider lifecycle, HttpCallback callback){
        try {
            JSONObject param = new JSONObject();
            param.put("keyword", keyword);
            request(SHAREEXPLIST,param,lifecycle,callback);

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
            JSONObject param = new JSONObject();
            param.put("id_pat", id_pat);
            request(PATIENT_SHAREEXPLIST,param,lifecycle,callback);


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
            JSONObject param = new JSONObject();
            param.put("id_ex", id_ex);
            request(SHAREEXP_ZAN,param,lifecycle,callback);


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
            JSONObject param = new JSONObject();
            param.put("id_ex", id_ex);
            param.put("cotent", content);
            upload(SHAREEXP_COMMENT,param,fileMap,lifecycle,callback);

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
            JSONObject param = new JSONObject();
            param.put("keyword", keyword);
            request(SEARCH_PATIENT,param,lifecycle,callback);


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
            JSONObject param = new JSONObject();
            param.put("keyword", keyword);
            request(SEARCH_DOCTOR,param,lifecycle,callback);

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
            JSONObject param = new JSONObject();
            param.put("id_pat", id_pat);
            request(DETAIL_PATIENT,param,lifecycle,callback);


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
            JSONObject param = new JSONObject();
            param.put("id_doc", id_doc);
            request(DETAIL_DOCTOR,param,lifecycle,callback);

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
            JSONObject param = new JSONObject();
            param.put("cotent", content);

            upload(ADVICE,param,fileMap,lifecycle,callback);

        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }

    /**
     * 获取用户消息
     * @param id_pat 用户id
     * @param type 类别 1训练提醒 2 追踪警报
     * @param lifecycle
     * @param callback
     */
    public void getUserMessage(String id_pat,int type,LifecycleProvider lifecycle, HttpCallback callback){

        try {
            JSONObject param = new JSONObject();
            param.put("id_pat", id_pat);
            param.put("type", type);
            request(MESSAGE,param,lifecycle,callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
    public void getUserHealthInfo(String id_pat,LifecycleProvider lifecycle, HttpCallback callback){

        try {
            JSONObject param = new JSONObject();
            param.put("id_pat", id_pat);
            request(HEALTHINFO,param,lifecycle,callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
}
