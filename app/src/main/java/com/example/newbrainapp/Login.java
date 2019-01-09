package com.example.newbrainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lmq.common.Appstorage;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private Button loginButton;
    private TextView signup;
    private EditText input_username;
    private EditText input_userpsd;
    private String name1;
    private String psd1;
    private CheckBox remember_psd;

    private LoginBean loginBean = new LoginBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);
        input_username = findViewById(R.id.input_username);
        input_userpsd = findViewById(R.id.input_password);
        remember_psd = findViewById(R.id.remember_psd);
        boolean isRemember = Appstorage.getRememberPsdState(this);
        if (isRemember){
            input_username.setText(Appstorage.getLoginUserName(this));
            input_userpsd.setText(Appstorage.getLoginUserPwd(this, Appstorage.getLoginUserName(this)));
            remember_psd.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               name1 = input_username.getText().toString().trim();
                psd1 = input_userpsd.getText().toString().trim();
                postRequest(name1, psd1);
            /*    goLogin();*/
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this, Register.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    public void goLogin(){
        final LoginInfo info = new LoginInfo("acc_01", "111111");

      /*  NIMClient.getService(AuthService.class).login(info)
                .setCallback(new RequestCallback<LoginInfo>() {//sdk提供的手动登录方法
                    @Override
                    public void onSuccess(LoginInfo param) {

                        showMes("登录成功！");
                        NimUIKitImpl.setAccount(param.getAccount());
                        NimUIKit.startP2PSession(mContext,"acc_02");

                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });*/

        NimUIKit.login(info, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {

                //启动单聊界面
              //  showMes("登录成功！");
                Log.d("Login","登录成功！");
                NimUIKitImpl.setAccount(loginInfo.getAccount());
                NimUIKit.startP2PSession(Login.this,"acc_02");

               // NimUIKit.startChatting(Login.this, "acc_02",SessionTypeEnum.P2P, null,null);
                // 启动群聊界面
                // NimUIKit.startTeamSession(MainActivity.this, "群ID");
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });


    }
    private void postRequest(final String username, final String password) {
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        String loginJson = new Gson().toJson(loginBean);

        HttpUtil.postHttpRequest(HttpUtil.IP + "/app/login", loginJson, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String state = response.body().string();
                    try {
                        final JSONObject stateJson = new JSONObject(state);
                        if (stateJson.get("flag").equals("success")) {
                            if (remember_psd.isChecked()){
                                Appstorage.setRememberPsdState(Login.this, true);
                                Appstorage.setLoginUsernameAndPwd(Login.this, username, password);
                            }
                            Appstorage.setLoginState(Login.this, true);
                            Login.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("input_username", name1);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            Log.d("Login", stateJson.get("flag").toString());
                            Login.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new IOException("Unexpected code:" + response);
                }
            }
        });
    }
}
