package com.cisdi.ext.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Util {
    public static String insertData(JdbcTemplate jdbcTemplate, String entCode) {
        String newId = (String) (jdbcTemplate).execute(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement cs = con.prepareCall("{CALL DEF_" + entCode + "_INSERT_DATA(?)}");
                cs.registerOutParameter(1, 12);
                return cs;
            }
        }, new CallableStatementCallback<String>() {
            public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                String ret = cs.getString(1);
                cs.close();
                return ret;
            }
        });
        return newId;
    }
}
