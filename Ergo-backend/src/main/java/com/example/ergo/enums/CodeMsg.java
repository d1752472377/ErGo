package com.example.ergo.enums;

/**
 * @author 董冠男
 * @date 2023/9/18
 * 描述：返回值枚举类
 */
public enum CodeMsg {
    //
    SUCCESS(200, "成功！"),
    PARAMETER_ERROR(400,"参数异常");


    private int code;
    private String msg;

    CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

}
