package org.test.mapper;

import org.test.entity.Order;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/18 0:00
 */
public interface OrderMapper {

    Order selectOrderByUserId(int userId);

    Order selectOrderById(int id);
}
