package com.itheima.pojo;

import java.util.Date;

public class ChatMessage {
    private Integer id;
    private Integer applyId;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Date createTime;
    private Integer isRead;

    // getter setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getApplyId() {
        return applyId;
    }
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
    public Integer getFromId() {
        return fromId;
    }
    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }
    public Integer getToId() {
        return toId;
    }
    public void setToId(Integer toId) {
        this.toId = toId;
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
    public Integer getIsRead() {
        return isRead;
    }
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}