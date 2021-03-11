package org.test.mapper;

import org.test.entity.Author;
import org.test.entity.Book;

public interface BookMapper {
    Book getBookById(Integer id);

    Book getBookById2(Integer id);

    Author getAuthorById(Integer id);
}
