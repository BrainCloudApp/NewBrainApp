package com.lmq.common;

import com.lmq.base.BaseActivity;
import com.lmq.base.BasePresenter;
import com.lmq.http.CommonAllHttpCallback;
import com.lmq.http.CommonUploadCallback;
import com.r.http.cn.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class CommonPresenter extends BasePresenter<CommonView, LifecycleProvider> {
    private final String TAG = CommonPresenter.class.getSimpleName();
    public CommonPresenter(CommonView view, LifecycleProvider activity) {
        super(view, activity);
    }
    public void getPatientExprienceList(String id_pat) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().getPatientexpriencelist(id_pat,getActivity(), httpCallback);

    }
    public void getExprienceList(String keyword) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().getexpriencelist(keyword,getActivity(), httpCallback);

    }
    public void getDoctorInfo(String id_doc) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().getDoctorInfo(id_doc,getActivity(), httpCallback);

    }
    public void getPersonInfo(String id_pat) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().getPatientInfo(id_pat,getActivity(), httpCallback);

    }
    public void advice(String content,File uploadfile){
        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();
        CommonUploadCallback uploadCallback=  new CommonUploadCallback<String>() {
            @Override
            public String convert(String data) {

                return data;
                //return new Gson().toJson(data);
            }

            @Override
            public void onProgress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile) {
                // progressBar.setProgress((int) (progress * 100));
                //tvIndex.setText("正在上传第:  " + currentIndex + "  张，总共:  " + totalFile + "   " + (progress * 100) + "%");
                // tvResult.setText("正在上传中\n文件名称:" + file.getName() + "\n已上传:" + currentSize + "  总大小:" + totalSize + "\n当前文件序号:" + currentIndex + "  文件总数:" + totalFile);
                //tvResult.setText("正在上传中\n文件名称:" + file.getName() + "\n已上传:" + currentSize + "  总大小:" + totalSize);

            }

            @Override
            public void onSuccess(String value) {
                //tvResult.setText("上传成功\n" + value);
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(value!=null) {

                        getView().onResult(value);
                    }
                }

            }

            @Override
            public void onError(int code, String desc) {

                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {

                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };
        Map<String, File> fileMap = new TreeMap<>();
        fileMap.put("img", uploadfile);

        new Appservices().advice(content,fileMap,getActivity(), uploadCallback);
    }
    public void share(String id_ex,String content,File uploadfile){
        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();
        CommonUploadCallback uploadCallback=  new CommonUploadCallback<String>() {
            @Override
            public String convert(String data) {

                return data;
                //return new Gson().toJson(data);
            }

            @Override
            public void onProgress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile) {
                // progressBar.setProgress((int) (progress * 100));
                //tvIndex.setText("正在上传第:  " + currentIndex + "  张，总共:  " + totalFile + "   " + (progress * 100) + "%");
                // tvResult.setText("正在上传中\n文件名称:" + file.getName() + "\n已上传:" + currentSize + "  总大小:" + totalSize + "\n当前文件序号:" + currentIndex + "  文件总数:" + totalFile);
                //tvResult.setText("正在上传中\n文件名称:" + file.getName() + "\n已上传:" + currentSize + "  总大小:" + totalSize);

            }

            @Override
            public void onSuccess(String value) {
                //tvResult.setText("上传成功\n" + value);
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(value!=null) {

                        getView().onResult(value);
                    }
                }

            }

            @Override
            public void onError(int code, String desc) {

                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {

                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };
        Map<String, File> fileMap = new TreeMap<>();
        fileMap.put("img", uploadfile);

        new Appservices().comment(id_ex,content,fileMap,getActivity(), uploadCallback);
    }
    public void searchDoctor(String keyword) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().searchDoctor(keyword,getActivity(), httpCallback);

    }
    public void searchPatient(String keyword) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonAllHttpCallback httpCallback = new CommonAllHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().onResult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };


        new Appservices().searchPatient(keyword,getActivity(), httpCallback);

    }
}
