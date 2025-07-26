package com.cdac.controller;

import com.cdac.dto.BookReqDTO;
import com.cdac.dto.BookRespDTO;
import com.cdac.entities.Book;
import com.cdac.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookRespDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRespDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookRespDTO> addBook(@RequestBody BookReqDTO bookDto) {
        return ResponseEntity.ok(bookService.saveBook(bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookRespDTO>> getBooksByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.getBookByCategoryId(categoryId));
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookRespDTO>> getBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.getBookByAuthor(author));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookRespDTO>> searchBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBookByTitle(title));
    }

}
