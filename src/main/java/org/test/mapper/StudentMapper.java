package org.test.mapper;

import org.test.entity.TbStudent;

public interface StudentMapper {

    TbStudent selectStudentById(int id);

    TbStudent selectStudentByClazzId(int clazzId);
}
