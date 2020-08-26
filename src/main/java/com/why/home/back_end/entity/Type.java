package com.why.home.back_end.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/*---------------------------------------------------------------
              Type Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/20.
;            数据表对应到实体类的映射.
;  Function:
;          Attributes of Type.
----------------------------------------------------------------*/

/* Entity 注解属于集成JPA (Java Persistence API) 内容。
JPA就是JavaEE的一个ORM标准，它的实现其实和Hibernate没啥本质区别，但是用户如果使用JPA，
那么引用的就是javax.persistence这个“标准”包，而不是org.hibernate这样的第三方包 */
/*注解Entity用来与数据库连接*/
@Entity
/*指定数据表的名称，若不指定则默认为类名Type*/
@Table(name="home_type")
public class Type {

    /*注解主键*/
    @Id
    /*注解主键生成策略(自增长)*/
    @GeneratedValue
    /*id表示对应数据表主键*/
    private Long id;
    /*表示类别名称*/
    /*后端校验 分类名称不能为空 否则提示message*/
    @NotBlank(message = "后端校验：分类名称为空！")
    private String name;


    /*----------------------------处理实体类之间关系(开始线)-----------------------------------*/
    /*---一个Type对应多个Blog用OneToMany （即一种分类对应多篇博客）---*/
                /*  当前type被维护，blogs主动维护（many端作为关系维护端） */
                /*  mappedBy表示自己不是一对多的关系维护端，由对方来维护，是在一的一方进行声明的。mappedBy的值应该为一的一方的表名 */
                /*  在JPA中，在@OneToMany里加入mappedBy属性可以避免生成一张中间表 */
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();


    /*----------------------------处理实体类之间关系(结束线)-----------------------------------*/


    /*生成构造方法*/
    public Type() {
    }

    /*生成set和get方法*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    /*生成toString方法，主要用于日志输出*/
    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
