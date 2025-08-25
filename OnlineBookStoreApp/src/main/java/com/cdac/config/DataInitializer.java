package com.cdac.config;

import com.cdac.entities.Book;
import com.cdac.entities.Category;
import com.cdac.repository.BookRepository;
import com.cdac.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no categories exist
        if (categoryRepository.count() == 0) {
            initializeCategories();
            initializeBooks();
        }
    }

    private void initializeCategories() {
        Category fiction = new Category();
        fiction.setCategoryName("Fiction");
        
        Category nonFiction = new Category();
        nonFiction.setCategoryName("Non-Fiction");
        
        Category sciFi = new Category();
        sciFi.setCategoryName("Science Fiction");
        
        Category mystery = new Category();
        mystery.setCategoryName("Mystery");
        
        Category romance = new Category();
        romance.setCategoryName("Romance");
        
        Category biography = new Category();
        biography.setCategoryName("Biography");
        
        Category technology = new Category();
        technology.setCategoryName("Technology");
        
        Category selfHelp = new Category();
        selfHelp.setCategoryName("Self-Help");
        
        List<Category> categories = Arrays.asList(
            fiction, nonFiction, sciFi, mystery, romance, biography, technology, selfHelp
        );
        
        categoryRepository.saveAll(categories);
        System.out.println("Categories initialized successfully!");
    }

    private void initializeBooks() {
        // Get categories for reference
        Category fiction = categoryRepository.findByCategoryName("Fiction").orElse(null);
        Category nonFiction = categoryRepository.findByCategoryName("Non-Fiction").orElse(null);
        Category sciFi = categoryRepository.findByCategoryName("Science Fiction").orElse(null);
        Category mystery = categoryRepository.findByCategoryName("Mystery").orElse(null);
        Category romance = categoryRepository.findByCategoryName("Romance").orElse(null);
        Category biography = categoryRepository.findByCategoryName("Biography").orElse(null);
        Category technology = categoryRepository.findByCategoryName("Technology").orElse(null);
        Category selfHelp = categoryRepository.findByCategoryName("Self-Help").orElse(null);

        List<Book> books = Arrays.asList(
            createBook("The Great Gatsby", "F. Scott Fitzgerald", 1078.17, 15, fiction),
            createBook("To Kill a Mockingbird", "Harper Lee", 1244.17, 20, fiction),
            createBook("1984", "George Orwell", 995.17, 12, sciFi),
            createBook("Pride and Prejudice", "Jane Austen", 1161.17, 18, romance),
            createBook("The Hobbit", "J.R.R. Tolkien", 1410.17, 25, sciFi),
            createBook("The Da Vinci Code", "Dan Brown", 1327.17, 22, mystery),
            createBook("Steve Jobs", "Walter Isaacson", 1659.17, 10, biography),
            createBook("The Art of War", "Sun Tzu", 829.17, 30, nonFiction),
            createBook("Clean Code", "Robert C. Martin", 3817.17, 8, technology),
            createBook("Atomic Habits", "James Clear", 2074.17, 35, selfHelp),
            createBook("The Alchemist", "Paulo Coelho", 1161.17, 28, nonFiction),
            createBook("Gone Girl", "Gillian Flynn", 1244.17, 16, mystery),
            createBook("The Lord of the Rings", "J.R.R. Tolkien", 2489.17, 12, sciFi),
            createBook("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1576.17, 40, fiction),
            createBook("The Hunger Games", "Suzanne Collins", 1410.17, 25, sciFi),
            createBook("The Fault in Our Stars", "John Green", 1244.17, 30, romance)
        );

        bookRepository.saveAll(books);
        System.out.println("Books initialized successfully!");
    }
    
    private Book createBook(String title, String author, double price, int stock, Category category) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStock(stock);
        book.setCategory(category);
        book.setImageUrl(null); // No image for now
        return book;
    }
} 