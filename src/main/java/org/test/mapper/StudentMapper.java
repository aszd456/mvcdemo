package org.test.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.test.entity.Student;
import org.test.entity.TbStudent;

import java.util.List;

public interface StudentMapper {

    TbStudent selectStudentById(int id);

    TbStudent selectStudentByClazzId(int clazzId);

    @Select("select * from tb_student where clazz_id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "sex", property = "sex")
    })
    TbStudent selectByClazzId(int clazzId);

    /**
     * findByName("%张%");
     *
     * @param name
     * @return
     */
    @Select("select * from tb_student where name like #{name}")
    List<TbStudent> findByName(String name);

    @Select("select count(*) from tb_student")
    int countStundets();

    /**
     * 查询所有操作.
     * Results可以用id命名，这里命名为studentMap，在其他方法中可以直接调用
     * @return
     */
    @Select("select * from tb_student")
    @Results(id = "studentMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex")
    })
    List<TbStudent> findAll();

    /**
     * 根据id查询学生
     * @param integer
     * @return
     */
    @Select("select * from tb_student where id=#{id}")
    @ResultMap("studentMap")
    TbStudent findById(Integer integer);

    /**
     * 查询所有操作
     * @return
     */
    @Select("select * from tb_student")
    @Results(id = "studentMap2",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "clazz_id",property = "clazz",
/*select属性中填写执行方法的全限定类名加方法名，这里还开启了延迟加载（懒加载），在第四节会讲到
*/
                    one = @One(select = "org.test.mapper.ClazzMapper.findByCId",
                            fetchType = FetchType.EAGER))
    })
    List<TbStudent> findAll2();

}
