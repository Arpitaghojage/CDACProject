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
import java.util.Base64;

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
                .map(book -> {
                    BookRespDTO dto = modelMapper.map(book, BookRespDTO.class);
                    if (book.getImageUrl() != null) {
                        dto.setImageUrl(Base64.getEncoder().encodeToString(book.getImageUrl()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BookRespDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID : "+id));
        BookRespDTO dto = modelMapper.map(book, BookRespDTO.class);
        if (book.getImageUrl() != null) {
            dto.setImageUrl(Base64.getEncoder().encodeToString(book.getImageUrl()));
        }
        return dto;
    }

    @Override
    public BookRespDTO saveBook(BookReqDTO bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        // Set category
        Category category = categoryRepository.findByCategoryName(bookDto.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with Name : " + bookDto.getCategoryName()));
        book.setCategory(category);

        // Explicitly set image data (in case ModelMapper doesn't map byte[] well)
        book.setImageUrl(bookDto.getImageUrl());

        // Save book
        Book savedBook = bookRepository.save(book);

        // Convert to response DTO
        BookRespDTO dto = modelMapper.map(savedBook, BookRespDTO.class);
        if (savedBook.getImageUrl() != null) {
            dto.setImageUrl(Base64.getEncoder().encodeToString(savedBook.getImageUrl()));
        }
        return dto;
    }


    @Override
    public BookRespDTO updateBook(Long id, BookReqDTO bookDto) {
        // Fetch the book to update
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        // Fetch and set the category
        Category category = categoryRepository.findByCategoryName(bookDto.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + bookDto.getCategoryName()));
        existingBook.setCategory(category);

        // Update book fields
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPrice(bookDto.getPrice());
        existingBook.setStock(bookDto.getStock());

        // Only update image if it's not null
        if (bookDto.getImageUrl() != null) {
            existingBook.setImageUrl(bookDto.getImageUrl());
        }

        // Save and return updated book
        Book updatedBook = bookRepository.save(existingBook);
        BookRespDTO dto = modelMapper.map(updatedBook, BookRespDTO.class);
        if (updatedBook.getImageUrl() != null) {
            dto.setImageUrl(Base64.getEncoder().encodeToString(updatedBook.getImageUrl()));
        }
        return dto;
    }



    //deletebook

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        bookRepository.delete(book);
    }



    @Override
    public List<BookRespDTO> getBookByCategoryName(String categoryName) {
        return bookRepository.findByCategoryCategoryName(categoryName)
                .stream()
                .map(book -> {
                    BookRespDTO dto = modelMapper.map(book, BookRespDTO.class);
                    if (book.getImageUrl() != null) {
                        dto.setImageUrl(Base64.getEncoder().encodeToString(book.getImageUrl()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookRespDTO> getBookByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(book -> {
                    BookRespDTO dto = modelMapper.map(book, BookRespDTO.class);
                    if (book.getImageUrl() != null) {
                        dto.setImageUrl(Base64.getEncoder().encodeToString(book.getImageUrl()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookRespDTO> searchBookByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(book -> {
                    BookRespDTO dto = modelMapper.map(book, BookRespDTO.class);
                    if (book.getImageUrl() != null) {
                        dto.setImageUrl(Base64.getEncoder().encodeToString(book.getImageUrl()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
