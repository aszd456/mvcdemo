package org.test.mapper;

import org.apache.ibatis.annotations.Select;
import org.test.entity.Article;

import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/17 23:56
 */
public interface ArticleMapper {

    List<Article> selectArticleByOrderId(int orderId);

    @Select("select * from tb_article where id in (select article_id from tb_item where order_id = #{id})")
    List<Article> selectByOrderId(int orderId);

}
