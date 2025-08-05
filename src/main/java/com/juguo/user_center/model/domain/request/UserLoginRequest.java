package com.juguo.user_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -5941309010737830758L;

    private String userAccount;
    private String userPassword;
}
