package com.fuzijian.community;

import com.fuzijian.community.dao.DiscussPostMapper;
import com.fuzijian.community.dao.UserMapper;
import com.fuzijian.community.entity.DiscussPost;
import com.fuzijian.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    // 测试查询数据
    @Test
    public void TestSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("zhangfei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder103@sina.com");
        System.out.println(user);
    }

    // 测试插入数据
    @Test
    public void TestInsertUser(){
        User user = new User();
        user.setUsername("测试");
        user.setPassword("4444");
        user.setSalt("asd");
        user.setEmail("53535@qq.com");
        user.setHeaderUrl("http://www.newcoder.com/102.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    // 测试修改数据
    @Test
    public void TestUpdate(){
        // 修改测试数据
        int rows = userMapper.updateStatus(150, 1);
        // 返回修改的行数
        System.out.println(rows);

        rows = userMapper.updateHeader(150, "http://www.newcoder.com/8.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for(DiscussPost post : list){
            System.out.println(post);
        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);
    }
}
