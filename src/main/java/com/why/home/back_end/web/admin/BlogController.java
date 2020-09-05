package com.why.home.back_end.web.admin;

import com.why.home.back_end.po.Blog;
import com.why.home.back_end.po.User;
import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.TagService;
import com.why.home.back_end.service.TypeService;
import com.why.home.back_end.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*---------------------------------------------------------------
              AdminController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/23.
;
;  Function:
;               用于访问博客管理和发布等页面
----------------------------------------------------------------*/

@Controller
@RequestMapping("/admin")
public class BlogController {

    /* 条件查询 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    /* 分类初始化 需要注入TypeService */
    @Autowired
    private TypeService typeService;

    /* 标签初始化 需要注入TagService */
    @Autowired
    private TagService tagService;


    /*-------------------------------------------------博客列表页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage */
    @GetMapping("/blogManage")
        /*----Pageable是SpringBoot构造好的分页方法 会根据前端页面构造好的参数 封装到pageable对象
          @PageableDefault可以设置分页参数:
                            size代表分页每页存放数据个数 默认10
                            sort代表排序依据 此处按照"updateTime"排序
                            direction代表排序方式 此处DESC表示倒序----------------- */
    /* Blog换为BlogQuery 构造的查询对象 */
    public String manage(@PageableDefault(size = 5 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        /* Model存储所有分类信息List 从而输出给前端页面 进行！！！分类搜索框！！！数据渲染 */
        /* typeService.listType()返回类似JSON的信息 */
        model.addAttribute("types",typeService.listType());
        /* Model存储查询后的分页信息 从而输出给前端页面 进行！！！博客列表！！！数据渲染 */
        /* blogService.listBlog(pageable,blog)返回类似JSON的信息 */
        model.addAttribute("page",blogService.listBlogQuery(pageable,blogQuery));
        return "admin/blogs-manage";
    }
    /*-------------------------------------------------博客列表页面显示逻辑（结束）------------------------------------------------------------*/

    /*-------------------------------------------------博客页面查询显示逻辑（只刷新博客列表）------------------------------------------------------------*/
    /* PostMapping 返回查询页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage/search */
    /* Blog换为BlogQuery 构造的查询对象 */
    /*-------若直接用Blog会报错java.lang.NullPointerException: null，因为若查询时Type未输入，则.getId()本身会报错。改用直接将三个查询参数封装为一个对象的方式，将type.id封装为这个对象的Long类型id----*/
    @PostMapping("/blogManage/search")
    public String search(@PageableDefault(size = 5 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page",blogService.listBlogQuery(pageable,blogQuery));
        /* :: blogList用于返回管理页下面的blogList片段，用于局部渲染，除列表显示外，其他区域不刷新，可以在点击上下页时保留查询条件，此处与HTML中th:fragment="blogList"对应 */
        return "admin/blogs-manage :: blogList";

    }
    /*-------------------------------------------------博客页面查询显示逻辑（只刷新博客列表）------------------------------------------------------------*/


    /*-------------------------------------------------博客新增页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交输入的分类名称 用于新增博客 */
    /* Post和Get类型不一样 所以参数一样也不冲突 */
    /* HttpSession用于获取当前登录用户 */
    @PostMapping("/blogManage/add")
    public String addBlogPost(Blog blog,
                              RedirectAttributes attributes,
                              HttpSession session){
        /* 利用HttpSession的getAttribute获取当前登录用户并对blog设置 */
        blog.setUser((User) session.getAttribute("user"));
        /* 利用传入blog提供的type的id对blog的类型名称进行设置 */
        blog.setType(typeService.getType(blog.getType().getId()));
        /* 利用传入blog提供的tag的所有id对blog的标签名称进行设置 */
        blog.setTags(tagService.listTag(blog.getTagIds()));

        /*-----------------blog_added校验信息输出逻辑（开始）--------------------*/
        /* 定义新增后的Blog对象 */
        Blog blog_added = blogService.saveBlog(blog);
        /* 如果对象为空 */
        if(blog_added == null){
            /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
            /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
            attributes.addFlashAttribute("message","新增失败！");
        }else{
            attributes.addFlashAttribute("message","新增成功！");
        }
        /* 需要用重定向 否则返回不了新输入的数据 */
        return "redirect:/admin/blogManage";
        /*-----------------blog_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------博客新增页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------博客新增页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回新增页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage/add */
    @GetMapping("/blogManage/add")
    /* 后端校验提示内容： 使用model 用于前端→后端 增加一个新的Blog对象 */
    public String addBlogPage(Model model) {
        /* Model存储所有分类信息List 从而输出给前端页面 进行数据渲染 */
        setTypeAndTagModel(model);
        /* 后端校验提示内容： 使用model 增加一个新的Blog对象 */
        model.addAttribute("blog", new Blog());
        return "admin/blogs-publish";
    }
    /*-------------------------------------------------博客新增页面显示逻辑（结束）------------------------------------------------------------*/


    /*-------------------------------------------------博客修改页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交输入的分类名称 用于修改博客 */
    /* Post和Get类型不一样 所以参数一样也不冲突 */
    /* HttpSession用于获取当前登录用户 */
    /*  @PathVariable与{id}对应 保证url中的id能作为参数输入Post */
    @PostMapping("/blogManage/update/{id}")
    public String updateBlogPost(@PathVariable Long id,Blog blog,
                              RedirectAttributes attributes,
                              HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        /*-----------------blog_added校验信息输出逻辑（开始）--------------------*/
        /* 定义新增后的Blog对象 */
        Blog blog_added = blogService.updateBlog(id,blog);
        /* 如果对象为空 */
        if(blog_added == null){
            /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
            /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
            attributes.addFlashAttribute("message","修改失败！");
        }else{
            attributes.addFlashAttribute("message","修改成功！");
        }
        /* 需要用重定向 否则返回不了新输入的数据 */
        return "redirect:/admin/blogManage";
        /*-----------------blog_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------博客修改页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------博客修改页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回修改页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage/update/{id} @PathVariable与{id}对应 保证id能输出到url */
    @GetMapping("/blogManage/update/{id}")
    /* 后端校验提示内容： 使用model 用于前端→后端 利用id查询对应Blog对象 */
    public String updateBlogPage(@PathVariable Long id, Model model) {
        /* Model存储所有分类信息List 从而输出给前端页面 进行数据渲染 */
        setTypeAndTagModel(model);
        /*---------------init()用于前端页面拿到"1,2,3..."形式的tagIds值------------*/
        Blog blog=blogService.getBlog(id);
        blog.init();
        /* 使用model 利用id查询对应Blog对象 */
        model.addAttribute("blog", blog);
        return "admin/blogs-publish";
    }
    /*-------------------------------------------------博客修改页面显示逻辑（结束）------------------------------------------------------------*/

    /*-------------------------------------------------博客删除页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回删除页 */
    /* 加参数 同样在全局/admin下访问 即/admin/blogManage/delete/{id} @PathVariable与{id}对应 保证url中的id能作为参数输入Post*/
    @GetMapping("/blogManage/delete/{id}")
    public String deleteTagPage(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        /* 通过RedirectAttributes 加校验反馈消息 与redirect连用 */
        attributes.addFlashAttribute("message","删除成功！");
        /* 修改后一定重定向 否则返回500 */
        return "redirect:/admin/blogManage";
    }
    /*-------------------------------------------------博客删除页面提交逻辑（结束）------------------------------------------------------------*/



    /* 用于返回前端指定字段信息 */
    private void setTypeAndTagModel(Model model){
        /* typeService.listType()、tagService.listTag()返回类似JSON的信息 */
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
}
