package com.juguo.user_center.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private String descriotion;

    public BaseResponse(int code, T data, String message , String descriotion) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.descriotion = descriotion;
    }

    public BaseResponse(int code, T data , String message) {
        this(code , data , message , "");
    }

    public BaseResponse(int code , T data){
        this(code , data , "" ,"");
    }

    public BaseResponse(int code , String message , String descriotion){
        this(code , null , message , descriotion);
    }

    public  BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode() , null , errorCode.getMessage() , errorCode.getDescription());
    }
}
