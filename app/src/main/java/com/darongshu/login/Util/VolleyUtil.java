package com.darongshu.login;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Administrator on 2016/3/22.
 */
public class VolleyUtil {

    private static RequestQueue queue;


    /**
     * 这是volley的get请求网络数据的方法
     * @param context
     * @param Url
     * @param Method
     * @param iRequestCallback
     */
    public static void doGet(Context context,String Url,int Method, com.darongshu.login.AssistantTask.IRequestCallback iRequestCallback){
        doRequest(context,Url, Request.Method.GET,null,iRequestCallback);
    }

    /**
     * 这是volley的post请求网络数据的方法
     * @param context
     * @param Url
     * @param Method
     * @param params
     * @param iRequestCallback
     */
    public static void doPost(Context context,String Url,Map<String,String> params,
                              com.darongshu.login.AssistantTask.IRequestCallback iRequestCallback){
        doRequest(context,Url,Request.Method.POST,params,iRequestCallback);
    }



   private static void doRequest(Context context,String Url,int Method, final Map<String,String> params,
       final com.darongshu.login.AssistantTask.IRequestCallback iRequestCallback){
       if (queue==null){
           synchronized (VolleyUtil.class){
               if (queue==null){
                   queue = Volley.newRequestQueue(context);
               }
           }

       }
       StringRequest request=new StringRequest(Method, Url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
                iRequestCallback.onSucces(response);
                com.darongshu.login.LogUtil.d("请求成功" + response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
                iRequestCallback.onError(error.getMessage());
               com.darongshu.login.LogUtil.d("请求失败" + error.getMessage());
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               return params;
           }
       };

       queue.add(request);


   }

    /**
     * 这是volley的网络请求网络图片的方法
     * @param context
     * @param url
     * @param width
     * @param height
     * @param iRequestCallback
     */
    public static void requestBitmap(Context context,String url,
                 int width,int height
                 , final com.darongshu.login.AssistantTask.IRequestCallback iRequestCallback ){
        if (queue==null){
            synchronized (VolleyUtil.class){
                if (queue==null){
                    queue = Volley.newRequestQueue(context);
                }
            }
        }
        ImageRequest request=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iRequestCallback.onSucces(response);
                com.darongshu.login.LogUtil.d("请求成功" + response);
            }
        }, width, height, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iRequestCallback.onError(error);
                com.darongshu.login.LogUtil.d("请求失败" + error.getMessage());
            }
        });
       queue.add(request);

    }


}
