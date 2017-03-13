package com.account.monitor.web.controller;

import com.account.monitor.domain.condition.*;
import com.account.monitor.domain.event.AccountRequest;
import com.account.monitor.domain.event.AccountResponse;
import com.account.monitor.domain.metadata.AccountStatus;
import com.account.monitor.domain.metadata.AccountType;
import com.account.monitor.domain.metadata.EventResult;
import com.account.monitor.domain.metadata.EventStatusType;
import com.account.monitor.domain.persistence.mysql.DBConstant;
import com.account.monitor.domain.services.AccountService;
import com.account.monitor.web.model.Account;
import com.account.monitor.web.model.OldNewValueMapping;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping("/dashboard")
    public String dashboard(@RequestParam(value="encode", required=false) String encode,
                            @RequestParam(value="name", required=false) String name,
                            @RequestParam(value="accountType", required=false) String accountType,
                            @RequestParam(value="startTime", required=false) String startTime,
                            @RequestParam(value="endTime", required=false) String endTime,
                            Model model){
        AccountRequest request = new AccountRequest();
        List<ColumnCondition> conditions = new ArrayList<>(5);
        Condition condition = null;
        if(!StringUtils.isBlank(encode)){
            condition = new ContainCondition(encode.trim());
            conditions.add(new ColumnCondition(DBConstant.ACC_ENCODE, condition));
        }
        if(!StringUtils.isBlank(name)){
            condition = new ContainCondition(name.trim());
            conditions.add(new ColumnCondition(DBConstant.ACC_NAME, condition));
        }
        if(!StringUtils.isBlank(accountType)){
            condition = new EqualsCondition(accountType.trim().toUpperCase());
            conditions.add(new ColumnCondition(DBConstant.ACC_TYPE, condition));
        }
        if(!StringUtils.isBlank(startTime)){
            condition = DateTimeCondition.after(startTime.trim());
            if (condition.check(startTime.trim())){
                conditions.add(new ColumnCondition(DBConstant.ACC_CREATE_TIME, condition));
            }
        }
        if(!StringUtils.isBlank(endTime)){
            condition = DateTimeCondition.before(endTime.trim());
            if (condition.check(endTime.trim())){
                conditions.add(new ColumnCondition(DBConstant.ACC_CREATE_TIME, condition));
            }
        }
        request.setConditions(conditions);
        request.setMergeLogic(Condition.MultipleConditionsMergeLogic.AND);
        AccountResponse response = accountService.queryAccount(request);
        if(response.getStatus() == EventStatusType.NOK){
            model.addAttribute("eventResult", new EventResult(EventStatusType.NOK , response.getResultMessage()));
        }else {
            model.addAttribute("accountList", response.getAccountList());
        }
        model.addAttribute("encode", encode);
        model.addAttribute("name", name);
        model.addAttribute("accountType", accountType);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        return "dashboard";
    }

    @RequestMapping("/addAccount")
    public String addAccount(@RequestParam(value="encode", required=false) String encode,
                             @RequestParam(value="name", required=false) String name,
                             @RequestParam(value="accountType", required=false) String accountType,
                             @RequestParam(value="status", required=false) String status,
                             @RequestParam(value = "comment" , required = false) String comment,
                             Model model){

        LOGGER.info("Add account encode:" + encode + " name:"+name+"  accountType:"+accountType+"  status:"+status+"  comment:"+comment);
        Account account = new Account();
        if(!StringUtils.isBlank(encode)){
            EventResult eventResult = AccountRequest.verify(encode, accountType, status);
            if(!StringUtils.isBlank(encode)){
                account.setEncode(encode.trim().toUpperCase());
            }
            if(!StringUtils.isBlank(name)){
                account.setName(name.trim());
            }
            if(!StringUtils.isBlank(comment)){
                account.setComment(comment.trim());
            }
            account.setAccountType(AccountType.valueOf(accountType.trim().toUpperCase()));
            account.setCreateTime(new DateTime());
            account.setStatus(AccountStatus.valueOf(status.trim()));

            if(eventResult.getStatus() == EventStatusType.NOK){
                model.addAttribute("eventResult", eventResult);
                model.addAttribute("account" , account);
                return "add_account";
            }

            AccountRequest request = new AccountRequest();
            request.setAccount(account);

            AccountResponse response = accountService.addAccount(request);
            if(response.getStatus() == EventStatusType.NOK){
                model.addAttribute("eventResult", new EventResult(EventStatusType.NOK , response.getResultMessage()));
            }else {
                model.addAttribute("eventResult", new EventResult(EventStatusType.OK , "添加账户操作成功。"));
            }
            model.addAttribute("account" , account);
            return "account_form";
        }
        model.addAttribute("account" , account);
        LOGGER.info("Add account:" + account);

        return "add_account";
    }

    @RequestMapping("/editAccount")
    public String editAccount(@RequestParam(value="encode", required=true) String encode,
                              @RequestParam(value="newEncode", required=false) String newEncode,
                              @RequestParam(value="name", required=false) String name,
                              @RequestParam(value="newName", required=false) String newName,
                              @RequestParam(value="accountType", required=false) String accountType,
                              @RequestParam(value="newAccountType", required=false) String newAccountType,
                              @RequestParam(value="status", required=false) String status,
                              @RequestParam(value="newStatus", required=false) String newStatus,
                              @RequestParam(value = "comment" , required = false) String comment,
                              @RequestParam(value = "newComment" , required = false) String newComment,
                              final RedirectAttributes redirectAttributes,  Model model){
        AccountRequest request = new AccountRequest();
        AccountResponse response = null;
        if(name == null){
            List<ColumnCondition> conditions = new ArrayList<>(1);
            Condition condition = new EqualsCondition(encode.trim());
            conditions.add(new ColumnCondition(DBConstant.ACC_ENCODE, condition));
            request.setConditions(conditions);
            response = accountService.queryAccount(request);
            if (response.getStatus() == EventStatusType.NOK) {
                model.addAttribute("eventResult", new EventResult(EventStatusType.NOK, response.getResultMessage()));
                return "add_account";
            }
            Account oldAccount = response.getAccountList().get(0);
            model.addAttribute("account", oldAccount);
            return "edit_account";
        }else{
            Map<String, OldNewValueMapping<String>> changeMap = new HashMap<>();
            OldNewValueMapping<String> oldNewValue = null;
            if(newEncode != null && !encode.equals(newEncode)){
                oldNewValue = new OldNewValueMapping<>();
                oldNewValue.oldValue = encode;
                oldNewValue.newValue = newEncode;
                changeMap.put(DBConstant.ACC_ENCODE , oldNewValue);
            }
            if(newName != null && !name.equals(newName)){
                oldNewValue = new OldNewValueMapping<>();
                oldNewValue.oldValue = name;
                oldNewValue.newValue = newName;
                changeMap.put(DBConstant.ACC_NAME , oldNewValue);
            }
            if(newAccountType != null && !accountType.equals(newAccountType)){
                oldNewValue = new OldNewValueMapping<>();
                oldNewValue.oldValue = accountType;
                oldNewValue.newValue = newAccountType;
                changeMap.put(DBConstant.ACC_TYPE , oldNewValue);
            }
            if(newStatus != null && !status.equals(newStatus)){
                oldNewValue = new OldNewValueMapping<>();
                oldNewValue.oldValue = status;
                oldNewValue.newValue = newStatus;
                changeMap.put(DBConstant.ACC_STATUS , oldNewValue);
            }
            if(newComment != null && !comment.equals(newComment)){
                oldNewValue = new OldNewValueMapping<>();
                oldNewValue.oldValue = comment;
                oldNewValue.newValue = newComment;
                changeMap.put(DBConstant.ACC_COMMENT , oldNewValue);
            }
            request.setChangeMap(changeMap);
            request.setEncode(encode);
            response = accountService.updateAccount(request);

            if(response.getStatus() == EventStatusType.NOK){
                redirectAttributes.addFlashAttribute("eventResult", new EventResult(EventStatusType.NOK , response.getResultMessage()));
                return "redirect:../account/editAccount?encode="+encode;
            }else {
                model.addAttribute("eventResult", new EventResult(EventStatusType.OK , "编辑账户操作成功。"));
                model.addAttribute("account" , response.getAccountList().get(0));
            }
            return "account_form";
        }
    }

    @RequestMapping("/deleteAccount")
    public String deleteAccount(@RequestParam(value="encode", required=true) String encode,
                                final RedirectAttributes redirectAttributes){
        if(StringUtils.isBlank(encode)){
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(EventStatusType.NOK , "参数错误，编码不能为空"));
            return "redirect:../account/dashboard";
        }
        AccountRequest request = new AccountRequest();
        request.setEncode(encode);
        AccountResponse response = accountService.deleteAccount(request);
        if(response.getStatus() == EventStatusType.NOK){
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(EventStatusType.NOK , response.getResultMessage()));
        }else {
            redirectAttributes.addFlashAttribute("eventResult", new EventResult(EventStatusType.OK , "删除账户操作成功"));
        }
        return "redirect:../account/dashboard";
    }
}
