package org.test.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.LruCache;
import org.test.entity.TbUser;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:49
 */
//注解开启二级缓存
//@CacheNamespace(eviction = LruCache.class,flushInterval = 60000,size = 512)
public interface TbUserMapper {

    TbUser selectUserById(int id);

    @Select("select * from tb_user where id=#{id}")
    TbUser selectById(int id);

}
