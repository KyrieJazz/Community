package com.fuzijian.community.Controller;

import com.fuzijian.community.dao.DiscussPostMapper;
import com.fuzijian.community.entity.DiscussPost;
import com.fuzijian.community.entity.Page;
import com.fuzijian.community.entity.User;
import com.fuzijian.community.service.DiscussPostService;
import com.fuzijian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationAotProcessor;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    //注入Service
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    // 方法调用之前，SpringMVC会自动实例化Model和Page，并且会将Page注入到Model当中
    public String getIndexPage(Model model, Page page){
        // 查询当前数据表中的数据个数，并初始化page对象 总行数
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        // 查询前十条数据
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());

        // 对于每个DiscussPost需要通过userId查询用户名，并实现数据拼接
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        // 检验查询的值是否为空
        if(list != null){
            for(DiscussPost post : list){
                // 通过userId查询
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                discussPosts.add(map);
            }
        }
        // 把拼接好的数据装入model当中
        model.addAttribute("discussPosts", discussPosts);


        return "/index";
    }


}
