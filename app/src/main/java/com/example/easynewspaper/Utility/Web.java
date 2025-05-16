package com.example.easynewspaper.Utility;


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
    static String baseURL = "http://54.180.97.86:8080";

    public static void Login(String loginId, String password, Callback callback) {
        new Thread(() -> {
            try {
                // 1. URL 지정
                URL url = new URL(baseURL + "/user/login");

                // 2. HttpURLConnection 열기
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true); // POST 전송을 위해 true

                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("loginId", loginId);
                jsonParam.put("password", password);

                // 4. JSON 데이터 전송
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 5. 응답 코드 확인
                int code = conn.getResponseCode();

                // 6. 응답 내용 읽기
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null){
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void Signup(String nickname, String loginId, String password, Callback callback){
        new Thread(() -> {
            try {
                // 1. URL 지정
                URL url = new URL(baseURL + "/user/signup");

                // 2. HttpURLConnection 열기
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true); // POST 전송을 위해 true

                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nickname", nickname);
                jsonParam.put("loginId", loginId);
                jsonParam.put("password", password);

                // 4. JSON 데이터 전송
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 5. 응답 코드 확인
                int code = conn.getResponseCode();

                // 6. 응답 내용 읽기
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null){
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null){
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetNews(long userId, Callback callback){
        new Thread(() -> {
            try {
                URL url = new URL(baseURL + "/news/" + userId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null) {
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetDetailNews(long userId, long newsId, Callback callback){
        new Thread(() -> {
            try {
                URL url = new URL(baseURL + "/news/" + userId + "/" + newsId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null) {
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetChat(long userId, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(baseURL + "/chat/" + userId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null) {
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void GetQuiz(long userId, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(baseURL + "/quiz/" + userId + "/");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null) {
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void solveQuiz(long userId, long quizId, String answer, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(baseURL + "/quiz/" + userId + "/solve");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("quizId", quizId);
                jsonParam.put("answer", answer);

                int code = conn.getResponseCode();
                InputStream is = conn.getInputStream();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (callback != null) {
                        callback.isSuccessed(response.toString());
                    }
                }

                conn.disconnect();

            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void PostChat(long userId, String content, Timestamp date, Callback callback) {
        new Thread(() -> {
            try {
                // 1. URL 지정
                URL url = new URL(baseURL + "/chat/" + userId + "/send");

                // 2. HttpURLConnection 열기
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true); // POST 전송을 위해 true

                // 3. JSON 객체 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userId", userId);
                jsonParam.put("content", content);
                jsonParam.put("date", date);

                // 4. JSON 데이터 전송
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 5. 응답 코드 확인
                int code = conn.getResponseCode();

                // 6. 응답 내용 읽기
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.isFailed();
                }
            }
        }).start();
    }

    public static void EditUserInfo(long userId, Callback callback) {

    }
}
