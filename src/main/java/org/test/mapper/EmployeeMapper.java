package org.test.mapper;

import org.apache.ibatis.annotations.*;
import org.test.entity.Employee;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/18 1:12
 */
public interface EmployeeMapper {
    List<Employee> selectEmployeeByIdLike(HashMap<String, Object> params);

    List<Employee> selectEmployeeByLoginLike(HashMap<String, Object> params);

    List<Employee> selectEmployeeChoose(HashMap<String, Object> params);

    List<Employee> selectEmployeeLike(HashMap<String, Object> params);

    Employee selectEmployeeWithId(Integer id);

    void updateEmployeeIfNecessary(Employee employee);

    List<Employee> selectEmployeeIn(@Param("ids") List<Integer> ids);

    List<Employee> selectEmployeeLikeName(Employee employee);

    /**
     * 可以传对象
     *
     * @param params
     * @return
     */
    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "selectWhitParam")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "age", column = "age")
    })
    List<Employee> selectWithParams(HashMap<String, Object> params);

    @InsertProvider(type = EmployeeDynaSqlProvider.class, method = "insertEmployee")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertEmployee(Employee employee);

    @UpdateProvider(type = EmployeeDynaSqlProvider.class, method = "updateEmployee")
    int updateEmployee(Employee employee);

    @DeleteProvider(type = EmployeeDynaSqlProvider.class, method = "deleteEmployee")
    int deleteEmployee(int id);
}
