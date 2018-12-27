package com.example.newbrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class StudyMaterialItem extends AppCompatActivity {

    private TextView materialName;
    private ImageView materialPic;
    private TextView materialCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_item);

        materialName = findViewById(R.id.material_name);
        materialPic = findViewById(R.id.material_pic);
        materialCon =findViewById(R.id.material_content);

        materialName.setText(getIntent().getStringExtra("material_name"));
//        Log.d("MedicalEquipment",HttpUtil.IP + getIntent().getStringExtra("equip_pic"));
        Glide.with(this).load(HttpUtil.IP + getIntent().getStringExtra("material_pic")).into(materialPic);
        materialCon.setText(getIntent().getStringExtra("material_con"));
    }
}
