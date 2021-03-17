package org.test.mapper;

import org.test.entity.Card;

/**
 * @author Administrator
 */
public interface CardMapper {
    Card selectCardById(Integer id);
}
