package com.why.home.back_end.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*---------------------------------------------------------------
              Comment Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/20.
;            数据表对应到实体类的映射.持久层对象(Persisent Object)
;  Function:
;          Attributes of Comment.
----------------------------------------------------------------*/

/* Entity 注解属于集成JPA (Java Persistence API) 内容。
JPA就是JavaEE的一个ORM标准，它的实现其实和Hibernate没啥本质区别，但是用户如果使用JPA，
那么引用的就是javax.persistence这个“标准”包，而不是org.hibernate这样的第三方包 */
/*注解Entity用来与数据库连接*/
@Entity
/*指定数据表的名称，若不指定则默认为类名Comment*/
@Table(name="home_comment")
public class Comment {

    /*注解主键*/
    @Id
    /*注解主键生成策略(自增长)*/
    @GeneratedValue
    /*id表示对应数据表主键*/
    private Long id;
    /*表示类别名称*/
    private String nickname;
    private String email;
    private String avatar;
    private String content;
    /*    Hibernate JPA注解 @Temporal(TemporalType.DATE) 日期注解    */
    /*    TemporalType.TIMESTAMP 参数代表形如 'HH:MM:SS' 格式的日期   */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*用于判断是否是博主评论*/
    private boolean adminComment;


    /*----------------------------处理实体类之间关系(开始线)-----------------------------------*/
    /*---多个Comment对应一个Blog用ManyToOne---*/
    /*---作为关系被维护端---*/
    @ManyToOne
    private Blog blog;

    /*----------------------------处理实体类之间关系(结束线)-----------------------------------*/


    /*----------------------------处理Comment类自关联关系(开始线)-----------------------------------*/
    /*---一条评论---*/
    /*---parentComment作为关系被维护端---*/
    @ManyToOne
    private Comment parentComment;
    /*---多条回复---*/
    /*---parentComment作为关系被维护端，replyComments作为主动维护端---*/
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    /*----------------------------处理Comment类自关联关系(结束线)-----------------------------------*/

    /*生成构造方法*/
    public Comment() {
    }

    /*生成set和get方法*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", adminComment=" + adminComment +
                ", blog=" + blog +
                ", parentComment=" + parentComment +
                ", replyComments=" + replyComments +
                '}';
    }
}
