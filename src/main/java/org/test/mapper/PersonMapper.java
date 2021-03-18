package org.test.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.test.entity.Person;

import java.util.List;

/**
 * @author Administrator
 */
public interface PersonMapper {

    Person selectPersonById(int id);

    /**
     * @param person
     * @return
     * @SelectKey: 主键返回主键:
     * before: 在mysql数据库中, 主键的生成返回是在插入语句之后, 所以在配置文件中是配置成after, 对应此处的before属性则是false
     * keyProperty: 主键名称, 此时为实体类中的"personId"
     * resultType: 返回值类型, 主键的数据类型自然是Integer
     * statement: 插入主键的sql语句, mysql中的为"select LAST_INSERT_ID()"
     */
    @Insert("insert into tb_person(name,sex,age) values (#{name},#{sex},#{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //@SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statement = {""
    //        + "select LAST_INSERT_ID()"})
    int savePerson(Person person);

    @Delete("delete from tb_person where id=#{id}")
    int removePerson(@Param("id") int id);

    @Update("update tb_person set name=#{name},sex=#{sex},age=#{age} where id=#{id}")
    int modifyPerson(Person person);

    @Select("select * from tb_person where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age")
    })
    Person selectPersonById2(int id);

    @Select("select * from tb_person")
    List<Person> selectAllPerson();

    /**
     * fetchType = FetchType.EAGER 立即加载
     *
     * @param id
     * @return
     */
    @Select("select * from tb_person where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "card_id", property = "card",
                    one = @One(
                            select = "org.test.mapper.CardMapper.selectCardById",
                            fetchType = FetchType.EAGER)
            )
    })
    Person selectById(int id);

    /**
     * MyBatis注解开发之关联查询:
     * "@Results"注解不能用于关联查询, 关联查询必须使用"@ResultMap"注解, 并且需要依赖配置文件
     */
    //@Select("select * from person p, orders o where p.person_id = o.person_id and p.person_id = #{personId}")
    //@ResultMap("com.rl.mapper.PersonMapper.selectOrdersByPersonIdRM")
    //public Person selectOrdersByPersonId(Integer personId);
}
