package com.gokuai.base;

import java.io.IOException;

public class DefaultAsyncTarget implements IAsyncTarget {

    private Thread mThread;

    @Override
    public void cancel() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

    }

    @Override
    public IAsyncTarget execute(final HttpEngine.DataListener listener, final HttpEngine.RequestHelper helper,
                                final HttpEngine.RequestHelperCallBack callBack, final int apiId) {
        mThread = new Thread() {
            @Override
            public void run() {
                ReturnResult result = helper.executeSync();
                if (listener != null) {
                    if (callBack == null) {
                        listener.onReceivedData(apiId, result, -1);
                    } else {
                        Object object = callBack.getReturnData(result);
                        listener.onReceivedData(apiId, object, -1);
                    }
                }
            }
        };
        mThread.start();
        return this;
    }


}
