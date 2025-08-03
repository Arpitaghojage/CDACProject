package com.cdac.controller;

import com.cdac.dto.BookReqDTO;
import com.cdac.dto.BookRespDTO;
import com.cdac.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<?> addBookWithImage(
            @RequestPart("book") BookReqDTO bookDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            // Validate image if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());

                if (originalFilename.contains("..")) {
                    return ResponseEntity.badRequest().body("Invalid file path");
                }

                String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
                List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

                if (!allowedExtensions.contains(extension)) {
                    return ResponseEntity.badRequest().body("Only JPG, JPEG, PNG, GIF files are allowed.");
                }

                // Convert image to byte array
                byte[] imageBytes = imageFile.getBytes();
                bookDTO.setImageUrl(imageBytes);
            }

            // Save the book
            BookRespDTO savedBook = bookService.saveBook(bookDTO);
            return ResponseEntity.ok(savedBook);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving book: " + e.getMessage());
        }
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
