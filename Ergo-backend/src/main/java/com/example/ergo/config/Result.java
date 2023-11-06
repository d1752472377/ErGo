package com.example.ergo.config;


import com.example.ergo.enums.CodeMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 董冠男
 * @date 2023/9/18
 * 描述：返回类结果封装
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 结果编码
     */
    private int code;
    /**
     * 错误提示
     */
    private String message;
    /**
     * 返回值
     */
    private T data;

    public Result(){
        this.code = 200;
    }
    public Result(int code,String message){
        this.code = code;
        this.message = message;
    }
    public Result(T data){
        this.code = 200;
        this.data =data;
    }
    public Result(String message){
        this.code = 500;
        this.message = message;
    }
    public Result(String message,T data){
        this.code = 200;
        this.message = message;
        this.data =data;
    }

    public static <T> Result<T> success(){
        return new Result<>();
    }
    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }
    public static <T> Result<T> success(Integer code, String message){
        return new Result<>(code,message);
    }
    public static <T> Result<T> success(String message,T data){
        return new Result<>(message,data);
    }
    public static <T> Result<T> fail(String message){
        return new Result<>(message);
    }
    public static <T> Result<T> fail(Integer code, String message){
        return new Result<>(code,message);
    }
    public static <T> Result<T> fail(CodeMsg codeMsg){
        return new Result<>(codeMsg.getCode(), codeMsg.getMsg());
    }

}
