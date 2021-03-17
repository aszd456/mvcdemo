package org.test.mapper;

import org.test.entity.Person;

/**
 * @author Administrator
 */
public interface PersonMapper {

    Person selectPersonById(int id);
}
