package com.account.monitor.domain.persistence.mysql;

import com.account.monitor.domain.event.AccountRequest;
import com.account.monitor.domain.metadata.AccountStatus;
import com.account.monitor.domain.metadata.AccountType;
import com.account.monitor.domain.persistence.AccountRepository;
import com.account.monitor.domain.unify.TimeFormat;
import com.account.monitor.web.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository(value = "accountRepository")
public class MysqlAccountRepository implements AccountRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    @Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addAccount(AccountRequest accountRequest) {
        Account account = accountRequest.getAccount();
        int insertedRowCount = this.jdbcTemplate.update(P_SQL.generateAddAccountSQL(account));
        if (insertedRowCount == 0) {
            throw new IllegalArgumentException("Nothing inserted");
        }
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteAccount(AccountRequest accountRequest) {
        int deleteRowCount = this.jdbcTemplate.update(P_SQL.generateDeleteAccountSQL(accountRequest.getEncode()));
        if (deleteRowCount == 0) {
            throw new IllegalArgumentException("Nothing deleted");
        }
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateAccount(AccountRequest request) {
        List<String> sql_withParameter = P_SQL.generateUpdateAccountSQL(request.getChangeMap(), request.getEncode());
        sql_withParameter.forEach(System.out::println);

        PreparedStatementCreator statement = new QueryRulePreparedStatementCreator(sql_withParameter);
        int updateRowCount = this.jdbcTemplate.update(statement);
        if (updateRowCount == 0) {
            throw new IllegalArgumentException("Nothing updated");
        }
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true, propagation = Propagation.REQUIRED)
    public List<Account> queryAccount(AccountRequest request) {
        List<String> sql_withParameter = P_SQL.generateQueryAccountSQL(request.getConditions() , request.getMergeLogic());
        sql_withParameter.forEach(System.out::println);

        PreparedStatementCreator statement = new QueryRulePreparedStatementCreator(sql_withParameter);
        List<Account> accountList = this.jdbcTemplate.query(statement , new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                Account account = new Account();
                account.setEncode(resultSet.getString(DBConstant.ACC_ENCODE));
                account.setName(resultSet.getString(DBConstant.ACC_NAME));
                account.setComment(resultSet.getString(DBConstant.ACC_COMMENT));
                account.setStatus(AccountStatus.valueOf(resultSet.getString(DBConstant.ACC_STATUS)));
                account.setCreateTime(TimeFormat.parseTime(resultSet.getString(DBConstant.ACC_CREATE_TIME)));
                if (resultSet.getString(DBConstant.ACC_LOGIN_TIME) != null){
                    account.setLastLoginTime(TimeFormat.parseTime(resultSet.getString(DBConstant.ACC_LOGIN_TIME)));
                }
                account.setAccountType(AccountType.valueOf(resultSet.getString(DBConstant.ACC_TYPE)));
                return account;
            }
        });
        return accountList;
    }

    public class QueryRulePreparedStatementCreator implements PreparedStatementCreator {
        private List<String> sql_withParameter;

        public QueryRulePreparedStatementCreator(List<String> sql_withParameter) {
            this.sql_withParameter = sql_withParameter;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(sql_withParameter.get(0), Statement.RETURN_GENERATED_KEYS);

            if (sql_withParameter.size() > 1) {
                for (int parameterIndex = 1; parameterIndex < sql_withParameter.size(); parameterIndex++) {
                    if (sql_withParameter.get(parameterIndex) != null) {
                        ps.setString(parameterIndex, sql_withParameter.get(parameterIndex));
                    } else {
                        ps.setNull(parameterIndex, java.sql.Types.VARCHAR);
                    }
                }
            }
            return ps;
        }
    }
}
