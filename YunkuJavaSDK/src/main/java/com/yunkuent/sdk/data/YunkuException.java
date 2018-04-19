package com.yunkuent.sdk.data;

import com.gokuai.base.ReturnResult;

public class YunkuException extends Exception {

    private ReturnResult result;

    public YunkuException(String message) {
        super(message);
    }

    public YunkuException(Throwable cause) {
        super(cause);
    }

    public YunkuException(String message, ReturnResult result) {
        super(message);
        this.result = result;
    }

    public ReturnResult getReturnResult() {
        return this.result;
    }
}
