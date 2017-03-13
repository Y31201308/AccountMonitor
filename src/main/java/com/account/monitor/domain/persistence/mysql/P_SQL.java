package com.account.monitor.domain.persistence.mysql;

import com.account.monitor.domain.condition.ColumnCondition;
import com.account.monitor.domain.condition.Condition;
import com.account.monitor.web.model.Account;
import com.account.monitor.web.model.OldNewValueMapping;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class P_SQL {
    private static final Logger LOGGER = LogManager.getLogger(P_SQL.class);

    public static final String QUERY_ALL_ACCOUNT = "SELECT * FROM " + DBConstant.ACC_TABLE_NAME;

    public static final List<String> generateQueryAccountSQL(List<ColumnCondition> conditions,
                                                             Condition.MultipleConditionsMergeLogic mergeLogic){
        List<String> sql_withParameterList = new ArrayList<String>();

        StringBuffer sql = new StringBuffer();
        sql.append(QUERY_ALL_ACCOUNT);
        if(conditions !=null && conditions.size() > 0){
            sql.append(" WHERE ");
            sql.append(ColumnCondition.generateGroupsList(conditions , mergeLogic.name()));
        }
        sql_withParameterList.add(sql.toString());
        sql_withParameterList.addAll(ColumnCondition.getAllConditionParameters(conditions));
        return sql_withParameterList;
    }

    public static String generateAddAccountSQL(Account account) {
        String addAccountSQL = "INSERT INTO " + DBConstant.ACC_TABLE_NAME + "("
                    + DBConstant.ACC_ENCODE + "," + DBConstant.ACC_NAME + "," + DBConstant.ACC_TYPE + ","
                    + DBConstant.ACC_STATUS + "," + DBConstant.ACC_CREATE_TIME + "," + DBConstant.ACC_COMMENT + ")" +
                "VALUES('"
                    + account.getEncode() + "','" + account.getName() + "','" + account.getAccountType() + "','"
                    + account.getStatus() + "','" + account.getCreateTime() + "','" + account.getComment() + "');";

        LOGGER.info("SQL: " + addAccountSQL);
        return addAccountSQL;
    }

    public static String generateDeleteAccountSQL(String encode){
        String deleteAccountSQL = "DELETE FROM " + DBConstant.ACC_TABLE_NAME + " WHERE "
                + DBConstant.ACC_ENCODE + " = '" + encode + "'";
        return deleteAccountSQL;
    }

    public static List<String> generateUpdateAccountSQL(Map<String, OldNewValueMapping<String>> changeMap , String encode) {
        String sql_update = "UPDATE " + DBConstant.ACC_TABLE_NAME  + " SET ";
        String sql_where = " WHERE " + DBConstant.ACC_ENCODE + " = '" + encode + "'";
        StringBuffer sql = new StringBuffer();
        sql.append(sql_update);
        List<String> sql_withParameter = new LinkedList<String>();
        boolean flag = false;
        for (Map.Entry<String, OldNewValueMapping<String>> changeEntry : changeMap.entrySet()) {
            if(flag)
                sql.append(", ");
            sql.append("`").append(changeEntry.getKey()).append("` = ?");
            sql_withParameter.add(changeEntry.getValue().newValue == null ? "" : changeEntry.getValue().newValue);
            flag = true;
        }
        sql.append(sql_where);

        sql_withParameter.add(0, sql.toString());
        return sql_withParameter;
    }
}
