
package com.darongshu.login;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.darongshu.login.Util.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    public static final String Path ="http://192.168.1.205:8080/login/a";
    String urltoken="http://192.168.1.205:8080/login/token ";
    String url = "http://zhushou.72g.com/app/gift/gift_list/";
    private EditText username, userpassword;
    private String name, password;
    private Map<String, String> map;
    private TextView showmessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.login_username);
        userpassword = (EditText) findViewById(R.id.login_password);
        showmessage= (TextView) findViewById(R.id.login_message);

    }

    public void login(View view) {
        name = username.getText().toString();
        password = userpassword.getText().toString();
        Toast.makeText(this, "username=" + name + ":" + "userpassword=" + password, Toast.LENGTH_SHORT).show();
        map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "密码为空" + password, Toast.LENGTH_SHORT).show();
            //TODO 不带用户名和密码的请求，却带有token
             String token =gettoken();
            Log.d("token",token);
            Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
             Requsettoken(token);

        }else {
            Toast.makeText(this, "密码不为空" + password, Toast.LENGTH_SHORT).show();
            //TODO 带用户名跟密码的请求
            beltRequst();
        }

    }



    private void Requsettoken(String token) {
        Map<String ,String> map=new HashMap<>();
        map.put("token",token);
        com.darongshu.login.VolleyUtil.doPost(getApplicationContext(), urltoken, map, new com.darongshu.login.AssistantTask.IRequestCallback() {
            @Override
            public void onSucces(Object obj) {
                try {
                    //{"type":"true","data":null,"token":null}
                    JSONObject object = new JSONObject(obj.toString());
                    String type = object.getString("type");
                    Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();
                    //showmessage.setText(type + name + password + token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Object obj) {

            }
        });


    }


    //TODO 保存token 下次再登陆时不用
    private void Storagetoken(String token) {
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCES_FIRST_USED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Constants.PREFERENCES_TOKEN, token);
        editor.commit();
    }

    //TODO 带有密码的请求
    private void beltRequst() {
        com.darongshu.login.VolleyUtil.doPost(getApplicationContext(), Path, map, new com.darongshu.login.AssistantTask.IRequestCallback() {
            @Override
            public void onSucces(Object obj) {
                Log.d("onSucces", obj.toString());
                //Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_SHORT).show();
                try {
                    //{"type":"true","data":{"name":"a","password":null},"token":"cb6e2d4f-cfc5-4043-ada5-63ff497f9b3f"}
                    JSONObject object = new JSONObject(obj.toString());
                    String type = object.getString("type");
                    JSONObject data = object.getJSONObject("data");
                    String name = data.getString("name");
                    String password = data.getString("password");
                    String token = object.getString("token");
                    Storagetoken(token);

                    showmessage.setText(type + name + password + token);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Object obj) {
                Log.d("onError", obj.toString());
            }
        });
    }
    //TODO 获取token
    private String gettoken() {
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCES_FIRST_USED, Context.MODE_PRIVATE);
        String string = preferences.getString(Constants.PREFERENCES_TOKEN, "0");
        return string;

    }


}
