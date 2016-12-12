package com.example.administrator.refresh;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class MyCallBack extends Callback<Object> {

    Object object;
    public MyCallBack(Object object) {
        super();
        this.object = object;
    }

    @Override
    public Object parseNetworkResponse(Response response) throws IOException
    {
        String string = response.body().string();

        object = new Gson().fromJson(string, object.getClass());
        return object;
    }
}
