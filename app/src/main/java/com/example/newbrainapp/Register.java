package com.example.newbrainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    private Button signup;
    private EditText regUsername;
    private EditText regUserpsd;
    private EditText regUserVerifypsd;
    private String name, psd1, psd2,reg_class;
    RegisterBean registerBean = new RegisterBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = findViewById(R.id.reg_name);
        regUserpsd = findViewById(R.id.reg_password);
        regUserVerifypsd = findViewById(R.id.reg_verifypsd);

        signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = regUsername.getText().toString().trim();
                psd1 = regUserpsd.getText().toString().trim();
                psd2 = regUserVerifypsd.getText().toString().trim();

                if(psd1.equals(psd2)){
                    registerBean.setReg_username(name);
                    registerBean.setReg_psd(psd1);
                    String registerJson = new Gson().toJson(registerBean);
                    Log.d("Callback", registerJson.toString());
                    postRequest(registerJson);

                }else{
                    Toast.makeText(Register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void postRequest(String jsonData){
        HttpUtil.postHttpRequest(HttpUtil.IP + "/app/register", jsonData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Callback","服务器访问失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String state = response.body().string();
                Log.d("State",state);
//                Log.d("State","" + state.charAt(state.length()-3));
//                Log.d("Callback",state.getClass().toString());
//
                try {
                    JSONObject stateJson = new JSONObject(state);
                    Log.d("State","" + stateJson.get("flag"));
                    if(stateJson.get("flag").equals("success")){
                        Register.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else{
                        Register.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, "用户名已存在", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
