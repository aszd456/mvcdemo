package org.test.mapper;

import org.test.entity.Article;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:56
 */
public interface ArticleMapper {

    Article selectArticleByOrderId(int orderId);
}
