package com.cdac.service;

import com.cdac.dto.BookReqDTO;
import com.cdac.dto.BookRespDTO;
import com.cdac.entities.Book;

import java.util.List;

public interface BookService {

    List<BookRespDTO> getAllBooks();
    BookRespDTO getBookById(Long id);
    BookRespDTO saveBook(BookReqDTO bookDto);
    void deleteBook(Long id);
    List<BookRespDTO>getBookByCategoryId(Long categoryId);
    List<BookRespDTO>getBookByAuthor(String author);
    List<BookRespDTO>searchBookByTitle(String title);
}
