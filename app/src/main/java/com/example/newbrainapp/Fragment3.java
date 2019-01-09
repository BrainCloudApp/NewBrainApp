package com.example.newbrainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lmq.ui.HealthInfo_Activity;
import com.lmq.ui.Settings_Activity;
import com.lmq.ui.Settings_Message_Activity;
import com.lmq.ui.buletooth.BluetoothActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {

    private List<MyListItem> myListItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        Intent intent4 = new Intent(getActivity(), WebView1.class);
                        startActivity(intent4);
                        break;
                    case "训练数据采集":
                       /* Intent intent5 = new Intent(getActivity(), WebView2.class);
                        startActivity(intent5);*/
                        Intent intent5 = new Intent(getActivity(), BluetoothActivity.class);
                        startActivity(intent5);
                        break;
                    case "评估及康复情况":
                        Intent intent6 = new Intent(getActivity(), EvaluateFormList.class);
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



}
