package com.gokuai.base;

import com.gokuai.base.utils.Util;

import java.util.HashMap;

/**
 * Created by qp on 2017/4/21.
 */
public abstract class HttpEngine extends SignAbility {

    public static final int ERRORID_NETDISCONNECT = 1;
    private final static String LOG_TAG = "HttpEngine";

    public HttpEngine(String clientId, String secret) {
        super(clientId, secret);
    }

    /**
     * 从服务器获得数据后的回调
     */
    public interface DataListener {
        void onReceivedData(int apiId, ReturnResult result, int errorId);
    }

    public interface RequestHelperCallback {
        ReturnResult hook(ReturnResult result);
    }

    /**
     * 请求协助类
     */
    public class RequestHelper {
        String url;
        RequestMethod method = RequestMethod.POST;
        protected HashMap<String, String> params;
        HashMap<String, String> headParams;
        boolean checkAuth;
        String postType = NetConfig.POST_DEFAULT_FORM_TYPE;
        IAsyncTarget target;

        public RequestHelper setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        public RequestHelper setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        public RequestHelper setHeadParams(HashMap<String, String> headParams) {
            this.headParams = headParams;
            return this;
        }

        public RequestHelper setCheckAuth(boolean checkAuth) {
            this.checkAuth = checkAuth;
            return this;
        }

        public RequestHelper setPostType(String postType) {
            this.postType = postType;
            return this;
        }

        public RequestHelper setUrl(String url) {
            this.url = url;
            return this;
        }

        public RequestHelper setTarget(IAsyncTarget target) {
            this.target = target;
            return this;
        }

        /**
         * 设置公共参数, 如计算签名
         */
        protected void setCommonParams() {
            if (this.params == null) {
                this.params = new HashMap<String, String>();
            }
            this.params.put("sign", HttpEngine.this.generateSign(this.params));
        }

        private void checkNecessaryParams() {
            if (Util.isEmpty(url)) {
                throw new IllegalArgumentException("url must not be null");
            }

            this.setCommonParams();
        }

        /**
         * 同步执行
         *
         * @return
         */
        public ReturnResult executeSync() {
            this.checkNecessaryParams();

            if (checkAuth) {
                if (HttpEngine.this instanceof IAuthRequest) {
                    return ((IAuthRequest) HttpEngine.this).sendRequestWithAuth(url, method, params, headParams, postType);
                } else {
                    LogPrint.error(LOG_TAG, "You need implement IAuthRequest before set checkAuth=true");
                }
            }
            return NetConnection.sendRequest(url, method, params, headParams, postType);
        }

        /**
         * 异步执行
         *
         * @return
         */
        public IAsyncTarget executeAsync(DataListener listener, int apiId, RequestHelperCallback callback) {
            this.checkNecessaryParams();

            if (target != null) {
                return target.execute(listener, this, callback, apiId);
            }
            return new DefaultAsyncTarget().execute(listener, this, callback, apiId);
        }

        /**
         * 异步执行
         *
         * @return
         */
        public IAsyncTarget executeAsync(DataListener listener, int apiId) {
            return executeAsync(listener, apiId, null);
        }

    }
}
