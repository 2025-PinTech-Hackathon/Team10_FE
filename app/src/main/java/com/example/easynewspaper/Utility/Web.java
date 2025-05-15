package com.example.easynewspaper.Utility;


import com.example.easynewspaper.Interface.Callback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Web {
    public static void Signup(String nickname, String loginId, String password, Callback callback){
        new Thread(() -> {
            try {
                // 1. URL 지정
                URL url = new URL("/user/signup");

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
                URL url = new URL("/news/" + userId);
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
                URL url = new URL("/news/" + userId + "/" + newsId);
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
}
