package ru.alexandernosevich.springhwdatajdbc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springhwdatajdbc.entity.Book;
import ru.alexandernosevich.springhwdatajdbc.repository.BookRepository;
import ru.alexandernosevich.springhwdatajdbc.service.BookService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;

    //Запрос на создание/добавление книги
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }

    //Запрос на получение списка всех книг
    @GetMapping("/allBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //Запрос на поиск книги по АйДи
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    //Запрос на удаление книги по АйДи
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable UUID id) {
        bookService.deleteBookById(id);
    }

    //Запрос на обновление данных книги
    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable UUID id, @RequestBody Book book) {
        return bookService.updateBookById(id, book);
    }
}
