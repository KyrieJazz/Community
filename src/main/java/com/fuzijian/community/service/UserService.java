package com.fuzijian.community.service;

import com.fuzijian.community.dao.UserMapper;
import com.fuzijian.community.entity.User;
import com.fuzijian.community.util.CommunityUtil;
import com.fuzijian.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    // 注入域名和项目名
    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    // 根据user_id查询用户名
    // discussPost表中级联用户名会用到此函数
    public User findUserById(int userId){
        return userMapper.selectById(userId);
    }

    // 注册业务
    public Map<String, Object> register(User user){
        Map<String, Object> map = new HashMap<>();

        // 对非法空值进行处理
        if(user == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号的内容不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        // 验证账号是否已经存在
        User u = userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("usernameMsg", "该账号已经存在");
            return map;
        }

        // 邮箱验证
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("emailMsg", "该邮箱已经被注册");
            return map;
        }

        /*
        *  注册用户
        * */

        // 生成加密字符串
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.MD5(user.getPassword() + user.getSalt()));

        user.setType(0);
        user.setStatus(0);

        // 生成二十位的激活码
        user.setActivationCode(CommunityUtil.generateUUID().substring(0, 20));
        // 设置随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        // 记录当前用户的注册时间
        user.setCreateTime(new Date());

        // 向数据库中插入用户数据
        userMapper.insertUser(user);

        /*
        *   向用户发送激活邮件
        * */

        Context context = new Context();
        context.setVariable("email", user.getEmail());

        // 拼接访问激活页面 http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);

        // 生成邮件的内容
        String content = templateEngine.process("/mail/activation", context);

        // 发送邮件
        mailClient.sendMail(user.getEmail(), "论坛账号激活", content);
        return map;
    }
}
