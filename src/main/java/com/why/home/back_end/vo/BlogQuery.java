package com.why.home.back_end.vo;

import com.why.home.back_end.po.Type;


/*---------------------------------------------------------------
              BlogQuery Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/27.
;     通常对应数据模型(数据库),本身还有部分业务逻辑的处理。值对象(Value Object)
;  Function:
;                  定义博客查询对象
----------------------------------------------------------------*/


public class BlogQuery {

    /*title表示对应查询标题 typeId表示查询类型ID recommendOpening表示是否推荐 */
    private String title;
    private Long typeId;
    private boolean recommendOpening;

    /* 构造方法 */

    public BlogQuery() {
    }

    /* set/get方法 */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommendOpening() {
        return recommendOpening;
    }

    public void setRecommendOpening(boolean recommendOpening) {
        this.recommendOpening = recommendOpening;
    }
}
