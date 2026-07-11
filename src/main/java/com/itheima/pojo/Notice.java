package com.itheima.pojo;

import java.time.LocalDateTime;

public class Notice {

     private Integer id;
     private String title;
     private String content;
     private LocalDateTime createTime;
   public Notice()

   {

   }
    public Notice(Integer id, String title, String content, LocalDateTime createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
