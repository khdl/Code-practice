package com.liu.po;

import java.util.Date;

/**
 * @className: Community
 * @author: yu.liu
 * @date: 2019/6/19 10:34
 * @description: 社区
 */
public class Community {
    private String id;
    private String name;
    private String preCode;
    private String communityCode;
    private Date sysCreateTime;
    private String communityAddr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreCode() {
        return preCode;
    }

    public void setPreCode(String preCode) {
        this.preCode = preCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public String getCommunityAddr() {
        return communityAddr;
    }

    public void setCommunityAddr(String communityAddr) {
        this.communityAddr = communityAddr;
    }
}
