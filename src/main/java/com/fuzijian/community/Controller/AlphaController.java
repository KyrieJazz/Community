package com.fuzijian.community.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello, Springboot";
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 读取请求中所包含的数据
        System.out.println(request.getMethod());
        // 获取请求的路径
        System.out.println(request.getServletPath());
        // 请求行的所有key
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " " + value);
        }

        // 返回响应数据
        response.setContentType("text/html;charset:utf-8");

    }

    // GET请求

    // 查询所有的学生 students?current=1&limit=20
    // 获取浏览器中的get请求参数
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1")int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some Students";
    }
    // get请求的参数在请求路径当中
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    // POST 请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "student";
    }

    // 向浏览器响应数据，动态的html数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "张三");
        modelAndView.addObject("age", 30);
        // 模板的路径和名称
        modelAndView.setViewName("demo/view");
        return modelAndView;
    }
    @RequestMapping(path = "school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "北境代学");
        model.addAttribute("age", "100");
        return "/demo/view";
    }

    // 响应JSON数据：出现在异步请求当中
    // Java对象 通过JSON对象 -> JS对象
    @RequestMapping(path = "/tmp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        HashMap<String, Object> emp = new HashMap<>();
        emp.put("name", "zhangsan");
        emp.put("age", "22");
        emp.put("salary", "28383");
        return emp;
    }

    // 多个JSON对象查询
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        HashMap<String, Object> emp = new HashMap<>();
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        emp.put("name", "zhangsan");
        emp.put("age", "22");
        emp.put("salary", "28383");
        list.add(emp);
        emp = new HashMap<>();
        emp.put("name", "dafn");
        emp.put("age", "22");
        emp.put("salary", "283");
        list.add(emp);
        emp = new HashMap<>();
        emp.put("name", "san");
        emp.put("age", "2");
        emp.put("salary", "83");
        list.add(emp);

        return list;
    }
}
