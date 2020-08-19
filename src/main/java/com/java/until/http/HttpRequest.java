package com.java.until.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.message.JsonResult;
import com.java.until.StringUntil;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequest {

    //取健康浏览器的token
    public static String sendPostToken(String url, Map<String, String> paramMap){
        String authToken = "";
        String Json = JSONObject.toJSONString(paramMap);
        BufferedReader reader = null;
        String result = "";
        String line = "";
        try {
            URL urlPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlPath.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setRequestProperty("orgId","a9211734e03d47e9a6968ddca24e9c06");
            conn.setRequestProperty("authCode","E1A477ED046549AA9A9D5FFE1EFA13C1");
            if (Json != null) {
                byte[] writebytes = Json.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                conn.getResponseCode();
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(result != null && !StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            String data = object.getString("data");
            if(data != null && data.contains("http")){
                String[] dataArray = data.split("&");
                authToken = dataArray[dataArray.length-1];
                if(authToken != null && !StringUtils.isEmpty(authToken)){
                    authToken = authToken.replace("authToken=","");
                }
            }
        }
        return authToken;
    }
    //获取档案详情
    public static JsonResult sendPost(String url, Map<String, String> paramMap, String token){
        String Json = JSONObject.toJSONString(paramMap);
        String customerData = "";
        BufferedReader reader = null;
        String result = "";
        String line = "";
        try {
            URL urlPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlPath.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setRequestProperty("authToken",token);
            if (Json != null) {
                byte[] writebytes = Json.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                conn.getResponseCode();
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject object = JSONObject.parseObject(result);
        String data = object.getString("data");
        if(data != null && data.contains("sfzh")){
            if ("{".equals(data.substring(0, 1))) {
                JSONObject resultData = JSONObject.parseObject(data);
                return new JsonResult(resultData, object.getInteger("retCode"), object.getString("retMsg"));
            }
        }
        return new JsonResult("", object.getInteger("retCode"), object.getString("retMsg"));
    }

}
