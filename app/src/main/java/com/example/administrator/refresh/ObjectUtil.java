package com.example.administrator.refresh;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ObjectUtil {

    public static boolean isNull(List object){
        if (object != null && object.size() > 0){
            return false;
        }else {
            return true;
        }
    }
}
