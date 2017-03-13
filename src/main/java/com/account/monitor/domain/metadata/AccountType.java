package com.account.monitor.domain.metadata;

public enum AccountType {

    NORMAL("This is a normal access"),
    SHOP_ADMIN("This is a shop manager access"),
    AREA_ADMIN("This is a area manager access"),
    SUPER_ADMIN("This is a spuer admin access");

    private String meaning;

    AccountType(String meaning){
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
