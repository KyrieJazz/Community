package com.fuzijian.community.service;

import com.fuzijian.community.dao.UserMapper;
import com.fuzijian.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    // 根据user_id查询用户名
    // discussPost表中级联用户名会用到此函数
    public User findUserById(int userId){
        return userMapper.selectById(userId);
    }
}
