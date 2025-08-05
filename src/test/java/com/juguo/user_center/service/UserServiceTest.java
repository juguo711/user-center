package com.juguo.user_center.service;
import java.util.Date;

import com.juguo.user_center.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 *
 * @author : juguo
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("dogjuguo");
        user.setUserAccount("890");
        user.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/c6uHzjOM0Euo2juHK6dIUv9vkictqOPfyPk2sqfuxNAfibaObnMdfwyY9diczIRLKYk3ZRcHuRlGibDz7Ekj8beflvUX6rXFTQJCX7qQ8iaIJwick/132");
        user.setUserPassword("789");
        user.setPhone("123");
        user.setEmail("456");
        boolean res = userService.save(user);
        System.out.println(user.getId());
        assertTrue(res);
    }

    @Test
    void userRegister() {
        String userAccount = "juguo3";
        String userPassword = "123456789";
        String checkPassword = "123456789";
        String planetCode = "123";
        long result = userService.userRegister(userAccount, userPassword, checkPassword , planetCode);
//        Assertions.assertEquals( -1 , result);
        Assertions.assertTrue(result > 0);
    }
}