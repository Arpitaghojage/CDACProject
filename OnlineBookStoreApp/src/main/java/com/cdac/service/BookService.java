package com.cdac.service;

import com.cdac.entities.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
    List<Book>getBookByCategoryId(Long categoryId);
    List<Book>getBookByAuthor(String author);
    List<Book>searchBookByTitle(String title);
}
