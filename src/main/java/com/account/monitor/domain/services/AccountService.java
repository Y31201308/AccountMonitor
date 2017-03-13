package com.account.monitor.domain.services;

import com.account.monitor.domain.event.AccountRequest;
import com.account.monitor.domain.event.AccountResponse;

public interface AccountService {

    AccountResponse queryAccount(AccountRequest accountRequest);

    AccountResponse addAccount(AccountRequest accountRequest);

    AccountResponse deleteAccount(AccountRequest accountRequest);

    AccountResponse updateAccount(AccountRequest accountRequest);

}
