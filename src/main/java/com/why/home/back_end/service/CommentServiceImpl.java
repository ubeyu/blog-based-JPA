package com.why.home.back_end.service;

import com.why.home.back_end.dao.CommentRepository;
import com.why.home.back_end.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*---------------------------------------------------------------
              CommentServiceImpl Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/4.
;
;  Function:
;            第二步---CommentService接口实现
----------------------------------------------------------------*/


/*------@Service表示该类是业务层-----*/
@Service
public class CommentServiceImpl implements CommentService{

    /*---------第三步CommentRepository接口构建完成后注入-------*/
    /*---- 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作 (只有定义了CommentRepository，Spring才能自动生成下面的方法)---*/
    @Autowired
    private CommentRepository commentRepository;


    //用于将每个父类评论的所有下级评论统一存放在sonComments集合中，并将其父类统一设置
    private List<Comment> sonComments=new ArrayList<>();


    /*--------保存评论（判断是否有父级评论）---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        /*--------建立博客和评论对象间的关系-------*/
        /*--------1.获取父级评论的id-------*/
        Long parentCommentId=comment.getParentComment().getId();
        /*--------2.若id!=-1，则说明存在父级评论 -------*/
        if(parentCommentId != -1){
            /*----3.对comment赋父级评论 -------*/
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        }else{
            /*----4.对comment父级评论赋空 -------*/
            comment.setParentComment(null);
        }
        /*----5.设置创建时间-------*/
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /*--------通过Comment的ID查询指定评论---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).get();
    }

    /*--------通过Blog的ID查询指定评论---@Transactional将操作放入事务中-----*/
    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        /*--------Sort用于以指定顺序排序评论--------*/
        Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
        List<Comment> fatherComments=commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return commentsProcess(fatherComments);
    }

    @Override
    public void deleteComment(Long id) {}


    /*--------将父级Comment的所有下级评论都放到一级中去，形成两层结构(输入为父级Comment集合，输出为二层所有评论集合)-----*/
    //1.将父级评论存放到新的List中操作
    private List<Comment> commentsProcess(List<Comment> fatherComments){
        List<Comment> resultComment = new ArrayList<>();
        for(Comment fatherComment:fatherComments){
            Comment newFatherComment = new Comment();
            BeanUtils.copyProperties(fatherComment,newFatherComment);
            resultComment.add(newFatherComment);
        }
        newCommentsProcess(resultComment);
        return resultComment;
    }
    //2.循环遍历每个父级评论，获取其回复评论的列表，处理回复评论的列表
    private void newCommentsProcess(List<Comment> resultComment){
        for(Comment comment:resultComment){
            List<Comment> replyComments=comment.getReplyComments();
            for(Comment replyComment:replyComments){
                commentProcess(replyComment);
            }
            //将父级评论的回复评论统一设为sonComments集合
            comment.setReplyComments(sonComments);
            //每处理一个父级评论，清空一次sonComments集合
            sonComments = new ArrayList<>();
        }
    }
    //3.递归处理父级评论的每个回复
    private void commentProcess(Comment replyComment){
        sonComments.add(replyComment);
        if(replyComment.getReplyComments().size() > 0){
            List<Comment> replys = replyComment.getReplyComments();
            for(Comment reply:replys){
                commentProcess(reply);
            }
        }
    }


}
