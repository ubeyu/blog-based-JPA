package com.why.home.back_end.web.admin;



import com.why.home.back_end.po.Tag;
import com.why.home.back_end.service.TagService;
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

import javax.validation.Valid;

/*---------------------------------------------------------------
              TagController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/24.
;
;  Function:
;                    用于标签管理
----------------------------------------------------------------*/


@Controller
@RequestMapping("/admin")
public class TagController {

    /* 分页查询 需要注入TagService */
    @Autowired
    private TagService tagService;

    /*-------------------------------------------------标签管理主页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/tagManage */
    @GetMapping("/tagManage")
    /*----Pageable是SpringBoot构造好的分页方法 会根据前端页面构造好的参数 封装到pageable对象
          @PageableDefault可以设置分页参数:
                            size代表分页每页存放数据个数 默认10
                            sort代表排序依据 此处按照"id"排序
                            direction代表排序方式 此处DESC表示倒序----------------- */
    /* Model存储SpringBoot是查询后的信息 */
    public String managePage(@PageableDefault(size = 3 , sort = {"id"} , direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* tagService.listTag(pageable)返回类似JSON的信息 */
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags-manage";
    }
    /*-------------------------------------------------标签管理主页面显示逻辑（结束）------------------------------------------------------------*/


    /*-------------------------------------------------标签增加页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交输入的标签名称 用于新增标签 */
    /* Post和Get类型不一样 所以参数一样也不冲突 */
    @PostMapping("/tagManage/add")
    /*  Post提交 将页面输入的标签名称引入 */
    /*  @Valid注解代表要校验Tag实体类 与@NotBlank(message = "后端校验：标签名称为空！")相对应 */
    /*  （一定要与Tag相邻，否则无效）BindingResult result用于拿到@NotBlank(message = "后端校验：标签名称为空！")中message输出给前端页面 */
    public String addTagPost(@Valid Tag tag,
                       BindingResult result,
                       RedirectAttributes attributes){
        /*-----------------管理列表业务校验是否已存在逻辑（开始）--------------------*/
        Tag tag_get=tagService.getTagByName(tag.getName());
        if(tag_get != null){
            /* 向result添加错误 此处的result可以自己添加验证内容 name代表tag实体类中的字段 s1表示自定义验证结果 s2表示最终返回的消息 */
            result.rejectValue("name","nameError", "添加的标签名已存在");
        }
        /*-----------------管理列表业务校验是否已存在逻辑（结束）--------------------*/

        /*-----------------后端校验信息输入是否为空/已存在逻辑（开始）--------------------*/
        /* 后端校验提示内容：此处的result和Tag实体类中NotBlank绑定 */
        if(result.hasErrors()){
            /* 后端校验提示内容： 返回文件夹内html */
            return "admin/tags-publish";
        }
        /*-----------------后端校验信息输入是否为空/已存在逻辑（结束）--------------------*/

        /*-----------------tag_added校验信息输出逻辑（开始）--------------------*/
        /* 定义新增后的Tag对象 */
        Tag tag_added = tagService.saveTag(tag);
        /* 如果对象为空 */
        if(tag_added == null){
            /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
            /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
            attributes.addFlashAttribute("message","新增失败！");
        }else{
            attributes.addFlashAttribute("message","新增成功！");
        }
        /* 需要用重定向 否则返回不了新输入的数据 */
        return "redirect:/admin/tagManage";
        /*-----------------type_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------标签增加页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------标签增加页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回新增页 */
    /* 加参数 同样在全局/admin下访问 即/admin/tagManage/add */
    @GetMapping("/tagManage/add")
    /* 后端校验提示内容： 使用model 用于前端→后端 增加一个新的tag对象 */
    public String addTagPage(Model model) {
        /* 后端校验提示内容： 使用model 增加一个新的tag对象 */
        model.addAttribute("tag", new Tag());
        return "admin/tags-publish";
    }
    /*-------------------------------------------------标签增加页面显示逻辑（结束）------------------------------------------------------------*/


    /*-------------------------------------------------标签修改页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交修改的标签名称 用于修改标签 */
    /* Post和Get类型不一样 所以参数一样也不冲突 Post需要传入id--/tagManage/{id} */
    @PostMapping("/tagManage/update/{id}")
    /*  Post提交 将页面输入的标签名称引入 */
    /*  @Valid注解代表要校验Tag实体类 与@NotBlank(message = "后端校验：标签名称为空！")相对应 */
    /*  （一定要与Tag相邻，否则无效）BindingResult result用于拿到@NotBlank(message = "后端校验：标签名称为空！")中message输出给前端页面 */
    /*  @PathVariable与{id}对应 保证url中的id能作为参数输入Post */
    public String updateTagPost(@Valid Tag tag,
                                 BindingResult result,
                                 @PathVariable Long id,
                                 RedirectAttributes attributes) {
        /*-----------------校验是否已存在逻辑（开始）--------------------*/
        Tag tag_get=tagService.getTagByName(tag.getName());
        if(tag_get != null){
            /* 向result添加错误 此处的result可以自己添加验证内容 name代表tag实体类中的字段 s1表示自定义验证结果 s2表示最终返回的消息 */
            result.rejectValue("name","nameError", "修改的标签名已存在");
        }
        /*-----------------管理列表业务校验是否已存在逻辑（结束）--------------------*/

        /*-----------------后端校验信息输入是否为空/已存在逻辑（开始）--------------------*/
        /* 后端校验提示内容：此处的result和Tag实体类中NotBlank绑定 */
        if(result.hasErrors()){
            /* 后端校验提示内容： 返回文件夹内html */
            return "admin/tags-publish";
        }
        /*-----------------后端校验信息输入是否为空/已存在逻辑（结束）--------------------*/

        /*-----------------tag_added校验信息输出逻辑（开始）--------------------*/
        /* 定义修改后的Tag对象 */
        Tag tag_added = tagService.updateTag(id,tag);
        /* 如果对象为空 */
        if(tag_added == null){
            /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
            /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
            attributes.addFlashAttribute("message","修改失败！");
        }else{
            attributes.addFlashAttribute("message","修改成功！");
        }
        /* 需要用重定向 否则返回不了新输入的数据 */
        return "redirect:/admin/tagManage";
        /*-----------------tag_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------标签修改页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------标签修改页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回修改页 */
    /* 加参数 同样在全局/admin下访问 即/admin/tagManage/update/{id} @PathVariable与{id}对应 保证id能输出到url*/
    @GetMapping("/tagManage/update/{id}")
    /* 后端校验提示内容： 使用model 用于前端→后端 利用id查询对应tag对象 */
    public String updateTagPage(@PathVariable Long id, Model model) {
        /* 使用model 利用id查询对应tag对象 */
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-publish";
    }
    /*-------------------------------------------------标签修改页面显示逻辑（结束）------------------------------------------------------------*/

    /*-------------------------------------------------标签删除页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回删除页 */
    /* 加参数 同样在全局/admin下访问 即/admin/tagManage/delete/{id} @PathVariable与{id}对应 保证url中的id能作为参数输入Post*/
    @GetMapping("/tagManage/delete/{id}")
    public String deleteTagPage(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        /* 通过RedirectAttributes 加校验反馈消息 与redirect连用 */
        attributes.addFlashAttribute("message","删除成功！");
        /* 修改后一定重定向 否则返回500 */
        return "redirect:/admin/tagManage";
    }
    /*-------------------------------------------------标签删除页面提交逻辑（结束）------------------------------------------------------------*/




}
