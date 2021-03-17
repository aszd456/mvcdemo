package org.test.mapper;

import org.apache.ibatis.annotations.Param;
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
}
