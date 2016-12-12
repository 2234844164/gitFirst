package com.example.administrator.refresh;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class Info {
    private String msg;
    private String code;
    List<ImageInfo> masters;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ImageInfo> getMasters() {
        return masters;
    }

    public void setMasters(List<ImageInfo> masters) {
        this.masters = masters;
    }
}
