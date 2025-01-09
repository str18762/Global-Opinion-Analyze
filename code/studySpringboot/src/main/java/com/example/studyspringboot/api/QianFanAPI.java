package com.example.studyspringboot.api;


import cn.hutool.json.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 百度智能云的问答机器人API
 */
@Component
public class QianFanAPI {
    @Value("${qianfan.api-key}")
    private String API_KEY;

    @Value("${qianfan.secret-key}")
    private String SECRET_KEY;

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 推文关注主题分析接口
     *
     * @param content 输入的文本
     * @return 机器人回复的文本
     * @throws IOException IO异常
     */
    public String process(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\"messages\":[{\"role\":\"user\",\"content\":\"" + formatContent(content) + "\"}]," +
                        "\"temperature\":0.8," +
                        "\"top_p\":0.8," +
                        "\"penalty_score\":1," +
                        "\"disable_search\":false," +
                        "\"enable_citation\":false," +
                        "\"collapsed\":true," +
                        "\"system\":\"你是一个舆情分析人员，需要根据用户的twitter推文总结出用户关注的时事<可以搜索网络时事进行参考>；" +
                        "以字符串格式给出<只需给出一句话时事概括即可（20字以内）,无需解释，无需多言>；" +
                        "<如果推文无法概括，请回答：“错误”>\"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        if (!response.isSuccessful() || response.body() == null) {
            return null;
        }
        String bodyStr = response.body().string();
        System.out.println("bodyStr: " + bodyStr);
        JSONObject jsonObject = null;
        String result = null;
        if (response.isSuccessful() && !bodyStr.isEmpty()) {
            jsonObject = new JSONObject(bodyStr);
            result = jsonObject.get("result").toString();
        }

        if (result != null) {
            if (result.contains("错误") || result.contains("智能语言模型") || result.length() > 25) {
                result = null;
            }
        }
        return result;
    }

    /**
     * 人物关系分析接口
     *
     * @param content 输入的文本
     * @return 人物关系列表
     * @throws IOException IO异常
     */
    public List<String> relation(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\"messages\":[{\"role\":\"user\",\"content\":\"" + formatContent(content) + "\"}]," +
                        "\"temperature\":0.3," +
                        "\"top_p\":0.3," +
                        "\"penalty_score\":1," +
                        "\"disable_search\":false," +
                        "\"enable_citation\":false," +
                        "\"collapsed\":true," +
                        "\"system\":\"#你是一个舆情分析人员，需要根据提供的人物<人物名为其在twitter上的账号名>给出其人物关系。" +
                        "请按以下要求回答：<以字符串返回，不同关系间用‘；’分隔>," +
                        "人物没有关系则回答“错误”，" +
                        "回答格式例如：问“特朗普和拜登” 答“[此处写关系（10字以内）]“：”[此处写关系的具体描述（10字以内）]”，<请根据实际作答>，" +
                        "每种关系不可超过20字，不要过多分析\"," +
                        "\"stop\":[]}");

        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-4.0-turbo-8k?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        if (!response.isSuccessful() || response.body() == null) {
            return null;
        }
        String bodyStr = response.body().string();
        System.out.println("bodyStr: " + bodyStr);
        JSONObject jsonObject = null;
        String result = null;
        if (response.isSuccessful() && !bodyStr.isEmpty()) {
            jsonObject = new JSONObject(bodyStr);
            result = jsonObject.get("result").toString();
        }

        if (result != null) {
            if (result.contains("错误") || result.contains("智能语言模型")) {
                result = null;
            }
        }

        return parseRelation(result);
    }

    private List<String> parseRelation(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        // 去除字符串中的方括号和空格
        input = input.replace("\\n", "")
                .replace("\"", "")
                .replace(" ", "")
                .replace("。", "")
                .replace("\n", "");

        // 按逗号分割字符串
        String[] items = input.split("；");

        // 将数组转换为List
        return Arrays.asList(items);
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    public String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBodyContent = "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY;

        RequestBody body = RequestBody.create(requestBodyContent, mediaType);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        String token = null;
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.body() != null) {
                token = new JSONObject(response.body().string()).getStr("access_token");
            }
        }
        return token;
    }

    /**
     * 格式化输入的文本(处理特殊字符)
     * @param content 输入的文本
     * @return 格式化后的文本
     */
    private String formatContent(String content) {
        content = content.replace("\"", "\\\"").replace("\n", "\\n");
        return content;
    }
}