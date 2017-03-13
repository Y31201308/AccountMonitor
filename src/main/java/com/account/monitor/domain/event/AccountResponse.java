package com.account.monitor.domain.event;

import com.account.monitor.domain.metadata.ConfigurableImmutable;
import com.account.monitor.domain.metadata.EventResult;
import com.account.monitor.web.model.Account;

import java.util.List;


public class AccountResponse extends EventResult implements ConfigurableImmutable {

    private List<Account> accountList;

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
