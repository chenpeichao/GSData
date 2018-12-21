package org.pcchen.uar.bean.uar_analyse_content_search;

public class RongMeiTiWXDTO {
    private Integer id;

    private Integer no;

    private String publishSourceName;

    private String wechatNo;

    private String wechatName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getPublishSourceName() {
        return publishSourceName;
    }

    public void setPublishSourceName(String publishSourceName) {
        this.publishSourceName = publishSourceName == null ? null : publishSourceName.trim();
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo == null ? null : wechatNo.trim();
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName == null ? null : wechatName.trim();
    }
}