package com.wj.bookmanager.service;


import com.wj.bookmanager.dao.BookDAO;
import com.wj.bookmanager.model.Book;
import com.wj.bookmanager.model.enums.BookStatusEnum;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookDAO bookMapper;

  public List<Book> getAllBooks() {
    return bookMapper.selectAll();
  }

  public int addBooks(Book book) {
    return bookMapper.addBook(book);
  }

  public void deleteBooks(int id) {
    bookMapper.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
  }

  public void recoverBooks(int id) {
    bookMapper.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
  }
}
