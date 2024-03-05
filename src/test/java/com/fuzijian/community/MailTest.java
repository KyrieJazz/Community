package com.fuzijian.community;

import com.fuzijian.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    // 注入邮件工具
    @Autowired
    private MailClient mailClient;

    // 主动调用tymeleaf模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("657435299@qq.com", "论坛邮箱功能测试", "你好，这是邮箱功能测试");
    }

    @Test
    public void testHTMLMail(){
        Context context = new Context();
        context.setVariable("username", "JJ");
        // 发送的内容
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("657435299@qq.com", "DemoHTML", content);
    }
}
