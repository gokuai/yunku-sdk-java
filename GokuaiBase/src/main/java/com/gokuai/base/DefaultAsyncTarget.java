package com.gokuai.base;

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
                String returnString = helper.executeSync();
                if (listener != null) {
                    if (callBack == null) {
                        listener.onReceivedData(apiId, ReturnResult.create(returnString), -1);
                    } else {
                        Object object = callBack.getReturnData(returnString);
                        listener.onReceivedData(apiId, object, -1);
                    }
                }
            }
        };
        mThread.start();
        return this;
    }


}
