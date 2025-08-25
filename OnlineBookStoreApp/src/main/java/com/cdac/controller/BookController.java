package com.cdac.controller;

import com.cdac.dto.BookFormDTO;
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

    //AddBooK
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBookWithImage(@ModelAttribute BookFormDTO form) {
        try {
            MultipartFile image = form.getImage();
            byte[] imageBytes = null;

            // Validate image if provided
            if (image != null && !image.isEmpty()) {
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());

                if (originalFilename.contains("..")) {
                    return ResponseEntity.badRequest().body("Invalid file path");
                }

                String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
                List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

                if (!allowedExtensions.contains(extension)) {
                    return ResponseEntity.badRequest().body("Only JPG, JPEG, PNG, GIF files are allowed.");
                }

                imageBytes = image.getBytes();
            }

            // Convert BookFormDTO to BookReqDTO
            BookReqDTO bookDTO = new BookReqDTO();
            bookDTO.setTitle(form.getTitle());
            bookDTO.setAuthor(form.getAuthor());
            bookDTO.setPrice(form.getPrice());
            bookDTO.setStock(form.getStock());
            bookDTO.setCategoryName(form.getCategoryName());
            bookDTO.setImageUrl(imageBytes); // set image as byte[] for DB

            BookRespDTO savedBook = bookService.saveBook(bookDTO);
            return ResponseEntity.ok(savedBook);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving book: " + e.getMessage());
        }
    }

   //UpdateBooK

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @ModelAttribute BookFormDTO form) {
        try {
            MultipartFile image = form.getImage();
            byte[] imageBytes = null;

            if (image != null && !image.isEmpty()) {
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());

                if (originalFilename.contains("..")) {
                    return ResponseEntity.badRequest().body("Invalid file path");
                }

                String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
                List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

                if (!allowedExtensions.contains(extension)) {
                    return ResponseEntity.badRequest().body("Only JPG, JPEG, PNG, GIF files are allowed.");
                }

                imageBytes = image.getBytes();
            }

            BookReqDTO bookDTO = new BookReqDTO();
            bookDTO.setTitle(form.getTitle());
            bookDTO.setAuthor(form.getAuthor());
            bookDTO.setPrice(form.getPrice());
            bookDTO.setStock(form.getStock());
            bookDTO.setCategoryName(form.getCategoryName());
            bookDTO.setImageUrl(imageBytes);

            BookRespDTO updatedBook = bookService.updateBook(id, bookDTO);
            return ResponseEntity.ok(updatedBook);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating book: " + e.getMessage());
        }
    }



    //deleteBook

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<BookRespDTO>> getBooksByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(bookService.getBookByCategoryName(categoryName));
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
