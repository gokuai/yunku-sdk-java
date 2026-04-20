package com.gokuai.base;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class SocketIOConnection {

    private static final String LOG_TAG = "SocketIOConnection";

    private static long mTimeout = 1800;
    private static long mConnectTimeout = 10;
    private static SocketIOConnection instance = null;

    private SocketIOConnection() {
    }

    public static synchronized SocketIOConnection getInstance() {
        if (instance == null) {
            instance = new SocketIOConnection();
        }
        return instance;
    }

    public static void setTimeout(long timeout) {
        mTimeout = timeout;
    }

    public static void setConnectTimeout(long timeout) {
        mConnectTimeout = timeout;
    }

    public ReturnResult sendExportRequest(SocketExportConfig config) {
//        LogPrint.debug(LOG_TAG, "sendExportRequest(): url=" + config.url);
//        LogPrint.debug(LOG_TAG, "Socket path: " + config.path + "/socket.io");

        try {
            IO.Options options = new IO.Options();
            options.timeout = (int) (mTimeout * 1000);
            options.reconnection = false;
            options.forceNew = true;
            options.transports = new String[]{"websocket", "polling"};
            options.path = config.path + "/socket.io";

            String fullUrl = config.url;
            if (!fullUrl.endsWith("/")) {
                fullUrl += "/";
            }
            String queryString = buildQueryString(config.query);
            fullUrl += "?" + queryString;
//            LogPrint.debug(LOG_TAG, "Full URL: " + fullUrl);

//            LogPrint.debug(LOG_TAG, "Connecting...");

            Socket socket = IO.socket(fullUrl, options);

            SocketExportResponseHandler responseHandler = new SocketExportResponseHandler(mTimeout);

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                public void call(Object... args) {
//                    LogPrint.debug(LOG_TAG, "Socket connected, emitting export event");
                    socket.emit("export", config.exportMsg);
                }
            });

            socket.on("progress", responseHandler.getSuccessListener());

            socket.on("err", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String errorMsg = args.length > 0 ? args[0].toString() : "Unknown server error";
//                    LogPrint.error(LOG_TAG, "Server Error: " + errorMsg);
//                    System.out.println("Server Error" + errorMsg);
                    responseHandler.setErrorMessage(errorMsg);
                    socket.disconnect();
                }
            });

            socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                public void call(Object... args) {
//                    LogPrint.error(LOG_TAG, "Connect error: " + (args.length > 0 ? args[0].toString() : "unknown"));
//                    System.out.println("Connect Error" + (args.length > 0 ? args[0].toString() : "unknown"));
//                    if (args.length > 0 && args[0] instanceof Exception) {
//                        Exception e = (Exception) args[0];
//                        LogPrint.error(LOG_TAG, "Stack: " + e.getMessage());
//                        System.out.println("Stack: " + e.getMessage());
//                        if (e.getCause() != null) {
//                            LogPrint.error(LOG_TAG, "Cause: " + e.getCause().getMessage());
//                            System.out.println("Cause: " + e.getCause().getMessage());
//                        }
//                    }
                    if (args.length > 0) {
                        responseHandler.handleError("Connection error: " + args[0].toString());
                    } else {
                        responseHandler.handleError("Connection error");
                    }
                    socket.disconnect();
                }
            });

            socket.connect();
            ReturnResult result = responseHandler.waitForResponse();
            socket.disconnect();

            return result;

        } catch (URISyntaxException e) {
//            LogPrint.error(LOG_TAG, "Invalid socket URL: " + e.getMessage());
//            System.out.println("Invalid socket URL: " + e.getMessage());
            return new ReturnResult(new Exception("Invalid socket URL: " + e.getMessage()));
        } catch (Exception e) {
//            LogPrint.error(LOG_TAG, "Socket request failed: " + e.getMessage());
//            System.out.println("Socket request failed: " + e.getMessage());
            return new ReturnResult(e);
        }
    }

    private String buildQueryString(Map<String, String> queryParams) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (Exception e) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    private static class SocketExportResponseHandler {
        private final CountDownLatch latch;
        private final long timeoutSeconds;
        private ReturnResult result;
        private boolean hasError = false;

        public SocketExportResponseHandler(long timeoutSeconds) {
            this.latch = new CountDownLatch(1);
            this.timeoutSeconds = timeoutSeconds;
            this.result = null;
        }

        public Emitter.Listener getSuccessListener() {
            return new Emitter.Listener() {
                public void call(Object... args) {
                    try {
                        if (args.length > 0) {
                            String responseData = args[0].toString();
                            LogPrint.debug(LOG_TAG, "Received response: " + responseData);
                            
                            try {
                                JSONObject json = new JSONObject(responseData);
                                if (json.has("url")) {
                                    String url = json.getString("url");
                                    LogPrint.debug(LOG_TAG, "Got download URL: " + url);
                                    result = new ReturnResult(200, url);
                                    latch.countDown();
                                } else if (json.has("progress")) {
                                    LogPrint.debug(LOG_TAG, "Progress update: " + json.optInt("progress"));
                                } else {
                                    LogPrint.debug(LOG_TAG, "Unknown response format");
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                        LogPrint.error(LOG_TAG, "Listener error: " + e.getMessage());
                    }
                }
            };
        }

        public void handleError(String errorMessage) {
            if (!hasError) {
                hasError = true;
                LogPrint.error(LOG_TAG, errorMessage);
                result = new ReturnResult(new Exception(errorMessage));
                latch.countDown();
            }
        }

        public void setErrorMessage(String errorMsg) {
            if (!hasError) {
                hasError = true;
                int errorCode = 500; // 默认为 500
                try {
                    JSONObject json = new JSONObject(errorMsg);
                    if (json.has("error_code")) {
                        errorCode = json.getInt("error_code");
                    }
                    if (json.has("error_msg")) {
                        errorMsg = json.getString("error_msg");
                    }
                } catch (Exception e) {
                }
                result = new ReturnResult(errorCode, "Error: " + errorMsg);
                latch.countDown();
            }
        }

        public ReturnResult waitForResponse() throws TimeoutException {
            try {
                boolean completed = latch.await(timeoutSeconds, TimeUnit.SECONDS);
                if (!completed) {
                    throw new TimeoutException("Socket request timeout after " + timeoutSeconds + " seconds");
                }
                return result != null ? result : new ReturnResult(new Exception("No response received"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ReturnResult(e);
            }
        }
    }

    public static class SocketExportConfig {
        public String url;
        public String path;
        public Map<String, String> query;
        public String exportMsg;

        public SocketExportConfig() {
            this.query = new HashMap<>();
        }
    }
}