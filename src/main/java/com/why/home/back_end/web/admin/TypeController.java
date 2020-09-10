package com.why.home.back_end.web.admin;

import com.why.home.back_end.po.Type;
import com.why.home.back_end.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/*---------------------------------------------------------------
              TypeController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/24.
;
;  Function:
;                    用于分类管理
----------------------------------------------------------------*/


@Controller
@RequestMapping("/admin")
public class TypeController {

    /* 分页查询 需要注入TypeService */
    @Autowired
    private TypeService typeService;

    /*-------------------------------------------------分类管理主页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回管理页 */
    /* 加参数 同样在全局/admin下访问 即/admin/typeManage */
    @GetMapping("/typeManage")
    /*----Pageable是SpringBoot构造好的分页方法 会根据前端页面构造好的参数 封装到pageable对象
          @PageableDefault可以设置分页参数:
                            size代表分页每页存放数据个数 默认10
                            sort代表排序依据 此处按照"id"排序
                            direction代表排序方式 此处DESC表示倒序----------------- */
    /* Model存储SpringBoot是查询后的信息 */
    public String managePage(@PageableDefault(size = 8 , sort = {"id"} , direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* typeService.listType(pageable)返回类似JSON的信息 */
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types-manage";
    }
    /*-------------------------------------------------分类管理主页面显示逻辑（结束）------------------------------------------------------------*/


    /*-------------------------------------------------分类增加页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交输入的分类名称 用于新增分类 */
    /* Post和Get类型不一样 所以参数一样也不冲突 */
    @PostMapping("/typeManage/add")
    /*  Post提交 将页面输入的分类名称引入 */
    /*  @Valid注解代表要校验Type实体类 与@NotBlank(message = "后端校验：分类名称为空！")相对应 */
    /*  （一定要与Type相邻，否则无效）BindingResult result用于拿到@NotBlank(message = "后端校验：分类名称为空！")中message输出给前端页面 */
    public String addTypePost(@Valid Type type,
                       BindingResult result,
                       RedirectAttributes attributes){
        /*-----------------管理列表业务校验是否已存在逻辑（开始）--------------------*/
        Type type_get=typeService.getTypeByName(type.getName());
        if(type_get != null){
            /* 向result添加错误 此处的result可以自己添加验证内容 name代表type实体类中的字段 s1表示自定义验证结果 s2表示最终返回的消息 */
            result.rejectValue("name","nameError", "添加的分类名已存在");
        }
        /*-----------------管理列表业务校验是否已存在逻辑（结束）--------------------*/

        /*-----------------后端校验信息输入是否为空/已存在逻辑（开始）--------------------*/
        /* 后端校验提示内容：此处的result和Type实体类中NotBlank绑定 */
        if(result.hasErrors()){
            /* 后端校验提示内容： 返回文件夹内html */
            return "admin/types-publish";
        }
        /*-----------------后端校验信息输入是否为空/已存在逻辑（结束）--------------------*/

        /*-----------------type_added校验信息输出逻辑（开始）--------------------*/
        /* 定义新增后的Type对象 */
        Type type_added = typeService.saveType(type);
            /* 如果对象为空 */
            if(type_added == null){
                /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
                /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
                attributes.addFlashAttribute("message","新增失败！");
            }else{
                attributes.addFlashAttribute("message","新增成功！");
            }
            /* 需要用重定向 否则返回不了新输入的数据 */
            return "redirect:/admin/typeManage";
        /*-----------------type_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------分类增加页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------分类增加页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回新增页 */
    /* 加参数 同样在全局/admin下访问 即/admin/typeManage/add */
    @GetMapping("/typeManage/add")
    /* 后端校验提示内容： 使用model 用于前端→后端 增加一个新的type对象 */
    public String addTypePage(Model model) {
        /* 后端校验提示内容： 使用model 增加一个新的type对象 */
        model.addAttribute("type", new Type());
        return "admin/types-publish";
    }
    /*-------------------------------------------------分类增加页面显示逻辑（结束）------------------------------------------------------------*/


    /*-------------------------------------------------分类修改页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Post请求路径 提交修改的分类名称 用于修改分类 */
    /* Post和Get类型不一样 所以参数一样也不冲突 Post需要传入id--/typeManage/update/{id} */
    @PostMapping("/typeManage/update/{id}")
    /*  Post提交 将页面输入的分类名称引入 */
    /*  @Valid注解代表要校验Type实体类 与@NotBlank(message = "后端校验：分类名称为空！")相对应 */
    /*  （一定要与Type相邻，否则无效）BindingResult result用于拿到@NotBlank(message = "后端校验：分类名称为空！")中message输出给前端页面 */
    /*  @PathVariable与{id}对应 保证url中的id能作为参数输入Post */
    public String updateTypePost(@Valid Type type,
                                 BindingResult result,
                                 @PathVariable Long id,
                                 RedirectAttributes attributes) {
        /*-----------------校验是否已存在逻辑（开始）--------------------*/
        Type type_get=typeService.getTypeByName(type.getName());
        if(type_get != null){
            /* 向result添加错误 此处的result可以自己添加验证内容 name代表type实体类中的字段 s1表示自定义验证结果 s2表示最终返回的消息 */
            result.rejectValue("name","nameError", "修改的分类名已存在");
        }
        /*-----------------管理列表业务校验是否已存在逻辑（结束）--------------------*/

        /*-----------------后端校验信息输入是否为空/已存在逻辑（开始）--------------------*/
        /* 后端校验提示内容：此处的result和Type实体类中NotBlank绑定 */
        if(result.hasErrors()){
            /* 后端校验提示内容： 返回文件夹内html */
            return "admin/types-publish";
        }
        /*-----------------后端校验信息输入是否为空/已存在逻辑（结束）--------------------*/

        /*-----------------type_added校验信息输出逻辑（开始）--------------------*/
        /* 定义修改后的Type对象 */
        Type type_added = typeService.updateType(id,type);
        /* 如果对象为空 */
        if(type_added == null){
            /* 由于使用重定向 后端→前端 给前端页面提示需要用attributes.addFlashAttribute */
            /* 切换页面 不能用post加Model model参数 会造成重定向后 返回页面无法拿到数据 需要用model.addAttribute的方式 */
            attributes.addFlashAttribute("message","修改失败！");
        }else{
            attributes.addFlashAttribute("message","修改成功！");
        }
        /* 需要用重定向 否则返回不了新输入的数据 */
        return "redirect:/admin/typeManage";
        /*-----------------type_added校验信息输出逻辑（结束）--------------------*/
    }
    /*-------------------------------------------------分类修改页面提交逻辑（结束）------------------------------------------------------------*/
    /*-------------------------------------------------分类修改页面显示逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回修改页 */
    /* 加参数 同样在全局/admin下访问 即/admin/typeManage/update/{id} @PathVariable与{id}对应 保证id能输出到url*/
    @GetMapping("/typeManage/update/{id}")
    /* 使用model 利用id查询对应type对象 */
    public String updateTypePage(@PathVariable Long id, Model model) {
        /* 后端校验提示内容： 使用model 用于前端→后端 利用id查询对应type对象 */
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-publish";
    }
    /*-------------------------------------------------分类修改页面显示逻辑（结束）------------------------------------------------------------*/

    /*-------------------------------------------------分类删除页面提交逻辑（开始）------------------------------------------------------------*/
    /* 通过Get请求路径 返回删除页 */
    /* 加参数 同样在全局/admin下访问 即/admin/typeManage/delete/{id} @PathVariable与{id}对应 保证url中的id能作为参数输入Post*/
    @GetMapping("/typeManage/delete/{id}")
    public String deleteTypePage(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        /* 通过RedirectAttributes 加校验反馈消息 与redirect连用 */
        attributes.addFlashAttribute("message","删除成功！");
        /* 修改后一定重定向 否则返回500 */
        return "redirect:/admin/typeManage";
    }
    /*-------------------------------------------------分类删除页面提交逻辑（结束）------------------------------------------------------------*/




}
