package org.pcchen.uar.bean.local_pdmi;

import java.util.Date;

public class PubAccountAddTaskDTO {
    private Integer taskId;

    private String userId;

    private Integer userFollow;

    private Date createDate;

    private Date modifyDate;

    private String url;

    private String biz;

    private String wxId;

    private String wxNickname;

    private Integer gsdataGroupId;

    private Integer taskStatus;

    private Date startTime;

    private Date expiredTime;

    private Integer industryCatalogId;

    public PubAccountAddTaskDTO() {
    }

    public PubAccountAddTaskDTO(Integer taskId, String userId, Integer userFollow,
                                Date createDate, Date modifyDate, String url, String biz,
                                String wxId, String wxNickname, Integer gsdataGroupId,
                                Integer taskStatus, Date startTime, Date expiredTime, Integer industryCatalogId) {
        this.taskId = taskId;
        this.userId = userId;
        this.userFollow = userFollow;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.url = url;
        this.biz = biz;
        this.wxId = wxId;
        this.wxNickname = wxNickname;
        this.gsdataGroupId = gsdataGroupId;
        this.taskStatus = taskStatus;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
        this.industryCatalogId = industryCatalogId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getUserFollow() {
        return userFollow;
    }

    public void setUserFollow(Integer userFollow) {
        this.userFollow = userFollow;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz == null ? null : biz.trim();
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getWxNickname() {
        return wxNickname;
    }

    public void setWxNickname(String wxNickname) {
        this.wxNickname = wxNickname == null ? null : wxNickname.trim();
    }

    public Integer getGsdataGroupId() {
        return gsdataGroupId;
    }

    public void setGsdataGroupId(Integer gsdataGroupId) {
        this.gsdataGroupId = gsdataGroupId;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getIndustryCatalogId() {
        return industryCatalogId;
    }

    public void setIndustryCatalogId(Integer industryCatalogId) {
        this.industryCatalogId = industryCatalogId;
    }
}