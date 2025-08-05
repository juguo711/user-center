package com.juguo.user_center;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void md5test(){
        final String SALT = "juguo";
        String newPassword = DigestUtils.md5DigestAsHex((SALT + "mypassword").getBytes());
        System.out.println(newPassword);
    }


    @Test
    void contextLoads() {
    }

}
