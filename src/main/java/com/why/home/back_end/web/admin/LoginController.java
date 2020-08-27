package com.why.home.back_end.web.admin;

import com.why.home.back_end.po.User;
import com.why.home.back_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/*---------------------------------------------------------------
              LoginController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/22.
;
;  Function:
;               用于系统管理员登录
----------------------------------------------------------------*/


/*@Controller表示该类是控制层 控制层定义返回首页的控制器 用于测试*/
@Controller
/*@RequestMapping 来映射请求，也就是通过它来指定控制器可以处理哪些URL请求 整个Controller在/admin下*/
@RequestMapping("/admin")
public class LoginController {


    /*-------------------------------------------------管理员登录注销控制层逻辑（开始）------------------------------------------------------------*/
    /* 根据Post提交对象判断用户名和密码正确与否 需要注入UserService */
    @Autowired
    private UserService userService;

    /* 通过Get请求路径 跳转到登录页面 */
    /* 不加参数 默认使用@RequestMapping("/admin")全局的/admin访问 */
    @GetMapping
    public String loginPage(){
        /* return是templates下文件名 */
        return "admin/blogs-login";
    }

    /* 通过Post请求路径 提交输入的用户名和密码 用于登录逻辑 */
    /* 加参数 同样在全局/admin下访问 即/admin/login */
    @PostMapping("/login")
    /* Post提交 输入参数需要加@RequestParam注解  若用户名密码正确放入httpSession*/
    public String login(@RequestParam String username,
                        @RequestParam  String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        /* 检查用户名和密码正确与否----正确传入User 错误传入Null */
        User user = userService.checkUser(username,password);
        if( user != null){
            /* 验证成功后 传session前把密码设为空 防止密码传到页面产生不安全 */
            user.setPassword(null);
            /* 设置session当前登录用户 */
            session.setAttribute("user",user);
            /* return是templates下文件名 */
            return "admin/blogs-afterLogin";
        }else{
            /* 由于使用重定向 给前端页面提示需要用attributes.addFlashAttribute */
            /* 不能用 login参数加Model model 然后用model.addAttribute的方式 会造成重定向后 返回页面无法拿到数据 */
            attributes.addFlashAttribute("message","用户名或密码错误");
            /* 验证失败后 不能直接return "admin/blogs-login" 会造成路径出现问题 需要使用重定向redirect到admin下 */
            /* return是再次转向Controller */
            return "redirect:/admin";
        }
    }

    /* 通过Get请求路径 用于注销逻辑 */
    /* 加参数 同样在全局/admin下访问 即/admin/logout */
    @GetMapping("/logout")
    public String loginOut(HttpSession session){
        /* 拿到session 清空内部user */
        session.removeAttribute("user");
        /* return是再次转向Controller */
        return "redirect:/admin";
    }
    /*-------------------------------------------------管理员登录注销控制层逻辑（结束）------------------------------------------------------------*/


}
