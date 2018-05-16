package com.gokuai.yunku.demo.helper;

import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.model.BaseData;

import java.net.HttpURLConnection;

/**
 * Created by qp on 2017/3/2.
 */
public class DeserializeHelper {

    private DeserializeHelper() {

    }

    private static class SingletonHolder {
        private static final DeserializeHelper INSTANCE = new DeserializeHelper();
    }

    public static DeserializeHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 解析返回内容
     *
     * @param result
     */
    public void deserializeReturn(ReturnResult result) {

        if (result.isOK()) {
            //成功的结果
            System.out.println("success");
            System.out.println(result.getBody());

        } else {
            if (result.getException() != null) {
                //出现网络或IO错误
                result.getException().printStackTrace();
            } else {
                System.out.println("http response code: " + result.getCode() + ", body: " + result.getBody());

                //解析result中的内容
                BaseData data = BaseData.create(result.getBody());
                if (data != null) {
                    //如果可解析，则返回错误信息和错误号
                    System.out.println(data.getErrorCode() + ":" + data.getErrorMsg());
                }
            }
        }

    }
}
