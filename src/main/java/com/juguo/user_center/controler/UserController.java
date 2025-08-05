package com.juguo.user_center.controler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juguo.user_center.common.BaseResponse;
import com.juguo.user_center.common.ErrorCode;
import com.juguo.user_center.common.ResultUtils;
import com.juguo.user_center.exception.BusinessException;
import com.juguo.user_center.model.domain.User;
import com.juguo.user_center.model.domain.request.UserLoginRequest;
import com.juguo.user_center.model.domain.request.UserRegisterRequest;
import com.juguo.user_center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.juguo.user_center.constant.UserConstant.ADMIN_ROLE;
import static com.juguo.user_center.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 *
 * @author :juguo
 */
@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //规范一点的写法，先进行校验
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userPassword = userRegisterRequest.getUserPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount , userPassword, checkPassword, planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );
        }
        Long result = userService.userRegister(userAccount , userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest request){
        if(userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );
        }
        //规范一点的写法，先进行校验
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount , userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );
        }
        User result = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(result);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );

        }
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) objUser;
        if(currentUser == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User user = userService.getById(currentUser.getId());
        User result = userService.getSafetyUser(user);
        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username , HttpServletRequest request){
        //仅管理员可以查询
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //开始查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username" , username);
        }
        List<User> userList = userService.list(queryWrapper);
        //返回的是脱敏之后的信息
        List<User> result = userList.stream().map(user -> {
            user.setUserPassword(null);
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id , HttpServletRequest request){
        //仅管理员可以删除
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH );
        }
        if(id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR );
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     * 把判断管理员的代码提取出来
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员可以查询
        Object o = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) o;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }


}
