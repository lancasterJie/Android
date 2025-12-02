package com.example.dialectgame;

import android.util.Base64;
import androidx.annotation.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SparkApiHelper {
    private static final String API_WSS_URL = "wss://spark-api.xf-yun.com/v1/x1";
    // 敏感信息已做星号处理，实际使用时需替换为真实值
    private static final String API_KEY = "9d6d8c4340a8ca1bb905eddd03c10eaf"; // 例如：94fb02c1**********fa38657e
    private static final String API_SECRET = "Y2NjYzcxYmNmY2UyNTYzNDM2NmY5ZDdm"; // 例如：NjM2ZTA5Nm**********ZiYzI2MWYw
    private static final String APP_ID = "8af9426b"; // 例如：b578ae16
    private static final String HOST = "spark-api.xf-yun.com";
    private static final String PATH = "/v1/x1";
    private static final String DOMAIN = "x1";
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public interface ApiCallback {
        void onSuccess(String response);
        void onFailure(String error);
        void onPartialResult(String partialText);
    }

    public static void sendRequest(String question, ApiCallback callback) {
        try {
            if (API_KEY.isEmpty() || API_SECRET.isEmpty() || APP_ID.isEmpty()) {
                callback.onFailure("API配置信息不完整");
                return;
            }
            String date = getGMTTime();
            String signature = generateSignature(date);
            String authorization = generateAuthorization(signature);
            String wsUrl = buildWebSocketUrl(authorization, date);
            Request request = new Request.Builder().url(wsUrl).build();
            client.newWebSocket(request, new WsListener(question, callback));
        } catch (Exception e) {
            callback.onFailure("请求初始化失败: " + e.getMessage());
        }
    }

    private static String generateSignature(String date) throws NoSuchAlgorithmException, InvalidKeyException {
        String signatureOrigin = "host: " + HOST + "\n" + "date: " + date + "\n" + "GET " + PATH + " HTTP/1.1";
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(API_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.encodeToString(mac.doFinal(signatureOrigin.getBytes(StandardCharsets.UTF_8)), Base64.NO_WRAP);
    }

    private static String generateAuthorization(String signature) {
        String authorizationOrigin = String.format(
                "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                API_KEY, "hmac-sha256", "host date request-line", signature
        );
        return Base64.encodeToString(authorizationOrigin.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    private static String buildWebSocketUrl(String authorization, String date) throws UnsupportedEncodingException {
        String encodedAuth = URLEncoder.encode(authorization, "UTF-8");
        String encodedDate = URLEncoder.encode(date, "UTF-8");
        String encodedHost = URLEncoder.encode(HOST, "UTF-8");
        return API_WSS_URL + "?authorization=" + encodedAuth + "&date=" + encodedDate + "&host=" + encodedHost;
    }

    private static JSONObject buildRequestData(String question) throws JSONException {
        JSONObject header = new JSONObject().put("app_id", APP_ID).put("uid", "android_client_" + System.currentTimeMillis());
        JSONObject chat = new JSONObject().put("domain", DOMAIN).put("temperature", 0.5).put("max_tokens", 4096);
        JSONObject parameter = new JSONObject().put("chat", chat);
        JSONArray textArray = new JSONArray().put(new JSONObject().put("role", "user").put("content", question));
        JSONObject message = new JSONObject().put("text", textArray);
        JSONObject payload = new JSONObject().put("message", message);
        return new JSONObject().put("header", header).put("parameter", parameter).put("payload", payload);
    }

    private static String getGMTTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(calendar.getTime());
    }

    private static class WsListener extends WebSocketListener {
        private final ApiCallback callback;
        private final String question;
        private int retryCount = 0;
        private static final int MAX_RETRIES = 3;

        WsListener(String question, ApiCallback callback) {
            this.callback = callback;
            this.question = question;
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull okhttp3.Response response) {
            super.onOpen(webSocket, response);
            try {
                webSocket.send(buildRequestData(question).toString());
            } catch (JSONException e) {
                webSocket.close(1000, "数据格式错误");
                callback.onFailure("发送数据格式错误: " + e.getMessage());
            }
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            try {
                JSONObject resp = new JSONObject(text);
                JSONObject header = resp.getJSONObject("header");

                if (header.has("code") && header.getInt("code") != 0) {
                    callback.onFailure("API错误: " + header.optString("message", "未知错误"));
                    webSocket.close(1000, "API返回错误");
                    return;
                }

                JSONObject payload = resp.getJSONObject("payload");
                JSONObject choices = payload.getJSONObject("choices");
                JSONArray textArray = choices.getJSONArray("text");

                if (textArray.length() > 0) {
                    JSONObject textObj = textArray.getJSONObject(0);
                    String content = textObj.optString("content", "");
                    String reasoning = textObj.optString("reasoning_content", "");

                    // 优先使用reasoning_content字段
                    String deltaText = !reasoning.isEmpty() ? reasoning : content;

                    if (!deltaText.isEmpty()) {
                        callback.onPartialResult(deltaText);
                    }

                    // 检查是否结束
                    if (choices.has("status") && choices.getInt("status") == 2) {
                        callback.onSuccess("");
                        webSocket.close(1000, "生成完成");
                    }
                }
            } catch (JSONException e) {
                callback.onFailure("解析响应失败: " + e.getMessage());
                webSocket.close(1000, "响应格式错误");
            }
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, okhttp3.Response response) {
            if (retryCount < MAX_RETRIES) {
                retryCount++;
                sendRequest(question, callback);
            } else {
                callback.onFailure("连接失败，已达最大重试次数: " + t.getMessage());
            }
        }
    }
}