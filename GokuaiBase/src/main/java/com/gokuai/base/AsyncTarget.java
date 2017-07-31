package com.gokuai.base;

/**
 * Created by Brandon on 2017/5/25.
 */
public class AsyncTarget implements IAsyncTarget {

    private Thread mThread;


    @Override
    public void cancel() {

    }

    @Override
    public IAsyncTarget execute() {
        mThread =new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };

        mThread.start();
        return null;
    }
}
