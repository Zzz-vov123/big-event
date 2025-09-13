package com.Zzz.controller;

import com.Zzz.pojo.Result;
import com.Zzz.pojo.User;
import com.Zzz.service.UserService;
import com.Zzz.utils.JwtUtil;
import com.Zzz.utils.Md5Util;
import com.Zzz.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    //步骤1
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {

        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            userService.register(username,password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    //步骤二
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {
        //根据用户名查询用户
        User loginUser=userService.findByUserName(username);
        //判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token=JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token){
        /*//根据用户名查询用户
        Map<String,Object>map=JwtUtil.parseToken(token);
        String username=(String)map.get("username");*/

        Map<String,Object> map=ThreadLocalUtil.get();
        String username=map.get("username").toString();
        User user=userService.findByUserName(username);
        return Result.success(user);
    }
}
