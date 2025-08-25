package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.BookReqDTO;
import com.cdac.dto.BookRespDTO;
import com.cdac.entities.Book;
import com.cdac.entities.Category;
import com.cdac.repository.BookRepository;
import com.cdac.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl  implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;


    @Override
    public List<BookRespDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookRespDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID : "+id));
        return modelMapper.map(book, BookRespDTO.class);
    }

    @Override
    public BookRespDTO saveBook(BookReqDTO bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        // Set category
        Category category = categoryRepository.findById(bookDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + bookDto.getCategoryId()));
        book.setCategory(category);

        // Explicitly set image data (in case ModelMapper doesn't map byte[] well)
        book.setImageUrl(bookDto.getImageUrl());

        // Save book
        Book savedBook = bookRepository.save(book);

        // Convert to response DTO
        return modelMapper.map(savedBook, BookRespDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID : "+id));
        bookRepository.delete(book);
    }

    @Override
    public List<BookRespDTO> getBookByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId)
                .stream()
                .map(book -> modelMapper.map(book, BookRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookRespDTO> getBookByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(book -> modelMapper.map(book, BookRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookRespDTO> searchBookByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(book -> modelMapper.map(book, BookRespDTO.class))
                .collect(Collectors.toList());
    }
}
