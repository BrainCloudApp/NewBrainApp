package com.example.newbrainapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lmq.common.AppContact;
import com.lmq.common.Appservices;
import com.lmq.common.CommonPresenter;
import com.lmq.http.CommonHttpCallback;
import com.lmq.http.CommonUploadCallback;
import com.lmq.tool.DialogItem;
import com.lmq.tool.FileCache;
import com.lmq.tool.LmqTool;
import com.lmq.tool.PermisstionCheck;
import com.lmq.ui.HealthInfo_Activity;
import com.lmq.ui.Settings_Activity;
import com.lmq.ui.Settings_Message_Activity;
import com.lmq.ui.buletooth.BluetoothActivity;
import com.r.http.cn.RHttp;
import com.r.http.cn.callback.UploadCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment3 extends Fragment {

    private List<MyListItem> myListItems = new ArrayList<>();
    private Context mContext;
    private static final int ACTIVITYRESULT_CHOSE_SYSIMG=2;
    private static final int ACTIVITYRESULT_CHOSE_TAKEIMG=3;
    private static final int ACTIVITYRESULT_CUTIMG=4;
    private Uri muri;
    private File picpath,uploadFile;

    private final String UPLOAD_PHOTO_ICON = "/app/icon";

    @BindView(R.id.icon_image) CircleImageView iconChange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context Context) {
        super.onAttach(Context);
        mContext = Context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initItem();
        Button settings=(Button)getActivity().findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it=new Intent(getActivity(), Settings_Activity.class);
                startActivity(it);
            }
        });

        iconChange = getActivity().findViewById(R.id.icon_image);
        final boolean haspermission = getArguments().getBoolean("hasperission");
        iconChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseimg(haspermission);
            }
        });

        ItemAdapter adapter = new ItemAdapter(getContext(), R.layout.my_item, myListItems);
        final ListView listView = getActivity().findViewById(R.id.my_item_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyListItem myListItem = myListItems.get(i);
                switch (myListItem.getItemName()) {
                    case "消息提醒":
//                        Log.d("Switch", myListItem.getItemName());
                        Intent intent = new Intent(getActivity(), Settings_Message_Activity.class);
                        startActivity(intent);
                        break;
                    case "个人基本信息":
                        Toast.makeText(getContext(), myListItem.getItemName(), Toast.LENGTH_LONG);
//                        Intent intent1 = new Intent(getActivity(), WebView2.class);
//                        Log.d("Switch", myListItem.getItemName());
//                        startActivity(intent1);
                        break;
                    case "健康档案":
                        Intent intent3 = new Intent(getActivity(), HealthInfo_Activity.class);
                        startActivity(intent3);
                        break;
                    case "治疗记录":
                        Intent intent4 = new Intent(getActivity(), TreatmentRecord.class);
                        startActivity(intent4);
                        break;
                    case "训练数据采集":
                       /* Intent intent5 = new Intent(getActivity(), WebView2.class);
                        startActivity(intent5);*/
                        Intent intent5 = new Intent(getActivity(), BluetoothActivity.class);
                        startActivity(intent5);
                        break;
                    case "评估及康复情况":
                        Intent intent6 = new Intent(getActivity(), AssessAndRecover.class);
                        startActivity(intent6);
                        break;
                    case "患者位置信息":
//                        Intent intent6 = new Intent(getActivity(), Evaluate_form4.class);
//                        startActivity(intent6);
                        break;
                    default:
                }
            }
        });
    }

    private void initItem() {
        MyListItem item1 = new MyListItem("消息提醒", R.drawable.my_remind);
        myListItems.add(item1);
//        MyListItem item2 = new MyListItem("个人基本信息", R.drawable.my_info);
//        myListItems.add(item2);
        MyListItem item3 = new MyListItem("健康档案", R.drawable.my_heal);
        myListItems.add(item3);
        MyListItem item4 = new MyListItem("治疗记录", R.drawable.my_record);
        myListItems.add(item4);
        MyListItem item5 = new MyListItem("训练数据采集", R.drawable.my_data);
        myListItems.add(item5);
        MyListItem item6 = new MyListItem("评估及康复情况", R.drawable.my_anlysis);
        myListItems.add(item6);
        MyListItem item7 = new MyListItem("患者位置信息", R.drawable.my_loc);
        myListItems.add(item7);
    }

    private void choseimg(boolean haspermission){
        //选择图片
        if(haspermission)
            showChoseImg();
        else{
            showMes("请允许App所需要的权限！");
        }
    }

    private void showChoseImg(){
        ArrayList<DialogItem> items = new ArrayList<>();
        items.add(new DialogItem("拍照", R.layout.custom_dialog_normal2){
            @Override
            public void onClick() {
                Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
                FileCache fc=new FileCache(mContext);

                String filepath =fc.getFileName("imagTemp");

                String date = (new SimpleDateFormat("MMddhhmmss")).format(new java.util.Date());
                picpath = new File(filepath+date+"_img.jpg");
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picpath));
                startActivityForResult(it, ACTIVITYRESULT_CHOSE_TAKEIMG);
            }
        });
        items.add(new DialogItem("相册选取", R.layout.custom_dialog_normal2){

            @Override
            public void onClick() {
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, ACTIVITYRESULT_CHOSE_SYSIMG);
            }

        });

        items.add(new DialogItem("取消", R.layout.custom_dialog_normal2));

        LmqTool.createCustomDialog(mContext, items, R.style.CustomDialogNew);
    }

    public void showMes( String msg) {
        Toast toast = new Toast(getActivity());
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_view, null);
        TextView tvMessage = view.findViewById(R.id.tv_message_toast);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode==ACTIVITYRESULT_CHOSE_SYSIMG){
                muri=null;
                if(data==null)
                    return;
                muri= data.getData();

                startPicCut(muri);

            }

            else if(requestCode==ACTIVITYRESULT_CHOSE_TAKEIMG){
                muri=null;
                if(picpath!=null&&picpath.length()>0){
                    muri=Uri.fromFile(picpath);
                    startPicCut(muri);
                    //startPicCut(muri);
                    //  asyncUpload(picpath);
                }
            }
            else if(requestCode==ACTIVITYRESULT_CUTIMG){
                SaveBitMap(data);
            }

        }
    }

    /**
     * 裁剪图片的方法
     * @param uri
     */
    public void  startPicCut(Uri uri){
        Intent intentCarema = new Intent("com.android.camera.action.CROP");
        intentCarema.setDataAndType(uri, "image/*");
        intentCarema.putExtra("crop", true);
        //intentCarema.putExtra("scale", false);
        //intentCarema.putExtra("noFaceDetection", true);//不需要人脸识别功能
        //intentCarema.putExtra("circleCrop", "");//设定此方法选定区域会是圆形区域
        //aspectX aspectY是宽高比例
        if(android.os.Build.MODEL.contains("HUAWEI"))
        {//华为特殊处理 不然会显示圆
            intentCarema.putExtra("aspectX", 9998);
            intentCarema.putExtra("aspectY", 9999);
        }
        else
        {
            intentCarema.putExtra("aspectX", 1);
            intentCarema.putExtra("aspectY", 1);
        }

        //outputX outputY	是裁剪图片的宽高
        intentCarema.putExtra("outputX", 300);
        intentCarema.putExtra("outputY", 300);
        intentCarema.putExtra("return-data", true);
        startActivityForResult(intentCarema, ACTIVITYRESULT_CUTIMG);
    }

    /**
     * 保存裁剪后的图片
     * @param picData
     */
    private void SaveBitMap(Intent picData){
        Bundle bundle = picData.getExtras();
        if(bundle != null){
            Bitmap photo = bundle.getParcelable("data");
            FileCache fc=new FileCache(mContext);

            String filepath =fc.getFileName("imagTemp");

            String date = (new SimpleDateFormat("MMddhhmmss")).format(new java.util.Date());
            String	mfilepath = filepath+date+"_img.jpg";
            File picpath = new File(mfilepath);
            try{
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(picpath));
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }catch(IOException e){
                e.printStackTrace();
            }
            uploadFile =picpath;
            Glide.with(this).load(uploadFile).into(iconChange);
//             上传文件

            Map<String, File> fileMap = new TreeMap<>();
            fileMap.put("icon", uploadFile);

            CommonUploadCallback uploadCallback = new CommonUploadCallback() {
                @Override
                public Object convert(String data) {
                    return null;
                }

                @Override
                public void onProgress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile) {

                }

                @Override
                public void onSuccess(Object value) {

                }

                @Override
                public void onError(int code, String desc) {

                }

                @Override
                public void onCancel() {

                }
            };
            //             上传文件
             upload(UPLOAD_PHOTO_ICON, fileMap, uploadCallback);
        }
    }

    private void upload(String api, Map<String, File> fileMap, UploadCallback callback){
        try {

            /**
             * 发送请求
             */
            new RHttp.Builder()
                    .post()
                    .baseUrl(AppContact.getBaseUrl)
                    .apiUrl(api)

                    .file(fileMap)
                    .build()
                    .upload(callback);
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(1,e.getMessage());
        }
    }
}
