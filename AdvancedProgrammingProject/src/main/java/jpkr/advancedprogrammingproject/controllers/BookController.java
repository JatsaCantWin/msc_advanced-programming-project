package jpkr.advancedprogrammingproject.controllers;

import jpkr.advancedprogrammingproject.services.BookService;
import jpkr.advancedprogrammingproject.models.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/filter")
    public List<Book> getFilteredBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String title) {
        return bookService.getFilteredBooks(author, releaseYear, title);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }
}