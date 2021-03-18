package org.test.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.test.entity.Employee;

import java.util.Map;

/**
 * @ClassName EmployeeDynaSqlProvider
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/18 10:25
 * @Version 1.0
 **/
public class EmployeeDynaSqlProvider {
    public String selectWhitParam(Map<String,Object> param){
        return new SQL(){
            {
                SELECT("*");
                FROM("tb_employee");
                if (param.get("id")!=null){
                    WHERE("id=#{id}");
                }
                if (param.get("userName")!=null){
                    WHERE("user_name=#{userName}");
                }
                if (param.get("loginName")!=null){
                    WHERE("login_name=#{loginName}");
                }
                if (param.get("password")!=null){
                    WHERE("password=#{password}");
                }
                if (param.get("phone")!=null){
                    WHERE("phone=#{phone}");
                }
                if (param.get("address")!=null){
                    WHERE("address=#{address}");
                }
                if (param.get("sex")!=null){
                    WHERE("sex=#{sex}");
                }
                if (param.get("age")!=null){
                    WHERE("age=#{age}");
                }
            }
        }.toString();
    }

    public String insertEmployee(Employee employee){
        return new SQL(){
            {
                INSERT_INTO("tb_employee");
                if (employee.getAge()!=null){
                    VALUES("age","#{age}");
                }
                /***其他省略**/
            }
        }.toString();
    }

    public String updateEmployee(Employee e){
        return new SQL(){
            {
                UPDATE("tb_employee");
                if(e.getAge()!=null){
                    SET("age=#{age}");
                }
                /***其他省略**/
                WHERE(" id=#{id}");
            }
        }.toString();
    }

    public String deleteEmployee(int id){
        return new SQL(){
            {
                DELETE_FROM("tb_employee");
                WHERE(" id=#{id}");
            }
        }.toString();
    }
}
