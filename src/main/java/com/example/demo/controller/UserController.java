package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
//UserController 类用来处理 /user 路径下所有请求
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户
        User u = userService.findByUsername(username);
        if (u == null){
            userService.register(username, password);
            return Result.success();
        }
        else{
            return Result.error("用户名已经被注册");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser = userService.findByUsername(username);
        if (loginUser!= null && loginUser.getPassword().equals(Md5Util.getMD5String(password))){
            Map<String,Object> claims = new HashMap<String,Object>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        else if (loginUser == null){
            return Result.error("用户名不正确");
        }
        return Result.error("密码错误");
    }


    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvater(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success("更新头像成功");
    }

    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("参数不能为空");
        }
        Map<String,Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User user = userService.findByUsername(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        if (!newPwd.equals(rePwd)){
            return Result.error("两次新密码不一致");
        }

        userService.updatePdw(newPwd);
        return Result.success("更新密码成功");
    }

}
