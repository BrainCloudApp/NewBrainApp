package com.example.newbrainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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

    private ArrayList<News> newsList = new ArrayList<>();
    private StringBuffer result;
    private LoginBean loginBean = new LoginBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);
        input_username = findViewById(R.id.input_username);
        input_userpsd = findViewById(R.id.input_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = input_username.getText().toString().trim();
                psd1 = input_userpsd.getText().toString().trim();
                getInitContent();
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

    private void getInitContent() {
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment1", "服务器访问失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    result = new StringBuffer();
                    result.append(response.body().string());
//                    Log.d("Fragment1", "result; " + result.toString());
                    parseJsonObject(result.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postRequest(name1, psd1);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postRequest(String username, String password) {
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
                    Log.d("response", state);
                    try {
                        final JSONObject stateJson = new JSONObject(state);
//                            Log.d("Login", stateJson.get("flag").toString());
                        if (stateJson.get("flag").equals("success")) {
                            Login.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("input_username", name1);
                                    Log.d("News", newsList.toString());
                                    intent.putExtra("news", newsList);
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


    public void parseJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("Fragment1", jsonObject.toString());
                String title = jsonObject.getString("title");
                String picture = jsonObject.getString("img");
//                String con = jsonObject.getString("con");
//                Content content = new Content(title, picture, con);
                News news = new News(title, picture);
                newsList.add(news);
//                Log.d("Fragment1","title: " + title);
//                Log.d("Fragment1","picture: " + picture);
//                Log.d("Fragment1","content: " + con);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
