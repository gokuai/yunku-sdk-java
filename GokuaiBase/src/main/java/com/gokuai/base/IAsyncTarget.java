package com.gokuai.base;

/**
 * Created by Brandon on 2017/5/25.
 */
public interface IAsyncTarget {

    void cancel();

    IAsyncTarget execute(HttpEngine.DataListener listener, HttpEngine.RequestHelper helper, HttpEngine.RequestHelperCallback callback, int apiId);
}
