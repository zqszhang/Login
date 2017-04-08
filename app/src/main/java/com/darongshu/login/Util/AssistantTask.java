package com.darongshu.login;

import android.os.AsyncTask;

/**用异步任务封装网络请求工具
 * Created by Administrator on 2016/3/15.
 */
public class AssistantTask extends AsyncTask<Void,Void,Object>{
    private IRequest iRequest;
    private IRequestCallback iRequestCallback;
    public AssistantTask(IRequest iRequest, IRequestCallback iRequestCallback)  {
        if (iRequest==null||iRequestCallback==null){
            throw  new NullPointerException("iRequest=null or IRequestCallback=null");
        }
        this.iRequest = iRequest;
        this.iRequestCallback = iRequestCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... params) {


        return iRequest.doRequest();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o==null){
            iRequestCallback.onError(o);
        }else {
            iRequestCallback.onSucces(o);
        }
    }



    public interface  IRequest{
        Object doRequest();
    }



    public interface  IRequestCallback{
    void onSucces(Object obj);
    void onError(Object obj);

     }

    public interface downloandProgress{
        void updateProgress(int Progress);
    }


}

