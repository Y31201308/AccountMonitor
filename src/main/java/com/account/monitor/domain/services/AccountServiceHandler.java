package com.account.monitor.domain.services;

import com.account.monitor.domain.event.AccountRequest;
import com.account.monitor.domain.event.AccountResponse;
import com.account.monitor.domain.metadata.RequestFailedException;
import com.account.monitor.domain.persistence.AccountRepository;
import com.account.monitor.web.model.Account;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "accountService")
public class AccountServiceHandler implements AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountServiceHandler.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountResponse queryAccount(AccountRequest request) {
        List<Account> accountList = null;
        AccountResponse response = response = new AccountResponse();
        try{
            accountList = accountRepository.queryAccount(request);
            response.setAccountList(accountList);
        }catch (Exception e){
            LOGGER.error("Query access base info error:" + e.getMessage());
            response.fail("Query access base info error:" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public AccountResponse addAccount(AccountRequest accountRequest) {
        AccountResponse response = new AccountResponse();
        try{
            LOGGER.info("Added account info in service:" + accountRequest.getAccount());
            accountRepository.addAccount(accountRequest);
        }catch (DuplicateKeyException e){
            LOGGER.error("Add account error:" + e.getMessage());
            response.fail("Duplicate entry ["+ accountRequest.getAccount().getEncode() +"] for column encode;");
            e.printStackTrace();
        }catch (Exception e){
            LOGGER.error("Add account error: " + e.getMessage());
            response.fail("Add account error: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public AccountResponse deleteAccount(AccountRequest accountRequest) {
        AccountResponse response = new AccountResponse();
        try{
            accountRepository.deleteAccount(accountRequest);
        }catch (Exception e){
            LOGGER.error("Delete account by encode error:" + e.getMessage());
            response.fail("Delete account by encode error:" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public AccountResponse updateAccount(AccountRequest request) {
        List<Account> accountList = null;
        AccountResponse response = new AccountResponse();
        try{
            if(request.getChangeMap().isEmpty()){
                throw new RequestFailedException("没有任何信息被修改。");
            }
            accountRepository.updateAccount(request);

            accountList = accountRepository.queryAccount(request);
            response.setAccountList(accountList);

        }catch (RequestFailedException e){
            response.fail("Update account by encode error:" + e.getMessage());
        }catch (Exception e){
            LOGGER.error("Update account by encode error:" + e.getMessage());
            response.fail("Update account by encode error:" + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
