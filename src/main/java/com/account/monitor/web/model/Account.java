package com.account.monitor.web.model;

import com.account.monitor.domain.metadata.AccountStatus;
import com.account.monitor.domain.metadata.AccountType;
import com.account.monitor.domain.unify.TimeFormat;
import org.joda.time.DateTime;

public class Account {

    private String encode;
    private String name;
    private String comment;
    private AccountStatus status;
    private DateTime createTime;
    private DateTime lastLoginTime;
    private AccountType accountType;

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public void setLastLoginTime(DateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return TimeFormat.formatTime(createTime);
    }

    public String getLastLoginTime() {
        if(lastLoginTime == null){
            return "";
        }
        return TimeFormat.formatTime(lastLoginTime);
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "encode='" + encode + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", accountType=" + accountType +
                '}';
    }
}
