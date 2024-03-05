package com.fuzijian.community.Controller;

import com.fuzijian.community.entity.User;
import com.fuzijian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 处理访问注册页面的请求
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(){
        return "/site/register";
    }

    // 处理注册的请求  返回视图的名称
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);

        // 根据map的值进行响应
        if(map == null || map.isEmpty()){
            //提示注册成功
            model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送激活邮件，请尽快激活！");
            model.addAttribute("target", "/index");
            return "site/operate-result";
            // 跳转到首页 通过js实现
        }
        else {
            // 根据
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }
}
