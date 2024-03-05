package com.fuzijian.community.dao;

import com.fuzijian.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 声明CRUD的方法

    User selectById(int id);

    User selectByName(String name);

    User selectByEmail(String email);

    // 增加用户

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

}
