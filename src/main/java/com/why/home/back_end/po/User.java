package com.why.home.back_end.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*---------------------------------------------------------------
              User Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/20.
;            数据表对应到实体类的映射.持久层对象(Persisent Object)
;  Function:
;          Attributes of User.
----------------------------------------------------------------*/

/* Entity 注解属于集成JPA (Java Persistence API) 内容。
JPA就是JavaEE的一个ORM标准，它的实现其实和Hibernate没啥本质区别，但是用户如果使用JPA，
那么引用的就是javax.persistence这个“标准”包，而不是org.hibernate这样的第三方包 */
/*注解Entity用来与数据库连接*/
@Entity
/*指定数据表的名称，若不指定则默认为类名User*/
@Table(name = "home_user")
public class User {
    /*注解主键*/
    @Id
    /*注解主键生成策略(自增长)*/
    @GeneratedValue
    /*id表示对应数据表主键*/
    private Long id;
    /*表示类别名称*/
    private String nickname;
    private String username;
    private String password;
    private String email;
    private Integer type;
    private String avatar;
    /*    Hibernate JPA注解 @Temporal(TemporalType.DATE) 日期注解    */
    /*    TemporalType.TIMESTAMP 参数代表形如 'HH:MM:SS' 格式的日期   */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*----------------------------处理实体类之间关系(开始线)-----------------------------------*/
    /*---一个User对应多个Blog用OneToMany （即一种分类对应多篇博客）---*/
                /*  当前user被维护，blog主动维护（many端作为关系维护端） */
                /*  mappedBy表示自己不是一对多的关系维护端，由对方来维护，是在一的一方进行声明的。mappedBy的值应该为一的一方的表名 */
                /*  在JPA中，在@OneToMany里加入mappedBy属性可以避免生成一张中间表 */
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs=new ArrayList<>();

    /*----------------------------处理实体类之间关系(结束线)-----------------------------------*/

    /*生成构造方法*/
    public User() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
