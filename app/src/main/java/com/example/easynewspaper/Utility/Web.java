package com.example.easynewspaper.Utility;


import android.content.Context;
import android.util.Log;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.DataStruct.UserInfo;
import com.example.easynewspaper.Interface.Callback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

public class Web {
    private static UserInfo userInfo = new UserInfo();

    public static long GetUserUserId() {
        return userInfo.getUserId();
    }

    public static String GetNickname() {
        return userInfo.getNickname();
    }

    public static String GetId() {
        return userInfo.getId();
    }

    public static String GetPw() {
        return userInfo.getPw();
    }

    static String baseURL = "http://54.180.97.86:8080";

    private static String Post(String targetUrl, JSONObject reqJson) {
        try {
            URL url = new URL(baseURL + targetUrl);

            // 2. HttpURLConnection 열기
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // POST 전송을 위해 true

            // 4. JSON 데이터 전송
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = reqJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            catch (Exception e) {
                return null;
            }

            // 6. 응답 내용 읽기
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                conn.disconnect();

                return response.toString();
            }
            catch (Exception e) {
                conn.disconnect();

                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    private static String Patch(String targetUrl, JSONObject reqJson) {
        try {
            URL url = new URL(baseURL + targetUrl);

            // 2. HttpURLConnection 열기
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PATCH"); // PATCH로 변경
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // PATCH도 데이터를 전송하므로 true

            // 4. JSON 데이터 전송
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = reqJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (Exception e) {
                return null;
            }

            // 6. 응답 내용 읽기
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                conn.disconnect();

                return response.toString();
            } catch (Exception e) {
                conn.disconnect();

                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static String Get(String targetUrl) {
        try {
            URL url = new URL(baseURL + targetUrl);

            // 2. HttpURLConnection 열기
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            Log.d("태그", url.toString());

            int code = conn.getResponseCode();
            InputStream is = conn.getInputStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                conn.disconnect();

                return response.toString();
            }
            catch (Exception e) {
                conn.disconnect();

                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void Login(String loginId, String password, Callback callback) {
        new Thread(() -> {
            try {
                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("loginId", loginId);
                jsonParam.put("password", password);

                String response = Post("/user/login", jsonParam);

                if (response != null){
                    JSONObject resJson = new JSONObject(response);

                    boolean isSuccess = resJson.getBoolean("isSuccess");

                    int sCode = resJson.getInt("code");

                    Status status = StatusCheck.isSuccess(sCode);

                    if (isSuccess && status.succesed) {
                        JSONObject data = resJson.getJSONObject("data");

                        userInfo.setUserId(data.getLong("userId"));
                        userInfo.setId(loginId);
                        userInfo.setNickname(data.getString("nickname"));
                    }

                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null){
                        callback.isFailed();
                    }
                }
            }
            catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void Signup(String nickname, String loginId, String password, Callback callback){
        new Thread(() -> {
            try {
                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nickname", nickname);
                jsonParam.put("loginId", loginId);
                jsonParam.put("password", password);

                String response = Post("/user/signup", jsonParam);

                if (response != null){
                    JSONObject resJson = new JSONObject(response);

                    boolean isSuccess = resJson.getBoolean("isSuccess");

                    int sCode = resJson.getInt("code");

                    Status status = StatusCheck.isSuccess(sCode);

                    if (isSuccess && status.succesed) {
                        JSONObject data = resJson.getJSONObject("data");

                        long userId = data.getLong("userId");

                        if (!data.isNull("userId")) {
                            userInfo.setUserId(userId);
                            userInfo.setId(loginId);
                            userInfo.setNickname(nickname);
                        }
                    }

                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null){
                        callback.isFailed();
                    }
                }

            } catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void EditUserInfo(String nickname, String password, Callback callback) {
        new Thread(() -> {
            try {
                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("loginId", userInfo.getId());
                jsonParam.put("password", password);
                jsonParam.put("nickname", nickname);

                String response = Patch("/user/" + userInfo.getUserId() + "/edit", jsonParam);

                if (response != null){
                    JSONObject resJson = new JSONObject(response);

                    boolean isSuccess = resJson.getBoolean("isSuccess");

                    int sCode = resJson.getInt("code");

                    Status status = StatusCheck.isSuccess(sCode);

                    if (isSuccess && status.succesed) {
                        JSONObject data = resJson.getJSONObject("data");

                        userInfo.setNickname(nickname);
                        userInfo.setPw(password);
                    }

                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null){
                        callback.isFailed();
                    }
                }
            }
            catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetNews(Callback callback){
        new Thread(() -> {
            try {
                String response = Get("/news/" + userInfo.getUserId());

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetDetailNews(long newsId, Callback callback){
        new Thread(() -> {
            try {
                String response = Get("/news/" +  + userInfo.getUserId() + "/" + newsId);

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetChat(Callback callback) {
        new Thread(() -> {
            try {
                String response = Get("/chat/" +  + userInfo.getUserId());

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void PostChat(String content, Timestamp date, Callback callback) {
        new Thread(() -> {
            try {
                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userId", userInfo.getUserId());
                jsonParam.put("content", content);
                jsonParam.put("date", date);

                String response = Post("/chat/" +  + userInfo.getUserId() + "/send", jsonParam);

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }
            }
            catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetQuiz(Callback callback) {
        new Thread(() -> {
            try {
                String response = Get("/quiz/" + userInfo.getUserId());

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void solveQuiz(long quizId, String answer, Callback callback) {
        new Thread(() -> {
            try {
                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("quizId", quizId);
                jsonParam.put("answer", answer);

                String response = Post("/quiz/" +  + userInfo.getUserId() + "/solve", jsonParam);

                if (response != null) {
                    if (callback != null) {
                        callback.isSuccessed(response);
                    }
                }
                else {
                    if (callback != null) {
                        callback.isFailed();
                    }
                }
            }
            catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }
}
