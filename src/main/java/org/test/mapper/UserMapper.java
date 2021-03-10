package org.test.mapper;

import org.apache.ibatis.annotations.Param;
import org.test.entity.User;

import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/10 0:01
 */
public interface UserMapper {

    User getUserById(int id);

    int addUser(String username, String address);

    int addUser2(String username, String address);

    int addUser3(User user);

    int deleteUserById(int id);

    int updateUser(User user);

    List<User> getAllUser();

    List<User> getUserByPage(@Param("start") Integer start, @Param("count") Integer count);

    List<User> getUserByUsernameAndId(@Param("id") Integer id, @Param("name") String name);

    List<User> getUserByIds(@Param("ids")Integer[] ids);

    Integer batchInsertUser(@Param("users") List<User> users);

    Integer updateUser2(User user);

}
