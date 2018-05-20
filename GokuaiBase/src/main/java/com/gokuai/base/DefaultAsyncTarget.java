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
                                final HttpEngine.RequestHelperCallback callback, final int apiId) {
        mThread = new Thread() {
            @Override
            public void run() {
                ReturnResult result = helper.executeSync();
                if (listener != null) {
                    listener.onReceivedData(apiId, (callback == null ? result : callback.hook(result)), -1);
                }
            }
        };
        mThread.start();
        return this;
    }


}
