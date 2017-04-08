package com.darongshu.login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2016/3/15.
 */
public class HttpUtil {

    public static Object doGet(String httpUrl){
        InputStream inputStream=null;
        BufferedReader reader=null;
        HttpURLConnection cooon=null;

        try {
            //创建url
            URL url=new URL(httpUrl);
            //打开连接
            cooon= (HttpURLConnection) url.openConnection();
            cooon.setRequestMethod("GET");
            cooon.setConnectTimeout(5000);
            cooon.setReadTimeout(5000);
            cooon.setUseCaches(true);
            cooon.setDoInput(true);
            cooon.setDoOutput(true);
            //开始连接
            cooon.connect();
            if (cooon.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=cooon.getInputStream();
                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuffer sb=new StringBuffer();
                while ((line=reader.readLine())!=null){
                        sb.append(line);
                }
                String result=sb.toString();
                return result;

            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cooon.disconnect();

        }
        return null;

    }


    public static Object doPost(String Httpurl,Map<String,String> params){
        if (Httpurl==null||params==null){
            try {
                throw new Exception("失败了");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       Set<Map.Entry<String,String>> set= params.entrySet();
         Iterator<Map.Entry<String,String>> iter=set.iterator();
         StringBuffer paramssb=new StringBuffer();
         while (iter.hasNext()){
             Map.Entry<String,String> entry=iter.next();
             String key=entry.getKey();
             paramssb.append(key);
             paramssb.append("=");
             String values=entry.getValue();
             paramssb.append(values);
             paramssb.append("&");
         }

        String paramsString=paramssb.toString();
        paramsString.substring(0,paramsString.length()-1);
        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedReader reader=null;
        HttpURLConnection coon=null;
        try {
            URL url=new URL(Httpurl);
            coon= (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("POST");
            coon.setConnectTimeout(5000);
            coon.setReadTimeout(5000);
            coon.setUseCaches(true);
            coon.setDoInput(true);
            coon.setDoOutput(true);
            //建立连接
            coon.connect();
            outputStream=coon.getOutputStream();
            outputStream.write(paramsString.getBytes());
            outputStream.flush();

            if (coon.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=coon.getInputStream();
                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuffer sb=new StringBuffer();
                while ((line=reader.readLine())!=null){
                    sb.append(line);
                }
                String result=sb.toString();
                return result;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            coon.disconnect();
        }


        return null;
    }

    /**
     * 下载apk
     * @param HttpUrl
     * @param dir
     * @return
     */
    public static File downloadFile(String HttpUrl,File dir,String fileName, AssistantTask.downloandProgress lister){

        InputStream inputStream=null;
        FileOutputStream fileOutputStream=null;
        //TODO 判断目录是否存在，不存在创建，连上级一起创建
        if (!dir.exists()){
            dir.mkdirs();
        }
        File renameFile=new File(dir,fileName);
        try {
            URL url=new URL(HttpUrl);
            HttpURLConnection coon= (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("GET");
            coon.setConnectTimeout(5000);
            coon.setReadTimeout(5000);
            coon.connect();
            if (coon.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=coon.getInputStream();
                fileOutputStream=new FileOutputStream(renameFile);
                byte[] buff=new byte[1024];
                long length=coon.getContentLength();
                int read=-1;
                LogUtil.d("开始下载length="+length);
                long down=0;
                while (true){
                    int real=inputStream.read(buff);

                    if (real==-1){
                        break;
                    }
                    down+=real;
                    LogUtil.d("down="+down);
                    fileOutputStream.write(buff,0,real);
                    fileOutputStream.flush();
                    int per=(int)(down*100/length);
                    if (lister!=null){
                        lister.updateProgress(per);
                    }
                }
                LogUtil.d("下载成功");
                return renameFile;

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }


    /**
     * 下载图片
     * @param httpUrl
     * @return
     */
    public static Bitmap requestBitmap(String httpUrl)
    {
        InputStream inputStream = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                LogUtil.d("图片下载成功");
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        LogUtil.e("请求失败");
        return  null;
    }



}
