package com.why.home.back_end.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*---------------------------------------------------------------
              Blog Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/20.
;            数据表对应到实体类的映射.持久层对象(Persisent Object)
;  Function:
;          Attributes of Blog.
----------------------------------------------------------------*/

/* Entity 注解属于集成JPA (Java Persistence API) 内容。
JPA就是JavaEE的一个ORM标准，它的实现其实和Hibernate没啥本质区别，但是用户如果使用JPA，
那么引用的就是javax.persistence这个“标准”包，而不是org.hibernate这样的第三方包 */
/*注解Entity用来与数据库连接*/
@Entity
/*指定数据表的名称，若不指定则默认为类名Blog*/
@Table(name="home_blog")
public class Blog {

    /*注解主键*/
    @Id
    /*注解主键生成策略(自增长)*/
    @GeneratedValue
    /*id表示对应数据表主键*/
    private Long id;
    /*分别表示标题、内容、首图、标记、浏览次数、赞赏开启、版权开启、评论开启、是否发布、是否推荐、创建时间、更新时间*/
    private String title;
    private String content;
    private String topPicture;
    private String flag;
    private Integer views;
    private boolean appreciationOpening;
    private boolean shareOpening;
    private boolean commentOpening;
    private boolean publishOpening;
    private boolean recommendOpening;
    /*    Hibernate JPA注解 @Temporal(TemporalType.DATE) 日期注解    */
    /*    TemporalType.TIMESTAMP 参数代表形如 'HH:MM:SS' 格式的日期   */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*----------------------------处理实体类之间关系(开始线)-----------------------------------*/
    /*---多个Blog对应一个Type用ManyToOne---*/
    /*---作为关系被维护端---*/
    @ManyToOne
    private Type type;

    /*---多个Blog对应一个User用ManyToOne---*/
    /*---作为关系被维护端---*/
    @ManyToOne
    private User user;

    /*---多个Blog对应多个Tag用ManyToMany（即多篇博客对应多种标签）---*/
                /*------------------cascade表示设置级联关系----------------------------------
                        级联在编写触发器时经常用到，触发器的作用是当主控表信息改变时，用来保证其关联表中数据同步更新。若对触发器来修改或删除关联表相记
                     录，必须要删除对应的关联表信息，否则，会存有脏数据。所以，适当的做法是，删除主表的同时，关联表的信息也要同时删除，在hibernate中，
                     只需设置cascade属性值即可。

                    · CascadeType.PERSIST：级联新增（又称级联保存）：对order对象保存时也对items里的对象也会保存。对应EntityManager的presist方法。
                        例子：只有A类新增时，会级联B对象新增。若B对象在数据库存（跟新）在则抛异常（让B变为持久态）
                    · CascadeType.MERGE：级联合并（级联更新）：若items属性修改了那么order对象保存时同时修改items里的对象。对应EntityManager的merge方法 。
                        例子：指A类新增或者变化，会级联B对象（新增或者变化）
                    · CascadeType.REMOVE：级联删除：对order对象删除也对items里的对象也会删除。对应EntityManager的remove方法。
                        例子：REMOVE只有A类删除时，会级联删除B类；
                    · CascadeType.REFRESH：级联刷新：获取order对象里也同时也重新获取最新的items时的对象。对应EntityManager的refresh(object)方法有效。
                        即会重新查询数据库里的最新数据。
                    · CascadeType.ALL：以上四种都是。------------------------------------------------------------------*/
    /*---设置为级联新增，当新增一篇博客时会连同新增标签---*/
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags=new ArrayList<>();


    /*---一个User对应多个Comment用OneToMany （即一个用户对应多条评论）---*/
                    /*  当前blog被维护，comments主动维护（many端作为关系维护端） */
                    /*  mappedBy表示自己不是一对多的关系维护端，由对方来维护，是在一的一方进行声明的。mappedBy的值应该为一的一方的表名 */
                    /*  在JPA中，在@OneToMany里加入mappedBy属性可以避免生成一张中间表 */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();

    /*----------------------------处理实体类之间关系(结束线)-----------------------------------*/



    /*生成构造方法*/
    public Blog() {

    }

    /*生成set和get方法*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopPicture() {
        return topPicture;
    }

    public void setTopPicture(String topPicture) {
        this.topPicture = topPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciationOpening() {
        return appreciationOpening;
    }

    public void setAppreciationOpening(boolean appreciationOpening) {
        this.appreciationOpening = appreciationOpening;
    }

    public boolean isShareOpening() {
        return shareOpening;
    }

    public void setShareOpening(boolean shareOpening) {
        this.shareOpening = shareOpening;
    }

    public boolean isCommentOpening() {
        return commentOpening;
    }

    public void setCommentOpening(boolean commentOpening) {
        this.commentOpening = commentOpening;
    }

    public boolean isPublishOpening() {
        return publishOpening;
    }

    public void setPublishOpening(boolean publishOpening) {
        this.publishOpening = publishOpening;
    }

    public boolean isRecommendOpening() {
        return recommendOpening;
    }

    public void setRecommendOpening(boolean recommendOpening) {
        this.recommendOpening = recommendOpening;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /*生成toString方法，主要用于日志输出*/
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", topPicture='" + topPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciationOpening=" + appreciationOpening +
                ", shareOpening=" + shareOpening +
                ", commentOpening=" + commentOpening +
                ", publishOpening=" + publishOpening +
                ", recommendOpening=" + recommendOpening +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
