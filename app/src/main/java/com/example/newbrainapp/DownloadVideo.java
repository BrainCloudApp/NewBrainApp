package com.example.newbrainapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadVideo {

    private static final String TAG = "JZVD";

    public static void downloadThread(String downloadPath, Context mContext){
        final String path = downloadPath;
        // 创建文件夹，在存储卡下
        String dirName = Environment.getExternalStorageDirectory() + "/" + mContext.getPackageName();
        File file = new File(dirName);
        // 文件夹不存在时创建
        if (!file.exists()) {
            file.mkdir();
        }

        // 下载后的文件名
        int i = path.lastIndexOf("/"); // 取的最后一个斜杠后的字符串为名
        final String fileName = dirName + path.substring(i, path.length());
        File file1 = new File(fileName);
        if (file1.exists()) {
            Toast.makeText(mContext, "视频已存在", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DOWNLOAD(path, fileName);
                }
            }).start();
        }
    }

    private static void DOWNLOAD(String path, String fileName){
        try {
            URL url = new URL(path);
            // 打开连接
            URLConnection conn = url.openConnection();
            // 打开输入流
            InputStream is = conn.getInputStream();
            // 创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            // 写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完成后关闭流
            Log.e(TAG, "download-finish");
            os.close();
            is.close();
            //            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e.getMessage() --- " + e.getMessage());
        }
    }
}
