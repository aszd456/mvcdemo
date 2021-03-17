package org.test.mapper;

import org.test.entity.TbUser;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:49
 */
public interface TbUserMapper {

    TbUser selectUserById(int id);
}
