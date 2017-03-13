package com.account.monitor.domain.metadata;

public enum AccountStatus {

    FREEZN("Account status is frozen"),
    NORMAL("Account is activated");

    private String meaning;

    AccountStatus(String meaning){
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
