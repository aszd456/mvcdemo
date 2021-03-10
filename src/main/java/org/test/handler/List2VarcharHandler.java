package org.test.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName List2VarcharHandler
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/10 15:50
 * @Version 1.0
 **/
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class List2VarcharHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        //StringBuilder sb = new StringBuilder();
        //for (String s : parameter) {
        //    sb.append(s).append(",");
        //}
        ps.setString(i, String.join(",",parameter));
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value != null) {
            return Arrays.asList(value.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value != null) {
            return Arrays.asList(value.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value != null) {
            return Arrays.asList(value.split(","));
        }
        return null;
    }
}
