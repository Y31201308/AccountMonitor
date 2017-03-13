package com.account.monitor.domain.persistence;

import com.account.monitor.domain.event.AccountRequest;
import com.account.monitor.web.model.Account;

import java.util.List;

public interface AccountRepository {

    void addAccount(AccountRequest accountRequest);
    void deleteAccount(AccountRequest accountRequest);
    void updateAccount(AccountRequest accountRequest);

    List<Account> queryAccount(AccountRequest accountRequest);

}
