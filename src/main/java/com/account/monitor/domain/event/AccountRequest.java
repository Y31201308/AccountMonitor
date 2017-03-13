package com.account.monitor.domain.event;

import com.account.monitor.domain.condition.ColumnCondition;
import com.account.monitor.domain.condition.Condition;
import com.account.monitor.domain.metadata.*;
import com.account.monitor.web.model.Account;
import com.account.monitor.web.model.OldNewValueMapping;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AccountRequest {

    public static final Pattern ENCODE_FORMAT = Pattern.compile("^[a-zA-Z].[0-9]{0,20}$");

    private List<ColumnCondition> conditions;
    private Condition.MultipleConditionsMergeLogic mergeLogic = Condition.MultipleConditionsMergeLogic.AND;
    private String encode;
    private Account account;
    private Map<String , OldNewValueMapping<String>> changeMap;

    public String getEncode() {
        return encode;
    }

    public Map<String, OldNewValueMapping<String>> getChangeMap() {
        return changeMap;
    }

    public void setChangeMap(Map<String, OldNewValueMapping<String>> changeMap) {
        this.changeMap = changeMap;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<ColumnCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<ColumnCondition> conditions) {
        this.conditions = conditions;
    }

    public Condition.MultipleConditionsMergeLogic getMergeLogic() {
        return mergeLogic;
    }

    public void setMergeLogic(Condition.MultipleConditionsMergeLogic mergeLogic) {
        this.mergeLogic = mergeLogic;
    }

    public static EventResult verify(String encode, String accountType, String accountStatus){
        EventResult eventResult = new EventResult();
        try {
            if (StringUtils.isBlank(encode)) {
                throw new RequestFailedException("添加用户失败，由于您的编码为空.");
            }

            if (!ENCODE_FORMAT.matcher(encode).matches()) {
                throw new RequestFailedException("账户编码必须以字母开头，由字母或字母和数字组成");
            }
            AccountType.valueOf(accountType);
            AccountStatus.valueOf(accountStatus);

        }catch (RequestFailedException e) {
            eventResult = new EventResult(EventStatusType.NOK, "参数错误：" + e.getMessage());
        }catch (IllegalArgumentException e){
            eventResult = new EventResult(EventStatusType.NOK, "参数错误：设置的账户类型或者账户状态类型暂时不支持。");
        }

        return eventResult;
    }
}
