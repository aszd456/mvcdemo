package org.test.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.test.entity.Order;
import org.test.entity.Person;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/18 0:00
 */
public interface OrderMapper {

    Order selectOrderByUserId(int userId);

    Order selectOrderById(int id);

    @Select("select * from tb_order where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "code",column = "code"),
            @Result(property = "total",column = "total"),
            @Result(property = "tbUser",column = "user_id",
            one = @One(select = "org.test.mapper.TbUserMapper.selectById",fetchType = FetchType.EAGER)),
            @Result(property = "articleList",column = "id",
            many = @Many(select = "org.test.mapper.ArticleMapper.selectByOrderId",fetchType = FetchType.LAZY))
    })
    Order selectById(int id);

}
