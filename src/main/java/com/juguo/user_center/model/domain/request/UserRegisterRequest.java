package com.juguo.user_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author :juguo
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 5643491464762720250L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
