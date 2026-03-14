package ru.alexandernosevich.springhw2.controller;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springhw2.entity.Book;
import ru.alexandernosevich.springhw2.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public Page<Book> getAllBooks(
            @PageableDefault(size = 5, sort = "bookName")
            Pageable pageable
            ) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable UUID id) {
        return bookService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book book){
        return bookService.addBook(book);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable UUID id) {
        bookService.deleteBookByBookId(id);
    }

}
