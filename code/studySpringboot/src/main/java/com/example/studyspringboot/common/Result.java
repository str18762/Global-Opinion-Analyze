/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午3:35
 */
package com.example.studyspringboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result("200","请求成功",null);
    }
    public static Result success(Object data) {
        return new Result("200","请求成功",data);
    }
    public static Result success(String msg) {
        return new Result("200", msg, null);
    }
    public static Result success(String msg, Object data) {
        return new Result("200", msg, data);
    }
    public static Result success(String code, String msg, Object data) {
        return new Result("200", msg, data);
    }
    public static Result success(String code, String msg) {
        return new Result("200", msg, null);
    }
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }
    public static Result error(String msg) {
        return new Result("405", msg, null);
    }
}
