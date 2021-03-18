package org.test.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.test.entity.Clazz;

public interface ClazzMapper {

    Clazz selectClazzById(int id);

    @Select("select * from tb_clazz where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "students", column = "id",
                    many = @Many(
                            select = "org.test.mapper.StudentMapper.selectByClazzId",
                            fetchType = FetchType.LAZY
                    ))
    })
    Clazz selectById(int id);

    @Select("select * from tb_clazz where id=#{id}")
    Clazz findByCId(int i);
}
